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

import java.util.ArrayList;
import java.util.Iterator;

import squirrel.model.io.Reader;
import squirrel.util.Range;

public class HashMapModel implements SensorDataModel {

    private int capacity;
    private int rangeSize;
    private int delta;
    private ArrayList<ArrayList<SensorDatum>> map;

    private Reader reader;

    /**
     * @param cap Capacity (Number of slots in HashMap)
     * @param rangeS range size (elements in one slot)
     * @param approxDelta approximated/estimated delta between timestamps
     * @param reader Reader
     */
    public HashMapModel(int cap, int rangeS, int approxDelta, Reader reader) {
        capacity = cap;
        rangeSize = rangeS;
        delta = approxDelta <= 0 ? 1 : approxDelta;
        this.reader = reader;
        map = new ArrayList<>();
        for (int i = 0; i < capacity; ++i) {
            map.add(null);
        }
    }

    @Override
    public SensorDatum getSensorDatum(long timestamp) {
        int h = hash(timestamp);
        int o = offset(timestamp);
        if (o == -1) {
            System.out.println("needs to read from file"); // DEBUG
            readDataFromDisc(timestamp, h);
            o = offset(timestamp);
        }

        if (o < 0)
            return null;
        return map.get(h).get(o);
    }

    protected void readDataFromDisc(long timestamp, int h) {
        map.set(h, new ArrayList<SensorDatum>());
        for (SensorDatum s : reader.readSensorData(new Range(rangeStart(timestamp),
                rangeEnd(timestamp)))) {
            map.get(hash(s.getTimestamp())).add(s);
        }
    }

    @Override
    public double getSensorValue(long timestamp, int track) {
        SensorDatum sd = getSensorDatum(timestamp);
        return (sd != null) ? sd.getValues()[track] : Double.NaN;
    }

    @Override
    public Iterable<SensorDatum> getSensorDataRange(Range range, int rate) {
        if (range.getStart() > reader.endTimeStamp() || range.getEnd() < reader.startTimeStamp())
            return new ArrayList<SensorDatum>();
        if (range.getStart() < reader.startTimeStamp()) range = new Range(reader.startTimeStamp(), range.getEnd());
        if (range.getEnd() > reader.endTimeStamp()) range = new Range(range.getStart(), reader.endTimeStamp());

        ArrayList<SensorDatum> al = new ArrayList<>();

        long time = range.getStart();
        int hash = hash(time);
        if (!neighboorsExist(time)) {
            readDataFromDisc(time, hash(time));
        }
        int offset = offset(time);
        while (offset < 0) {
            ++time;
            offset = offset(time);
        }
        for (; time < range.getEnd(); time += rate) {
            if (hash != hash(time)) {
                offset = 0;
                hash = hash(time);
                if (!neighboorsExist(time)) {
                    readDataFromDisc(time, hash(time));
                }
            }

            while (offset < map.get(hash).size()-1 && map.get(hash).get(offset).getTimestamp() < time) ++offset;
            if (offset > 0 && map.get(hash).get(offset).getTimestamp() > time) -- offset;

            SensorDatum sd = map.get(hash).get(offset);
            if (al.size() == 0 || sd != al.get(al.size()-1)) al.add(sd);
        }

        return al;
        //return new SensorIterator(range, rate);
    }

    protected int hash(long key) {
        return (int) ((key / (rangeSize * (long)delta)) % capacity);
    }

    protected long rangeStart(long key) {
        return (key / (rangeSize * (long)delta)) * (rangeSize * (long)delta);
    }

    protected long rangeEnd(long key) {
        return (key / (rangeSize * (long)delta) + 1) * (rangeSize * (long)delta);
    }

    protected int offset(long key) {
        if (map.get(hash(key)) == null) {
            readDataFromDisc(key, hash(key));
            if (map.get(hash(key)) == null)
                return -1;
        }
        return findOffset(key, hash(key), 0, map.get(hash(key)).size());
    }

    protected int findOffset(long key, int h, int a, int b) {
        ArrayList<SensorDatum> list = map.get(h);

        int m = (b - a) / 2 + a;
        if (m >= list.size() || b < 0)
            return -1;
        if (a > b)
            return -2;

        SensorDatum sensorDatum = list.get(m);

        if (sensorDatum.getTimestamp() > key)
            return findOffset(key, h, a, m - 1);
        if (sensorDatum.getTimestamp() < key)
            return findOffset(key, h, m + 1, b);
        if (sensorDatum.getTimestamp() == key)
            return m;
        return -2;
    }

    protected boolean neighboorsExist(long key) {
        // determine wether key belongs to currently loaded data or no not
        int h = hash(key);
        if (map.get(h) == null || map.get(h).size() == 0)
            return false;
        long val = map.get(h).get(0).getTimestamp();

        return rangeStart(key) == rangeStart(val);
    }

    class SensorIterator implements Iterator<SensorDatum>, Iterable<SensorDatum> {

        protected Range range;
        protected long rate;
        protected int hash;
        protected int offset;
        protected long time;

        public SensorIterator(Range _range, long _rate) {
            range = _range;
            rate = _rate;
            time = range.getStart();
            if (time < reader.startTimeStamp())
                time = reader.startTimeStamp();
            getSensorDatum(time);
            hash = hash(time);
            offset = offset(time);
        }

        @Override
        public boolean hasNext() {
            return time < range.getEnd()-rate && time < reader.endTimeStamp()-rate;
        }

        @Override
        public SensorDatum next() {
            while (offset < 0) {
                time++;
                hash = hash(time);
                offset = offset(time);
                if (offset == map.get(hash).size()) {
                    time++;
                    hash = hash(time);
                    offset = 0;
                }
            }
            SensorDatum elem = map.get(hash).get(offset);

            time += rate;
            offset++;
            if (map.get(hash).size() == offset) {
                hash = hash(time);
                offset = 0;
            }
            if (offset == 0 && !neighboorsExist(time)) {
                readDataFromDisc(time, hash(time));
            }
            while (time <= map.get(hash).get(offset).getTimestamp()) time += rate;

            if (hash != hash(time)) {
                offset = 0;
                if (!neighboorsExist(time)) {
                    readDataFromDisc(time, hash(time));
                }
            }

            hash = hash(time);
            ArrayList<SensorDatum> list = map.get(hash);

            long tstamp = list.get(offset).getTimestamp();
            while (offset < list.size() - 1 &&list.get(offset).getTimestamp() < time) {
                tstamp = list.get(offset).getTimestamp();
                offset++;
            }
            if (time - tstamp < list.get(offset).getTimestamp() - time && offset > 0)
                --offset;

            return elem;
        }

        @Override
        public void remove() {
            throw new UnsupportedOperationException();
        }

        @Override
        public Iterator<SensorDatum> iterator() {
            return this;
        }

    }
}
