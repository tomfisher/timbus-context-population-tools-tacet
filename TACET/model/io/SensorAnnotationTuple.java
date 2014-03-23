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
