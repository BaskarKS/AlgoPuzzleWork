package hard;

import java.util.Arrays;

/*
Heap sort takes in array of integers and returns a sorted version of that array,
Heap sort algorithm to sort.

input : [8, 5, 2, 9, 5, 6, 3]
output : [2, 3, 5, 5, 6, 8, 9]

we solve this
1. relocate items in array to transform into a Heap, by Heapify
2. since we want the input array to be sorted in ascending fashion
3, use a max heap, so that parent is greater than both of its children
4. Hence the largest element in array will be at the root
5. iterate from end to start of array
6. to remove a item in heap the root is pop out, last item will be moved to root location
     and bubble down to its right location
7. If we remove the root which is arrays 0th location and replace the root with last element
of array, last location will be empty location
8. we take the root value and swap it with the last location value and bubble down the root
value to its right location, Hence the second largest in array will arrive at root.
* */
public class HeapSort {
    public static void main(String[] args) {
        int[] array = new int[] {8, 5, 2, 9, 5, 6, 3};
        heapSort(array);
    }

    // Time : O(n log(n)), Space : O(1)
    public static int[] heapSort(int[] array) {
        heapify(array); // first transform the array into heap form all parent should be greater than its children
        for (int idx = array.length - 1; idx > 0; idx--) {
            swap(array, 0, idx); // push the larger value to the end of the array and move the end of array value to root location to bubble it down
            bubbleDown(array, 0, idx - 1); // bubble down the root value till the end
        }
        return array;
    }

    public static void bubbleDown(int[] array, int start, int end) {
        int leftChildIdx = start * 2 + 1;
        int rightChildIdx = start * 2 + 2;
        int largeIdx = start; // assume initially parent is bigger among childs
        if (leftChildIdx <= end && array[leftChildIdx] > array[largeIdx]) // track whether left child is bigger than parent
            largeIdx = leftChildIdx;
        if (rightChildIdx <= end && array[rightChildIdx] > array[largeIdx]) // track whether right child is larger among parent and left child
            largeIdx = rightChildIdx;
        if (start == largeIdx) // bubble down till we could see the parent is largest among the children
            return;
        swap(array, start, largeIdx); // swap the parent location with the largest child
        bubbleDown(array, largeIdx, end); // largest child index will be next parent to bubble it down
    }
    // to transform a array items to rearrange the items to reflect a heap(max heap)
    public static void heapify(int[] array) {
        int lastParentIdx = (array.length - 2) / 2;
        for (int idx = lastParentIdx; idx >= 0; idx--) { // iterating from last parent to start of array
            int parentIdx = idx;
            while (parentIdx < array.length) { // loop from the picked item to the end of array
                int leftChildIdx = parentIdx * 2 + 1; // calculate left child index
                int rightChildIdx = parentIdx * 2 + 2; // calculate right child index
                int largeIdx = parentIdx; // assume the picked item index is the largest among its children
                if (leftChildIdx < array.length &&
                    array[leftChildIdx] > array[largeIdx]) // left child is compared with the picked item, if left child is bigger, track the left child index as big
                    largeIdx = leftChildIdx;
                if (rightChildIdx < array.length &&           // compare the right child item with the biggest among the (parent and left child)
                    array[rightChildIdx] > array[largeIdx])  // if right child is bigger among parent and left child, track it
                    largeIdx = rightChildIdx;
                if (parentIdx == largeIdx) // if parent is the largest among the children means, down the tree all items are properly located, hence we quit
                    break;
                swap(array, parentIdx, largeIdx); // swap the parent with the largest children
                parentIdx = largeIdx; // now the child location will become the parent location, navigate from the largest child to downwards
            }
        }
    }
    public static void swap(int[] array, int from, int to) {
        int temp = array[to];
        array[to] = array[from];
        array[from] = temp;
    }
}
