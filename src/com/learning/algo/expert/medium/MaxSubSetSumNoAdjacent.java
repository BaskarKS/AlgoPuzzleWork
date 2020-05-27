package medium;
/*
* function takes an array of positive integers, and returns a maximum sum of
* non-adjacent elements in the array.
* if a sum cant be generated it should return 0.
*
* sample ip: {75, 105, 120, 75, 90, 135}
* sample op : 330 // 75, 120, 135
* */
public class MaxSubSetSumNoAdjacent {
    public static void main(String[] args) {
        int[] array = new int[]{75, 105, 120, 75, 90, 135};
        int maxSum = maxSubsetSumNoAdjacentBetterSpace(array);
        System.out.println(maxSum);
    }
    // Time : O(N)
    // Space : O(N)
    public static int maxSubsetSumNoAdjacent(int[] array) {
        // Write your code here.
        if  (array == null || array.length == 0)
            return 0;
        if (array.length == 1)
            return array[0];
        int[] maxSum = new int[array.length];
        maxSum[0] = array[0];
        maxSum[1] = array[1] > array[0] ? array[1] : array[0];
        for (int idx = 2; idx < maxSum.length; idx++) {
            maxSum[idx] = Math.max(maxSum[idx - 1],
                                                            maxSum[idx - 2] + array[idx]);
        }
        return maxSum[array.length - 1];
    }
    // Time : O(N)
    // Space : O(1)
    public static int maxSubsetSumNoAdjacentBetterSpace(int[] array) {
        if (array == null || array.length  == 0)
            return 0;
        if (array.length == 1)
            return array[0];
        int second = array[0];
        int first = Math.max(array[0], array[1]);
        for (int idx = 2; idx < array.length; idx++) {
            int current = Math.max(first, second + array[idx]);
            second = first  ;
            first = current;
        }
        return first;
    }
}
