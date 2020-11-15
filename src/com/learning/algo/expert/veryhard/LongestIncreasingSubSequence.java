package veryhard;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

public class LongestIncreasingSubSequence {

    /*
    Given a non empty array of integers, write a function that returns the longest
    Strictly increasing subsequence in the array.

    Subsequence of an array is a set of numbers that aren't necessarily adjacent
    in the  array but that are in same order as they appear in array.
    Eg: numbers [1, 3, 4] for a subsequence of the array, [1, 2, 3, 4] and so do the
    numbers [2, 4].

    Note that a single number in an array and the array itself are both valid
    subsequence of the array.

    Assume there will be only one longest increasing subsequence.

    Eg:
    Ip : array = [5, 7, -24, 12, 10, 2, 3, 12, 5, 6, 35]
    Op : [-24, 2, 3, 5, 6, 35] // total 6 elements which is longest of all

    Hints:
    Try build an array of same length, at each index in new array. store the
    length of the longest increasing subsequence ending with the number
    found at that index in the input array.

    try storing the sequences, by storing the indices of the previous numbers
    Eg: at index 3 in other array, store the index of the before-last number in
    the longest increasing subsequence ending with the number at index 3

    Instead of the above approach, which is O(n^2). Follow below approach
    which provides Time complexity of O(n log n)

    build an array whose indices represent the length of subsequences.
    and values represent the smallest number in the input array that can
    end a subsequence of given length.
    traverse the input array, for each value in input array determine what the
    length L of the longest increasing subsequence ending with that number is;
    store the position of that number at index L in the new array that you are
    building. find a way to use binary search to build this array
    * */
    public static void main(String[] args) {
        List<Integer> result = longestIncreasingSubsequenceBetter(new int[] {-1, 2, 1, 2}); //new int[] {5, 7, -24, 12, 10, 2, 3, 12, 5, 6, 35}
        System.out.println(result);
    }
    // Time : O(n ^2) , Space : O(n)
    public static List<Integer> longestIncreasingSubsequence(int[] array) {
        int[] sequences = new int[array.length];
        int[] length = new int[array.length];
        Arrays.fill(length, 1);
        Arrays.fill(sequences, Integer.MAX_VALUE);
        int maxIdx = 0;
        for (int idx = 1; idx < array.length; idx++) {
            for (int seqIdx = 0; seqIdx < idx; seqIdx++) {
                if (array[idx] > array[seqIdx] && (length[seqIdx] + 1) > length[idx]) {
                    length[idx] = length[seqIdx] + 1;
                    sequences[idx] = seqIdx;
                }
            }
            if (length[idx] > length[maxIdx])
                maxIdx = idx;
        }

        return buildFinalList(array, sequences, maxIdx);
    }
    /*
        Index:  0, 1,    2,    3,   4, 5, 6,   7, 8, 9, 10
        Input: [5, 7, -24, 12, 10, 2, 3, 12, 5, 6, 35])

        For Iteration 1:
            length = 0
            idx = 0
            newLength = binarySearch(startIdx=1, endIdx=0, array, [None, ...None], 5) // return 1
            sequences[0] = indexes[0] // newLength - 1 // last element of indexes[] // [None, None, .....None]
            indices[1] = 0 // indices[newLength] = idx // [None, 0, None, None....]
            length = 1 // max(length, newLength) // max(0, 1)

        Index:  0, 1,    2,    3,   4, 5, 6,   7, 8, 9, 10
        Input: [5, 7, -24, 12, 10, 2, 3, 12, 5, 6, 35])

        For Iteration 2:
            length = 1
            idx = 1
            newLength = binarySearch(startIdx=1, endIdx=1, array, [None, 0, None, ...None], 7) // return 2
            sequences[1] = indexes[1] // newLength - 1 // last element of indexes[] // [None, 0, None, .....None]
            indices[2] = 1 // indices[newLength] = idx // [None, 0, 1, None....None]
            length = 2 // max(length, newLength) // max(1, 2)

        Index:  0, 1,    2,    3,   4, 5, 6,   7, 8, 9, 10
        Input: [5, 7, -24, 12, 10, 2, 3, 12, 5, 6, 35])

        For Iteration 3:
            length = 2
            idx = 2
            newLength = binarySearch(startIdx=1, endIdx=2, array, [None, 0, 1, ...None], -24) // return 1
            sequences[2] = indexes[0] // newLength - 1 // l// [None, 0, None, .....None]
            indices[1] = 2 // indices[newLength] = idx // [None, 2, 1, None....None]
            length = 2 // max(length, newLength) // max(2, 1) // 2

        Index:  0, 1,    2,    3,   4, 5, 6,   7, 8, 9, 10
        Input: [5, 7, -24, 12, 10, 2, 3, 12, 5, 6, 35])

         For Iteration 4:
            length = 2
            idx = 3
            newLength = binarySearch(startIdx=1, endIdx=2, array, [None, 2, 1, ...None], 12) // return 3
            sequences[3] = indexes[2] // newLength - 1 // l// [None, 0, None, 1, None .....None]
            indices[3] = 3 // indices[newLength] = idx // [None, 2, 1, 3, None....None]
            length = 3 // max(length, newLength) // max(2, 3) // 3

        Index:  0, 1,    2,    3,   4, 5, 6,   7, 8, 9, 10
        Input: [5, 7, -24, 12, 10, 2, 3, 12, 5, 6, 35])

        For Iteration 5:
            length = 3
            idx = 4
            newLength = binarySearch(startIdx=1, endIdx=3, array, [None, 2, 1, 3 ...None], 10) // return 3
            sequences[4] = indexes[2] // newLength - 1 // l// [None, 0, None, 1, 1, None .....None]
            indices[3] = 4 // indices[newLength] = idx // [None, 2, 1, 4, None....None]
            length = 3 // max(length, newLength) // max(3, 3) // 3


        Index:  0, 1,    2,    3,   4, 5, 6,   7, 8, 9, 10
        Input: [5, 7, -24, 12, 10, 2, 3, 12, 5, 6, 35])

        For Iteration 6:
            length = 3
            idx = 5
            newLength = binarySearch(startIdx=1, endIdx=3, array, [None, 2, 1, 4 ...None], 2) // return 2
            sequences[5] = indexes[1] // newLength - 1 // l// [None, 0, None, 1, 1, 2, None .....None]
            indices[2] = 5 // indices[newLength] = idx // [None, 2, 5, 4, None....None]
            length = 3 // max(length, newLength) // max(3, 2) // 3

        Index:  0, 1,    2,    3,   4, 5, 6,   7, 8, 9, 10
        Input: [5, 7, -24, 12, 10, 2, 3, 12, 5, 6, 35])

        For Iteration 7:
            length = 3
            idx = 6
            newLength = binarySearch(startIdx=1, endIdx=3, array, [None, 2, 5, 4 ...None], 3) // return 3
            sequences[6] = indexes[2] // newLength - 1 // l// [None, 0, None, 1, 1, 2, 5, None .....None]
            indices[3] = 6 // indices[newLength] = idx // [None, 2, 5, 6, None....None]
            length = 3 // max(length, newLength) // max(3, 3) // 3

        Index:  0, 1,    2,    3,   4, 5, 6,   7, 8, 9, 10
        Input: [5, 7, -24, 12, 10, 2, 3, 12, 5, 6, 35])

        For Iteration 8:
            length = 3
            idx = 7
            newLength = binarySearch(startIdx=1, endIdx=3, array, [None, 2, 5, 6 ...None], 12) // return 4
            sequences[7] = indexes[3] // newLength - 1 // l// [None, 0, None, 1, 1, 2, 5, 6, None .....None]
            indices[4] = 7 // indices[newLength] = idx // [None, 2, 5, 6, 7, None....None]
            length = 4 // max(length, newLength) // max(3, 4) // 4

        Index:  0, 1,    2,    3,   4, 5, 6,   7, 8, 9, 10
        Input: [5, 7, -24, 12, 10, 2, 3, 12, 5, 6, 35])

        For Iteration 9:
            length = 4
            idx = 8
            newLength = binarySearch(startIdx=1, endIdx=4, array, [None, 2, 5, 6, 7 ...None], 5) // return 4
            sequences[8] = indexes[3] // newLength - 1 // l// [None, 0, None, 1, 1, 2, 5, 6, 6, None .....None]
            indices[4] = 8 // indices[newLength] = idx // [None, 2, 5, 6, 8, None....None]
            length = 4 // max(length, newLength) // max(4, 4) // 4

        Index:  0, 1,    2,    3,   4, 5, 6,   7, 8, 9, 10
        Input: [5, 7, -24, 12, 10, 2, 3, 12, 5, 6, 35])

        For Iteration 10:
            length = 4
            idx = 9
            newLength = binarySearch(startIdx=1, endIdx=4, array, [None, 2, 5, 6, 8 ...None], 6) // return 5
            sequences[9] = indexes[4] // newLength - 1 // l// [None, 0, None, 1, 1, 2, 5, 6, 6, 8, None]
            indices[5] = 9 // indices[newLength] = idx // [None, 2, 5, 6, 8, 9, None....None]
            length = 5 // max(length, newLength) // max(4, 5) // 5

        Index:  0, 1,    2,    3,   4, 5, 6,   7, 8, 9, 10
        Input: [5, 7, -24, 12, 10, 2, 3, 12, 5, 6, 35])

        For Iteration 11:
            length = 5
            idx = 10
            newLength = binarySearch(startIdx=1, endIdx=5, array, [None, 2, 5, 6, 8, 9 ...None], 35) // return 6
            sequences[10] = indexes[5] // newLength - 1 // l// [None, 0, None, 1, 1, 2, 5, 6, 6, 8, 9]
            indices[6] = 10 // indices[newLength] = idx // [None, 2, 5, 6, 8, 9, 10, None, ....None]
            length = 6 // max(length, newLength) // max(5, 6) // 6

 BinarySearch, Searching value and either replace value / add value.
 Index:  0, 1,    2,    3,   4, 5, 6,   7, 8, 9, 10
 Input: [5, 7, -24, 12, 10, 2, 3, 12, 5, 6, 35])

 index : 0,   value : 5,    before : [No, No, .....No],              after : [No, 0, .....No]                       seq : None    - Length : 1
 index : 1,   value : 7,    before : [No, 0, No, ...No],            after : [No, 0, 1 .....No]                    seq : 0          - Length : 2
 index : 2,   value : -24, before : [No, 0, 1, ...No],              after : [No, 2, 1 .....No]                    seq : None    - Length : 2
 index : 3,   value : 12,  before : [No, 2, 1, ...No],              after : [No, 2, 1, 3.....No]                 seq : 1           - Length : 3
 index : 4,   value : 10,  before : [No, 2, 1, 3.....No],          after : [No, 2, 1, 4.....No]                 seq : 1           - Length : 3
 index : 5,   value : 2,    before : [No, 2, 1, 4.....No],          after : [No, 2, 5, 4.....No]                 seq : 2           - Length : 3
 index : 6,   value : 3,    before : [No, 2, 5, 4.....No],          after : [No, 2, 5, 6.....No]                 seq : 5           - Length : 3
 index : 7,   value : 12,  before : [No, 2, 5, 6.....No],          after : [No, 2, 5, 6, 7.....No]             seq : 6           - Length : 4
 index : 8,   value : 5,    before : [No, 2, 5, 6, 7.....No],      after : [No, 2, 5, 6, 8.....No]             seq : 6           - Length : 4
 index : 9,   value : 6,    before : [No, 2, 5, 6, 8.....No],      after : [No, 2, 5, 6, 8, 9.....No]         seq : 8           - Length : 5
 index : 10, value : 35,  before : [No, 2, 5, 6, 8, 9.....No],  after : [No, 2, 5, 6, 8, 9, 10.....No]  seq : 9           - Length : 6

 If a new value which is larger than the largest value in binary search list, new value is
 inserted. If new value is with in the range of the binary values list then its replaced at
 the right location.

 This approach will ensure the binary values list will always maintains a increasing values
 which is constructed from the input value iterated from left to right.

 At the end the total number of values in the binary-search list will expose the
 increasing subsequence, sequences array will help to identify the previous location of the

*/
    // Time : O(n log n) , Space : O(n)
    public static List<Integer> longestIncreasingSubsequenceBetter(int[] array) {
        int[] sequences = new int[array.length];
        int[] indices = new int[array.length + 1];
        Arrays.fill(indices, Integer.MAX_VALUE);
        Arrays.fill(sequences, Integer.MAX_VALUE);
        int length = 0; // # this will always maintains the last index value of growing subsequence in indices array
        for (int idx = 0; idx < array.length; idx++) {
            int newLength = binarySearch(1, length, array, indices, array[idx]);  //# returns the suitable index location to place the input value index in indices array.
            sequences[idx] = indices[newLength - 1]; //  # for every respective input array value its previous value location is recorded at the input value index, Eg: If ip is [2, 6, 3, 5, 7], result is 2, 3, 5, 7 and sequence is [None, 0, 0, 2, 3]
            indices[newLength] = idx; //# this array will hold the indices of the IncreasingSubSequence, whenever newLength grows the respective value's index of input array is placed in the grown location, if newLength maintains a particular length then the input value's index is replaced in indices array. we maintain a set of indices in this array and we do binary search only in this set of values
            length = Math.max(length, newLength); // # latest maximum length of indices array is maintained, which will be the endIndex of binary search
        }
        return buildFinalList(array, sequences, indices[length]);
    }

    /*
    # Every index position in indices array denote the max length of subsequence
# Eg:
# index 1 denote the maxSubsequence length of 1
# index 2 denote the maxSubsequence length of 2
# index 3 denote the maxSubsequence length of 3
# index 4 denote the maxSubsequence length of 4
# index 5 denote the maxSubsequence length of 5

# value of indices array denote the indices of maximum value of input array for that subsequence
#             0, 1,    2,    3,   4, 5, 6,   7, 8, 9, 10
# Eg: Ip [5, 7, -24, 12, 10, 2, 3, 12, 5, 6, 35])
# 1 length subsequence for input array is -24
# 2 length subsequence for input array is -24, 2 when execution is at index 5 // 2 is the max/end value and its index in input is 5(this is stored in indices array)
# 3 length subsequence for input array is -24, 2, 3 when execution is at index 6 // 3 is the max/end value and its index in input is 6(this is stored in indices array)
# 4 length subsequence for input array is -24, 2, 3, 5 when execution is at index 8 // 5 is the max/end value and its index in input is 8(this is stored in indices array)
# 5 length subsequence for input array is -24, 2, 3, 5, 6 when execution is at index 9
# 6 length subsequence for input array is -24, 2, 3, 5, 6, 35 when execution is at index 10
# values in indices array is [None, 2, 5, 6, 8, 9, 10, None, ...]

    This method will always maintain the indexes of the longestIncreasingSubSequence in
    indices array. If a new value to be searched in Binary search is larger than the maxValue
    in indices, then the new value (index) is added into the indices array, this is the growing
    of IncreasingSubSequence, if the new value is less than the maxValue of indices array(rightmost)
    or if the new value is larger than the minValue of indices array(leftmost) then the new value
    is replaced in that location.
    This method will only let know the parent method that where the new value can best
    fit into the IncreasingSubSequence
*/
    public static int binarySearch(int startIdx, int endIdx, int[] array, int[] indices, int value) {
        if (startIdx > endIdx)
            return startIdx;
        int midIdx = (startIdx + endIdx) / 2;
        if (value < array[indices[midIdx]])
            endIdx = midIdx - 1;
        else
            startIdx = midIdx + 1;
        return binarySearch(startIdx, endIdx, array, indices, value);
    }
    // this will build the final output LongestIncreasingSubSequence list using sequence array which
    // hold the index of previous positions, with input array
    public static List<Integer> buildFinalList(int[] array, int[] sequences, int maxIdx) {
        List<Integer> finalList = new ArrayList<>();
        while(maxIdx != Integer.MAX_VALUE) {
            finalList.add(array[maxIdx]);
            maxIdx = sequences[maxIdx];
        }
        Collections.reverse(finalList);
        return finalList;
    }
}
