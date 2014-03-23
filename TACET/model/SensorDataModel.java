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

public interface SensorDataModel {
    public SensorDatum getSensorDatum(long timestamp);

    /**
     * Returns a SensorValue for the given timestamp and track. If timestamp is
     * invalid, NaN is returned.
     * @param timestamp timestamp of sensor value
     * @param track track of sensor value
     * @return sensor value if it exists, NaN if not.
     */
    public double getSensorValue(long timestamp, int track);

    public Iterable<SensorDatum> getSensorDataRange(Range range, int rate);
}
