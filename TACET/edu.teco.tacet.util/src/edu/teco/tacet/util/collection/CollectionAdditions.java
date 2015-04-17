package edu.teco.tacet.util.collection;

import java.util.Collection;

import edu.teco.tacet.util.function.F1;
import edu.teco.tacet.util.function.F2;

public class CollectionAdditions {
    public static <A> F2<Collection<A>, A, Boolean> add() {
        return new F2<Collection<A>, A, Boolean>() {
            @Override
            public Boolean apply(Collection<A> c, A a) {
                return c.add(a);
            }
        };
    }
    
    public static <A> F2<Collection<A>, Collection<A>, Boolean> addAll() {
        return new F2<Collection<A>, Collection<A>, Boolean>() {
            @Override
            public Boolean apply(Collection<A> c1, Collection<A> c2) {
                return c1.addAll(c2);
            }
        };
    }
    
    public static <A> F2<Collection<A>, A, Boolean> contains() {
        return new F2<Collection<A>, A, Boolean>() {
            @Override
            public Boolean apply(Collection<A> c, A a) {
                return c.contains(a);
            }
        };
    }
    
    public static <A> F2<Collection<A>, Collection<A>, Collection<A>> retainAll() {
        return new F2<Collection<A>, Collection<A>, Collection<A>>() {
            @Override
            public Collection<A> apply(Collection<A> a1, Collection<A> a2) {
                a1.retainAll(a2);
                return a1;
            }
        };
    }
    
    public static <A> F1<Collection<A>, Boolean> isEmpty() {
        return new F1<Collection<A>, Boolean>() {
            @Override
            public Boolean apply(Collection<A> a) {
                return a.isEmpty();
            }
        };
    }
    
    public static <A> F1<Collection<A>, Integer> size() {
        return new F1<Collection<A>, Integer>() {
            @Override
            public Integer apply(Collection<A> a) {
                return a.size();
            }
        };
    }
}
