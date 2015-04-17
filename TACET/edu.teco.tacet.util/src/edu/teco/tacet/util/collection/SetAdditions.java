package edu.teco.tacet.util.collection;

import java.util.Arrays;
import java.util.Set;
import java.util.TreeSet;

import edu.teco.tacet.util.function.F1;
import edu.teco.tacet.util.function.F2;

public class SetAdditions {
    @SafeVarargs
    public static <A> Set<A> set(A... as) {
        Set<A> set = new TreeSet<>();
        set.addAll(Arrays.asList(as));
        return set;
    }
    
    public static <A, B> F2<F1<A, B>, Iterable<A>, Set<B>> map() {
        return new F2<F1<A, B>, Iterable<A>, Set<B>>() {
            @Override
            public Set<B> apply(F1<A, B> f, Iterable<A> itA) {
                Set<B> set = new TreeSet<>();
                for (A a : itA) {
                    set.add(f.apply(a));
                }
                return set;
            }
        };
    }
}
