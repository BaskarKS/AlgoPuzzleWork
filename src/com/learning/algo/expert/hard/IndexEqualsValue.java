package hard;

public class IndexEqualsValue {
    /*
        Write a function that takes in a sorted array of distinct integers
        and returns the first index in the array that is equal to the value at that
        index. In other words your function should return the minimum index
        where index == array[index]

        If there is no such index , your function should return -1

        Eg:
        Input :
        Index       0,  1, 2, 3, 4, 5, 6
        array = [-5, -3, 0, 3, 4, 5, 9]
        Output:
            3 // 3 == array[3]

    */
    public static int indexEqualsValue(int[] array) {
        if (array == null || array.length == 0)
            return -1;
        return findIndexValue(array, 0, array.length - 1);
    }
    public static int findIndexValue(int[] array, int start, int end) {
        int midIdx = (start + end) / 2;
        if (start >= end) {
            return (array[midIdx] == midIdx) ? midIdx : -1;
        }
        int midVal = array[midIdx];
        if (midVal == midIdx) {
            return findIndexValue(array, start, midIdx);
        }else if (midVal < midIdx) {
            return findIndexValue(array, midIdx + 1, end);
        } else {
            return findIndexValue(array, start, midIdx - 1);
        }
    }

/*
if value is less than the index, eliminate left side half of search space ( since values before that will
be definitely less than its index by at-least 1, since the values in array is distinct and sorted)

if value is greater than the index, eliminate the right half side of search space ( since values after
that will be definitely greater than its index by at-least 1, since the values in array is distinct and sorted)

if the value is equal to index and index == 0, return the current index
if the value is equal to index and prevElement is less than prevIdx then return the current index

Implementation            Recursive                     Iterative
Time Complexity               O(log n)                    O(log n)
Space Complexity             O(log n)                         O(1)

*/
    // Iterative Approach
    // Time : O(log(n)) and Space : O(1)
    public static int indexEqualsValueIterative(int[] array) {
        int leftIdx = 0;
        int rightIdx = array.length - 1;
        while (leftIdx <= rightIdx) {
            int midIdx = leftIdx + ((rightIdx - leftIdx) / 2);
            int midValue = array[midIdx];
            if (midValue < midIdx)
                leftIdx = midIdx + 1;
            else if (midValue == midIdx && midIdx == 0)
                return midIdx;
            else if (midValue == midIdx && (array[midIdx - 1] < (midIdx - 1)))
                return midIdx;
            else
                rightIdx = midIdx - 1;
        }
        return -1;
    }

    public static void main(String[] args) {
        int result = indexEqualsValueIterative(new int[]{-5, -3, 0, 3, 4, 5, 9});
        System.out.println(result);
    }
}
