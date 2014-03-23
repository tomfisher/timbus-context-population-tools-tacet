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

import java.awt.Color;
import java.io.Serializable;

import squirrel.util.Range;

/**
 * An abstract annotation, which is untyped, but knows start and end of
 * annotation.
 *
 * @author Olivier
 *
 */
public abstract class Annotation implements Serializable {
    /**
     *
     */
    private static final long serialVersionUID = 1L;
    private Range range;
    private int track;

    /**
     * Creates a new abstract and untyped annotation with start and end.
     *
     * @param range start and end of the annotation
     */
    protected Annotation(Range range) {
        this.range = range;
    }

    protected Annotation(Range range, int track) {
        this.range = range;
        this.track = track;
    }

    public int getTrack() {
        return track;
    }

    public void setTrack(int track) {
        this.track = track;
    }

    /**
     * Returns the range of this annotation.
     * @return range of this annotation
     */
    public Range getRange() {
        return this.range;
    }

    /**
     * Returns the start of the annotation.
     * @return start of annotation
     */
    public long getStart() {
        return range.getStart();
    }

    /**
     * Returns the end of the annotation.
     *
     * @return end of annotation
     */
    public long getEnd() {
        return range.getEnd();
    }

    /**
     * Sets a new start of the annotation.
     * @param start new start of the annotation
     */
    public void setStart(long start) {
        this.range = range.setStart(start);
    }

    /**
     * Sets a new end of the annotation.
     * @param end new end of the annotation
     */
    public void setEnd(long end) {
        this.range = range.setEnd(end);
    }

    public void setEndCorrectly(long end) {
        this.range = new Range(this.range.getStart(), end);
    }

    /**
     * Sets a new {@link Range} for this annotation.
     * @param range new range to set
     */
    public void setNewRange(Range range) {
        this.range = range;
    }

    /**
     * Returns, whether {@code timestamp} is in the range of this annotation.
     * @param timestamp timestamp to check.
     * @return {@code true} if {@code timestamp} is in range, {@code false} if
     *         not
     */
    public boolean isInRange(long timestamp) {
        return range.contains(timestamp);
    }

    @Override
    public String toString() {
        return this.range.toString() + ": ";
    }

    public abstract String toValue();
}
