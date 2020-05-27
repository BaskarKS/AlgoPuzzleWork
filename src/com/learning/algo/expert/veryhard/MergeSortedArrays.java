package veryhard;

import java.util.ArrayList;
import java.util.List;

import medium.MinHeap;

public class MergeSortedArrays {
    /*
    function takes in non - empty list of non-empty sorted arrays of Integers and
    returns a merged list of all of those arrays.

    The integer in the merged list should be in sorted order

    eg:
    Ip:
    arrays = [[1, 5, 9, 21],
                    [-1, 0],
                    [-124, 81, 121],
                    [3, 6, 12, 20, 150]]
    op:
    [-124, -1, 0, 1, 3, 5, 6, 9, 12, 20, 21, 81, 121, 150]

    */
    public static void main(String[] args) {
        List<List<Integer>> sortedLists = new ArrayList<>();
        sortedLists.add(List.of(-2, 4, 6));
        sortedLists.add(List.of(-10, 11, 12, 13));
        sortedLists.add(List.of(-1, 0));
        List<Integer> result = mergeSortedArraysBetter(sortedLists);
        System.out.println(result);
    }

    // Time : O(nk) - Space - O(n + k) - k is the size of total individual arrays in passed array
    public static List<Integer> mergeSortedArrays(List<List<Integer>> arrays) {
        // Write your code here.
        List<Integer> result = new ArrayList<Integer>();
        if (arrays == null || arrays.size() == 0)
            return result;
        int totalCount = getTotalSize(arrays);
        int[] indexes = new int[arrays.size()]; // O(K) Space
        int maxLoop = arrays.size();
        while (result.size() < totalCount) { // O(N) iterations
            int toAdd = Integer.MAX_VALUE;
            int[] toAddListIdx = new int[]{0, 0};
            int idx = 0;
            while (idx < maxLoop) { // for each element O(K) iterations, K is size of outer array
                if (indexes[idx] < arrays.get(idx).size() &&
                    arrays.get(idx).get(indexes[idx]) < toAdd) {
                    toAdd = arrays.get(idx).get(indexes[idx]);
                    toAddListIdx[0] = idx;
                    toAddListIdx[1] = indexes[idx];
                }
                idx++;
            }
            indexes[toAddListIdx[0]] = toAddListIdx[1] + 1;
            result.add(toAdd);
        }
        return result;
    }

    public static int getTotalSize(List<List<Integer>> arrays) {
        int totalCount = 0;
        for (List<Integer> subArray : arrays)
            totalCount += subArray.size();
        return totalCount;
    }

    // ----------------------------------------------_Better Implementation ----------------------------------------------------
    // k is the size of total individual arrays in passed array
    // Time : O(n log(k) + k) - log(k) is to remove and insert element in min heap
    // and k is the initial size to build min heap
    // Space - O(n + k)

    public static List<Integer> mergeSortedArraysBetter(List<List<Integer>> arrays) {
        List<MinNumberDetails> initialElements = new ArrayList<>();
        List<Integer> result = new ArrayList<>();

        for (int idx = 0; idx < arrays.size(); idx++) {
            initialElements.add(new MinNumberDetails(idx, 0, arrays.get(idx).get(0)));
        }
        MinHeapForMergeSortedArray heap = new MinHeapForMergeSortedArray(initialElements);

        while (!heap.isEmpty()) {
            MinNumberDetails minElement = heap.remove();
            result.add(minElement.getElementValue());
            if (minElement.getElementIdx() == arrays.get(minElement.getArrayIdx()).size() - 1)
                continue;
            heap.insert(new MinNumberDetails(minElement.arrayIdx,
                                                    minElement.elementIdx + 1,
                                                                       arrays.get(minElement.arrayIdx).get(minElement.getElementIdx() + 1)));
        }
        return result;
    }

    static class MinNumberDetails {
        public MinNumberDetails(int arrayIdx, int elementIdx, int elementValue) {
            this.arrayIdx = arrayIdx;
            this.elementIdx = elementIdx;
            this.elementValue = elementValue;
        }
        int arrayIdx;

        public int getArrayIdx() {
            return arrayIdx;
        }

        public void setArrayIdx(int arrayIdx) {
            this.arrayIdx = arrayIdx;
        }

        public int getElementIdx() {
            return elementIdx;
        }

        public void setElementIdx(int elementIdx) {
            this.elementIdx = elementIdx;
        }

        public int getElementValue() {
            return elementValue;
        }

        public void setElementValue(int elementValue) {
            this.elementValue = elementValue;
        }

        int elementIdx;
        int elementValue;
    }

    static class MinHeapForMergeSortedArray {
        List<MinNumberDetails> heap = new ArrayList<>();

        public MinHeapForMergeSortedArray(List<MinNumberDetails> array) {
            heap = buildHeap(array);
        }

        public boolean isEmpty() {
            return heap.size() == 0;
        }
        // Time Complexity : O(n), Space Complexity : O(1)
        public List<MinNumberDetails> buildHeap(List<MinNumberDetails> array) {
            // Write your code here.
            int lastParentIdx = ((array.size() - 1) - 1) / 2;
            while (lastParentIdx >= 0) {
                siftDown(lastParentIdx, array.size() - 1, array);
                lastParentIdx--;
            }
            return array;
        }
        // Time Complexity : O(log(n)), Space Complexity : O(1)
        public void siftDown(int currentIdx, int endIdx, List<MinNumberDetails> heap) {
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
        public void siftUp(int currentIdx, List<MinNumberDetails> heap) {
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
        public MinNumberDetails peek() {
            // Write your code here.
            return heap.get(0);
        }
        // Time Complexity : O(log(n)), Space Complexity : O(1)
        public MinNumberDetails remove() {
            // Write your code here.
            MinNumberDetails removedVal = heap.get(0);
            int lastItemIdx = heap.size() - 1;
            swap(0, lastItemIdx, heap);
            heap.remove(lastItemIdx);
            siftDown(0, heap.size() - 1, heap);
            return removedVal;
        }
        // Time Complexity : O(log(n)), Space Complexity : O(1)
        public void insert(MinNumberDetails value) {
            // Write your code here.
            heap.add(value);
            siftUp(heap.size() - 1, heap);
        }

        public void swap(int idxOne, int idxTwo, List<MinNumberDetails> heap) {
            MinNumberDetails temp = heap.get(idxTwo);
            heap.set(idxTwo, heap.get(idxOne));
            heap.set(idxOne, temp);
        }
    }
}