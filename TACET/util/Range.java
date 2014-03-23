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

package squirrel.util;

import java.io.Serializable;
import java.util.Arrays;
import java.util.Comparator;
import java.util.Iterator;

/**
 * An instance of this class represents a range between a defined {@code start}
 * (including) and {@code end}. It is at all times true that {@code start} is
 * less than or equal to {@code end}. An instance of this class is immutable, so
 * attempting to change its values will create a new instance with the updated
 * values.
 */
public class Range implements Iterable<Long>, Serializable {

    public static Comparator<Range> START_COMPARATOR = new StartComparator();
    public static Comparator<Range> END_COMPARATOR = new EndComparator();

    private final long start;
    private final long end;

    private Predicate<Long> iteratorPredicate;

    /**
     * Constructs a new instance with {@code start} and {@code end}.
     * {@code start} must be <= {@code end}.
     *
     * @param start the first value of this range.
     * @param end the last value not contained in this range.
     * @throws IllegalArgumentException if {@code start} > {@code end}.
     */
    public Range(long start, long end) {
        checkRange(start, end);
        this.start = start;
        this.end = end;
        this.iteratorPredicate = new Predicate<Long>() {
            @Override
            public boolean f(Long input) {
                return true;
            }
        };
    }

    /**
     * Creates a new Range with {@code start} as its first value.
     *
     * @param start the first value of the new range.
     * @return a new Range with {@code start} as its first value.
     * @throws IllegalArgumentException if {@code start} > {@code end}.
     */
    public Range setStart(long start) {
        return new Range(start, getEnd());
    }

    /**
     * Returns the first value of this range.
     *
     * @return the first value of this range.
     */
    public long getStart() {
        return start;
    }

    /**
     * Creates a new Range with {@code end} as the first value not contained in
     * the Range.
     *
     * @param end the first value not contained in the new range.
     * @return a new Range with {@code end} as the first value not contained in
     *         this range.
     * @throws IllegalArgumentException if {@code start} > {@code end}.
     */
    public Range setEnd(long end) {
        return new Range(getStart(), end);
    }

    /**
     * Returns the last value of this range.
     *
     * @return the last value of this range.
     */
    public long getEnd() {
        return end;
    }

    /**
     * Constructs a new Range by adding {@code amount} to this instance
     * {@code start} and {@code end} values.
     *
     * @param amount the amount to be added to this range. This value may be
     *            negative.
     * @return a new instance of this class, with the modified {@code start} and
     *         {@code end} values.
     */
    public Range move(long amount) {
        return new Range(getStart() + amount, getEnd() + amount);
    }

    /**
     * Contructs a new Range by adding {@code amount} to this instance'
     * {@code end} value. {@code amount} may be negative.
     *
     * @param amount the amount by which the {@code end} value is resized.
     * @return a new Range with resized {@code resized} accordingly.
     */
    public Range resizeRight(long amount) {
        return new Range(getStart(), getEnd() + amount);
    }

    /**
     * Contructs a new Range by adding {@code amount} to this instance'
     * {@code start} value. {@code amount} may be negative.
     *
     * @param amount the amount by which the {@code start} value is resized.
     * @return a new Range with resized {@code resized} accordingly.
     */
    public Range resizeLeft(long amount) {
        return new Range(getStart() + amount, getEnd());
    }

    /**
     * Constructs a new Range by moving the {@code end} value to fit the new
     * distance specified by {@code newDistance}.
     *
     * @param newDistance the distance of the new Range.
     * @return a new Range with adjusted distance {@code newDistance}.
     */
    public Range changeDistance(long newDistance) {
        return new Range(getStart(), getStart() + newDistance);
    }

    public Range union(Range other) {
        if (other == null)
            throw new IllegalArgumentException("[other] may not be null.");
        if (other.equals(this) || this.contains(other))
            return this;
        if (other.contains(this))
            return other;
        long[] bounds = new long[] { this.getStart(), this.getEnd(),
                other.getStart(), other.getEnd() };
        Arrays.sort(bounds);
        return new Range(bounds[0], bounds[3]);
    }

    /**
     * Checks whether {@code other} is contained in this instance. Range A
     * contains Range B if A.start <= B.start and B.end <= A.end. Note that this
     * method returns {@code true} in case both ranges are equal.
     *
     * @param other the range to be compared to this instance.
     * @return {@code false} if this range does not contain {@code other},
     *         {@code true} otherwise.
     */
    public boolean contains(Range other) {
        if (other == this)
            return true;
        if (other == null)
            throw new IllegalArgumentException("[other] may not be null.");
        return this.getStart() <= other.getStart() && other.getEnd() <= this.getEnd();
    }

    /**
     * Checks whether {@code timestamp} is contained in this instance. A
     * specific range R contains a timestamp t if R.start <= t < R.end.
     *
     * @param timestamp the timestamp to be compared to this instance.
     * @return {@code false} if this range does not contain {@code timestamp},
     *         {@code true} otherwise.
     */
    public boolean contains(long timestamp) {
        return start <= timestamp && timestamp < end;
    }

    /**
     * Get this instance' absolut value which is denoted by the difference
     * between its end and its start. Note that this value may be {@code 0} as
     * {@code start} and {@code end} may be equal.
     *
     * @return this instance' absolut value.
     */
    public long getDistance() {
        return getEnd() - getStart();
    }

    /**
     * Two ranges are equal if their respective {@code start} and {@code end}
     * values are equal.
     * @param other the range this instance is to be compared to.
     * @return {@code true} if this instance is equal to {@code other} and
     *         {@code false} otherwise.
     */
    @Override
    public boolean equals(Object other) {
        if (other == null)
            return false;
        if (!(other instanceof Range))
            return false;
        if (this == other)
            return true;
        Range r = (Range) other;
        return this.start == r.getStart() && this.end == r.getEnd();
    }

    @Override
    public int hashCode() {
        return (int) ((start + end) % Integer.MAX_VALUE);
    }

    @Override
    public String toString() {
        return String.format("[%d, %d)", start, end);
    }

    private void checkRange(long start, long end) {
        if (start > end) {
            throw new IllegalArgumentException(
                    String.format("start(=%d) is not <= end(=%d).", start, end));
        }
    }

    /**
     * Returns an iterator over all long values contained in this range.
     *
     * @return an iterator over all long values contained in this range.
     */
    @Override
    public Iterator<Long> iterator() {
        return new Iterator<Long>() {
            private Long cursor = start - 1L;
            private boolean hasNextCalled = false;
            private boolean lastHasNext = false;

            @Override
            public boolean hasNext() {
                if (hasNextCalled)
                    return lastHasNext;
                hasNextCalled = true;
                return lastHasNext = advanceCursor() < end;
            }

            @Override
            public Long next() {
                if (!hasNextCalled)
                    return advanceCursor();
                hasNextCalled = false;
                return cursor;
            }

            private long advanceCursor() {
                while (!iteratorPredicate.f(++cursor));
                return cursor;
            }

            @Override
            public void remove() {
                throw new UnsupportedOperationException();
            }
        };
    }

    public Range by(final long skip) {
        this.iteratorPredicate = new Predicate<Long>() {
            @Override
            public boolean f(Long input) {
                if (input % skip == 0) {
                    return true;
                }
                return false;
            }
        };
        return this;
    }

    public static Range fromUnsorted(long l1, long l2) {
        return new Range(Math.min(l1, l2), Math.max(l1, l2));
    }

    /**
     * Create a new Range with {@code start} and {@code end} having the same
     * {@code value}, thus producing a Range of length 0.
     * @param value the start and end value of the new Range
     * @return the new Range
     */
    public static Range point(long value) {
        return new Range(value, value);
    }

    static class StartComparator implements Comparator<Range> {
        @Override
        public int compare(Range range1, Range range2) {
            if (range1.getStart() == range2.getStart()) {
                return compareLongs(range1.getEnd(), range2.getEnd());
            } else {
                return compareLongs(range1.getStart(), range2.getStart());
            }
        }
    }

    static class EndComparator implements Comparator<Range> {
        @Override
        public int compare(Range range1, Range range2) {
            if (range1.getEnd() == range2.getEnd()) {
                return compareLongs(range1.getStart(), range2.getStart());
            } else {
                return compareLongs(range1.getEnd(), range2.getEnd());
            }
        }
    }

    private static int compareLongs(long l1, long l2) {
        if (l1 == l2)
            return 0;
        return l1 < l2 ? -1 : 1;
    }
}
