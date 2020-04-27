package hard;

public class SubArraySort {

    // ---------------------------- SUB ARRAY SORT ----------------------------------------- //
    // Time Complexity O(n) - Space Complexity O(1)
    public static int[] subArraySort(int[] array) {
        // Write your code here.
        int min = Integer.MAX_VALUE, max = Integer.MIN_VALUE;
        boolean valid = true;
        int arrLength = array.length - 1;
        //To find the range of wrong values
        for (int i = 0; i < array.length; i++) {
            if (i > 0 && i < arrLength) {
                valid = array[i] >= array[i - 1] && array[i] <= array[i + 1];
            } else {
                valid = (i == 0) ? array[i] <= array[i + 1] : array[i] >= array[i - 1];
            }
            if (!valid) {
                if (i < min)
                    min = i;
                else
                    max = i;
                valid = true;
            }
        }
        //couldn't find a invalid range / subarray in array
        if (min > max)
            return new int[]{-1, -1};
        //finding minimum and maximum value in range
        int valueMin = Integer.MAX_VALUE, valueMax = Integer.MIN_VALUE;
        for (int loop = min; loop <= max; loop++) {
            if (array[loop] < valueMin)
                valueMin = array[loop];
            if (array[loop] > valueMax)
                valueMax = array[loop];
        }
        //finding the right index of the minimum and maximum values are supposed to be
        int minIdx = 0, maxIdx = array.length - 1;
        while (minIdx < array.length) {
            if (array[minIdx] <= valueMin) {
                minIdx++;
                continue;
            }
            break;
        }
        while (maxIdx >= 0) {
            if (array[maxIdx] >= valueMax) {
                maxIdx--;
                continue;
            }
            break;
        }
        return new int[]{minIdx, maxIdx};
    }
    // ---------------------------- SUB ARRAY SORT ----------------------------------------- //

}
