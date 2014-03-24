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
