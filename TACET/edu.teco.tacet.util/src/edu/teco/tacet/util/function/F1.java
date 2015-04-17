package edu.teco.tacet.util.function;

public abstract class F1<A, B> {
    public abstract B apply(A a);

    public <C> F1<A, C> andThen(final F1<B, C> f1) {
        return Functions.compose(this, f1);
    }
    
    public F0<B> partial(final A a) {
        return new F0<B>() {
            @Override
            public B apply() {
                return F1.this.apply(a);
            }
        };
    }
}
