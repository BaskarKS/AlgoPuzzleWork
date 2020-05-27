package medium;
import java.util.ArrayList;
import java.util.List;

/*
Build a max Heap from array input of numbers,
* Insert into Heap
* Remove Heap min / root value
* peeking at Heap min / root value
* shift integers up / down the heap which is used when
        inserting / removing values from heap
* heap should be represented in form of array
*/
public class MaxHeap {
    List<Integer> heap = new ArrayList<Integer>();

    public MaxHeap(List<Integer> array) {
        heap = buildHeap(array);
    }

    public List<Integer> buildHeap(List<Integer> array) {
        // Write your code here.
        int lastParentIdx = ((array.size() - 1) - 1) / 2;
        while (lastParentIdx >= 0) {
            siftDown(lastParentIdx, array.size() - 1, array);
            lastParentIdx--;
        }
        return array;
    }

    public void siftDown(int currentIdx, int endIdx, List<Integer> heap) {
        // Write your code here.
        int childOneIdx = (currentIdx * 2) + 1;
        while (childOneIdx <= endIdx) {
            int idxToSwap = childOneIdx;
            int childTwoIdx = (currentIdx * 2) + 2 <= endIdx ? (currentIdx * 2) + 2 : -1;

            if (childTwoIdx != -1 &&
                heap.get(childTwoIdx) > heap.get(childOneIdx))
                idxToSwap = childTwoIdx;

            if (heap.get(idxToSwap) > heap.get(currentIdx)) {
                swap(idxToSwap, currentIdx, heap);
                currentIdx = idxToSwap;
                childOneIdx = (currentIdx * 2) + 1;
            } else
                break;
        }
    }

    public void siftUp(int currentIdx, List<Integer> heap) {
        // Write your code here.
        int parentIdx = (currentIdx - 1) / 2;
        while ((currentIdx > 0) &&
            (heap.get(currentIdx) > heap.get(parentIdx))) {
            swap(currentIdx, parentIdx, heap);
            currentIdx = parentIdx;
            parentIdx = (currentIdx - 1) / 2;
        }
    }

    public int peek() {
        // Write your code here.
        return heap.get(0);
    }

    public int remove() {
        // Write your code here.
        int removedVal = heap.get(0);
        int lastItemIdx = heap.size() - 1;
        swap(0, lastItemIdx, heap);
        heap.remove(lastItemIdx);
        siftDown(0, heap.size() - 1, heap);
        return removedVal;
    }

    public void insert(int value) {
        // Write your code here.
        heap.add(value);
        siftUp(heap.size() - 1, heap);
    }

    public void swap(int idxOne, int idxTwo, List<Integer> heap) {
        int temp = heap.get(idxTwo);
        heap.set(idxTwo, heap.get(idxOne));
        heap.set(idxOne, temp);
    }
}

