package edu.teco.tacet.util.test;

import static edu.teco.tacet.util.collection.IterableAdditions.*;
import static edu.teco.tacet.util.collection.ListAdditions.*;
import static edu.teco.tacet.util.collection.ArrayAdditions.*;
import static edu.teco.tacet.util.function.Functions.greaterThan;
import static edu.teco.tacet.util.function.Functions.iAdd;
import static edu.teco.tacet.util.function.Functions.iMul;
import static org.junit.Assert.assertArrayEquals;
import static org.junit.Assert.assertEquals;

import java.util.Arrays;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

import org.junit.BeforeClass;
import org.junit.Test;

import edu.teco.tacet.util.collection.IterableAdditions;

public class IterableAdditionsTest {

    static List<Integer> from1Until10, from0Until10, from0Until3;

    @BeforeClass
    public static void setup() {
        from1Until10 = list(1, 2, 3, 4, 5, 6, 7, 8, 9);
        from0Until10 = list(0, 1, 2, 3, 4, 5, 6, 7, 8, 9);
        from0Until3 =  list(0, 1, 2);
    }

    @Test
    public void filterTest() {
        Iterable<? extends Integer> result1 = filter(greaterThan(4), from0Until10);
        Iterable<? extends Integer> result2 = filter(greaterThan(4), Collections.<Integer>emptyList());

        assertArrayEquals(array(5, 6, 7, 8, 9), toList(result1).toArray());
        assertArrayEquals(array(), toList(result2).toArray());
    }

    @Test
    public void foldTest() {
        int sum = 1 + 2 + 3 + 4 + 5 + 6 + 7 + 8 + 9;
        int prod = 1 * 2 * 3 * 4 * 5 * 6 * 7 * 8 * 9;

        assertEquals((Integer) sum, foldl(iAdd, 0, from1Until10));
        assertEquals((Integer) prod, foldl(iMul, 1, from1Until10));
        assertEquals((Integer) sum, foldr(iAdd, 0, from1Until10));
        assertEquals((Integer) prod, foldr(iMul, 1, from1Until10));
    }

    @Test
    public void takeTest() {
        assertArrayEquals(from0Until3.toArray(), toList(take(5, from0Until3)).toArray());
        assertArrayEquals(from0Until3.toArray(), toList(take(3, from0Until3)).toArray());
        assertArrayEquals(array(), toList(take(0, from0Until3)).toArray());
        assertArrayEquals(array(), toList(take(1, Collections.emptyList())).toArray());
        assertArrayEquals(array(0, 1), toList(take(2, from0Until3)).toArray());
    }
    
    @Test
    public void dropTest() {
        assertArrayEquals(from0Until3.toArray(), toList(drop(0, from0Until3)).toArray());
        assertArrayEquals(array(), toList(drop(5, from0Until3)).toArray());
        assertArrayEquals(array(), toList(drop(3, from0Until3)).toArray());
        assertArrayEquals(array(), toList(drop(1, Collections.emptyList())).toArray());
        assertArrayEquals(array(2), toList(drop(2, from0Until3)).toArray());
        assertArrayEquals(array(1, 2), toList(drop(1, from0Until3)).toArray());
    }
    
    @Test
    public void zipWithTest() {
        assertIterableEquals(map(iMul.apply(2), from0Until3),
            zipWith(iAdd, from0Until3, from0Until3));
        assertIterableEquals(map(iMul.apply(2), from0Until3),
            zipWith(iAdd, from0Until3, from0Until10));
        Iterable<Integer> emptyIterable = IterableAdditions.<Integer>emptyIterable();
        assertIterableEquals(emptyIterable, zipWith(iAdd, emptyIterable, emptyIterable));
    }
    
    @Test
    public void concatTest() {
        assertArrayIterableEquals(array(0, 1, 2, 0, 1, 2),
            IterableAdditions.<Integer>concat(Arrays.asList(from0Until3, from0Until3)));
        Iterable<Integer> emptyIterable = IterableAdditions.<Integer>emptyIterable();
        assertArrayIterableEquals(array(0, 1, 2),
            IterableAdditions.<Integer>concat(
                Arrays.asList(emptyIterable, from0Until3, emptyIterable)));
        assertArrayIterableEquals(new Integer[0], IterableAdditions.<Integer>concat(
            Arrays.asList(emptyIterable)));
        assertArrayIterableEquals(new Integer[0], IterableAdditions.<Integer>concat(
            IterableAdditions.<Iterable<Integer>>emptyIterable()));
        
    }

    @Test
    public void removeDuplicatesTest() {
        assertArrayIterableEquals(array(0, 1, 2, 3),
            IterableAdditions.<Integer>removeDuplicates(iterable(0, 1, 1, 2, 3, 3)));
    }
    
    public static <A> void assertIterableEquals(Iterable<? extends A> expecteds, Iterable<? extends A> actuals) {
        assertArrayEquals(toList(expecteds).toArray(), toList(actuals).toArray());
    }
    
    public static <A> void assertArrayIterableEquals(A[] expecteds, Iterable<? extends A> actuals) {
        assertArrayEquals(expecteds, toList(actuals).toArray());
    }
    
}
