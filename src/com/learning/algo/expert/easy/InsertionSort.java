package easy;

import java.util.Arrays;


public class InsertionSort {
    public static void main(String[] args) {
        int[] array = {8, 5, 2, 9, 5, 6, 3};
        System.out.println(Arrays.toString(insertionSort(array)));
    }
    public static int[] insertionSort(int[] array) {
        // Write your code here.
        if (array == null || array.length == 0)
            return array;
        for (int outIdx = 1; outIdx < array.length; outIdx++) {
            int itemToInsert = array[outIdx];
            int inIdx = outIdx - 1;
            for (; inIdx >= 0; inIdx--) {
                if (array[inIdx] >= itemToInsert)
                    array[inIdx + 1] = array[inIdx];
                else
                    break;
            }
            array[inIdx + 1] = itemToInsert;
        }
        return array;
    }
}
