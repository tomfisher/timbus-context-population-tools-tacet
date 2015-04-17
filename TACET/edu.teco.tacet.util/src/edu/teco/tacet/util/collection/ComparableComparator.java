package edu.teco.tacet.util.collection;

import java.util.Comparator;

public class ComparableComparator<U extends Comparable<U>> implements Comparator<U> {
    @Override
    public int compare(U o1, U o2) {
        return o1 != null ? o1.compareTo(o2) : -1;
    }
}