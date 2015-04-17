package edu.teco.tacet.util.function;

import static edu.teco.tacet.util.function.Curry.*;

public abstract class F3<A, B, C, D> {
    public abstract D apply(A a, B b, C c);
    
    public F1<C,D> apply(A a, B b) {
        return uncurry(partial(a, b));
    }

    public F2<B,C,D> apply(A a) {
        return uncurry2(partial(a));
    }
    
    public F1<B, F1<C, F0<D>>> partial(A a) {
        return Curry.partial(this, a);
    }
    
    public F1<C, F0<D>> partial(A a, B b) {
        return Curry.partial(this, a, b);
    }
    
    public F0<D> partial(A a, B b, C c) {
        return Curry.partial(this, a, b, c);
    }
}
