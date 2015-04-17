package edu.teco.tacet.util.test;

import static edu.teco.tacet.util.function.Functions.*;
import static org.junit.Assert.*;

import org.junit.Test;

import edu.teco.tacet.util.function.F1;
import edu.teco.tacet.util.function.F2;

public class FunctionsTest {

    @Test
    public void greaterThanTest() {
        F2<Integer, Integer, Boolean> gt = greaterThan();
        assertTrue(gt.apply(3, 2));
        assertFalse(gt.apply(2, 3));
        assertFalse(gt.apply(2, 2));
        F1<Integer, Boolean> gt2 = greaterThan(2);
        assertTrue(gt2.apply(3));
        assertFalse(gt2.apply(2));
        assertFalse(gt2.apply(1));
    }

    @Test
    public void lessThanTest() {
        F2<Integer, Integer, Boolean> lt = lessThan();
        assertTrue(lt.apply(2, 3));
        assertFalse(lt.apply(3, 2));
        assertFalse(lt.apply(2, 2));
        F1<Integer, Boolean> lt2 = lessThan(2);
        assertTrue(lt2.apply(1));
        assertFalse(lt2.apply(2));
        assertFalse(lt2.apply(3));
    }

    @Test
    public void lessThanOrEqualToTest() {
        F2<Integer, Integer, Boolean> lteq = lessThanOrEqualTo();
        assertTrue(lteq.apply(2, 3));
        assertTrue(lteq.apply(2, 2));
        assertFalse(lteq.apply(2, 1));
        F1<Integer, Boolean> lteq2 = lessThanOrEqualTo(2);
        assertTrue(lteq2.apply(1));
        assertTrue(lteq2.apply(2));
        assertFalse(lteq2.apply(3));
    }
    
    @Test
    public void greaterThanOrEqualToTest() {
        F2<Integer, Integer, Boolean> gteq = greaterThanOrEqualTo();
        assertFalse(gteq.apply(2, 3));
        assertTrue(gteq.apply(2, 2));
        assertTrue(gteq.apply(2, 1));
        F1<Integer, Boolean> lteq2 = greaterThanOrEqualTo(2);
        assertFalse(lteq2.apply(1));
        assertTrue(lteq2.apply(2));
        assertTrue(lteq2.apply(3));
    }
    
    @Test
    public void equalToTest() {
        F2<Integer, Integer, Boolean> eq = equalTo();
        assertTrue(eq.apply(2, 2));
        assertFalse(eq.apply(1, 2));
        F1<Integer, Boolean> eq2 = equalTo(2);
        assertTrue(eq2.apply(2));
        assertFalse(eq2.apply(1));
    }
}
