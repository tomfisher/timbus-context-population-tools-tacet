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

import java.util.Arrays;
import java.util.Comparator;
import java.util.Iterator;

/**
 * An instance of this class represents a range between a defined {@code start} (including) and
 * {@code end}. It is at all times true that {@code start} is less than or equal to {@code end}. An
 * instance of this class is immutable, so attempting to change its values will create a new
 * instance with the updated values.
 */
public class Range implements Iterable<Long> {

    public static Comparator<Range> START_COMPARATOR = new StartComparator();
    public static Comparator<Range> END_COMPARATOR = new EndComparator();

    private final long start;
    private final long end;

    /**
     * Constructs a new instance with {@code start} and {@code end}. {@code start} must be <=
     * {@code end}.
     *
     * @param start the first value of this range.
     * @param end the last value not contained in this range.
     * @throws IllegalArgumentException if {@code start} > {@code end}.
     */
    public Range(long start, long end) {
        checkRange(start, end);
        this.start = start;
        this.end = end;
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

    public boolean canSetStart(long start){
        return (start<getEnd());
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
     * Creates a new Range with {@code end} as the first value not contained in the Range.
     *
     * @param end the first value not contained in the new range.
     * @return a new Range with {@code end} as the first value not contained in this range.
     * @throws IllegalArgumentException if {@code start} > {@code end}.
     */
    public Range setEnd(long end) {
        return new Range(getStart(), end);
    }

    public boolean canSetEnd(long end){
        return (getStart()<end);
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
     * Constructs a new Range by adding {@code amount} to this instance {@code start} and
     * {@code end} values.
     *
     * @param amount the amount to be added to this range. This value may be negative.
     * @return a new instance of this class, with the modified {@code start} and {@code end} values.
     */
    public Range move(long amount) {
        return new Range(getStart() + amount, getEnd() + amount);
    }

    /**
     * Contructs a new Range by adding {@code amount} to this instance' {@code end} value.
     * {@code amount} may be negative.
     *
     * @param amount the amount by which the {@code end} value is resized.
     * @return a new Range with resized {@code resized} accordingly.
     */
    public Range resizeRight(long amount) {
        return new Range(getStart(), getEnd() + amount);
    }

    /**
     * Contructs a new Range by adding {@code amount} to this instance' {@code start} value.
     * {@code amount} may be negative.
     *
     * @param amount the amount by which the {@code start} value is resized.
     * @return a new Range with resized {@code resized} accordingly.
     */
    public Range resizeLeft(long amount) {
        return new Range(getStart() + amount, getEnd());
    }

    /**
     * Constructs a new Range by moving the {@code end} value to fit the new distance specified by
     * {@code newDistance}.
     *
     * @param newDistance the distance of the new Range.
     * @return a new Range with adjusted distance {@code newDistance}.
     */
    public Range changeDistance(long newDistance) {
        return new Range(getStart(), getStart() + newDistance);
    }

    public Range union(Range other) {
        if (other == null || other.equals(this) || this.contains(other))
            return this;
        if (other.contains(this))
            return other;
        long[] bounds = new long[] { this.getStart(), this.getEnd(),
            other.getStart(), other.getEnd() };
        Arrays.sort(bounds);
        return new Range(bounds[0], bounds[3]);
    }

    public Range union(Range... others) {
        Range acc = this;
        for (Range other : others) {
            acc = acc.union(other);
        }
        return acc;
    }

    public Range intersection(Range other) {
        long s1 = this.start;
        long e1 = this.end;
        long s2 = other.start;
        long e2 = other.end;
        if (s1 < s2 && s2 < e1 && e1 < e2) {
            return new Range(s2, e1);
        }
        if (this.contains(other)) {
            return other;
        }
        if (other.contains(this)) {
            return this;
        }
        if (s2 < s1 && s1 < e2 && e2 < e1) {
            return new Range(s1, e2);
        }
        return null;
    }

    /**
     * Checks whether {@code other} is contained in this instance. Range A contains Range B if
     * A.start <= B.start and B.end <= A.end. Note that this method returns {@code true} in case
     * both ranges are equal.
     *
     * @param other the range to be compared to this instance.
     * @return {@code false} if this range does not contain {@code other}, {@code true} otherwise.
     */
    public boolean contains(Range other) {
        if (other == this)
            return true;
        if (other == null)
            throw new IllegalArgumentException("[other] may not be null.");
        return this.getStart() <= other.getStart() && other.getEnd() <= this.getEnd();
    }

    /**
     * Checks whether {@code timestamp} is contained in this instance. A specific range R contains a
     * timestamp t if R.start <= t < R.end.
     *
     * @param timestamp the timestamp to be compared to this instance.
     * @return {@code false} if this range does not contain {@code timestamp}, {@code true}
     *         otherwise.
     */
    public boolean contains(long timestamp) {
        return start <= timestamp && timestamp < end;
    }

    public boolean overlapsWith(Range other) {
        return this.contains(other.start) || this.contains(other.end - 1L) ||
            other.contains(this.start) || other.contains(this.end - 1L);
    }

    /**
     * Get this instance' absolut value which is denoted by the difference between its end and its
     * start. Note that this value may be {@code 0} as {@code start} and {@code end} may be equal.
     *
     * @return this instance' absolut value.
     */
    public long getDistance() {
        return getEnd() - getStart();
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj)
            return true;
        if (obj == null)
            return false;
        if (getClass() != obj.getClass())
            return false;
        Range other = (Range) obj;
        if (end != other.end)
            return false;
        if (start != other.start)
            return false;
        return true;
    }

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + (int) (end ^ (end >>> 32));
        result = prime * result + (int) (start ^ (start >>> 32));
        return result;
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
            private Long cursor = start;

            @Override
            public boolean hasNext() {
                return cursor < getEnd();
            }

            @Override
            public Long next() {
                return cursor++;
            }

            @Override
            public void remove() {
                throw new UnsupportedOperationException();
            }
        };
    }

    public static Range fromUnsorted(long l1, long l2) {
        return new Range(Math.min(l1, l2), Math.max(l1, l2));
    }

    /**
     * Create a new Range with {@code start = value} and a length of 1.
     * @param value the start of the new Range
     * @return the new Range
     */
    public static Range point(long value) {
        return new Range(value, value + 1L);
    }

    static class StartComparator implements Comparator<Range> {
        @Override
        public int compare(Range range1, Range range2) {
            if (range1.getStart() == range2.getStart()) {
                return Long.compare(range1.getEnd(), range2.getEnd());
            } else {
                return Long.compare(range1.getStart(), range2.getStart());
            }
        }
    }

    static class EndComparator implements Comparator<Range> {
        @Override
        public int compare(Range range1, Range range2) {
            if (range1.getEnd() == range2.getEnd()) {
                return Long.compare(range1.getStart(), range2.getStart());
            } else {
                return Long.compare(range1.getEnd(), range2.getEnd());
            }
        }
    }

    /**
     * This comparator is able to report a very detailed result.
     *
     * <table border="2" cellspacing="0" cellpadding="6" rules="groups" frame="hsides">
     *
     *
     * <colgroup> <col class="left" />
     *
     * <col class="right" />
     *
     * <col class="left" /> </colgroup> <thead>
     * <tr>
     * <th scope="col" class="left">Logischer Ausdruck</th>
     * <th scope="col" class="right">Return</th>
     * <th scope="col" class="left">Beschreibung</th>
     * </tr>
     * </thead> <tbody>
     * <tr>
     * <td class="left"><code>E1 &lt; S2</code></td>
     * <td class="right">-4</td>
     * <td class="left">1 links außerhalb von 2</td>
     * </tr>
     *
     * <tr>
     * <td class="left"><code>E1 == S2</code></td>
     * <td class="right">-3</td>
     * <td class="left">1 links von 2, Ende 1 gleich Anfang 2</td>
     * </tr>
     *
     * <tr>
     * <td class="left"><code>S1 &lt; S2 &lt; E1 &amp;&amp; E1 &lt; E2</code></td>
     * <td class="right">-2</td>
     * <td class="left">Anfang 2 in 1 enthalten, Ende 2 außerhalb</td>
     * </tr>
     *
     * <tr>
     * <td class="left"><code>S2 &lt; S1 &amp;&amp; E1 &lt; E2</code></td>
     * <td class="right">-1</td>
     * <td class="left">2 komplett in 1 enthalten</td>
     * </tr>
     *
     * <tr>
     * <td class="left"><code>S1 == S2 &amp;&amp; E1 == E2</code></td>
     * <td class="right">0</td>
     * <td class="left">1 und zwei gleich</td>
     * </tr>
     *
     * <tr>
     * <td class="left"><code>S1 &lt; S2 &amp;&amp; E2 &lt; E1</code></td>
     * <td class="right">1</td>
     * <td class="left">1 komplett in 2 enthalten</td>
     * </tr>
     *
     * <tr>
     * <td class="left"><code>S2 &lt; S1 &amp;&amp; S1 &lt; E2 &lt; E1</code></td>
     * <td class="right">2</td>
     * <td class="left">Anfang 1 in 2 enthalten, Ende 1 außerhalb</td>
     * </tr>
     *
     * <tr>
     * <td class="left"><code>E2 == S1</code></td>
     * <td class="right">3</td>
     * <td class="left">2 links von 1, Ende 2 gleich Anfang 1</td>
     * </tr>
     *
     * <tr>
     * <td class="left"><code>E2 &lt; S1</code></td>
     * <td class="right">4</td>
     * <td class="left">2 rechts außerhalb von 1</td>
     * </tr>
     * </tbody>
     * </table>
     */
    static class MultiComparator implements Comparator<Range> {

        @Override
        public int compare(Range o1, Range o2) {
            return 0;
        }

    }
}
