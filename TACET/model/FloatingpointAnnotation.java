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
