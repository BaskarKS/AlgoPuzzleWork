package veryhard;

import java.util.ArrayList;
import java.util.List;

/*
Build a min Heap from array input of numbers,
* Insert into Heap
* Remove Heap min / root value
* peeking at Heap min / root value
* shift integers up / down the heap which is used when
        inserting / removing values from heap
* heap should be represented in form of array
*/
public class MinHeapForMergeSortedArrays {
    List<MergeSortedArrays.MinNumberDetails> heap = new ArrayList<>();

    public MinHeapForMergeSortedArrays(List<MergeSortedArrays.MinNumberDetails> array) {
        heap = buildHeap(array);
    }

    public boolean isEmpty() {
        return heap.size() == 0;
    }
    // Time Complexity : O(n), Space Complexity : O(1)
    public List<MergeSortedArrays.MinNumberDetails> buildHeap(List<MergeSortedArrays.MinNumberDetails> array) {
        // Write your code here.
        int lastParentIdx = ((array.size() - 1) - 1) / 2;
        while (lastParentIdx >= 0) {
            siftDown(lastParentIdx, array.size() - 1, array);
            lastParentIdx--;
        }
        return array;
    }
    // Time Complexity : O(log(n)), Space Complexity : O(1)
    public void siftDown(int currentIdx, int endIdx, List<MergeSortedArrays.MinNumberDetails> heap) {
        // Write your code here.
        int childOneIdx = (currentIdx * 2) + 1;
        while (childOneIdx <= endIdx) {
            int idxToSwap = childOneIdx;
            int childTwoIdx = (currentIdx * 2) + 2 <= endIdx ? (currentIdx * 2) + 2 : -1;

            if (childTwoIdx != -1 &&
                                        heap.get(childTwoIdx).getElementValue() < heap.get(childOneIdx).getElementValue())
                idxToSwap = childTwoIdx;

            if (heap.get(idxToSwap).getElementValue() < heap.get(currentIdx).getElementValue()) {
                swap(idxToSwap, currentIdx, heap);
                currentIdx = idxToSwap;
                childOneIdx = (currentIdx * 2) + 1;
            } else
                break;
        }
    }
    // Time Complexity : O(log(n)), Space Complexity : O(1)
    public void siftUp(int currentIdx, List<MergeSortedArrays.MinNumberDetails> heap) {
        // Write your code here.
        int parentIdx = (currentIdx - 1) / 2;
        while ((currentIdx > 0) &&
                        (heap.get(currentIdx).getElementValue() < heap.get(parentIdx).getElementValue())) {
            swap(currentIdx, parentIdx, heap);
            currentIdx = parentIdx;
            parentIdx = (currentIdx - 1) / 2;
        }
    }
    // Time Complexity : O(log(n)), Space Complexity : O(1)
    public MergeSortedArrays.MinNumberDetails peek() {
        // Write your code here.
        return heap.get(0);
    }
    // Time Complexity : O(log(n)), Space Complexity : O(1)
    public MergeSortedArrays.MinNumberDetails remove() {
        // Write your code here.
        MergeSortedArrays.MinNumberDetails removedVal = heap.get(0);
        int lastItemIdx = heap.size() - 1;
        swap(0, lastItemIdx, heap);
        heap.remove(lastItemIdx);
        siftDown(0, heap.size() - 1, heap);
        return removedVal;
    }
    // Time Complexity : O(log(n)), Space Complexity : O(1)
    public void insert(MergeSortedArrays.MinNumberDetails value) {
        // Write your code here.
        heap.add(value);
        siftUp(heap.size() - 1, heap);
    }

    public void swap(int idxOne, int idxTwo, List<MergeSortedArrays.MinNumberDetails> heap) {
        MergeSortedArrays.MinNumberDetails temp = heap.get(idxTwo);
        heap.set(idxTwo, heap.get(idxOne));
        heap.set(idxOne, temp);
    }
}
