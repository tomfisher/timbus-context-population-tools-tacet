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
