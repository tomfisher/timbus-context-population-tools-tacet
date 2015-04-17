package edu.teco.tacet.track;

import java.util.List;

public interface MergeStrategy<T> {

    Iterable<? extends T> merge(List<Iterable<? extends T>> values);

}
