package datastructures.concrete;

import datastructures.interfaces.IPriorityQueue;
import misc.exceptions.EmptyContainerException;

/**
 * See IPriorityQueue for details on what each method must do.
 */
public class ArrayHeap<T extends Comparable<T>> implements IPriorityQueue<T> {
    // See spec: you must implement a implement a 4-heap.
    private static final int NUM_CHILDREN = 4;

    // You MUST use this field to store the contents of your heap.
    // You may NOT rename this field: we will be inspecting it within
    // our private tests.
    private T[] heap;
    private int length;

    // Feel free to add more fields and constants.

    public ArrayHeap() {
        heap = makeArrayOfT(20);
        length = 0;
    }

    /**
     * This method will return a new, empty array of the given size
     * that can contain elements of type T.
     *
     * Note that each element in the array will initially be null.
     */
    @SuppressWarnings("unchecked")
    private T[] makeArrayOfT(int size) {
        // This helper method is basically the same one we gave you
        // in ArrayDictionary and ChainedHashDictionary.
        //
        // As before, you do not need to understand how this method
        // works, and should not modify it in any way.
        return (T[]) (new Comparable[size]);
    }

    @Override
    public T removeMin() {
        if (this.length == 0) {
            throw new EmptyContainerException("EmptyContainerException");
        }
        
        T min = heap[0];
        
        if (this.length > 1) {
            heap[0] = heap[this.length - 1];
            heap = removeMinHelper(0);
        }
        
        this.length--;
        return min;
    }
    
    private T[] removeMinHelper(int index) {
        int count = 1;
        T min = heap[index];
        int minIndex = index;
        int baseIndex = NUM_CHILDREN * index;
        while (baseIndex + count < this.length && heap[baseIndex + count] != null && count <= NUM_CHILDREN) {
            int current = baseIndex + count;
            if (leq(heap[current], min)) {
                min = heap[current];
                minIndex = current;
            }
            count++;
        }
        
        if (index != minIndex) {
            T temp = heap[minIndex];
            heap[minIndex] = heap[index];
            heap[index] = temp;
            heap = removeMinHelper(minIndex);
        }
        
        return heap;
    }

    @Override
    public T peekMin() {
        if (this.length == 0) {
            throw new EmptyContainerException("EmptyContainerException");
        }
        
        return heap[0];
    }

    @Override
    public void insert(T item) {
        if (item == null) {
            throw new IllegalArgumentException("IllegalArgumentException: null item");
        }
        
        this.length++;
        
        if (this.length % 20 == 0) {
            T[] newHeap = makeArrayOfT(this.length * 2);
            for (int i = 0; i < this.length; i++) {
                newHeap[i] = heap[i];
            }
            heap = newHeap;
        }
        
        heap[this.length - 1] = item;
        
        if (this.length > 1) {
            heap = insertHelper(this.length - 1);
        }
    }
    
    private T[] insertHelper(int index) {
        int parentIndex = (index - 1) / NUM_CHILDREN;
        if (leq(heap[index], heap[parentIndex])) {
            T temp = heap[index];
            heap[index] = heap[parentIndex];
            heap[parentIndex] = temp;
            heap = insertHelper(parentIndex);
        }
        
        return heap;
    }

    @Override
    public int size() {
        return this.length;
    }
    
    private boolean leq(T a, T b) {
        return a.compareTo(b) < 0;
    }
}
