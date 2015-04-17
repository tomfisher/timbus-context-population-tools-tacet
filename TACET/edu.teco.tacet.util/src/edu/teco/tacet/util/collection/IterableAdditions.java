package edu.teco.tacet.util.collection;

import static edu.teco.tacet.util.collection.CList.cons;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.Iterator;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.Set;
import java.util.TreeSet;

import edu.teco.tacet.util.function.F1;
import edu.teco.tacet.util.function.F2;
import edu.teco.tacet.util.function.F3;
import edu.teco.tacet.util.function.Functions;

public class IterableAdditions {

    @SafeVarargs
    public static <A> Iterable<A> iterable(A... as) {
        return toIterable(as);
    }

    public static <A, B> F2<F1<? super A, ? extends B>, Iterable<? extends A>, Iterable<? extends B>> map() {
        return new F2<F1<? super A, ? extends B>, Iterable<? extends A>, Iterable<? extends B>>() {
            @Override
            public Iterable<? extends B> apply(final F1<? super A, ? extends B> f,
                final Iterable<? extends A> iterable) {
                return new Iterable<B>() {

                    @Override
                    public Iterator<B> iterator() {
                        final Iterator<? extends A> tIter = iterable.iterator();
                        return new Iterator<B>() {

                            @Override
                            public boolean hasNext() {
                                return tIter.hasNext();
                            }

                            @Override
                            public B next() {
                                return f.apply(tIter.next());
                            }

                            @Override
                            public void remove() {
                                tIter.remove();
                            }
                        };
                    }
                };
            }

        };
    }

    public static <A, B> Iterable<? extends B> map(final F1<A, B> f,
        final Iterable<? extends A> iterable) {
        return IterableAdditions.<A, B> map().apply(f, iterable);
    }

    public static <A> F2<F1<A, Boolean>, Iterable<? extends A>, Iterable<A>> filter() {
        return new F2<F1<A, Boolean>, Iterable<? extends A>, Iterable<A>>() {
            @Override
            public Iterable<A> apply(final F1<A, Boolean> predicate,
                final Iterable<? extends A> iterable) {
                return new Iterable<A>() {

                    @Override
                    public Iterator<A> iterator() {
                        final Iterator<? extends A> iter = iterable.iterator();
                        return new Iterator<A>() {

                            A nextElement = null;
                            boolean hasNext = false;
                            boolean elemProcessed = false;

                            @Override
                            public boolean hasNext() {
                                if (!elemProcessed) {
                                    getNextElement();
                                }
                                return hasNext;
                            }

                            @Override
                            public A next() {
                                if (!elemProcessed) {
                                    getNextElement();
                                }
                                if (!hasNext) {
                                    throw new NoSuchElementException();
                                }
                                elemProcessed = false;
                                return nextElement;
                            }

                            private void getNextElement() {
                                while (iter.hasNext()) {
                                    A elem = iter.next();
                                    if (predicate.apply(elem)) {
                                        nextElement = elem;
                                        hasNext = true;
                                        elemProcessed = true;
                                        return;
                                    }
                                }
                                hasNext = false;
                            }

                            @Override
                            public void remove() {
                                if (!elemProcessed) {
                                    getNextElement();
                                }
                                getNextElement();
                            }
                        };
                    }
                };
            }
        };
    }

    public static <A> Iterable<A> filter(final F1<A, Boolean> predicate,
        final Iterable<? extends A> iterable) {
        return IterableAdditions.<A> filter().apply(predicate, iterable);
    }

    public static <A> F2<Long, Iterable<A>, Iterable<A>> take() {
        return new F2<Long, Iterable<A>, Iterable<A>>() {
            @Override
            public Iterable<A> apply(final Long n, final Iterable<A> it) {
                return new Iterable<A>() {
                    @Override
                    public Iterator<A> iterator() {
                        final Iterator<A> iter = it.iterator();

                        return new Iterator<A>() {
                            long cnt = 0;

                            @Override
                            public boolean hasNext() {
                                return cnt < n && iter.hasNext();
                            }

                            @Override
                            public A next() {
                                cnt++;
                                return iter.next();
                            }

                            @Override
                            public void remove() {
                                iter.remove();
                            }
                        };
                    }
                };
            }
        };
    }

    public static <A> Iterable<A> take(final long n, final Iterable<A> it) {
        return IterableAdditions.<A> take().apply(n, it);
    }

    public static <A> F2<Long, Iterable<A>, Iterable<A>> drop() {
        return new F2<Long, Iterable<A>, Iterable<A>>() {
            @Override
            public Iterable<A> apply(final Long n, final Iterable<A> it) {
                return new Iterable<A>() {
                    @Override
                    public Iterator<A> iterator() {
                        final Iterator<A> iter = it.iterator();

                        return new Iterator<A>() {
                            long cnt = 0;

                            @Override
                            public boolean hasNext() {
                                if (cnt < n) {
                                    while (cnt < n && iter.hasNext()) {
                                        iter.next();
                                        cnt++;
                                    }
                                }
                                return iter.hasNext();
                            }

                            @Override
                            public A next() {
                                return iter.next();
                            }

                            @Override
                            public void remove() {
                                iter.remove();
                            }
                        };
                    }
                };
            }
        };
    }

    public static <A> Iterable<A> drop(final long n, final Iterable<A> it) {
        return IterableAdditions.<A> drop().apply(n, it);
    }

    public static <A> F2<Comparator<A>, Iterable<? extends A>, Iterable<? extends A>> removeDuplicates() {
        return new F2<Comparator<A>, Iterable<? extends A>, Iterable<? extends A>>() {

            @Override
            public Iterable<? extends A> apply(final Comparator<A> comp,
                Iterable<? extends A> iterable) {
                return IterableAdditions.<A, CList<A>> foldl().apply(
                    new F2<CList<A>, A, CList<A>>() {
                        private A lastElement;

                        @Override
                        public CList<A> apply(CList<A> acc, A elt) {
                            if (comp.compare(lastElement, elt) != 0) {
                                lastElement = elt;
                                return cons(elt, acc);
                            }
                            return acc;
                        }
                    },
                    CList.<A> cList(), // empty cons list
                    iterable);
            }

        };

    }

    public static <A extends Comparable<A>> Iterable<? extends A> removeDuplicates(
        Iterable<? extends A> it) {
        return IterableAdditions.<A> removeDuplicates().apply(new ComparableComparator<A>(), it);
    }

    public static <A> Iterable<? extends A> removeDuplicates(Iterable<? extends A> it,
        Comparator<A> comp) {
        return IterableAdditions.<A> removeDuplicates().apply(comp, it);
    }

    public static <A> A reduce(F2<A, A, A> f, Iterable<? extends A> as) {
        return IterableAdditions.<A> reduce().apply(f, as);
    }

    public static <A> F2<F2<A, A, A>, Iterable<? extends A>, A> reduce() {
        return new F2<F2<A, A, A>, Iterable<? extends A>, A>() {
            @Override
            public A apply(F2<A, A, A> f, Iterable<? extends A> as) {
                Iterator<? extends A> itA = as.iterator();
                if (itA.hasNext()) {
                    return IterableAdditions.<A, A> foldl()
                        .apply(f, itA.next(), toIterable(itA));
                }
                return null;
            }
        };
    }

    public static <A, B> Iterable<? extends B> cast(Iterable<? extends A> as) {
        return map(Functions.<A, B> cast(), as);
    }

    public static <A, B> F3<F2<B, A, B>, B, Iterable<? extends A>, B> foldl() {
        return new F3<F2<B, A, B>, B, Iterable<? extends A>, B>() {
            @Override
            public B apply(F2<B, A, B> op, B zero,
                Iterable<? extends A> it) {
                B acc = zero;
                for (A elem : it) {
                    acc = op.apply(acc, elem);
                }
                return acc;
            }
        };
    }

    public static <A, B> B foldl(F2<B, A, B> op, B zero,
        Iterable<? extends A> it) {
        return IterableAdditions.<A, B> foldl().apply(op, zero, it);
    }

    public static <A, B> F3<F2<A, B, B>, B, Iterable<A>, B> foldr() {
        return new F3<F2<A, B, B>, B, Iterable<A>, B>() {
            @Override
            public B apply(F2<A, B, B> op, B zero, Iterable<A> it) {
                return foldrRec(op, zero, it.iterator());
            }
        };
    }

    private static <A, B> B foldrRec(F2<A, B, B> op, B acc, Iterator<A> it) {
        if (!it.hasNext()) {
            return acc;
        } else {
            return op.apply(it.next(), foldrRec(op, acc, it));
        }
    }

    public static <A, B> B foldr(F2<A, B, B> op, B zero, Iterable<A> it) {
        return IterableAdditions.<A, B> foldr().apply(op, zero, it);
    }

    public static <A, B, C> F3<F2<A, B, C>, Iterable<A>, Iterable<B>, Iterable<C>> zipWith() {
        return new F3<F2<A, B, C>, Iterable<A>, Iterable<B>, Iterable<C>>() {
            @Override
            public Iterable<C> apply(final F2<A, B, C> f, final Iterable<A> itA,
                final Iterable<B> itB) {
                return new Iterable<C>() {
                    @Override
                    public Iterator<C> iterator() {
                        final Iterator<A> iterA = itA.iterator();
                        final Iterator<B> iterB = itB.iterator();

                        return new Iterator<C>() {

                            @Override
                            public boolean hasNext() {
                                return iterA.hasNext() && iterB.hasNext();
                            }

                            @Override
                            public C next() {
                                return f.apply(iterA.next(), iterB.next());
                            }

                            @Override
                            public void remove() {
                                iterA.remove();
                                iterB.remove();
                            }
                        };
                    }
                };
            }
        };
    }

    public static <A, B, C> Iterable<C> zipWith(final F2<A, B, C> f, final Iterable<A> itA,
        final Iterable<B> itB) {
        return IterableAdditions.<A, B, C> zipWith().apply(f, itA, itB);
    }

    public static <A, B> F2<Iterable<A>, Iterable<B>, Iterable<Tuple<A, B>>> zip() {
        return IterableAdditions.<A, B, Tuple<A, B>> zipWith().apply(Tuple.<A, B> tuple());
    }

    public static <A, B> Iterable<Tuple<A, B>> zip(Iterable<A> itA, Iterable<B> itB) {
        return IterableAdditions.<A, B> zip().apply(itA, itB);
    }

    public static <A> F1<Iterable<? extends Iterable<A>>, Iterable<A>> concat() {
        return new F1<Iterable<? extends Iterable<A>>, Iterable<A>>() {
            @Override
            public Iterable<A> apply(final Iterable<? extends Iterable<A>> iterables) {
                return new Iterable<A>() {
                    @Override
                    public Iterator<A> iterator() {
                        final Iterator<? extends Iterable<A>> iterablesIter = iterables.iterator();
                        final Iterator<A> firstIter = getFirstNonEmptyIterator(iterablesIter);

                        if (!firstIter.hasNext()) {
                            return firstIter; // empty iterator
                        }

                        return new Iterator<A>() {
                            Iterator<A> currentIter = firstIter;

                            @Override
                            public boolean hasNext() {
                                if (currentIter.hasNext()) {
                                    return true;
                                }
                                currentIter = getFirstNonEmptyIterator(iterablesIter);
                                if (currentIter.hasNext()) {
                                    return true;
                                }
                                return false;
                            }

                            @Override
                            public A next() {
                                return currentIter.next();
                            }

                            @Override
                            public void remove() {
                                throw new UnsupportedOperationException();
                            }
                        };
                    }

                    private Iterator<A> getFirstNonEmptyIterator(
                        Iterator<? extends Iterable<A>> iterablesIter) {
                        while (iterablesIter.hasNext()) {
                            Iterator<A> next = iterablesIter.next().iterator();
                            if (next.hasNext()) {
                                return next;
                            }
                        }
                        return Collections.emptyIterator();
                    }
                };
            }
        };

    }

    public static <A> Iterable<A> concat(final Iterable<? extends Iterable<A>> iterables) {
        return IterableAdditions.<A> concat().apply(iterables);
    }

    public static <T> Iterable<T> toIterable(final Iterator<T> iterator) {
        return new Iterable<T>() {
            @Override
            public Iterator<T> iterator() {
                return iterator;
            }
        };
    }

    public static <T> List<T> toList(Iterable<T> iterable) {
        List<T> ret = new ArrayList<>();
        for (T t : iterable) {
            ret.add(t);
        }
        return ret;
    }

    @SuppressWarnings("unchecked")
    public static <A> A[] toArray(Iterable<A> iterable) {
        return (A[]) toList(iterable).toArray();
    }

    public static <A> Iterable<A> toIterable(final A[] array) {
        return new Iterable<A>() {
            @Override
            public Iterator<A> iterator() {
                return new Iterator<A>() {
                    int pos = 0;

                    @Override
                    public boolean hasNext() {
                        return pos < array.length;
                    }

                    @Override
                    public A next() {
                        return array[pos++];
                    }

                    @Override
                    public void remove() {
                        throw new UnsupportedOperationException();
                    }

                };
            }
        };
    }

    public static <A> Set<A> toSet(Iterable<A> iterable) {
        return toSet(iterable, null);
    }

    public static <A> Set<A> toSet(Iterable<A> iterable, Comparator<A> comp) {
        Set<A> ret = comp == null ? new TreeSet<A>() : new TreeSet<A>(comp);
        for (A a : iterable) {
            ret.add(a);
        }
        return ret;
    }

    public static <A> Iterable<A> emptyIterable() {
        return new Iterable<A>() {
            @Override
            public Iterator<A> iterator() {
                return Collections.emptyIterator();
            }
        };
    }

    public static <A> F1<Integer, A> indexFrom(final Iterable<A> iterable) {
        return new F1<Integer, A>() {
            @Override
            public A apply(Integer index) {
                int cnt = 0;
                for (A a : iterable) {
                    if (cnt == index) {
                        return a;
                    }
                    cnt++;
                }
                throw new IndexOutOfBoundsException();
            }
        };
    }

    public static <A> String toString(final Iterable<A> iterable) {
        if (!iterable.iterator().hasNext()) {
            return "[]";
        }
        StringBuilder builder = new StringBuilder();
        builder.append("[");
        for (A a : iterable) {
            builder.append(a).append(", ");
        }
        return builder.delete(builder.length() - 2, builder.length()).append("]").toString();
    }
}
