package datastructures.sorting;

import misc.BaseTest;
import org.junit.Test;

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
        
        for (int i = limit; i >= 0; i++) {
            test.insert(i);
            assertEquals(i, test.peekMin());
        }
        
        for (int i = 0; i <= limit; i++) {
            assertEquals(i, test.peekMin());
            assertEquals(i, test.removeMin());
            assertEquals(limit - i, test.size());
            assertFalse(test.isEmpty());
        }
        
        assertTrue(test.isEmpty());
    }
}
