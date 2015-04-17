package edu.teco.tacet.util.test;

import static edu.teco.tacet.util.collection.CList.cList;
import static edu.teco.tacet.util.collection.CList.cons;
import static edu.teco.tacet.util.collection.IterableAdditions.iterable;
import static edu.teco.tacet.util.collection.ListAdditions.list;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import org.junit.Test;

import edu.teco.tacet.util.collection.CList;

public class CListTest {
    
    CList<Long> explicitConsList =
        cons(1l, cons(2l, cons(3l, CList.<Long>nil())));
    
    CList<Long> listFromArray = cList(1l, 2l, 3l);
    
    CList<Long> listFromIterable = cList(list(1l, 2l, 3l));
    
    Iterable<CList<Long>> allLists =
        iterable(explicitConsList, listFromArray, listFromIterable);

    @Test
    public void headAndTailTest() {
        for (CList<Long> list : allLists) {
            assertEquals((Long) 1l, list.getHead());
            assertEquals(null, list.getTail().getTail().getTail().getHead());
            assertEquals(list.getHead(), list.getHead());
            assertEquals(list.getTail(), list.getTail());
        }
    }
    
    @Test
    public void iterableTest() {
        for (CList<Long> list : allLists) {
            long elt = 0;
            for (Long l : list) {
                assertEquals((long) l, ++elt);
            }
        }
    }
    
    @Test
    public void structuralSharingTest() {
        Object o1 = new Object();
        Object o2 = new Object();
        Object o3 = new Object();
        
        CList<Object> l1 = cList(o1, o2);
        CList<Object> l2 = cons(o3, l1.getTail());
        
        assertTrue(o2 == l2.getTail().getHead());
    }
}
