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
import java.util.Collections;
import java.util.Iterator;

import squirrel.model.io.Reader;
import squirrel.util.Range;

public class SparseDataModel implements SensorDataModel {

    private ArrayList<SensorDatum> data;
    private Range all;
    private Reader reader;

    public SparseDataModel(Reader reader) {
        all = new Range(reader.startTimeStamp(), reader.endTimeStamp());
        data = new ArrayList<>(reader.readSensorData(all));
        this.reader = reader;
    }

    @Override
    public SensorDatum getSensorDatum(long timestamp) {
        int idx = getNextIndex(timestamp);
        return  data.get(idx).getTimestamp() == timestamp ? data.get(idx) : null;
    }

    private int getNextIndex(long timestamp) {
        return getNextIndex(timestamp, 0, data.size());
    }

    private int getNextIndex(long timestamp, int a, int b) {
        int m = (a+b) / 2;

        while (a < b) {
            if (data.get(m).getTimestamp() < timestamp) {
                a = m+1;
            } else if (data.get(m).getTimestamp() > timestamp) {
                b = m;
            } else {
                return m;
            }
            m = (a+b) / 2;
        }
        return a;
    }

    @Override
    public double getSensorValue(long timestamp, int track) {
        SensorDatum sd = this.getSensorDatum(timestamp);
        if (sd != null)
            return sd.getValues()[track];
        return Double.NaN;
    }

    @Override
    public Iterable<SensorDatum> getSensorDataRange(Range range, final int rate) {
        if (range.getStart() > all.getEnd() || range.getEnd() < all.getStart())
            return Collections.emptyList();
        if (range.getStart() < all.getStart()) range = new Range(all.getStart(), range.getEnd());
        if (range.getEnd() > reader.endTimeStamp()) range = new Range(range.getStart(), all.getEnd());

        final Range finalRange = range;
        final int start = getNextIndex(range.getStart());
        return new Iterable<SensorDatum>() {
            @Override
            public Iterator<SensorDatum> iterator() {
                return new Iterator<SensorDatum>() {
                    int current = start;
                    @Override
                    public boolean hasNext() {
                        return current < data.size() && data.get(current).getTimestamp() < finalRange.getEnd();
                    }

                    @Override
                    public SensorDatum next() {
                        int oldCurrent = current;
                        current = getNextIndex(
                            data.get(current).getTimestamp() + rate, current, data.size());
                        return data.get(oldCurrent);
                    }

                    @Override
                    public void remove() {
                        throw new UnsupportedOperationException("remove() is not supported by this iterator.");
                    }};
            }
        };
    }

}
