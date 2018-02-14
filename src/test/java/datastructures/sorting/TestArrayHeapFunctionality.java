package datastructures.sorting;

import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;

import misc.BaseTest;
import misc.exceptions.EmptyContainerException;
import datastructures.concrete.ArrayHeap;
import datastructures.interfaces.IPriorityQueue;
import org.junit.Test;

/**
 * See spec for details on what kinds of tests this class should include.
 */
public class TestArrayHeapFunctionality extends BaseTest {
    protected <T extends Comparable<T>> IPriorityQueue<T> makeInstance() {
        return new ArrayHeap<>();
    }
    
    protected IPriorityQueue<Integer> makeBasic() {
        IPriorityQueue<Integer> basic = this.makeInstance();
        basic.insert(1);
        basic.insert(2);
        basic.insert(3);
        basic.insert(4);
        basic.insert(5);
        return basic;
    }

    @Test(timeout=SECOND)
    public void testBasicSize() {
        IPriorityQueue<Integer> heap = this.makeInstance();
        heap.insert(3);
        assertEquals(1, heap.size());
        assertTrue(!heap.isEmpty());
    }
    
    @Test(timeout=SECOND)
    public void testForGaps() {
        
    }
    
    @Test(timeout=SECOND)
    public void testEmptyContainerException() {
        IPriorityQueue<Integer> empty = this.makeInstance();
        try {
            empty.removeMin();
            fail("Expected EmptyContainerException");
        } catch (EmptyContainerException ex) {
            // Do nothing: this is ok
        }
    }
    
    @Test(timeout=SECOND)
    public void testSingleRemove() {
        IPriorityQueue<Integer> test = this.makeBasic();
        assertEquals(1, test.removeMin());
    }
    
    @Test(timeout=SECOND)
    public void testRemoveMany() {
        IPriorityQueue<Integer> test = this.makeInstance();
        for (int i = 0; i < 250; i++) {
            test.insert(i);
        }
        
        for (int i = 0; i < 250; i++) {
            assertEquals(i, test.removeMin());
        }
    }
    
    @Test(timeout=SECOND)
    public void testRemoveFromHeapWithSizeOne() {
        // ANI
    }
    
    @Test(timeout=SECOND)
    public void testRemoveEqualElements() {
        // ANI
    }
    
    @Test(timeout=SECOND)
    public void testPeekMinEmptyContainerException() {
        IPriorityQueue<Integer> empty = this.makeInstance();
        try {
            empty.peekMin();
            fail("Expected EmptyContainerException");
        } catch (EmptyContainerException ex) {
            // Do nothing: this is ok
        }
    }
    
    @Test(timeout=SECOND)
    public void testPeekMinHeapSizeOne() {
        IPriorityQueue<Integer> test = this.makeInstance();
        test.insert(5);
        assertEquals(5, test.peekMin());
    }
    
    @Test(timeout=SECOND)
    public void testPeekMinMany() {
        IPriorityQueue<Integer> test = this.makeInstance();
        for (int i = 0; i < 250; i++) {
            test.insert(i);
        }
        
        for (int i = 0; i < 250; i++) {
            assertEquals(i, test.peekMin());
            test.removeMin();
        }
    }
    
    @Test(timeout=SECOND) 
    public void testPeekMinTie() {
        // ANI
    }
    
    @Test(timeout=SECOND)
    public void testInsertNullException() {
        IPriorityQueue<Integer> test = this.makeInstance();
        try {
            test.insert(null);
            fail("Expected IllegalArgumentException");
        } catch (IllegalArgumentException ex) {
            // Do nothing: this is ok
        }
    }
    
    @Test(timeout=SECOND) 
    public void testInsertToEmptyHeap() {
        // ANI
    }
    
    @Test(timeout=SECOND)
    public void testInsertNewMin() {
        IPriorityQueue<Integer> test = this.makeBasic();
        test.insert(0);
        assertEquals(0, test.peekMin());
        
        test.insert(-5);
        assertEquals(-5, test.peekMin());
    }
    
    @Test(timeout=SECOND)
    public void testInsertMany() {
        IPriorityQueue<Integer> test = this.makeInstance();
        for (int i = 0; i < 250; i++) {
            test.insert(i);
        }
        
        for (int i = 0; i < 250; i++) {
            assertEquals(i, test.removeMin());
        }
    }
    
    @Test(timeout=SECOND)
    public void testInsertManyNewMins() {
        IPriorityQueue<Integer> test = this.makeInstance();
        for (int i = 250; i >= 0; i--) {
            test.insert(i);
        }
        
        for (int i = 0; i <= 250; i++) {
            assertEquals(i, test.removeMin());
        }
    }
    
    @Test(timeout=SECOND)
    public void testInsertAnEqualElement() {
        // ANI
    }
    
    @Test(timeout=SECOND)
    public void testSizeZero() {
        // ANI
    }
    
    @Test(timeout=SECOND)
    public void testSizeOne() {
        // ANI
    }
    
    @Test(timeout=SECOND) 
    public void testSizeMany() {
        IPriorityQueue<Integer> test = this.makeInstance();
        for (int i = 0; i < 250; i++) {
            test.insert(i);
            assertEquals(i + 1, test.size());
        }
    }
    
    @Test(timeout=SECOND)
    public void testIsEmptyTrue() {
        // ANI
    }
    
    @Test(timeout=SECOND)
    public void testIsEmptyFalse() {
        // ANI
    }
}
