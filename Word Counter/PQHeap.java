package PQ;


import java.util.Comparator;

public class PQHeap<T> {
    T[] heap;
    int size;
    Comparator<T> comparator;
    T last;

    public PQHeap(int initialSize, Comparator<T> comparator) {
        heap = (T[]) new Object[initialSize];
        last = null;
        size = 0;
        this.comparator = comparator;
    }

    private void resize(int newSize) {
        T[] newHeap = (T[]) new Object[newSize];
        if (size + 1 >= 0) System.arraycopy(heap, 0, newHeap, 0, size + 1);
        heap = newHeap;
    }

    public int size(){
        return size;
    }

    public int capacity(){
        return heap.length;
    }

    private int left(int index) {
        return 2 * index;
    }

    private int right(int index) {
        return 2 * index + 1;
    }

    private int parent(int index) {
        return index / 2;
    }

    private void swap(int index1, int index2) {
        T temp = heap[index1];
        heap[index1] = heap[index2];
        heap[index2] = temp;
    }

    private void bubbleUp(int index) {
        if (index == 1) {
            return;
        }

        T myself = heap[index];
        T myParent = heap[parent(index)];

        if (comparator.compare(myself, myParent) > 0) {
            swap(index, parent(index));
            bubbleUp(parent(index));
        }
    }

    private void bubbleDown(int pos) {
        int left = left(pos);
        int right = right(pos);

        while (left <= size) {
            Integer maxIndex = left;
            if (right < this.size && comparator.compare(heap[right], heap[left]) > 0)
                maxIndex = right;
            if (comparator.compare(heap[pos], heap[maxIndex]) < 1) {
                swap(pos, maxIndex);
                pos = maxIndex;
                left = left(pos);
                right = right(pos);
            } else {
                return;
            }
        }
    }

    public void offer(T item) {
        if (size + 1 == heap.length) resize(2 * heap.length);
        heap[size + 1] = item;
        size++;
        bubbleUp(size);
    }

    public T peek() {
        return heap[1];
    }

    public T poll() {
        if (size == heap.length / 4) resize(heap.length / 2);
        T toReturn = peek();
        heap[1] = heap[size];
        size--;
        bubbleDown(1);
        return toReturn;
    }

}