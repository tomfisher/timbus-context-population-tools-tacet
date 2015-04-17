package edu.teco.tacet.util.collection;

import java.util.ArrayList;
import java.util.List;

import edu.teco.tacet.util.function.F1;
import edu.teco.tacet.util.function.F2;
import edu.teco.tacet.util.function.F3;

public class ArrayAdditions {
    @SuppressWarnings("unchecked")
    public static <A> A[] array() {
        return (A[]) new ArrayList<A>().toArray();
    }
    
    @SafeVarargs
    public static <A> A[] array(A... as) {
        return as;
    }
    
    public static <A> F2<A[], Integer, A> index() {
        return new F2<A[], Integer, A>() {
            @Override
            public A apply(A[] array, Integer index) {
                return array[index];
            }
        };
    }
    
    public static <A> F1<Integer, A> indexFrom(final A[] array) {
        return ArrayAdditions.<A>index().apply(array);
    }
    
    public static <A> F3<A[], Integer, A, Void> update() {
        return new F3<A[], Integer, A, Void>() {
            @Override
            public Void apply(A[] array, Integer index, A a) {
                array[index] = a;
                return null;
            }
        };
    }
    
    public static <A> F2<Integer, A, Void> updateFrom(final A[] array) {
        return ArrayAdditions.<A>update().apply(array);
    }
    
    public static <A> F1<A[], Integer> length() {
        return new F1<A[], Integer>() {
            @Override
            public Integer apply(A[] a) {
                return a.length;
            }
        };
    }
    
    public static <A> A find(F1<A, Boolean> p, A[] array) {
        return ArrayAdditions.<A>find().apply(p).apply(array);
    }
    
    public static <A> F2<F1<A, Boolean>, A[], A> find() {
        return new F2<F1<A, Boolean>, A[], A>() {
            @Override
            public A apply(F1<A, Boolean> p, A[] array) {
                for (A a : array) {
                    if (p.apply(a)) {
                        return a;
                    }
                }
                return null;
            }
        };
    }

    public static <A,B> B[] map(F1<A, B> f, A[] as) {
        return ArrayAdditions.<A,B>map().apply(f, as);
    }
    
    public static <A,B> F2<F1<A, B>, A[], B[]> map() {
        return new F2<F1<A, B>, A[], B[]>() {
            @SuppressWarnings("unchecked")
            @Override
            public B[] apply(F1<A, B> f, A[] as) {
                List<B> bs = new ArrayList<>(as.length);
                for (A a : as) {
                    bs.add(f.apply(a));
                }
                return (B[]) bs.toArray();
            }
        };
    }
}
