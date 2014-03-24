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

package squirrel.view;

import org.jfree.data.DomainOrder;
import org.jfree.data.general.AbstractDataset;
import org.jfree.data.xy.XYDataset;

import squirrel.model.ModelFacade;
import squirrel.model.SensorDataFilter;
import squirrel.model.SensorDatum;
import squirrel.util.Range;

/**
 * This adapter fits a {@link SensorDataFilter} into the {@link XYDataset}
 * interface.<p>
 * The {@code SensorDataFilter} holds data for many sensors, the
 * XYDataset on the other hand represents data for only one sensor.  Which
 * sensor this is exactly, is specified by {@code sensorIdx} in the constructor.
 */
public class SensorDataFilterAdapter extends AbstractDataset implements XYDataset {

    private SensorDataFilter sdf;
    private ModelFacade model;
    private Range visibleExcerpt;

    private int sensorIdx;

    private int startIndex;
    private int endIndex;
    private boolean exceedsMinBound;
    private boolean exceedsMaxBound;
    private boolean completelyWrong;

    public SensorDataFilterAdapter(ModelFacade model, int sensorIdx, Range visibleExcerpt) {
        this.model = model;
        this.sensorIdx = sensorIdx;
        setVisibleExcerpt(visibleExcerpt);
    }

    public void setVisibleExcerpt(Range excerpt) {
        this.visibleExcerpt = excerpt;
        validateSDF();
        setIndices();
        fireDatasetChanged();
    }

    public Range getVisibleExcerpt() {
        return this.visibleExcerpt;
    }

    public void setSensorDataFilter(SensorDataFilter sdf) {
        this.sdf = sdf;
        super.fireDatasetChanged();
    }

    public SensorDataFilter getSensorDataFilter() {
        return this.sdf;
    }

    private void validateSDF() {
        if (sdf == null || sdf.getData().length == 0 ||
                !sdf.getBounds().contains(visibleExcerpt) ||
                visibleExcerpt.getDistance()*3 < sdf.getBounds().getDistance()) {
            // a new sdf needs to be loaded
            int zoom = (int) (visibleExcerpt.getDistance() * 3 / model.getFilterSize());
            if (zoom == 0) zoom = 1;
            if (zoom < 0) zoom = Integer.MAX_VALUE;
            if (visibleExcerpt.getDistance() * 3 % model.getFilterSize() != 0) ++zoom;
            System.out.println("Zoom = " + zoom);
            sdf = model.getFilteredSensorData(visibleExcerpt, zoom);
            // check bounds
            if (sdf == null || sdf.getBounds() == null) exceedsMaxBound = exceedsMinBound = true;
            else {
                exceedsMinBound = sdf.getBounds().getStart() > visibleExcerpt.getStart();
                exceedsMaxBound = sdf.getBounds().getEnd() < visibleExcerpt.getEnd();
                completelyWrong = sdf.getBounds().getStart() > visibleExcerpt.getEnd()
                        || sdf.getBounds().getEnd() < visibleExcerpt.getStart();
            }
        } else {
            exceedsMaxBound = exceedsMinBound = false;
        }
    }

    private void setIndices() {
        if (completelyWrong) {
            startIndex = -1;
            endIndex = 1;
            return;
        }
        setStartIndex();
        setEndIndex();
    }

    private void setStartIndex() {
        if (exceedsMinBound) {
            startIndex = -1;
            return;
        }
        // binary search
        int a = 0, b = sdf.getData().length;
        int m = (int) (b * (visibleExcerpt.getStart() - sdf.getBounds().getStart()) / sdf.getBounds().getDistance());
        while (a < b) {
            if (sdf.getData()[m].getTimestamp() < visibleExcerpt.getStart()) {
                a = m+1;
            }
            else if (sdf.getData()[m].getTimestamp() > visibleExcerpt.getStart()) {
                b = m;
            }
            else {
                a = b = m;
            }
            m = (a+b)/2;
        }
        startIndex = a;
    }

    private void setEndIndex() {
        if (sdf == null) {
            endIndex = -1;
            return;
        }
        if (exceedsMaxBound) {
            endIndex = sdf.getData().length +1;
            return;
        }
        // binary search
        int a = 0;
        int b = sdf.getData().length;
        //int m = (int) (b * (visibleExcerpt.getEnd() - sdf.getBounds().getStart()) / sdf.getBounds().getDistance());
        int m = (a+b)/2;
        while (a < b) {
            if (sdf.getData()[m].getTimestamp() < visibleExcerpt.getEnd()) {
                a = m+1;
            }
            else if (sdf.getData()[m].getTimestamp() > visibleExcerpt.getEnd()) {
                b = m;
            }
            else {
                a = b = m;
            }
            m = (a+b)/2;
        }
        endIndex = a;
    }

    private SensorDatum get(int item) {
        if (item + startIndex < 0) {
            // dummy element outside of actually existing data
            SensorDatum d = new SensorDatum();
            d.setTimestamp(visibleExcerpt.getStart());
            d.setValues(new double[sensorIdx+1]);
            d.getValues()[sensorIdx] = 0;
            return d;
        }
        if (completelyWrong || item + startIndex >= sdf.getData().length) {
            // dummy element outside of actually existing data
            SensorDatum d = new SensorDatum();
            d.setTimestamp(visibleExcerpt.getEnd());
            d.setValues(new double[sensorIdx+1]);
            d.getValues()[sensorIdx] = 0;
            return d;
        }
        return sdf.getData()[item + startIndex];
    }

    @Override
    public Number getX(int series, int item) {
        return get(item).getTimestamp();
    }

    @Override
    public double getXValue(int series, int item) {
        return get(item).getTimestamp();
    }

    @Override
    public Number getY(int series, int item) {
        return get(item).getValues()[sensorIdx];
    }

    @Override
    public double getYValue(int series, int item) {
        return get(item).getValues()[sensorIdx];
    }

    @Override
    public int getSeriesCount() {
        return 1; // there is only one graph per chart
    }

    @Override
    public Comparable getSeriesKey(int series) {
        return "Sensor Data"; // See manual p. 714 for more info
    }

    @Override
    public int indexOf(Comparable seriesKey) {
        // See manual p. 714 for more info
        return seriesKey == null ? -1 : 0;
    }

    @Override
    public DomainOrder getDomainOrder() {
        return DomainOrder.ASCENDING;
    }

    @Override
    public int getItemCount(int series) {
        return endIndex - startIndex;
    }
}
