package easy;

import java.util.List;

/*
* Determine second array is sub-sequence of first array, subsequence is
* a set of numbers not necessarily adjacent in the array but they are in same
* order they appear in array
* main array : {1, 2, 3, 4}, valid sub-seq are {1, 3, 4}, {2, 4}
* Single number in a array and array itself are both valid sub-seq
*
* ip: {5, 1, 22, 25, 6, -1, 8, 10}, {1, 6, -1, 10}
* op: true
* */
public class ValidateSubSequence {
    public static void main(String[] args) {
        List<Integer> main = List.of(5, 1, 22, 25, 6, -1, 8, 10);
        List<Integer> sub = List.of(6, -1, 10); //,
        System.out.println(isValidSubsequence(main, sub));
    }
    // Time O(n), Space : O(1)
    public static boolean isValidSubsequence(List<Integer> array, List<Integer> sequence) {
        if (sequence.size() == 0)
            return true;
        // Write your code here.
        int mainIdx = 0;
        int subIdx = 0;
        while (mainIdx < array.size()) {
            int mainVal = array.get(mainIdx);
            int subSeqVal = sequence.get(subIdx);
            if (mainVal == subSeqVal) {
                subIdx += 1;
                if (subIdx >= sequence.size())
                    return true;
            }
            mainIdx += 1;
        }
        return false;
    }
}
