package hard;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.function.BiFunction;

public class ContinuousMedian {
    /*
    this class will have a insert method which accepts integer values,
     these values should be maintained in some DS so that the median
     should be computed while insertion itself. whenever we retrieve the median
     value from this class, it should take only constant time.

     the median value for a set of numbers is the middle number when the numbers
     are ordered from lowest to highest.

     if there are odd number count in the set we maintain the numbers then the median
     is the middle number.
     {1, 3, 7} - count is 3
     the median of the above set is 3 which is the middle number

     if there are even number count in the set which we maintain the numbers then the median
     is the average of two middle numbers.
     {1, 3, 7, 8} - count is 4
     the median of the above set is (3 + 7) / 2 => 5 which is the average of two middle numbers

    Solution:
    we can sort the numbers and find the middle numbers and maintain the median,
    but we don't have predefined list of numbers, (here its the flow of numbers,
     which is inserted dynamically). If we use InsertionSort and maintain the number list
     its take huge time for shifting to insert a new number. We can solve this problem using
     Heaps and maintain median in O(log n) time which is the time complexity of
     Heaps(Insert and Remove)

     we need 2 Heaps (Min Heap and a MaxHeap)
     the root of MinHeap is lower number
     the root of MaxHeap is Higher number.

    we create 2 group(MinHeap, MaxHeap) and insert the input number into the relevant groups
    accordingly

    lowest(group) is MaxHeap, largest(group) is MinHeap

    1. initially we insert the first number into the 'lowest' group
    2. we always see the input number is larger than the root of 'lowest', if its larger then we insert it
    to the 'largest'.
    3. if the input number is smaller than the root of 'lowest' then we insert it to the 'lowest' itself
    4. we balance both the groups so the child counts exceeds the count of 1 which is 2.
         If the child count is greater than 1 which is 2 in one of the group(lowest, largest). we remove
        the more count(child count is 2) group and add it to the less count group.
    5. Update the median,
            If the child count is not larger than 1 then we update the median based on the
            guidelines below
            a. if both(lowest, largest) group length of child's are equal, we take the root value of
            both the groups and calculate average of both values and update the median.
            b. if the 'lowest' group child length is 1 greater than 'largest' group length then we update
            the median with the 'lowest' group root value.
            c. if the 'largest' group child length is 1 greater than 'lowest' group length then we update
            the median with the 'largest' group root value.

            Example:
            ContinuousMedianHandler()
            insert(5)
            insert(10)
            getMedian() => 7.5
            insert(100)
            getMedian() => 10
    * */

    static class ContinuousMedianHandler {
        double median = 0;
        private Heaps smaller;
        private Heaps larger;
        public ContinuousMedianHandler() {
            this.smaller = new Heaps(Heaps::maxHeap, new ArrayList<>());
            this.larger = new Heaps(Heaps::minHeap, new ArrayList<>());
        }

        // Time : O(log n), Space : O(n)
        public void insert(int number) {
            // Write your code here.
            if (this.smaller.length() == 0 || number < this.smaller.peek()) {
                this.smaller.insert(number);
            } else {
                this.larger.insert(number);
            }
            reBalanceHeaps();
            updateMedian();
        }
        public void reBalanceHeaps() {
            if (this.smaller.length() - this.larger.length() == 2) {
                this.larger.insert(this.smaller.remove());
            } else if (this.larger.length() - this.smaller.length() == 2) {
                this.smaller.insert(this.larger.remove());
            }
        }

        public void updateMedian() {
            if (this.smaller.length == this.larger.length) {
                this.median = ((double)this.smaller.peek() + (double)this.larger.peek()) / 2;
            } else if (this.smaller.length > this.larger.length) {
                this.median = this.smaller.peek();
            } else {
                this.median = this.larger.peek();
            }
        }

        public double getMedian() {
            return median;
        }
    }

    static class Heaps {

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
}
