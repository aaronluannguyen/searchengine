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
        
    }
    
    @Test(timeout=SECOND)
    public void testRemoveMany() {
        
    }
    
    @Test(timeout=SECOND)
    public void testRemoveFromHeapWithSizeOne() {
        
    }
    
    @Test(timeout=SECOND)
    public void testRemoveEqualElements() {
        
    }
    
    @Test(timeout=SECOND)
    public void testPeekMinEmptyContainerException() {
        
    }
    
    @Test(timeout=SECOND)
    public void testPeekMinHeapSizeOne() {
        
    }
    
    @Test(timeout=SECOND)
    public void testPeekMinMany() {
        
    }
    
    @Test(timeout=SECOND) 
    public void testPeekMinTie() {
        
    }
    
    @Test(timeout=SECOND)
    public void testInsertNullException() {
        
    }
    
    @Test(timeout=SECOND) 
    public void testInsertToEmptyHeap() {
        
    }
    
    @Test(timeout=SECOND)
    public void testInsertNewMin() {
        
    }
    
    @Test(timeout=SECOND)
    public void testInsertMany() {
        
    }
    
    @Test(timeout=SECOND)
    public void testInsertAnEqualElement() {
        
    }
    
    @Test(timeout=SECOND)
    public void testSizeZero() {
        
    }
    
    @Test(timeout=SECOND)
    public void testSizeOne() {
        
    }
    
    @Test(timeout=SECOND) 
    public void testSizeMany() {
        
    }
    
    @Test(timeout=SECOND)
    public void testIsEmptyTrue() {
        
    }
    
    @Test(timeout=SECOND)
    public void testIsEmptyFalse() {
        
    }
}
