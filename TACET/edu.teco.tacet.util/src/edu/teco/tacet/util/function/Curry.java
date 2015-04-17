package edu.teco.tacet.util.function;

public class Curry {
    public static <A, B> F1<A, F0<B>> curry(final F1<A, B> f) {
        return new F1<A, F0<B>>() {
            @Override
            public F0<B> apply(final A a) {
                return new F0<B>() {
                    @Override
                    public B apply() {
                        return f.apply(a);
                    }
                };
            }
        };
    }

    public static <A, B, C> F1<A, F1<B, F0<C>>> curry(final F2<A, B, C> f2) {
        return new F1<A, F1<B, F0<C>>>() {
            @Override
            public F1<B, F0<C>> apply(final A a) {
                return curry(new F1<B, C>() {
                    @Override
                    public C apply(B b) {
                        return f2.apply(a, b);
                    }
                });
            }
        };
    }

    public static <A, B, C, D> F1<A, F1<B, F1<C, F0<D>>>> curry(final F3<A, B, C, D> f3) {
        return new F1<A, F1<B, F1<C, F0<D>>>>() {
            @Override
            public F1<B, F1<C, F0<D>>> apply(final A a) {
                return curry(new F2<B, C, D>() {
                    @Override
                    public D apply(B b, C c) {
                        return f3.apply(a, b, c);
                    }
                });
            }
        };
    }
    
    public static <A, B> F0<B> partial(F1<A, B> f, A a) {
        return curry(f).apply(a);
    }

    public static <A, B, C> F1<B, F0<C>> partial(F2<A, B, C> f, A a) {
        return curry(f).apply(a);
    }

    public static <A, B, C> F0<C> partial(F2<A, B, C> f2, A a, B b) {
        return curry(f2).apply(a).apply(b);
    }

    public static <A, B, C, D> F1<B, F1<C, F0<D>>> partial(F3<A, B, C, D> f3, A a) {
        return curry(f3).apply(a);
    }

    public static <A, B, C, D> F1<C, F0<D>> partial(F3<A, B, C, D> f3, A a, B b) {
        return curry(f3).apply(a).apply(b);
    }
    
    public static <A, B, C, D> F0<D> partial(F3<A, B, C, D> f3, A a, B b, C c) {
        return curry(f3).apply(a).apply(b).apply(c);
    }

    public static <A, B> F1<A,B> uncurry(final F1<A,F0<B>> f) {
        return new F1<A,B>() {
            @Override
            public B apply(A a) {
                return f.apply(a).apply();
            }
        };
    }
    
    public static <A, B, C> F2<A, B, C> uncurry2(final F1<A, F1<B, F0<C>>> f) {
        return new F2<A, B, C>() {
            @Override
            public C apply(A a, B b) {
                return uncurry(f.apply(a)).apply(b);
            }
        };
    }

    public static <A, B, C, D> F3<A, B, C, D> uncurry3(final F1<A, F1<B, F1<C, F0<D>>>> f) {
        return new F3<A, B, C, D>() {
            @Override
            public D apply(A a, B b, C c) {
                return uncurry2(f.apply(a)).apply(b).apply(c);
            }
        };
    }
}
