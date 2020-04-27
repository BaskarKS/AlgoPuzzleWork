package veryhard;

import java.util.Arrays;

public class MergeSort {

    // ----------------------------- Merge Sort ---------------------------------//
    //Time Complexity : O(n Log n) //Space Complexity : O(n)
    public static int[] mergeSort(int[] array) {
        // Write your code here.
        if (array == null || array.length < 2)
            return array;
        int start = 0, end = array.length;
        mergeSortDivide(array,start, end);
        return array;
    }
    public static void mergeSortDivide(int[] array, int start, int end) {
        if ((end - start) < 2)
            return;

        int mid = (start + end) / 2;
        mergeSortDivide(array, start, mid);
        mergeSortDivide(array, mid, end);
        mergeSortConquer(array, start, mid, end);
    }
    public static void mergeSortConquer(int[] array, int start, int mid, int end) {
        if (array[mid] > array[mid - 1])
            return;
        int[] temp = new int[end - start];
        int leftIndex = start;
        int rightIndex = mid;
        int tempIndex = 0;
        while (leftIndex < mid && rightIndex < end) {
            if (array[leftIndex] <= array[rightIndex])
                temp[tempIndex++] = array[leftIndex++];
            else
                temp[tempIndex++] = array[rightIndex++];
        }
        while(leftIndex < mid)
            temp[tempIndex++] = array[leftIndex++];
        while(rightIndex < end)
            temp[tempIndex++] = array[rightIndex++];
        tempIndex = 0;
        while(tempIndex < temp.length) {
            array[start + tempIndex] = temp[tempIndex];
            tempIndex++;
        }
    }
    // Different Implementation of MergeSort
    public void sort(int[] array) {
        if (array.length < 2)
            return;
        var middle = array.length / 2;

        int[] left = new int[middle];
        for (var i = 0; i < middle; i++)
            left[i] = array[i];

        int[] right = new int[array.length - middle];
        for (var i = middle; i < array.length; i++)
            right[i - middle] = array[i];

        sort(left);
        sort(right);

        merge(left, right, array);
    }

    private void merge(int[] left, int[] right, int[] result) {
        int i = 0, j = 0, k = 0;

        while (i < left.length && j < right.length) {
            if (left[i] <= right[j])
                result[k++] = left[i++];
            else
                result[k++] = right[j++];
        }

        while (i < left.length)
            result[k++] = left[i++];

        while (j < right.length)
            result[k++] = right[j++];
    }
    //AlgoExpert Inplace Algorithm - Time Complexity O(n log n) - Space Complexity - O(n)
    public static int[] mergeSortInPlace(int[] array) {
        int[] auxiliary = Arrays.copyOf(array, array.length);
        mergeSortHelper(array, 0, array.length - 1, auxiliary);
        return array;
    }
    public static void mergeSortHelper(int[] main, int start, int end, int[] auxiliary) {
        if (start == end)
            return;
        int mid = (start + end) / 2;
        mergeSortHelper(auxiliary, start, mid, main);
        mergeSortHelper(auxiliary, mid + 1, end, main);
        mergeSortHelperMerge(main, start, mid, end, auxiliary);
    }
    public static void mergeSortHelperMerge(int[] main, int start, int mid,
                                            int end, int[] auxiliary) {
        int index = start, leftIndex = start, rightIndex = mid + 1;
        while(leftIndex <= mid && rightIndex <= end) {
            if (auxiliary[leftIndex] <= auxiliary[rightIndex]) {
                main[index] = auxiliary[leftIndex];
                leftIndex++;
            } else {
                main[index] = auxiliary[rightIndex];
                rightIndex++;
            }
            index++;
        }
        while (leftIndex <= mid) {
            main[index] = auxiliary[leftIndex];
            index++;
            leftIndex++;
        }
        while(rightIndex <= end) {
            main[index] = auxiliary[rightIndex];
            index++;
            rightIndex++;
        }
    }

    public static int[] mergeSortAlgoExpFirstApproach(int[] array) {
        if (array.length == 1)
            return array;
        int mid = array.length / 2;
        int index = 0;
        int[] left = new int[mid];
        while (index < mid) {
            left[index] = array[index];
            index++;
        }
        int rightLength = array.length - mid;
        int[] right = new int[rightLength];
        index = 0;
        while(index < rightLength) {
            right[index] = array[mid + index];
            index++;
        }
        return merge(mergeSortAlgoExpFirstApproach(left),
            mergeSortAlgoExpFirstApproach(right));
    }
    public static int[] merge(int[] left, int[] right) {
        int[] merged = new int[left.length + right.length];
        int index = 0, leftIdx = 0, rightIdx = 0;
        while(leftIdx < left.length && rightIdx < right.length) {
            if (left[leftIdx] <= right[rightIdx]) {
                merged[index] = left[leftIdx];
                leftIdx++;
            } else {
                merged[index] = right[rightIdx];
                rightIdx++;
            }
            index++;
        }
        while (leftIdx < left.length) {
            merged[index] = left[leftIdx];
            leftIdx++;
            index++;
        }
        while (rightIdx < right.length) {
            merged[index] = right[rightIdx];
            rightIdx++;
            index++;
        }
        return merged;
    }
// ----------------------------- Merge Sort ---------------------------------//
public static void main(String[] args) {
    int[] array = {5, 2, 6, 1, 2, -3, 4};
    System.out.println(Arrays.toString(array));
    //mergeSort(array);
    //mergeSortInPlace(array);
    int[] sortedArray = mergeSortAlgoExpFirstApproach(array);
    System.out.println(Arrays.toString(sortedArray));

}
}
