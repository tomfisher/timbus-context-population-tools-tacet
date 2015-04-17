package edu.teco.tacet.util.function;

public abstract class F2<A, B, C> {
    public abstract C apply(A a, B b);

    public F1<B, C> apply(A a) {
        return Curry.uncurry(partial(a));
    }
    
    public F1<B, F0<C>> partial(A a) {
        return Curry.partial(this, a);
    }
    
    public F0<C> partial(A a, B b) {
        return Curry.partial(this, a, b);
    }

    public <D> F2<A, B, D> andThen(final F1<C, D> f) {
        return Functions.compose(this, f);
    }
}
