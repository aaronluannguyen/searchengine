package datastructures.sorting;

import static org.junit.Assert.assertFalse;
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
    
    protected IPriorityQueue<Integer> makeBasicHeap(){
        IPriorityQueue<Integer> heap = this.makeInstance();
        heap.insert(1);
        heap.insert(2);
        heap.insert(3);
        heap.insert(4);
        heap.insert(5);
        return heap;
    }

    @Test(timeout=SECOND)
    public void testBasicSize() {
        IPriorityQueue<Integer> heap = this.makeInstance();
        heap.insert(3);
        assertEquals(1, heap.size());
        assertTrue(!heap.isEmpty());
    }
    
    @Test(timeout=SECOND)
    public void testEmptyContainerException() {
        IPriorityQueue<Integer> heap = this.makeInstance();
        try {
            heap.removeMin();
            fail("Expected EmptyContainerException");           
        }catch(EmptyContainerException ex) {
            //Do nothing: this is expected.
        }
    }
    
    @Test(timeout=SECOND)
    public void testSingleRemove() {
        IPriorityQueue<Integer> heap = this.makeBasicHeap();
        assertEquals(1, heap.removeMin());
    }
    
    @Test(timeout=SECOND)
    public void testRemoveMany() {
        
    }
    
    @Test(timeout=SECOND)
    public void testRemoveFromHeapWithSizeOne() {
        IPriorityQueue<Integer> heap = this.makeInstance();
        heap.insert(1);
        assertEquals(1, heap.removeMin());
    }
    
    @Test(timeout=SECOND)
    public void testRemoveEqualElements() {
        IPriorityQueue<Integer> heap = this.makeBasicHeap();
        heap.insert(1);
        heap.insert(1);        
        
        assertEquals(1, heap.removeMin());
        assertEquals(1, heap.removeMin());
        assertEquals(1, heap.removeMin());
        assertEquals(2, heap.removeMin());
                
    }
    
    @Test(timeout=SECOND)
    public void testPeekMinEmptyContainerException() {
        IPriorityQueue<Integer> heap = this.makeInstance();
        try {
            heap.peekMin();
            fail("Expected EmptyContainerException");           
        }catch(EmptyContainerException ex) {
            //Do nothing: this is expected.
        }
    }
    
    @Test(timeout=SECOND)
    public void testPeekMinHeapSizeOne() {
        
    }
    
    @Test(timeout=SECOND)
    public void testPeekMinMany() {
        
    }
    
    @Test(timeout=SECOND) 
    public void testPeekMinTie() {
        IPriorityQueue<Integer> heap = this.makeBasicHeap();
        heap.insert(1);
        heap.insert(1); 
        
        assertEquals(1, heap.peekMin());
    }
    
    @Test(timeout=SECOND)
    public void testInsertNullException() {
        
    }
    
    @Test(timeout=SECOND) 
    public void testInsertToEmptyHeap() {
        IPriorityQueue<Integer> heap = this.makeInstance();
        heap.insert(2);
        assertEquals(2, heap.peekMin());
        assertEquals(2, heap.removeMin());
    }
    
    @Test(timeout=SECOND)
    public void testInsertMany() {
        
    }
    
    @Test(timeout=SECOND)
    public void testInsertAnEqualElement() {
        IPriorityQueue<Integer> heap = this.makeBasicHeap();
        heap.insert(1);
        heap.insert(1);
        assertEquals(1, heap.removeMin());
        assertEquals(1, heap.removeMin());
        assertEquals(1, heap.removeMin());
    }
    
    @Test(timeout=SECOND)
    public void testSizeZero() {
        IPriorityQueue<Integer> heap = this.makeInstance();
        assertEquals(0, heap.size());
    }
    
    @Test(timeout=SECOND)
    public void testSizeOne() {
        IPriorityQueue<Integer> heap = this.makeInstance();
        heap.insert(1);
        assertEquals(1, heap.size());
    }
    
    @Test(timeout=SECOND) 
    public void testSizeMany() {
        
    }
    
    @Test(timeout=SECOND)
    public void testIsEmptyTrue() {
        IPriorityQueue<Integer> heap = this.makeInstance();
        assertTrue(heap.isEmpty());
    }
    
    @Test(timeout=SECOND)
    public void testIsEmptyFalse() {
        IPriorityQueue<Integer> heap = this.makeBasicHeap();
        assertFalse(heap.isEmpty());
    }
}
