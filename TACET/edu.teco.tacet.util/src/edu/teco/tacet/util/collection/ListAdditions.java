package edu.teco.tacet.util.collection;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

import edu.teco.tacet.util.function.F1;
import edu.teco.tacet.util.function.F2;
import edu.teco.tacet.util.function.Predicate;

public class ListAdditions {
    @SafeVarargs
    public static <A> List<A> list(A... as) {
        return Arrays.asList(as);
    }
    
    public static <T, U> void sort(List<T> toSort, final F1<T, U> transformer,
        final Comparator<U> comparator) {
        Collections.sort(toSort, new Comparator<T>() {
            @Override
            public int compare(T o1, T o2) {
                return comparator.compare(transformer.apply(o1), transformer.apply(o2));
            }
        });
    }

    public static <T, U extends Comparable<U>> void sort(List<T> toSort,
        F1<T, U> transformer) {
        sort(toSort, transformer, new ComparableComparator<U>());
    }

    public static <T, U> int binaryFind(List<T> sortedList, U toFind,
        F1<T, U> transformer, Comparator<U> comp) {
        int start = 0;
        int end = sortedList.size();

        while (true) {
            if (start == end) {
                System.out.println(start + "," + end);
                return sortedList.size();
            }
            int cursor = (end + start) / 2;
            U elemAtCursor = transformer.apply(sortedList.get(cursor));
            int compRes = comp.compare(toFind, elemAtCursor);
            if (compRes == 0) {
                return cursor;
            } else if (compRes < 0) { // toFind < elemAtCursor
                end = cursor;
            } else if (compRes > 0) {
                start = cursor + 1;
            }
        }
    }

    public static <T, U extends Comparable<U>> int binaryFind(List<T> sortedList, U toFind,
        F1<T, U> transformer) {
        return binaryFind(sortedList, toFind, transformer, new ComparableComparator<U>());
    }

    /**
     * If the exact value cannot be found, take the next greater one.
     * @param sortedList
     * @param toFind
     * @param transformer
     * @param comp
     * @return
     */
    public static <T, U> int binaryFindUpper(List<T> sortedList, U toFind,
        F1<T, U> transformer,
        Comparator<U> comp) {
        int start = 0;
        int end = sortedList.size();

        while (true) {
            if (start == end) {
                return start;
            }
            int cursor = (end + start) / 2;
            U elemAtCursor = transformer.apply(sortedList.get(cursor));
            int compRes = comp.compare(toFind, elemAtCursor);
            if (compRes == 0) {
                return cursor;
            } else if (compRes < 0) { // toFind < elemAtCursor
                end = cursor;
            } else if (compRes > 0) {
                start = cursor + 1;
            }
        }
    }

    public static <T, U extends Comparable<U>> int binaryFindUpper(List<T> sortedList, U toFind,
        F1<T, U> transformer) {
        return binaryFindUpper(sortedList, toFind, transformer, new ComparableComparator<U>());
    }

    /**
     * If the exact value cannot be found, take the next smaller one.
     * @param sortedList
     * @param toFind
     * @param transformer
     * @param comp
     * @return
     */
    public static <T, U> int binaryFindLower(List<T> sortedList, U toFind,
        F1<T, U> transformer,
        Comparator<U> comp) {
        int start = 0;
        int end = sortedList.size();

        while (true) {
            if (start == end) {
                return start - 1;
            }
            int cursor = (end + start) / 2;
            U elemAtCursor = transformer.apply(sortedList.get(cursor));
            int compRes = comp.compare(toFind, elemAtCursor);
            if (compRes == 0) {
                return cursor;
            } else if (compRes < 0) { // toFind < elemAtCursor
                end = cursor;
            } else if (compRes > 0) {
                start = cursor + 1;
            }
        }
    }

    public static <T, U extends Comparable<U>> int binaryFindLower(List<T> sortedList, U toFind,
        F1<T, U> transformer) {
        return binaryFindLower(sortedList, toFind, transformer, new ComparableComparator<U>());
    }

    public static <T, U> List<U> map(F1<T, U> transformer, List<T> list) {
        List<U> ret = new ArrayList<>(list.size());
        for (T elem : list) {
            ret.add(transformer.apply(elem));
        }
        return ret;
    }

    public static <A> void filter_(Predicate<A> predicate, List<A> list) {
        for (int i = 0; i < list.size(); i++) {
            if (predicate.apply(list.get(0))) {
                list.remove(i);
            }
        }
    }
    
    public static <A> List<A> filter(Predicate<A> predicate, List<A> list) {
        List<A> ret = new ArrayList<>(list.size());
        for (A a : list) {
            if (predicate.apply(a)) {
                ret.add(a);
            }
        }
        return ret;
    }
    
    public static <A, L extends List<A>> F2<L, Integer, A> index() {
        return new F2<L, Integer, A>() {
            @Override
            public A apply(L list, Integer index) {
                return list.get(index);
            }
        };
    }
    
    public static <A> F1<Integer, A> indexFrom(final List<A> list) {
        return ListAdditions.<A, List<A>>index().apply(list);
    }
}
