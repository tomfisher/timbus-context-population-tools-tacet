package edu.teco.tacet.util.collection;

import edu.teco.tacet.util.function.F2;

public class MutableTuple<A, B> {
    private A first;
    private B second;
    
    public MutableTuple(A first, B second) {
        this.first = first;
        this.second = second;
    }
    
    public A getFirst() {
        return first;
    }
    
    public void setFirst(A first) {
        this.first = first;
    }
    
    public B getSecond() {
        return second;
    }
    public void setSecond(B second) {
        this.second = second;
    }
    
    public static <A, B> F2<A, B, MutableTuple<A, B>> mutableTuple() {
        return new F2<A, B, MutableTuple<A, B>>() {
            @Override
            public MutableTuple<A, B> apply(A a, B b) {
                return new MutableTuple<>(a, b);
            }
        };
    }
}
