package datastructures.sorting;

import misc.BaseTest;
import misc.Searcher;

import org.junit.Test;

import datastructures.concrete.DoubleLinkedList;
import datastructures.interfaces.IList;

import static org.junit.Assert.assertTrue;

/**
 * See spec for details on what kinds of tests this class should include.
 */
public class TestSortingStress extends BaseTest {
    @Test(timeout=10*SECOND)
    public void testPlaceholder() {
        // TODO: replace this placeholder with actual code
        assertTrue(true);
    }
    
    @Test(timeout=10*SECOND)
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
