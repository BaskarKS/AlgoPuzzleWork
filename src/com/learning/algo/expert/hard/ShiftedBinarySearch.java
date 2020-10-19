package hard;

public class ShiftedBinarySearch {
        /*
    we can do a binary search only if the input array is in sorted fashion.
    we can take the mid index (left idx + right idx) / 2, and validate the target with
    mid value to determine which side we need to search further, either to search left or right.
    target < array[midIdx] search in left half of array, target > array[midIdx] continue
    search in right half of array.

   In this problem, the provided input array is in sorted fashion but it was shifted to left
   or shifted to right to some count.
   Eg :  3, 5, 8, 10, 12, 19 <= actual sorted input
   8, 10, 12, 19, 3, 5 <= sorted input shifted to left
   12, 19, 3, 5, 8, 10 <= sorted input shifted to right

    This problem is solved by first finding which half of array is in sorted order
    either the left half of array is in sorted with the shifted array,
    Only if we could able to find the sorted half, we can easily decide to compare
    the start and end values of the array part with the target and decide to continue search in that
    half or to avoid it and continue search in other half.
    * */

    public static int shiftedBinarySearch(int[] array, int target) {
        return shiftedBinarySearchIterativeHelper(array, target, 0, array.length - 1);
        //return shiftedBinarySearchRecursiveHelper(array, target, 0, array.length - 1);
    }

    // Time : O(log n), Space : O(1) In-place
    public static int shiftedBinarySearchIterativeHelper(int[] array, int target,
                                                         int leftIdx, int rightIdx) {

        int targetIndex = -1;
        while (leftIdx <= rightIdx) {
            int midIdx = (leftIdx + rightIdx) / 2;
            int rightValue = array[rightIdx];
            int leftValue = array[leftIdx];
            int midValue = array[midIdx];

            if (target == midValue) {
                targetIndex = midIdx;
                break;
            }
            else if (leftValue <= midValue) {
                if (target >= leftValue && target < midValue)
                    rightIdx = midIdx - 1;
                else
                    leftIdx = midIdx + 1;
            } else {
                if (target > midValue && target <= rightValue)
                    leftIdx = midIdx + 1;
                else
                    rightIdx=  midIdx - 1;
            }
        }
        return targetIndex;
    }

    // Time : O(log n), Space : O(log n) - depth of call stack
    public static int shiftedBinarySearchRecursiveHelper(int[] array, int target,
                                                int leftIdx, int rightIdx) {
        if (leftIdx > rightIdx) // when the leftIdx is greater than rightIndex means the search overlap crossed
            return -1;

        int midIdx = (leftIdx + rightIdx) / 2;
        int rightValue = array[rightIdx];
        int leftValue = array[leftIdx];
        int midValue = array[midIdx];

        if (target == midValue) // target meets the potential midIdx value
            return midIdx;
        else if (leftValue <= midValue) { // with this comparison, can confirm the left half is sorted
            if (target >= leftValue && target < midValue) // target compared with left and right end values to see whether its present in this range
                return shiftedBinarySearchRecursiveHelper(array, target, leftIdx, midIdx - 1);
            else
                return shiftedBinarySearchRecursiveHelper(array, target, midIdx + 1, rightIdx); // target is not in left range, continue search in right range
        } else { // can confirm the right half is sorted
            if (target > midValue && target <= rightValue) // target compared with left and right end values to see whether its present in this range
                return shiftedBinarySearchRecursiveHelper(array, target, midIdx + 1, rightIdx); // target is in the sorted right half, continue search in right half
            else
                return shiftedBinarySearchRecursiveHelper(array, target, leftIdx, midIdx - 1); // target is in left half of array, continue in left half       }
        }
    }
}
