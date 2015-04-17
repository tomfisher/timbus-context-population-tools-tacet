package edu.teco.tacet.test;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Iterator;
import java.util.List;

import edu.teco.tacet.track.AbstractTrack;
import edu.teco.tacet.track.Annotation;
import edu.teco.tacet.track.Datum;
import edu.teco.tacet.track.Range;
import edu.teco.tacet.track.Track;

public class TestHelper {

    private TestHelper() {}

    @SuppressWarnings("unchecked")
    public static <T> T[] iterableToArray(Iterable<T> iterable) {
        List<T> list = new ArrayList<T>();
        for (T t : iterable) {
            list.add(t);
        }
        return (T[]) list.toArray();
    }

    public static <T> Iterable<T> arrayToIterable(final T[] array) {
        return new Iterable<T>() {
            int cursor = 0;

            @Override
            public Iterator<T> iterator() {
                return new Iterator<T>() {

                    @Override
                    public boolean hasNext() {
                        return cursor < array.length;
                    }

                    @Override
                    public T next() {
                        return array[cursor++];
                    }

                    @Override
                    public void remove() {
                        throw new UnsupportedOperationException();
                    }
                };
            }
        };
    }

    public static <T extends Datum> Track<T> arrayToSensorTrack(T[] array) {
        return arrayToSensorTrack(array, -1, -1);
    }

    public static <T extends Datum> Track<T> arrayToSensorTrack(final T[] array, final long trackId,
        final long sourceId) {
        return new AbstractTrack<T>(trackId, sourceId, null) {

            @Override
            public Iterable<T> getData(Range range) {
                int start = 0;
                int end = array.length;


                for (int i = 0; i < array.length; i++) {
                    if (range.contains(array[i].timestamp)) {
                        start = i;
                        break;
                    }
                }

                for (int i = array.length - 1; i >= 0; i--) {
                    if (range.contains(array[i].timestamp)) {
                        end = i + 1;
                        break;
                    }
                }

                if (end - start == 0)
                    return Collections.emptyList();

                final int fStart = start;
                final int fEnd = end;

                return new Iterable<T>() {
                    @Override
                    public Iterator<T> iterator() {
                        return (new TestHelper()).new ArrayIterator<T>(array, fStart, fEnd);
                    }
                };
            }

            @Override
            public Range getCoveredRange() {
                return new Range(array[0].timestamp, array[array.length - 1].timestamp);
            }
        };
    }

    public static <T extends Annotation> Track<T> arrayToAnnotationTrack(final T[] array) {
        return arrayToAnnotationTrack(array, -1, -1);
    }

    public static <T extends Annotation> Track<T> arrayToAnnotationTrack(final T[] array, final long trackId,
        final long sourceId) {
        return new Track<T>() {

            @Override
            public Range getCoveredRange() {
                return new Range(array[0].getStart(), array[array.length - 1].getEnd());
            }

            @Override
            public long getId() {
                return trackId;
            }

            @Override
            public long getSourceId() {
                return sourceId;
            }

            @Override
            public Iterable<T> getData(Range range) {
                int start = 0;
                int end = array.length;
                for (int i = 1; i < array.length; i++) {
                    if (array[i].getRange().overlapsWith(range) && start != 0) {
                        start = i;
                    } else if (array[i].getStart() > range.getEnd()) {
                        end = i;
                        break;
                    }
                }

                if (end - start == 0)
                    return Collections.emptyList();

                final int fStart = start;
                final int fEnd = end;

                return new Iterable<T>() {
                    @Override
                    public Iterator<T> iterator() {
                        return (new TestHelper()).new ArrayIterator<T>(array, fStart, fEnd);
                    }
                };
            }

            @Override
            public Track.MetaData getMetaData() {
                return new MetaData();
            }

        };
    }

    public class ArrayIterator<T> implements Iterator<T> {
        int cursor;
        T[] array;
        int end;

        public ArrayIterator(T[] array, int start, int end) {
            this.cursor = start;
            this.end = end;
            this.array = array;
        }

        @Override
        public boolean hasNext() {
            return cursor < end;
        }

        @Override
        public T next() {
            return array[cursor++];
        }

        @Override
        public void remove() {
            throw new UnsupportedOperationException();
        }

    }
}
