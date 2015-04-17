package edu.teco.tacet.mock;

import java.util.Iterator;
import java.util.Random;

import edu.teco.tacet.readers.Reader;
import edu.teco.tacet.track.Annotation;
import edu.teco.tacet.track.Datum;
import edu.teco.tacet.track.Range;
import edu.teco.tacet.util.function.F1;

public class MockReader implements Reader {

    private long sourceId;
    private Range coveredRange;

    public MockReader(long sourceId, Range coveredRange) {
        this.sourceId = sourceId;
        this.coveredRange = coveredRange;
    }

    static class FunctionIterable<T> implements Iterable<T> {

        private F1<Long, T> function;
        private Range range;
        private long resolution;

        public FunctionIterable(F1<Long, T> function, Range range, long resolution) {
            this.function = function;
            this.range = range;
            this.resolution = resolution;
        }

        @Override
        public Iterator<T> iterator() {
            return new Iterator<T>() {
                long cursor = range.getStart();

                @Override
                public boolean hasNext() {
                    return cursor < range.getEnd();
                }

                @Override
                public T next() {
                    T ret = function.apply(cursor);
                    cursor += resolution;
                    return ret;
                }

                @Override
                public void remove() {
                    throw new UnsupportedOperationException();
                }

            };
        }

    }

    @Override
    public Iterable<Datum> readSensorData(final long trackId, Range range, long resolution) {
        return new FunctionIterable<Datum>(new F1<Long, Datum>() {
            long phase = new Random(trackId).nextLong();

            @Override
            public Datum apply(Long a) {
                return new Datum(a, 0.01 * Math.sin(a));
            }
        }, range, resolution);
    }

    // The base width of every generated annotation as a percentage of the covered range of the
    // given track
    private final double baseSize = 0.01;
    // The percentage by which the baseSize is allowed to vary
    private final double maxDeviation = 0.2;
    // The maximum distance between two annotations as a percentage of the covered range of the
    // given track
    private final double maxDistance = 0.001;

    @Override
    public Iterable<Annotation> readAnnotations(final long trackId, final Range range) {
        return new Iterable<Annotation>() {
            final long coveredDistance = getCoveredRange(trackId).getDistance();
            final long baseSizeMillis = (long) (coveredDistance * baseSize);
            final long maxDistanceMillis = (long) (coveredDistance * maxDistance);
            final long maxDeviationMillis = (long) (baseSizeMillis * maxDeviation);
            final long maxLengthMillis = baseSizeMillis + maxDistanceMillis + maxDeviationMillis;

            @Override
            public Iterator<Annotation> iterator() {
                return new Iterator<Annotation>() {

                    long cursor = initialiseCursor(maxLengthMillis, range.getStart());

                    long initialiseCursor(long base, long value) {
                        // Round down to beginning of current cell.
                        long startPreviousAnnot = value - (value % base);
                        if (!nextRange(startPreviousAnnot).overlapsWith(range)) {
                            // The annotation before requested range does not overlap with requested
                            // range, so we advance the cursor by one cell.
                            startPreviousAnnot += base;
                        }
                        return startPreviousAnnot;
                    }

                    @Override
                    public boolean hasNext() {
                        return range.overlapsWith(nextRange(cursor));
                    }

                    @Override
                    public Annotation next() {
                        Annotation next = new Annotation(nextRange(cursor), "mock");
                        this.cursor += maxLengthMillis;
                        return next;
                    }

                    private Range nextRange(long cursor) {
                        Random rndGen = new Random(cursor + trackId);
                        long start = cursor + (long) (maxDistanceMillis * rndGen.nextDouble());

                        long length = baseSizeMillis +
                            (long) (maxDeviationMillis * rndGen.nextDouble() *
                            (rndGen.nextBoolean() ? -1d : 1d)); // random sign;

                        return new Range(start, start + length);
                    }

                    @Override
                    public void remove() {
                        throw new UnsupportedOperationException();
                    }
                };
            }

        };
    }

    @Override
    public long getSourceId() {
        return sourceId;
    }

    @Override
    public double getAverageDistance(long trackId) {
        return 1d;
    }

    @Override
    public Range getCoveredRange(long trackId) {
        return coveredRange;
    }

}
