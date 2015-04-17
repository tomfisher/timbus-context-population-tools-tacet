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

package edu.teco.tacet.track;

import java.util.Comparator;

/**
 * An abstract annotation, which is untyped, but knows start and end of annotation.
 *
 */
public class Annotation {

    private Range range;
    private String label;
    private String description;

    /**
     * Creates a new abstract and untyped annotation with start and end.
     *
     * @param range start and end of the annotation
     */
    protected Annotation(Range range) {
        this(range, "", "");
    }

    public Annotation(Range range, String label) {
        this(range, label, "");
    }

    public Annotation(Range range, String label, String description) {
        super();
        this.range = range;
        this.label = label;
        this.description = description;
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

    /**
     * Sets a new {@link Range} for this annotation.
     * @param range new range to set
     */
    public void setRange(Range range) {
        this.range = range;
    }

    public String getLabel() {
        return label;
    }

    public void setLabel(String label) {
        this.label = label;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    /**
     * Returns, whether {@code timestamp} is in the range of this annotation.
     * @param timestamp timestamp to check.
     * @return {@code true} if {@code timestamp} is in range, {@code false} if not
     */
    public boolean contains(long timestamp) {
        return range.contains(timestamp);
    }

    @Override
    public String toString() {
        return this.range.toString() + ": " + label + " (" + description + ")";
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj)
            return true;
        if (obj == null)
            return false;
        if (getClass() != obj.getClass())
            return false;
        Annotation other = (Annotation) obj;
        if (description == null) {
            if (other.description != null)
                return false;
        } else if (!description.equals(other.description))
            return false;
        if (label == null) {
            if (other.label != null)
                return false;
        } else if (!label.equals(other.label))
            return false;
        if (range == null) {
            if (other.range != null)
                return false;
        } else if (!range.equals(other.range))
            return false;
        return true;
    }

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + ((description == null) ? 0 : description.hashCode());
        result = prime * result + ((label == null) ? 0 : label.hashCode());
        result = prime * result + ((range == null) ? 0 : range.hashCode());
        return result;
    }

    static class StartComparator implements Comparator<Annotation> {
        @Override
        public int compare(Annotation a1, Annotation a2) {
            if (a1.equals(a2))
                return 0;
            return Range.START_COMPARATOR.compare(a1.getRange(), a2.getRange());
        }
    }

    static class StartContainsEqualComparator implements Comparator<Annotation> {
        @Override
        public int compare(Annotation a1, Annotation a2) {
            if (a1.equals(a2) || a1.contains(a2.getStart()) || a2.contains(a1.getStart()))
                return 0;
            return Range.START_COMPARATOR.compare(a1.getRange(), a2.getRange());
        }
    }

    static class EndComparator implements Comparator<Annotation> {
        @Override
        public int compare(Annotation a1, Annotation a2) {
            if (a1.equals(a2))
                return 0;
            return Range.END_COMPARATOR.compare(a1.getRange(), a2.getRange());
        }
    }

    static class OverlapIsEqualComparator implements Comparator<Annotation> {
        @Override
        public int compare(Annotation a1, Annotation a2) {
            if (a1.getRange().overlapsWith(a2.getRange())) {
                return 0;
            }
            return a1.getRange().getStart() < a2.getRange().getStart() ? -1 : 1;
        }

    }

    static class LabelComparator implements Comparator<Annotation> {
        @Override
        public int compare(Annotation o1, Annotation o2) {
            return o1.getLabel().compareTo(o2.getLabel());
        }
    }

    static class DescriptionComparator implements Comparator<Annotation> {
        @Override
        public int compare(Annotation o1, Annotation o2) {
            return o1.getDescription().compareTo(o2.getDescription());
        }
    }

    public static final Comparator<Annotation> START_COMPARATOR = new StartComparator();
    public static final Comparator<Annotation> START_CONTAINS_EQUAL_COMPARATOR = new StartContainsEqualComparator();
    public static final Comparator<Annotation> END_COMPARATOR = new EndComparator();
    public static final Comparator<Annotation> OVERLAP_IS_EQUAL_COMPARATOR = new OverlapIsEqualComparator();
    public static final Comparator<Annotation> LABEL_COMPARATOR = new LabelComparator();
    public static final Comparator<Annotation> DESCRIPTION_COMPARATOR = new LabelComparator();

    public static Annotation createDummy(Range range) {
        return new DummyAnnotation(range);
    }

    public static Annotation createDummy(long start, long end) {
        return new DummyAnnotation(new Range(start, end));
    }

    private static class DummyAnnotation extends Annotation {
        protected DummyAnnotation(Range range) {
            super(range);
        }
    }

    public Annotation deepCopy() {
        Range rangeCopy = new Range(getRange().getStart(), getRange().getEnd());
        Annotation result = new Annotation(rangeCopy, label, description);
        return result;
    }
}
