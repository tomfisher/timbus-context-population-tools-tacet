package edu.teco.tacet.track;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.Iterator;
import java.util.List;

/**
 * This merge strategy expects the input {@code values} to be ordered in a way that every time stamp
 * has either none or one {@link Annotation}. If there are more than one, the behavior is undefined.
 */
public class NoConflictMerge<T> implements MergeStrategy<T> {

    private final Comparator<T> comparator;

    public NoConflictMerge() {
        this.comparator = new Comparator<T>() {
            @SuppressWarnings("unchecked")
            @Override
            public int compare(T o1, T o2) {
                return ((Comparable<T>) o1).compareTo(o2);
            }
        };
    }

    public NoConflictMerge(Comparator<T> comp) {
        super();
        this.comparator = comp;
    }

    @Override
    public Iterable<? extends T> merge(final List<Iterable<? extends T>> values) {

        return new Iterable<T>() {
            @Override
            public Iterator<T> iterator() {
                return new Iterator<T>() {
                    // each instance of this (anonymous) class has its own iterators
                    final List<Iterator<? extends T>> iterators = new ArrayList<>(values.size());
                    // holds the currently first element of each iterator
                    final List<T> heads = initialiseHeads();

                    List<T> initialiseHeads() {
                        // Get a (new) iterator from each of the Iterables
                        for (Iterable<? extends T> iter : values)
                            iterators.add(iter.iterator());
                        // Get their respective first element
                        List<T> list = new ArrayList<>(values.size());
                        List<Iterator<? extends T>> emptyIters = new ArrayList<>();
                        for (Iterator<? extends T> iter : iterators) {
                            if (iter.hasNext())
                                list.add(iter.next());
                            else
                                emptyIters.add(iter);
                        }
                        // remove the empty iterators
                        for (Iterator<? extends T> iter : emptyIters) {
                            iterators.remove(iter);
                        }
                        return list;
                    }

                    @Override
                    public boolean hasNext() {
                        return heads.size() > 0;
                    }

                    @Override
                    public T next() {
                        T min = heads.get(0);
                        int minIdx = 0;

                        // find the smallest head
                        for (int i = 1; i < heads.size(); i++) {
                            if (comparator.compare(heads.get(i), min) < 0) {
                                min = heads.get(i);
                                minIdx = i;
                            }
                        }
                        // If an iterator is empty, remove it
                        if (!iterators.get(minIdx).hasNext()) {
                            heads.remove(minIdx);
                            iterators.remove(minIdx);
                        } // otherwise fetch the next element
                        else {
                            heads.set(minIdx, iterators.get(minIdx).next());
                        }

                        return min;
                    }

                    @Override
                    public void remove() {
                        throw new UnsupportedOperationException();
                    }
                };
            }
        };
    }
}
