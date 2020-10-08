package hard;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.function.BiFunction;

public class Heaps {
    public static void main(String[] args) {
        Heaps maxHeap = new Heaps(Heaps::minHeap, new ArrayList<>());
        System.out.println(maxHeap.length());
        maxHeap.insert(10);
        maxHeap.insert(70);
        maxHeap.insert(20);
        maxHeap.insert(30);
        maxHeap.insert(50);
        maxHeap.insert(11);
        maxHeap.insert(30);
        System.out.println(maxHeap.length());
        maxHeap.printHeap();
        System.out.println(maxHeap.remove());
        System.out.println(maxHeap.length());
        maxHeap.printHeap();
        System.out.println(maxHeap.remove());
        System.out.println(maxHeap.length());
        maxHeap.printHeap();
        System.out.println(maxHeap.remove());
        System.out.println(maxHeap.length());
        maxHeap.printHeap();
    }


    public void printHeap() {
        System.out.println(this.heap);
    }
    public static Boolean maxHeap(Integer from, Integer to) {
        return from > to;
    }
    public static Boolean minHeap(Integer from, Integer to) {
        return from < to;
    }
    private List<Integer> heap = new ArrayList<>();
    private int length = 0;
    private BiFunction<Integer, Integer, Boolean> compareValues;
    public Heaps(BiFunction<Integer, Integer, Boolean> func,
                 List<Integer> values) {
        this.compareValues = func;
        Collections.copy(this.heap, values);
        this.length = this.heap.size();
        int lastParentIdx = (values.size() - 2) / 2;
        while(lastParentIdx >= 0) {
            bubbleDown(lastParentIdx);
            lastParentIdx -= 1;
        }
    }
    public void swap(Integer idx1, Integer idx2) {
        Integer temp  = heap.get(idx1);
        heap.set(idx1, heap.get(idx2));
        heap.set(idx2, temp);
    }
    public void bubbleDown(int fromIdx) {
        int endIdx = length - 1;
        int leftChildIdx = fromIdx * 2 + 1;
        while (leftChildIdx <= endIdx) {
            int idxToSwap = leftChildIdx;
            int rightChildIdx = (fromIdx * 2 + 2) <= endIdx ? (fromIdx * 2 + 2) : -1;
            if (rightChildIdx != -1 &&
                            compareValues.apply(heap.get(rightChildIdx), heap.get(leftChildIdx))) {
                idxToSwap = rightChildIdx;
            }
            if (compareValues.apply(heap.get(idxToSwap), heap.get(fromIdx))) {
                swap(fromIdx, idxToSwap);
                fromIdx = idxToSwap;
                leftChildIdx = (fromIdx * 2) + 1;
            } else
                break;
        }
    }

    public void bubbleUp(Integer fromIdx) {
        int parentIdx = (fromIdx - 1) / 2;
        while (parentIdx >= 0) {
            if (compareValues.apply(heap.get(fromIdx), heap.get(parentIdx))) {
                swap(fromIdx, parentIdx);
                fromIdx = parentIdx;
                parentIdx = (fromIdx - 1) / 2;
            } else
                break;
        }
    }

    public void bubbleUpDiff(Integer fromIdx) {
        int parentIdx = (fromIdx - 1) / 2;
        while (fromIdx > 0 &&
                        compareValues.apply(heap.get(fromIdx), heap.get(parentIdx))) {
            swap(fromIdx, parentIdx);
            fromIdx = parentIdx;
            parentIdx = (fromIdx - 1) / 2;
        }
    }

    public void insert(Integer value) {
        this.heap.add(value);
        this.length += 1;
        bubbleUp(this.length - 1);
    }

    public Integer remove() {
        swap(0, length - 1);
        int removedValue = heap.get(length - 1);
        heap.remove(length - 1);
        length -= 1;
        bubbleDown(0);
        return removedValue;
    }

    public Integer peek() {
        return this.heap.get(0);
    }

    public Integer length() {
        return length;
    }
}
