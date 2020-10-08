package hard;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
/*
Function takes in a non empty array of integers
returns the greatest sum which is generated from strictly increasing subsequence
in the array.

Subsequence is a set of numbers arent necessarily adjacent in array but that
are in same order as appeared in array.

Note: single number in array / array itself are both valid subsequence

Assume: only be one increasing subsequence with greatest sum

Eg: Ip : [10, 70, 20, 30, 50, 11, 30]
       Op: [110, [10, 20, 30, 50]]

       Solved using Dynamic programming
* */
public class MaxSumIncreasingSubSequence {
    // [10, 70, 20, 30, 50, 11, 30]
    // Time : O(n^2), Space : O(n)
// idx                     0,  1,   2,   3,     4,   5,   6
// sequence       [ -1,  0,   0,   2,     3,   0,   2], maxSubSeqIdx = 4  => final seq -> 4 => 3 => 2 => 0
// maxsum        [10, 80, 30, 60, 110, 21, 60]
// array              [10, 70, 20, 30,  50,  11, 30]
    public static List<Integer> buildSequenceList(int[] array,int[] sequence, int maxSubSeqIdx) {
        List<Integer> maxSeqListInrevOrder = new ArrayList<>();
        while (maxSubSeqIdx != -1) {
            maxSeqListInrevOrder.add(array[maxSubSeqIdx]);
            maxSubSeqIdx = sequence[maxSubSeqIdx];
        }
        Collections.reverse(maxSeqListInrevOrder);
        return maxSeqListInrevOrder;
    }
    public static void main(String[] args) {
        List<List<Integer>>  result = maxSumIncreasingSubsequence(new int[]{10, 70, 20, 30, 50, 11, 30});
        for (List<Integer> list : result)
            System.out.println(list);
    }

    public static List<List<Integer>> maxSumIncreasingSubsequence(int[] array) {
        // Initialization Code
        List<List<Integer>> result = new ArrayList<>();
//      int[] maxSum = new int[array.length];
        int[] sequence = new int[array.length];
        int[] maxSum = array.clone();
        Arrays.fill(sequence, -1);
//        for (int idx = 0; idx < array.length; idx++) {
//            maxSum[idx] = array[idx];
//            sequence[idx] = -1;
//        }
        int maxSubSeqIdx = 0;

        // Core Logic Build
        for (int idx = 1; idx < array.length; idx++) {  // from first index to last value
            int currentVal = array[idx];
            for (int prevIdx = 0; prevIdx < idx; prevIdx++) { // each value from beg to current value
                int prevVal = array[prevIdx];
                if (prevVal < currentVal && // each value before current val should be strictly lesser
                        maxSum[prevIdx] + currentVal > maxSum[idx]) { // maxSum of the value + currentVal should be greater than maxSum till now
                    maxSum[idx] = maxSum[prevIdx] + currentVal;
                    sequence[idx] = prevIdx; // to know from which prev max sum its updated
                }
            }
            if (maxSum[idx] >= maxSum[maxSubSeqIdx])
                maxSubSeqIdx = idx; // keeping track of maxSum Index
        }
        //Computing Result
        result.add(List.of(maxSum[maxSubSeqIdx]));
        result.add(buildSequenceList(array, sequence, maxSubSeqIdx));
        return result;
    }
}
