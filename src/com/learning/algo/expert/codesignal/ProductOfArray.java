package codesignal;

import java.util.Arrays;

public class ProductOfArray {
    public static void main(String[] args) {
        int[] array = {1, 2, 3, 4};
        System.out.println(Arrays.toString(array));
        int[] result = productOfArray(array);
        System.out.println(Arrays.toString(result));
        /*
            [1, 2, 3, 4] - 24


             1, 1, 2, 6
             24 ,12, 4, 1
            [24, 12, 8, 6]
        */
    }
    public static int[] productOfArrayExceptSelf(int[] array) {
        int[] result = new int[array.length];
        var product = 1;
        for (var value : array)
            product *= value;
        for(int idx = 0; idx < array.length; idx++)
            result[idx] = product / array[idx];
        return result;
    }
    public static int[] productOfArrayExceptSelfWithoutDivision(int[] array) {
        int length = array.length;
        int[] left = new int[length];
        int[] right = new int[length];
        int[] result = new int[length];
        left[0] = 1;
        for (var idx = 1; idx < length; idx++)
            left[idx] = array[idx - 1] * left[idx - 1];

        right[right.length - 1] = 1;
        for (var idx = length - 2; idx >= 0; idx--)
            right[idx] = array[idx + 1] * right[idx + 1];

        for (var idx = 0; idx < length; idx++)
            result[idx] = left[idx] * right[idx];
        return result;
    }
    //productOfArray - ExceptSelf, WithoutDivision and Extra Space
    public static int[] productOfArray(int[] array) {
        int length = array.length;
        int[] result = new int[length];

        result[0] = 1;
        for (var idx = 1; idx < length; idx++)
            result[idx] = array[idx - 1] * result[idx - 1];

        var right = 1;
        for (var idx = length - 1; idx >= 0; idx--) {
            result[idx] = result[idx] * right;
            right *= array[idx];
        }

        return result;
    }

}
