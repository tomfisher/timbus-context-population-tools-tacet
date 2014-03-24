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
