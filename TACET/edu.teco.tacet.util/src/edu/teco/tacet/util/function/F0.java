package edu.teco.tacet.util.function;

public abstract class F0<A> {
    public abstract A apply();
    
    public <B> F1<A, B> andThen(final F1<A, B> f) {
        return Functions.compose(this, f);
    }
}
