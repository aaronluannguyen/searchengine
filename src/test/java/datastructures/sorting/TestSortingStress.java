package datastructures.sorting;

import misc.BaseTest;
import misc.Searcher;

import org.junit.Test;


import datastructures.concrete.DoubleLinkedList;
import datastructures.interfaces.IList;

import datastructures.concrete.ArrayHeap;

import datastructures.interfaces.IPriorityQueue;

import static org.junit.Assert.assertFalse;

import static org.junit.Assert.assertTrue;

/**
 * See spec for details on what kinds of tests this class should include.
 */
public class TestSortingStress extends BaseTest {
    protected <T extends Comparable<T>> IPriorityQueue<T> makeInstance() {
        return new ArrayHeap<>();
    }
    
    @Test(timeout=10*SECOND)
    public void testStressForArrayHeap() {
        int limit = 100000;
        IPriorityQueue<Integer> test = this.makeInstance();
        
        for (int i = limit; i >= 0; i--) {
            test.insert(i);
            assertEquals(i, test.peekMin());
        }
        
        for (int i = 0; i <= limit; i++) {
            assertFalse(test.isEmpty());
            assertEquals(i, test.peekMin());
            assertEquals(i, test.removeMin());
            assertEquals(limit - i, test.size());            
        }
        
        assertTrue(test.isEmpty());
    }
    
    @Test(timeout=15*SECOND)
    public void testStressForTopKSort() {
        int limit = 100000;
        IList<Integer> list = new DoubleLinkedList<>();
        for(int i = 0; i < limit; i++) {
            list.add(i);
        }
        IList<Integer> top = Searcher.topKSort(50000, list);
        for(int i = 0 ; i < top.size(); i++) {
            assertEquals(50000 + i, top.get(i));
        }
    }
}

