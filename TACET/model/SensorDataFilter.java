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
