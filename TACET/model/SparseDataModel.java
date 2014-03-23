/*
 * Copyright 2013 TecO - Karlsruhe Institute of Technology
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
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
