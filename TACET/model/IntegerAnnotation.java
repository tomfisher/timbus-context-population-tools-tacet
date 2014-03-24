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
 * Represents an annotation, whose value is an integer.
 *
 * @author Olivier
 *
 */
public class IntegerAnnotation extends Annotation {

    /**
     * Value of the annotation.
     */
    private int value;

    /**
     * Constructs a new {@code IntegerAnnotation} with a start and end value and
     * an annotation value.
     *
     * @param start beginning of annotation
     * @param end ending of annotation
     * @param value value of annotation
     */
    public IntegerAnnotation(Range range, int value) {
        super(range);
        this.value = value;
    }

    /**
     * Constructs a new {@code IntegerAnnotation} with a start and end value and
     * an annotation value on the given track.
     *
     * @param range start and end of the annotation
     * @param value value of annotation
     * @param track track of the annotation
     */
    public IntegerAnnotation(Range range, int value, int track) {
        super(range, track);
        this.value = value;
    }

    /**
     * Returns the value of the annotation.
     * @return value of annotation
     */
    public int getValue() {
        return value;
    }

    /**
     * Prints this Annotation.
     */
    @Override
    public String toString() {
        return super.toString() + Integer.toString(value);
    }

    /**
     * Returns if this IntegerAnnotation has the same value, same start and same
     * start like another IntegerAnnotation. If so, the two IntegerAnnotations are
     * equal.
     * @param ia other IntegerAnnotation to compare with
     * @return if ia is equal to this IntegerAnnotation
     */
    @Override
    public boolean equals(Object other) {
        boolean res = false;
        if (other != null && (other instanceof IntegerAnnotation)) {
            IntegerAnnotation ia = (IntegerAnnotation) other;
            res = (ia != null && ia.value == this.value && ia.getStart() == this.getStart() && ia
                    .getEnd() == this.getEnd());
        }
        return res;
    }

    @Override
    public String toValue() {
        return value+"";
    }

}
