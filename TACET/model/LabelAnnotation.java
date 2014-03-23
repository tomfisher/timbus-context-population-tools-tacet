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
 * Represents an annotation, whose value is a String.
 *
 * @author Olivier
 *
 */
public class LabelAnnotation extends Annotation {

    /**
     * Value of the annotation
     */
    private String label;

    /**
     * Constructs a new {@code LabelAnnotation} with a start and end value and
     * an annotation label.
     *
     * @param range start and end of the annotation
     * @param label value of annotation
     */
    public LabelAnnotation(Range range, String label) {
        super(range);
        this.label = label;
    }

    /**
     * Constructs a new {@code LabelAnnotation} with a start and end value and
     * an annotation label on the given track.
     *
     * @param range start and end of the annotation
     * @param label value of annotation
     * @param track track of annotation
     */
    public LabelAnnotation(Range range, String label, int track) {
        super(range, track);
        this.label = label;
    }

    /**
     * Returns the value of the label.
     * @return value of the label
     */
    public String getLabel() {
        return label;
    }

    /**
     *
     * Prints this annotation.
     */
    @Override
    public String toString() {
        return super.toString() + this.label;
    }

    /**
     * Returns if this LabelAnnotation has the same label, same start and same
     * end like another LabelAnnotation. If so, the two LabelAnnotations are
     * equal.
     * @param la other LaberAnnotation to compare with
     * @return if la is equal to this LabelAnnotation
     */
    @Override
    public boolean equals(Object other) {
        boolean res = false;
        if (other != null && (other instanceof LabelAnnotation)) {
            LabelAnnotation la = (LabelAnnotation) other;
            res = (la != null && la.label == this.label && la.getStart() == this.getStart() && la
                    .getEnd() == this.getEnd());
        }
        return res;

    }

    @Override
    public String toValue() {
        return label;
    }

}
