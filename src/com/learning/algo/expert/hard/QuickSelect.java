package hard;

import java.util.Arrays;

public class QuickSelect {
    public static void main(String[] args) {
        int[] array = new int[] {5, 2, 8, 1, 2, 0, 15};
        System.out.println(Arrays.toString(array));
        int result = quickSelect(array, 1);
        System.out.println(Arrays.toString(array));
        System.out.println(result);
    }

    /*
    This is the correct algorithm implementation.
    This function will takes in a array of distinct integers as well as an Integer K
    this function returns the K'th smallest element in the array.

    This function should run in linear time on average case

    Eg:
    Ip : [8, 5, 2, 9, 7, 6, 3] , k = 3
    op : 5

    We use Quick sort algorithm to sort the array, this quick select algorithm also
    developed by the same creator of QuickSort founder.

    we use the same quick sort mechanism,
    1. by deciding a pivot element,
    2. partition the array into two,
    3. lowest to pivot is moved to its left,
    4. highest to pivot is moved to right of pivot,
    5. this make the pivot relocated to its correct position.
    Recursively follow the above 5 steps on left half and right half to sort the entire array

     In this algorithm, we know the position, kth smallest / kth largest
     everytime we get a correct pivot index after following above 5 steps,
     we check with the kth element, if it matches we return the element directly.
     if the kth value is lower than the pivot index we avoid the right half and traverse in left half
     if the kth value is greater than the pivot index we avoid the left half and traverse in right half

    */
    // Time : O(n) on Best and Average case, Space : O(1)
    public static int quickSelect(int[] array, int target) {
        if (array == null || array.length == 0 || target > array.length)
            return -1;
        int position = target - 1;
        int startIdx = 0;
        int endIdx = array.length - 1;
        while (startIdx <= endIdx) { // start crosses end if the entire array is traversed
            int pivotIdx = startIdx; // we chose the first element as pivot
            int leftIdx = startIdx + 1;
            int rightIdx = endIdx;
            //one pointer traverse from left and another traverse from right to find the spot to place the pivot at its correct position
            while (leftIdx <= rightIdx) { // logic of deciding a pivot and relocating elements and finding the exact pivot element index
                if (array[leftIdx] > array[pivotIdx] && array[rightIdx] < array[pivotIdx]) // if left element is greater and right element is smaller than pivot its swapped to proper positions
                    swap(array, leftIdx, rightIdx);
                if (array[leftIdx] <= array[pivotIdx]) //if left element is smaller and equal than pivot we just move ahead
                    leftIdx++;
                if (array[rightIdx] >= array[pivotIdx]) //if right element is greater and equal than pivot we just move ahead
                    rightIdx--;
            }
            swap(array, pivotIdx, rightIdx); // after above loop, rightIdx is the correct pivot Idx
            if (position == rightIdx) // if the pivot idx matches with the position we return the result
                return array[rightIdx];
            else if (position < rightIdx) // if position is lesser than pivot idx, we traverse left half by changing the end
                endIdx = rightIdx - 1;
            else // if position is greater than pivot idx, we traverse right half by changing the start
                startIdx = rightIdx + 1;
        }
        return -1;
    }
//    -----------------------------------------------------------------------------------------------------------
    private static void swap(int[] array, int posOne, int posTwo) {
        int temp = array[posOne];
        array[posOne] = array[posTwo];
        array[posTwo] = temp;
    }
//    -----------------------------------------------------------------------------------------------------------
    public static int myQuickSelect(int[] array, int target) {
        quickSort(array, 0, array.length - 1);
        if (array != null &&
            target > 0 && target <= array.length)
            return array[target - 1];
        else
            return -1;
    }
    public static void quickSort(int[] array, int start, int end) {
        if (start >= end)
            return;
        int pivotIdx = partitioningPivotAsFirst(array, start, end);
        quickSort(array, start, pivotIdx - 1);
        quickSort(array, pivotIdx + 1, end);
    }
    public static int partitioningPivotAsFirst(int[] array, int start, int end) {
        int pivot = array[start];
        int boundary = end + 1;
        for (int idx = end; idx >= start; idx--) {
            if (array[idx] >= pivot) {
                boundary--;
                if (idx == boundary)
                    continue;
                swap(array, idx, boundary);
            }
        }
        return boundary;
    }

    public static int partitioningPivotAsLast(int[] array, int start, int end) {
        int pivot = array[end];
        int boundary = start - 1;
        for (int idx = start; idx <= end; idx++) {
            if (array[idx] <= pivot) {
                boundary++;
                if (idx == boundary)
                    continue;
                swap(array, idx, boundary);
            }
        }
        return boundary;
    }
//    -----------------------------------------------------------------------------------------------------------
}
