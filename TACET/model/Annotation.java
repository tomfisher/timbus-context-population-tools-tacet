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
