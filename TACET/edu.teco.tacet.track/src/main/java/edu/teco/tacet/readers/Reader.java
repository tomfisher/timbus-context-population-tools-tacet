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

package edu.teco.tacet.readers;

import edu.teco.tacet.track.Annotation;
import edu.teco.tacet.track.Datum;
import edu.teco.tacet.track.Range;

public interface Reader {

    /**
     * Return all {@link Datum} instances contained in {@code range} on track with id
     * {@code trackId} with a resolution given by {@code resolution}.
     *
     * @param trackId the id identifying the track the data belongs to.
     * @param range for each {@link Datum} {@code d} returned by this method the following is true:
     *        {@code range.contains(d.timestamp)}.
     * @param resolution for every two consecutive {@link Datum} instances {@code d1} and {@code d2}
     *        returned by this method the following is true:
     *        {@code d2.timestamp - d1.timestamp >= resolution}.
     * @return the {@link Datum} instances requested sorted ascending by their timestamp.
     */
    Iterable<Datum> readSensorData(long trackId, Range range, long resolution);

    /**
     * Return all {@link Annotation} instances whose ranges overlap {@code range} on track with id
     * {@code trackId}.
     *
     * @param trackId the id identifying the track the annotations belong to.
     * @param range for each {@link Annotation} {@code a} returned by this method the following is
     *        true: {@code range.overlapsWith(a.getRange())}.
     * @return the {@code Annotation} instances requested sorted ascending by
     *         {@code Annotation.getStart()}.
     */
    Iterable<Annotation> readAnnotations(long trackId, Range range);

    /**
     * Return the id of this reader's {@link Datasource}.
     * @return
     */
    long getSourceId();

    /**
     * The average Difference between the timestamps.
     * @return difference between the timestamps
     */
    double getAverageDistance(long trackId);

    /**
     * Return a range {@code r} containing every {@link Datum} and {@link Annotation} instance
     * offered by this {@code Reader} for the track with id {@code trackId}. Note: it is possible
     * for this range to not contain any data, in case the track in question does not contain any
     * data. In this case {@code r.getStart()} and {@code r.getEnd()} return arbitrary values.
     *
     * @param trackId the id identifying the track the returned range refers to.
     * @return {@code Range r} with {@code r = new Range(minTs, maxTs + 1)} where {@code minTs} is
     *         the smallest of all {@link Datum#timestamp} or {@link Annotation#getStart()}, and
     *         where {@code maxTs} is the largest of all {@link Datum#timestamp} or
     *         {@link Annotation#getEnd()}.
     */
    Range getCoveredRange(long trackId);

}
