package medium;

import java.util.Arrays;

public class ThreeNumberSort {
    /*
    you are given an array of integers and another array of three distinct integers. first array
    is guaranteed to only contain that are in second array. and the second array represent
    a desired order for the integers in first array. Eg: second array of [x, y, z] represent a
    desired order of [x, x, ..., x, y, y, ...., y, z, z, ..., z] in the first array.

    write a function that sorts the first array according to the desired order in the second
    array.

    function should perform this in in-place (should mutate the array) shouldn't use any
    auxiliary space(should run with O(1) space)

    Note: desired order wont necessarily be ascending / descending and that the first array
    won't necessarily contain all three integers found in the second array, it might only contain
    one or two.

    Eg:
    input:
        array - {1, 0, 0, -1, -1, 0, 1, 1}
        order - {0, 1, -1}

    output:
        {0, 0, 0, 1, 1, 1, -1, -1}
    * */

/*
    // Time : O(n * m) and Space is O(n), n is size of input array and m is size of order array
    public static int[] threeNumberSort(int[] array, int[] order) {
        // Write your code here.
        int[] result = new int[array.length];
        int resultIdx = 0;
        for (int idx = 0; idx < order.length; idx++) {
            int orderVal = order[idx];
            for (int inIdx = 0; inIdx < array.length; inIdx++) {
                int arrVal = array[inIdx];
                if (orderVal != arrVal)
                    continue;
                result[resultIdx] = arrVal;
                resultIdx++;
            }
        }
        return result;
    }
*/

/*
//Solution 2:

    //Time : O(n) and Space : O(o) o is the size of order array
    public static int[] threeNumberSort(int[] array, int[] order) {
        int[] count = new int[order.length];
        for (int idx = 0; idx < array.length; idx++) {
            int value = array[idx];
            if (value == order[0])
                count[0]++;
            else if (value == order[1])
                count[1]++;
            else
                count[2]++;
        }
        int idx = 0;
        for (int countIdx = 0; countIdx < count.length; countIdx++) {
            int countVal = count[countIdx];
            while (countVal > 0) {
                array[idx] = order[countIdx];
                idx++;
                countVal--;
            }
        }
        return array;
    }*/

    /*
    // ThirdSolution
    // Time : O(2n) ~= O(n) and Space : O(1) // this is efficient because order array is fixed of 3 value
    // array - {1, 0, 0, -1, -1, 0, 1, 1}
    // order - {0, 1, -1}

    public static int[] threeNumberSort(int[] array, int[] order) {
        int firstValue = order[0];
        int firstIdx = 0;
        int lastValue = order[2];
        int lastIdx = array.length - 1;
        for (int forwardIdx = 0; forwardIdx < array.length; forwardIdx++) {
            int value = array[forwardIdx];
            if (firstValue != value)
                continue;
            swap(array, forwardIdx, firstIdx);
            firstIdx++;
        }
        for (int backwardIdx = array.length - 1; backwardIdx >= 0; backwardIdx--) {
            int value = array[backwardIdx];
            if (lastValue != value)
                continue;
            swap(array, backwardIdx, lastIdx);
            lastIdx--;
        }
        return array;
    }

    public static void swap(int[] array, int fromIdx, int toIdx) {
        int temp = array[toIdx];
        array[toIdx] = array[fromIdx];
        array[fromIdx] = temp;
    }

    */

    // Fourth solution
    // Time is O(n), Space : O(1) // single iteration with just single pass and in-place
    // array - {1, 0, 0, -1, -1, 0, 1, 1}
    // order - {0, 1, -1}

    public static int[] threeNumberSort(int[] array, int[] order) {
        int firstPtrIdx = 0;
        int secondPtrIdx = 0;
        int thirdPtrIdx = array.length - 1;
        int firstValue = order[0];
        int lastValue = order[2];
        while (secondPtrIdx <= thirdPtrIdx) {
            int value = array[secondPtrIdx];
            if (value == firstValue) {
                swap(array, secondPtrIdx, firstPtrIdx);
                firstPtrIdx++; // advancing first pointer as a correct value is placed
                secondPtrIdx++; // advancing second pointer as it will get value(1) pointed by first pointer which is a second value(1)
                continue;
            }
            if (value == lastValue) {
                swap(array, secondPtrIdx, thirdPtrIdx);
                thirdPtrIdx--; // decrementing thirdPointer as the last value is correctly placed, but not advancing second pointer as the swapped value(0) from lastIdx to second pointer position might be a first value(0) to consider.
                continue;
            }
            secondPtrIdx++; // if the current pointing value is second value, just simply forwarding second pointer.
        }
        return array;
    }

    public static void swap(int[] array, int fromIdx, int toIdx) {
        int temp = array[toIdx];
        array[toIdx] = array[fromIdx];
        array[fromIdx] = temp;
    }

    public static void main(String[] args) {
        int[] result = threeNumberSort(new int[]{1, 0, 0, -1, -1, 0, 1, 1}, new int[]{0, 1, -1});
        System.out.println(Arrays.toString(result));
    }
}
