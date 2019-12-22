import java.util.Arrays;

public class MediumProblems {

    // ------------------- Kadane Algorithm -----------------------------------------

    // my Own Worst Implementation, didn't work for some cases of inputs
    //int[] kadaneArray = {-10,-2,-9,-4,-8,-6,-7,-1,-3,-5};
    //int[] kadaneArray = {1,2,-4,3,5,-9,8,1,2};
    public static int kadanesAlgorithm(int[] array) {
        // Write your code here.
       int result = 0;
       int min = 0;
       int max = array.length - 1;
       int[] valueIdxs = new int[2];
       getSubArrayIndexes(array, min, max, valueIdxs);
       for (int index = valueIdxs[0]; index <= valueIdxs[1]; index++)
           result += array[index];
       return result;
    }
    public static void getSubArrayIndexes(int[] array, int min, int max, int[] resultIdx) {
        if (min == max) {
            resultIdx[0] = min;
            resultIdx[1] = max;
            return;
        }
        int[] tmpArray = Arrays.copyOf(array, array.length);
        int mid = (min + max) / 2;
        int leftMax = Integer.MIN_VALUE;
        int rightMax = Integer.MIN_VALUE;
        int newMin = mid, newMax = mid;
        int midVal = array[mid];
        for (int right = mid + 1; right <= max; right++) {
            tmpArray[right] = tmpArray[right - 1] + tmpArray[right];
            if ((tmpArray[right] > midVal) && (tmpArray[right] >= rightMax)) {
               rightMax = tmpArray[right];
               newMax = right;
            }
        }
        for (int left = mid - 1; left >= min; left--) {
            tmpArray[left] = tmpArray[left + 1] + tmpArray[left];
            if ((tmpArray[left] > midVal) && (tmpArray[left] >= leftMax)) {
                leftMax = tmpArray[left];
                newMin = left;
            }
        }
        if ((newMin <= newMax) &&
                (leftMax >= midVal && rightMax >= midVal)) {
            resultIdx[0] = newMin;
            resultIdx[1] = newMax;
            return;
        }
        if (newMin == mid && newMax == mid) {
            if (min < mid) {
                if (tmpArray[min] >= tmpArray[max]) {
                    getSubArrayIndexes(array, min, mid, resultIdx);
                } else {
                    getSubArrayIndexes(array, mid, max, resultIdx);
                }
            } else if (min == mid) {
                if (array[min] > array[max])
                    getSubArrayIndexes(array, min, min, resultIdx);
                else
                    getSubArrayIndexes(array, max, max, resultIdx);
            }
        }
    }

    // O(n) Time Complexity // O(n) spaceComplexity
    public static int kadanesAlgorithmDynamicProg(int[] array) {
        int[] maxSum = new int[array.length];
        maxSum[0] = array[0];
        int max = array[0];
        for (int i = 1 ; i < array.length; i++) {
            maxSum[i] = Math.max(maxSum[i - 1] + array[i], array[i]);
            if (maxSum[i] > max)
                max = maxSum[i];
        }
        return max;
    }
    // ------------------- Kadane Algorithm -----------------------------------------
}
