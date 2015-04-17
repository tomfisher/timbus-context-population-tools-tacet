package edu.teco.tacet.util.function;

import java.util.HashMap;
import java.util.Map;

public final class Cached {
    public static <A> F0<A> cached(final F0<A> function) {
        return new F0<A>() {
            A cache = null;

            @Override
            public A apply() {
                if (cache == null) {
                    cache = function.apply();
                }
                return cache;
            }
        };
    }

    public static <A, B> F1<A, B> cached(F1<A, B> function) {
        return new CachedF1<A, B>(function);
    }

    public static <A, B, C> F2<A, B, C> cached(F2<A, B, C> function) {
        return new CachedF2<A, B, C>(function);
    }

    private static class CachedF1<A, B> extends F1<A, B> {
        private Map<A, B> cache = new HashMap<A, B>();
        private F1<A, B> f1;

        public CachedF1(F1<A, B> f1) {
            this.f1 = f1;
        }

        public B apply(A t) {
            if (cache.get(t) == null) {
                cache.put(t, f1.apply(t));
            }
            return cache.get(t);
        }
    }

    private static class CachedF2<A, B, C> extends F2<A, B, C> {
        private Map<A, Map<B, C>> cache = new HashMap<>();
        private F2<A, B, C> f2;

        public CachedF2(F2<A, B, C> f2) {
            this.f2 = f2;
        }

        @Override
        public C apply(A a, B b) {
            if (cache.get(a) == null) {
                cache.put(a, new HashMap<B, C>());
                if (cache.get(a).get(b) == null) {
                    cache.get(a).put(b, f2.apply(a, b));
                }
            }
            return cache.get(a).get(b);
        }
    }
}
