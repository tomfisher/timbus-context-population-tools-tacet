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

package squirrel.model.io;

import squirrel.model.Annotation;
import squirrel.model.SensorDatum;

public class SensorAnnotationTuple {
    SensorDatum sensorDatum;
    Annotation[] annotation;
    public SensorDatum getSensorDatum() {
        return sensorDatum;
    }
    public void setSensorDatum(SensorDatum sensorDatum) {
        this.sensorDatum = sensorDatum;
    }
    public Annotation[] getAnnotation() {
        return annotation;
    }
    public void setAnnotation(Annotation[] annotation) {
        this.annotation = annotation;
    }
}
