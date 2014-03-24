/* 
 * Copyright 2013-2014 TECO - Karlsruhe Institute of Technology.
 * 
 * This file is part of TACET.
 * 
 * TACET is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 * 
 * TACET is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 * 
 * You should have received a copy of the GNU General Public License
 * along with TACET.  If not, see <http://www.gnu.org/licenses/>.
 */

package squirrel.model;

import java.lang.Thread.State;
import java.math.BigInteger;
import java.util.ArrayList;

import squirrel.util.Range;
import squirrel.util.CircularList;

public class SensorDataFilterManager {
    private int pannCacheSize;
    private int zoomCacheSize;
    private int filterSize;
    private double zoomAccuracy;
    private SensorDataModel dataModel;

    private CircularList<CircularList<SensorDataFilter>> cache;
    private CircularList<Integer> zoomList;
    private boolean initialized;

    private long minTS;
    private long maxTS;

    private Thread updateThread;

    /**
     * @param pannCacheSize number of items in panning direction
     * @param zoomCacheSize number of items in zoom direction
     * @param zoomAccuracy
     * @param filterSize items in one SensorDataFilter
     * @param dataModel the SensorDataModel from which data should be taken
     */
    public SensorDataFilterManager(int pannCacheSize, int zoomCacheSize, double zoomAccuracy,
            int filterSize, SensorDataModel dataModel, Range minMax) {
        this.pannCacheSize = pannCacheSize;
        this.zoomCacheSize = zoomCacheSize;
        this.filterSize = filterSize;
        this.zoomAccuracy = zoomAccuracy;
        this.dataModel = dataModel;
        initialized = false;
        this.minTS = minMax.getStart();
        this.maxTS = minMax.getEnd();
    }

    /**
     * get a SensorDataFilter. Adjusts cache around data if necessary.
     * @param range the desired range of data
     * @param zoom the desired zoom, distance between elements
     * @return a SensorDataFilter
     */
    public SensorDataFilter getFilteredSensorData(Range range, final int zoom) {
        // TODO if (range.getDistance() > zoom*filterSize) throw new Exception("Range too long for this zoom");

        System.out.println("getF (" + range.getStart() + ", " + range.getEnd() + ") with zoom " + zoom);
        System.out.println("filter :" + filterSize);

        System.out.println(minTS + " " + maxTS);
        if (range.getStart() < minTS) {
            range = new Range(minTS, range.getEnd() > minTS ? range.getEnd() : minTS + 1);
            System.out.println("changed range begin to (" + range.getStart() + ", " + range.getEnd());
        }
        if (range.getEnd() > maxTS-zoom) {
            range = new Range(range.getStart() < maxTS-zoom ? range.getStart() : maxTS-zoom-1, maxTS-zoom);
            System.out.println("changed range end to (" + range.getStart() + ", " + range.getEnd());
        }
        final Range frange = new Range(range.getStart(), range.getEnd());

        if (updateThread == null) {
            //data hasn't been initialised once
            SensorDataFilter sdf = loadFilter(stretch(range, zoom), zoom);
            updateThread = new Thread(new Runnable() {
                @Override
                public void run() {
                    initialize(frange, zoom);
                }
            });
            updateThread.start();
            return sdf;
        }

        if (initialized == false) {
            //data is beeing initialised
            //current solution: let initialisation continue and don't adjust cache according
            //to current parameters
            SensorDataFilter sdf = loadFilter(stretch(range, zoom), zoom);
            return sdf;
        }

        if (updateThread.getState() == State.RUNNABLE) {
            updateThread.interrupt();
            try {
                updateThread.join();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }

        //updateThread isn't doing anything. data can be fetched and if
        //necessary updated

        if (zoom/zoomList.get(0) < zoomAccuracy || zoom > zoomList.getLast()) {
            System.out.println("new zooms");
            //zoom not cached
            SensorDataFilter sdf = loadFilter(stretch(range, zoom), zoom);
            updateThread = new Thread(new Runnable() {
                @Override public void run() {
                    updateZooms(frange, zoom);
                }
            });
            updateThread.start();
            return sdf;
        }

        int i = getZoomIndex(zoom);

        //zoom is in cache, now check if range is cached
        final CircularList<SensorDataFilter> list = cache.get(i);
        SensorDataFilter sdf = null;

        if (list == null || list.size() < 1) {
            //range is not cached
            sdf = loadFilter(stretch(range, zoom), zoom);
            if (sdf == null || sdf.getBounds() == null) return null;
            updateThread = new Thread(new Runnable() {
                @Override public void run() {
                    adjust(zoom, frange);
                }
            });
            updateThread.start();
        }

        //compute range of cached data
        int m = getCachedStartIndex(list);
        int M = getCachedEndIndex(list);

        if (m == -1 || M == -1) {
            return null;
        }
        Range cached = new Range(list.get(m).getBounds().getStart(), list.get(M).getBounds().getEnd());

        if (!cached.contains(range)) {
            //range is not cached
            System.out.println("range not cached");
            sdf = loadFilter(stretch(range, zoom), zoom);
            updateThread = new Thread(new Runnable() {
                @Override public void run() {
                    adjust(zoom, frange);
                }
            });
            updateThread.start();
        }
        else {
            //range is cached
            final int j = (int) ((range.getStart() - cached.getStart()) / (zoom * filterSize / 2)) + m;
            // TODO prüfen, ob Daten evtl verrutscht sind. Ist das möglich?
            sdf = list.get(j);
            //center cache around range
            if (j > list.size()/2) {
                updateThread = new Thread(new Runnable() {
                    @Override public void run() {
                        moveRight(zoom, j - list.size()/2);
                    }
                });
                updateThread.start();

            }
            else if (j < list.size()/2) {
                updateThread = new Thread(new Runnable() {
                    @Override public void run() {
                        moveLeft(zoom, list.size()/2 - j);
                    }
                });
                updateThread.start();

            }
        }
        return sdf;
    }

    private synchronized int getCachedStartIndex(CircularList<SensorDataFilter> list) {
        int m = -1;
        for (int it = 0; it < pannCacheSize; ++it) {
            if (list.get(it).getBounds() != null) {
                m = it;
                break;
            }
        }
        return m;
    }

    private synchronized int getCachedEndIndex(CircularList<SensorDataFilter> list) {
        int M = -1;
        for (int it = pannCacheSize - 1; it >= 0; --it) {
            if (list.get(it).getBounds() != null) {
                M = it;
                break;
            }
        }
        return M;
    }

    private int getZoomIndex(int zoom) {
        int i = 0;
        for (; i < zoomCacheSize; ++i) {
            if (zoomList.get(i) >= zoom) break;
        }
        return i;
    }

    private synchronized void updateZooms(Range range, int zoom) {
        int i = 0;
        int tz = zoomList.get(0);
        while (zoom/tz < zoomAccuracy) {
            tz *= zoomAccuracy;
            --i;
        }
        tz = zoomList.getLast();
        while (tz < zoom) {
            tz /= zoomAccuracy;
            ++i;
        }
        if (2*zoomCacheSize <= i+1 || -i > zoomCacheSize) {
            //zoom so far away from cached zooms that we just reinitialize everything
            initialize(range, zoom);
        }
        else if (i > 0) {
            //add bigger zooms
            int zoomj = (int) (zoomList.getLast() / zoomAccuracy);
            if (zoomj == zoomList.getLast()) ++zoomj;
            do {
                cache.pushBack(new CircularList<SensorDataFilter>(pannCacheSize));
                zoomList.pushBack(zoomj);
                adjust(zoomj, range);
                zoomj = (int) (zoomList.getLast() / zoomAccuracy);
                if (zoomj == zoomList.getLast()) ++zoomj;
            } while (zoomList.getLast() < zoom && !Thread.interrupted());
        }
        else {
            //add smaller zooms

            int zoomj = (int) (zoomList.get(0) * zoomAccuracy);
            if (zoomj == zoomList.get(0)) --zoomj;
            do {
                cache.pushFront(new CircularList<SensorDataFilter>(pannCacheSize));
                zoomList.pushFront(zoomj);
                adjust(zoomj, range);
                zoomj = (int) (zoomList.get(0) * zoomAccuracy);
                if (zoomj == zoomList.get(0)) --zoomj;
            } while (zoom/zoomList.get(0) < zoomAccuracy && !Thread.interrupted());
        }
    }

    private synchronized Range stretch(Range range, int zoom) {
        //stretch range so it fits filterSize

        // This is the BigInteger code with longs:
        // long m = (range.getStart() + range.getEnd()) / 2;
        // if (m - zoom*filterSize/2 < 0) m -= (m - zoom*filterSize/2);
        // return new Range(m - zoom*filterSize/2, m + zoom*filterSize/2);

        BigInteger zoomL = BigInteger.valueOf(zoom);
        BigInteger filterSizeL = BigInteger.valueOf(filterSize);
        BigInteger zfS2 = zoomL.multiply(filterSizeL).divide(BigInteger.valueOf(2));
        BigInteger m = BigInteger.valueOf(range.getStart()).add(BigInteger.valueOf(range.getEnd())).divide(BigInteger.valueOf(2));
        if (m.subtract(zfS2).compareTo(BigInteger.valueOf(0)) == -1)
            m = m.subtract(zfS2);
        System.out.println("m=   "+m+"\nzf2L="+(zfS2));
        return new Range(m.subtract(zfS2).longValue(), m.add(zfS2).longValue());
    }

    private synchronized void moveLeft(int zoom, int n) {
        int i = getZoomIndex(zoom);
        while (--n >= 0  && !Thread.interrupted()) {
            long start = cache.get(i).get(getCachedStartIndex(cache.get(i))).getBounds().getStart() - (zoom * filterSize / 2);
            if (start < 0) return;
            long end = start + zoom * filterSize;
            cache.get(i).pushFront(loadFilter(new Range(start, end), zoom));
        }
    }

    private synchronized void moveRight(int zoom, int n) {
        int i = getZoomIndex(zoom);
        while (--n >= 0 && !Thread.interrupted()) {
            long start = cache.get(i).get(getCachedEndIndex(cache.get(i))).getBounds().getStart() + (zoom * filterSize / 2);
            long end = start + zoom * filterSize;
            // TODO feststellen, ob man hier abbrechen kann
            cache.get(i).pushBack(loadFilter(new Range(start, end), zoom));
        }
    }

    private synchronized void adjust(int zoom, Range range) {
        // TODO muss wirklich alles neu geladen werden? [Faktor 2 einsparen]
        System.out.println("adjust (" + range.getStart() + ", " + range.getEnd() + ") with zoom " + zoom);
        int i = getZoomIndex(zoom);
        cache.set(i, new CircularList<SensorDataFilter>(pannCacheSize));

        long rangelengthi = filterSize * zoom;
        long minstart = range.getStart() - pannCacheSize / 2 * rangelengthi / 2;
        if (minstart < 0) minstart = 0;
        System.out.println("rangelengthi " + rangelengthi + " minstart " + minstart);
        for (int j = 0; j < pannCacheSize; ++j) {
            Range rangeij = new Range(
                    minstart + j * rangelengthi / 2,
                    minstart + j * rangelengthi / 2 + rangelengthi);
            cache.get(i).pushBack(loadFilter(rangeij, zoom));
        }
    }

    /**
     * initializes the cache along specified data with certain options for the cache.
     * @param range the range around which cache will be centered
     * @param zoom the zoom around which cache will be centered
     * @param pannCS number of items in panning direction
     * @param zoomCS number of items in zoom direction
     * @param zoomSt zoomStep = zoom in cache[i+1] - zoom in cache[i]
     */
    public synchronized void initialize(Range range, int zoom, int pannCS, int zoomCS, int zoomA) {
        pannCacheSize = pannCS;
        zoomCacheSize = zoomCS;
        zoomAccuracy = zoomA;
        initialize(range, zoom);
    }

    /**
     * initializes the cache along specified data.
     * @param range the range around which cache will be centered
     * @param zoom the zoom around which cache will be centered
     */
    public synchronized void initialize(Range range, int zoom) {
        // TODO Code sparen, indem adjust aufgerufen wird?
        cache = new CircularList<>(zoomCacheSize);
        zoomList = new CircularList<>(zoomCacheSize);

        int zoom_0 = zoom;
        for (int i = 0; i < zoomCacheSize / 2 && zoom_0 > 1; ++i) zoom_0 = (int) (zoom_0 * zoomAccuracy);
        int zoomi = zoom_0;
        for (int i = 0; i < zoomCacheSize; ++i) {
            cache.pushBack(new CircularList<SensorDataFilter>(pannCacheSize));
            zoomList.pushBack(zoomi);

            long rangelengthi = filterSize * zoomi;
            long minstart = range.getStart() - pannCacheSize / 2 * rangelengthi / 2;
            if (minstart < 0) minstart = 0;

            System.out.println("initialize ... load (" + minstart + ", ...) with zoom " + zoomi);

            for (int j = 0; j < pannCacheSize; ++j) {
                Range rangeij = new Range(
                        minstart + j * rangelengthi / 2,
                        minstart + j * rangelengthi / 2 + rangelengthi);
                cache.get(i).pushBack(loadFilter(rangeij, zoomi));
            }

            zoomi = (int) (zoomi / zoomAccuracy);
            if (zoomList.getLast() == zoomi) ++zoomi;
        }
        initialized = true;
    }

    private synchronized SensorDataFilter loadFilter(Range range, int zoom) {
        System.out.println("loadFilter (" + range.getStart() + ", " + range.getEnd() + ") with zoom " + zoom);
        ArrayList<SensorDatum> al = new ArrayList<>();
        for (SensorDatum s : dataModel.getSensorDataRange(range, zoom)) {
            al.add(s);
        }
        System.out.println("SIZE: " + al.size());
        return new SensorDataFilter(al.toArray(new SensorDatum[al.size()]));
    }

    public int getPannCacheSize() {
        return pannCacheSize;
    }

    public void setPannCacheSize(int pannCacheSize) {
        this.pannCacheSize = pannCacheSize;
        initialized = false;
    }

    public int getZoomCacheSize() {
        return zoomCacheSize;
    }

    public void setZoomCacheSize(int zoomCacheSize) {
        this.zoomCacheSize = zoomCacheSize;
        initialized = false;
    }

    public double getZoomAccuracy() {
        return zoomAccuracy;
    }

    public void setZoomAccuracy(double zoomAccuracy) {
        if (zoomAccuracy == this.zoomAccuracy) return;
        this.zoomAccuracy = zoomAccuracy;
        initialized = false;
    }

    public int getFilterSize() {
        return filterSize;
    }

    public void setFilterSize(int filterSize) {
        if (filterSize == this.filterSize) return;
        this.filterSize = filterSize;
        initialized = false;
    }

    public SensorDataModel getDataModel() {
        return dataModel;
    }

    public void setDataModel(SensorDataModel dataModel) {
        this.dataModel = dataModel;
        initialized = false;
    }
}
