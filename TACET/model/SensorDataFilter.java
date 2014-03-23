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

import squirrel.util.Range;

public class SensorDataFilter {
    private SensorDatum[] data;

    protected SensorDataFilter(SensorDatum[] data) {
        this.data = data;
    }

    public Range getBounds() {
        // TODO stimmt .getTimestamp()+1 ... alternative zB: Range als Feld
        if (data == null || data.length < 1) return null;
        return new Range(data[0].getTimestamp(), data[data.length-1].getTimestamp()+1);
    }

    public SensorDatum[] getData() {
        return data;
    }
}
