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
