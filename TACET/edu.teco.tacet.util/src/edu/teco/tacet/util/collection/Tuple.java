package edu.teco.tacet.util.collection;

import edu.teco.tacet.util.function.F2;

public final class Tuple<A, B> {
    private final A first;
    private final B second;
    
    public Tuple(A first, B second) {
        this.first = first;
        this.second = second;
    }

    public A getFirst() {
        return first;
    }

    public B getSecond() {
        return second;
    }

    public static <A, B> F2<A, B, Tuple<A, B>> tuple() {
        return new F2<A, B, Tuple<A, B>>() {
            @Override
            public Tuple<A, B> apply(A a, B b) {
                return new Tuple<>(a, b);
            }
        };
    }
    
}
