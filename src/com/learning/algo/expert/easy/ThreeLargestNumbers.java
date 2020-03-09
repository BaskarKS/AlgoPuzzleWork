package easy;

import java.util.Arrays;

/*
* compose a function that takes an array of integers and returns
* a sorted array of three largest integers in the inout array.
* Function should return duplicate integers if necessary
* Eg: Ip : [10, 5, 9, 10, 12]
*     Op : [10, 10, 12]
*
* Eg 2: Ip : [141, 1, 17, -7, -17, -27, 18, 541, 8, 7, 7]
*       op : [18, 141, 541]
* */
public class ThreeLargestNumbers {
    public static void main(String[] args) {
        int[] array = {-1, -2, -3, -7 -17, -27, -18, -541, -8, -7, 7}; //{141, 1, 17, -7, -17, -27, 18, 541, 8, 7, 7}; // {10, 5, 9, 10, 12};

        int[] output = threeLargestNumbersTwo(array);
        System.out.println(Arrays.toString(output));
    }
    //Time Complexity : O(n Log(n)) // sorting time complexity
    public static int[] threeLargestNumbers(int[] array) {
        int[] output = new int[]{Integer.MIN_VALUE, Integer.MIN_VALUE, Integer.MIN_VALUE};
        Arrays.sort(array);
        int length = array.length - 3;
        int idx = length;
        while (idx < array.length) {
            output[idx - length] = array[idx];
            idx++;
        }
        return output;
    }

    //Time Complexity : O(3 * N) => O(n) // sorting time complexity
    public static int[] threeLargestNumbersTwo(int[] array) {
        //final int OUTPUT_SIZE = 3;
        int[] output = new int[]{Integer.MIN_VALUE, Integer.MIN_VALUE, Integer.MIN_VALUE};
        for (var each : array)
            push(output, each);
        return output;
    }
    public static void push(int[] array, int value) {
        int idx = array.length - 1; // last index
        //bottom to top
        while (idx >= 0) {
            if (value >= array[idx]) {
                var temp = array[idx];
                array[idx] = value;
                value = temp;
            }
            idx--;
        }
    }

}
