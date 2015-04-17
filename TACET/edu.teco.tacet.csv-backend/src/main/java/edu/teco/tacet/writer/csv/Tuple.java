package edu.teco.tacet.writer.csv;

public class Tuple<T, V> {

    final T first;
    final V second;

    public Tuple(T first, V second) {
        this.first = first;
        this.second = second;
    }

}
