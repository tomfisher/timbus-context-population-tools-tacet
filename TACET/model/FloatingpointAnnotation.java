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

import squirrel.util.Range;

/**
 * Represents an annotation, whose value is a floating point.
 *
 * @author Olivier
 *
 */
public class FloatingpointAnnotation extends Annotation {

    /**
     * Value of the annotation.
     */
    private double value;

    /**
     * Constructs a new {@code FloatingpointAnnotation} with a start and end
     * value and an annotation value.
     *
     * @param range start and end of the annotation
     * @param value value of annotation
     */
    public FloatingpointAnnotation(Range range, double value) {
        super(range);
        this.value = value;
    }

    /**
     * Constructs a new {@code FloatingpointAnnotation} with a start and end
     * value and an annotation value on the given track.
     *
     * @param range start and end of the annotation
     * @param value value of annotation
     * @param track track of the annotation
     */
    public FloatingpointAnnotation(Range range, double value, int track) {
        super(range, track);
        this.value = value;
    }

    /**
     * Returns the value of the annotation
     * @return value of the annotation
     */
    public double getValue() {
        return value;
    }

    /**
     * Prints this annotation.
     */
    @Override
    public String toString() {
        return super.toString() + Double.toString(value);
    }

    /**
     * Returns if this FloatingpointAnnotation has the same value, same start
     * and same end like another FloatingpointAnnotation. If so, the two
     * FloatingpointAnnotations are equal.
     * @param fpa other FloatingpointAnnotation to compare with
     * @return if fpa is equal to this FlaotingpointAnnotation
     */
    @Override
    public boolean equals(Object other) {
        boolean res = false;
        if (other != null && (other instanceof FloatingpointAnnotation)) {
            FloatingpointAnnotation fpa = (FloatingpointAnnotation) other;
            res = (fpa != null && fpa.value == this.value && fpa.getStart() == this.getStart() && fpa
                    .getEnd() == this.getEnd());
        }
        return res;
    }

    @Override
    public String toValue() {
        return value+"";
    }

}
