package edu.teco.tacet.util.function;

public final class Identity<A> extends F1<A,A> {
    @Override
    public A apply(A a) {
        return a;
    }
}