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

import java.util.List;

import squirrel.model.Annotation;
import squirrel.model.AnnotationFactory;
import squirrel.model.SensorDatum;
import squirrel.util.Range;

public interface Reader {

    /**
     * Returns a List of {@code SensorDatum} in the {@code range}.
     *
     * @param range contains start and end point of interval
     * @return List of Sensordata
     */
    public abstract List<SensorDatum> readSensorData(Range range);

    /**
     * Reads the annotations in the interval of range and returns a list of
     * {@code Annotation[]}. One Annotation Array represents all annotations for
     * one Sensordata.
     *
     * @param range contains start and end point of interval
     * @return List of Annotation Arrays
     */
    public abstract List<Annotation[]> readAnnotations(Range range);

    /**
     * Assign AnnotationFactory.
     *
     * @param aF AnnotationFactory
     */
    public abstract void setaF(AnnotationFactory aF);

    /**
     * Reads the file in a give range
     * @param range the range to read the file
     * @return a sensorAnnotationTuple of the lines of the file
     */
    public abstract Iterable<SensorAnnotationTuple> readData(Range range);

    /**
     * The average Difference between the timestamps
     * @return difference between the timestamps
     */
    public abstract double averageDistance();

    /**
     * The first timestamp
     * @return the first timestamp
     */
    public abstract long startTimeStamp();

    /**
     * The last timestamp
     * @return the last timestamp
     */
    public abstract long endTimeStamp();



}
