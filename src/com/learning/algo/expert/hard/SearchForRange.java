package hard;

import java.util.Arrays;

/*
* function takes a sorted array of integers as well as the
* target integer, the function should use a variation of
* Binary Search algorithm to find a range of indices in
* between which the target number is contained in the array
* and should return this range in the form of array.
* the first number in the output array should represent the
* first index at which the target number is located while the
* second number should represent the last index at which the
* target number is located the function should return [-1, -1]
* if the number is not contained in the array.
*
* Eg: IP: [0, 1, 21, 33, 45, 45, 45, 45, 45, 45, 61, 71, 73], 45
*     Op : [4, 9]
*
*
* */
public class SearchForRange {
    public static void main(String[] args) {
        int[] array = {0, 1, 21, 33, 45, 45, 45, 45, 45, 45, 45, 45, 45};
        int target = 45;
        int[] output = searchForRangeBestIterative(array, target);
        System.out.println(Arrays.toString(output));
    }
    // Naive way / Brute Force approach
    // O(n) time and O(1) space excluding output array size
    public static int[] searchForRange(int[] array, int target) {
        int[] output = new int[]{-1, -1};
        if (array == null || array.length == 0)
            return output;
        int first = -1, last = -1;
        for (int idx = 0; idx < array.length; idx++) {
            if (array[idx] == target) {
                if (first == -1)
                    first = idx;
                last = idx;
            }
        }
        output[0] = first;
        output[1] = last;
        return output;
    }
    //////////////////////////////////////////////////////////////////////////
    //   0, 1,  2,  3,  4,  5,  6,  7,  8,  9, 10, 11, 12
    //  {0, 1, 21, 33, 45, 45, 45, 45, 45, 45, 61, 71, 73};
    public static int[] searchForRangeBetter(int[] array, int target) {
        int[] output = new int[] {-1, -1};
        if (array == null || array.length == 0)
            return output;
        output[0] = getLeftIdx(array, target, 0, array.length - 1);
        output[1] = getRightIdx(array, target, 0, array.length - 1);
        return output;
    }
    public static int getLeftIdx(int[] array, int target, int left, int right) {
        if (left > right)
            return -1;
        int mid = (left + right) / 2;
        if (target < array[mid])
            right = mid - 1;
        else if (target > array[mid])
            left = mid + 1;
        else {
            if (mid > 0 && array[mid - 1] == target)
                return getLeftIdx(array, target, left, mid - 1);
            else
                return mid;
        }
        return getLeftIdx(array, target, left, right);
    }
    public static int getRightIdx(int[] array, int target, int left, int right) {
        if (left > right)
            return -1;
        int mid = (left + right) / 2;
        if (target < array[mid])
            right = mid - 1;
        else if (target > array[mid])
            left = mid + 1;
        else {
            if (mid < array.length - 1 && array[mid + 1] == target)
                return getRightIdx(array, target, mid + 1, right);
            else
                return mid;
        }
        return getRightIdx(array, target, left, right);
    }
    ////////////////////////////////////////////////////////////////////////
    public static int[] searchForRangeBest(int[] array, int target) {
        int[] output = new int[] {-1, -1};
        if (array == null || array.length == 0)
            return output;
        getIdx(array, target, 0, array.length - 1, output, true);
        getIdx(array, target, 0, array.length - 1, output, false);
        return output;
    }
    public static void getIdx(int[] array, int target, int left, int right,
                              int[] output, boolean goLeft) {
        if (left > right)
            return;
        int mid = (left + right) / 2;
        if (array[mid] > target)
            getIdx(array, target, left, mid - 1, output, goLeft);
        else if (array[mid] < target)
            getIdx(array, target, mid + 1, right, output, goLeft);
        else {
            if (goLeft) {
                if (mid == 0 || array[mid - 1] != target) {
                    output[0] = mid;
                } else {
                    getIdx(array, target, left, mid - 1, output, goLeft);
                }
            } else {
                if (mid == array.length - 1 || array[mid + 1] != target) {
                    output[1] = mid;
                } else {
                    getIdx(array, target, mid + 1, right, output, goLeft);
                }
            }
        }
    }
    ////////////////////////////////////////////////////////////////////////
    public static int[] searchForRangeBestIterative(int[] array, int target) {
        int[] output = new int[] {-1, -1};
        if (array == null || array.length == 0)
            return output;
        getIdxIterative(array, target, 0, array.length - 1, output, true);
        getIdxIterative(array, target, 0, array.length - 1, output, false);
        return output;
    }
    public static void getIdxIterative(int[] array, int target, int left, int right,
                              int[] output, boolean goLeft) {
        while (left <= right) {
            int mid = (left + right) / 2;
            if (array[mid] > target)
                right = mid - 1;
            else if (array[mid] < target)
                left = mid + 1;
            else {
                if (goLeft) {
                    if (mid == 0 || array[mid - 1] != target) {
                        output[0] = mid;
                        return;
                    } else {
                        right = mid - 1;
                    }
                } else {
                    if (mid == array.length - 1 || array[mid + 1] != target) {
                        output[1] = mid;
                        return;
                    } else {
                        left = mid + 1;
                    }
                }
            }
        }
    }
}
