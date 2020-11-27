package medium;

import java.util.Arrays;

public class ArrayOfProducts {
    /*
    write a function that takes in a non-empty array of integers
    and returns an array of the same length , where each element in the
    output array  is equal to the product of every other number in te input
    array.

    the value at output[i] is equal to the product of every number in the input array
    other than input[i]

    Note: don't use division to solve this problem

    ip : [5, 1, 4, 2]
    op: [8, 40, 10, 20]
    // 8 => 1 * 4 * 2
    // 40 => 5 * 4 * 2
    // 10 => 5 * 1 * 2
    // 20 => 5 * 1 * 4
    */

    /*
    // Time O(n^2) and Space : O(n)
    public static int[] arrayOfProducts(int[] array) {
        // Write your code here.
        int[] result = new int[array.length];
        int lastIdx = array.length - 1;
        for (int idx = 0; idx <= lastIdx; idx++) {
            int traverseIdx = 0;
            int value = 1;
            while (traverseIdx <= lastIdx) {
                if (traverseIdx != idx)
                    value *= array[traverseIdx];
                traverseIdx++;
            }
            result[idx] = value;
        }
        return result;
    }
    */

/*
        product of element at idx is equal to
        product of all elements to the left of "element at idx" and
        product of all elements to the right of "element at idx"
        result[1] = product of all input elements to left of idx 1 * product of all input elements to right of idx 1
    Eg result[1] = array[0] * (array[2] * array[3])
    Eg result[2] = (array[0] * array[1]) *  (array[3])
    Eg result[3] = (array[0] * array[1] * array[2]) *  (no element at right of idx-3, hence we multiply with 1)
*/
/*

    public static int[] arrayOfProducts(int[] array) { // {5, 1, 4, 2}
        int[] result = new int[array.length];
        int[] leftRunResult = new int[array.length];
        int[] rightRunResult = new int[array.length];

        int leftRunningProduct = 1;
        for (int idx = 0; idx < array.length; idx++) {
            leftRunResult[idx] = leftRunningProduct;
            leftRunningProduct *= array[idx];
        }//     {1, 5, 5, 20} // leftRunningProduct

        int rightRunningProduct = 1;
        for (int idx = array.length - 1; idx >= 0; idx--) {
            rightRunResult[idx] = rightRunningProduct;
            rightRunningProduct *= array[idx];
        } //     {8, 8, 2,  1} // rightRunningProduct

        for (int idx = 0; idx < array.length; idx++) {
            result[idx] = leftRunResult[idx] * rightRunResult[idx];
        } // product of leftRunResult and rightRunResult
        return result;
    }
*/
    // Time Complexity : O(n), Space : O(n)
    public static int[] arrayOfProducts(int[] array) {
        int result[] = new int[array.length];
        int leftRunningProduct = 1;
        for (int idx = 0; idx < array.length; idx++) {
            result[idx] = leftRunningProduct;
            leftRunningProduct *= array[idx];
        }
        int rightRunningProduct = 1;
        for (int idx = array.length - 1; idx >= 0; idx--) {
            result[idx] *= rightRunningProduct;
            rightRunningProduct *= array[idx];
        }
        return result;
    }
    public static void main(String[] args) {
        int[] result = arrayOfProducts(new int[] {5, 1, 4, 2});


                                                                   //      {8, 40, 10, 20}
                                                                    //      40
        System.out.println(Arrays.toString(result));
    }
}
