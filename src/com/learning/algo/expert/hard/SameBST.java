package hard;

import java.util.ArrayList;
import java.util.List;

public class SameBST {
    public static void main(String[] args) {
        List<Integer> arrayOne = List.of(10, 15, 8, 12, 94, 81, 5, 2, 10);
        List<Integer> arrayTwo = List.of(10, 8, 5, 15, 2, 10, 12, 94, 81);
        System.out.println(sameBsts(arrayOne, arrayTwo));
    }
    /*
    # Time O(n^2) and Space O(n^2) n is number of elements
    public static boolean sameBsts(List<Integer> arrayOne, List<Integer> arrayTwo) {
        // Write your code here.
        return checkIsBST(arrayOne, arrayTwo);
    }
    private static boolean checkIsBST(List<Integer> arrayOne, List<Integer> arrayTwo) {
        if (arrayOne.isEmpty() && arrayTwo.isEmpty())
            return true;

        if (!checkBoundary(arrayOne, arrayTwo))
            return false;

        List<Integer> firstLeft = getSubList(arrayOne, true);
        List<Integer> firstRight = getSubList(arrayOne, false);
        List<Integer> secondLeft = getSubList(arrayTwo, true);
        List<Integer> secondRight = getSubList(arrayTwo, false);
        return checkIsBST(firstLeft, secondLeft) && checkIsBST(firstRight, secondRight);
    }
    private static List<Integer> getSubList(List<Integer> array, boolean isLeft) {
        List<Integer> left = new ArrayList<>();
        List<Integer> right = new ArrayList<>();
        if (array.size() == 1)
            return left;
        int compare = array.get(0);
        for (int idx = 1; idx < array.size(); idx++) {
            int temp = array.get(idx);
            if (temp < compare)
                left.add(array.get(idx));
            else
                right.add(array.get(idx));
        }
        return isLeft ? left : right;
    }
    private static boolean checkBoundary(List<Integer> arrayOne, List<Integer> arrayTwo) {
        if (arrayOne.size() == 0 && arrayTwo.size() == 0)
            return true;

        if (arrayOne.size() != arrayTwo.size())
            return false;

        if (arrayOne.get(0) != arrayTwo.get(0))
            return false;

        return true;
    }

    */

    // Time O(n^2) and Space O(d) d is depth of tree
    public static boolean sameBsts(List<Integer> arrayOne, List<Integer> arrayTwo) {
        return sameBstInPlace(arrayOne, arrayTwo, 0, 0, Integer.MIN_VALUE, Integer.MAX_VALUE);
    }
//Idx:     [  0,   1, 2,   3,   4,    5,   6,   7,  8]
//One:   [10, 15, 8, 12, 94, 81,   5,   2, 10]
//Two:   [10,   8, 5, 15,   2, 10, 12, 94, 81]
    public static boolean sameBstInPlace(List<Integer> arrayOne, List<Integer> arrayTwo,
                                         int rootIdxOne, int rootIdxTwo, int minVal, int maxVal) {
        if (rootIdxOne == -1 || rootIdxTwo == -1)
            return rootIdxOne == rootIdxTwo;
        if (arrayOne.get(rootIdxOne) != arrayTwo.get(rootIdxTwo))
            return false;

        int leftRootIdxOne = getIdxOfFirstSmaller(arrayOne, rootIdxOne, minVal);
        int leftRootIdxTwo = getIdxOfFirstSmaller(arrayTwo, rootIdxTwo, minVal);
        int rightRootIdxOne = getIdxOfFirstLargerOrEqual(arrayOne, rootIdxOne, maxVal);
        int rightRootIdxTwo = getIdxOfFirstLargerOrEqual(arrayTwo, rootIdxTwo, maxVal);

        int currentVal = arrayOne.get(rootIdxOne);
        boolean sameLeft = sameBstInPlace(arrayOne, arrayTwo, leftRootIdxOne, leftRootIdxTwo, minVal, currentVal);
        boolean sameRight = sameBstInPlace(arrayOne, arrayTwo, rightRootIdxOne, rightRootIdxTwo, currentVal, maxVal);

        return sameLeft && sameRight;
    }

//Idx:     [  0,   1, 2,   3,   4,    5,   6,   7,  8]
//One:   [10, 15, 8, 12, 94, 81,   5,   2, 10]
//Two:   [10,   8, 5, 15,   2, 10, 12, 94, 81]
    public static int getIdxOfFirstSmaller(List<Integer> array, int rootIdx, int minVal) {
        int rootVal = array.get(rootIdx);
        for (int idx = rootIdx + 1; idx < array.size(); idx++) {
            int currVal = array.get(idx);
            if (currVal < rootVal && currVal >= minVal)
                return idx;
        }
        return -1;
    }

//Idx:     [  0,   1, 2,   3,   4,    5,   6,   7,  8]
//One:   [10, 15, 8, 12, 94, 81,   5,   2, 10]
//Two:   [10,   8, 5, 15,   2, 10, 12, 94, 81]
    public static int getIdxOfFirstLargerOrEqual(List<Integer> array, int rootIdx, int maxVal) {
        int rootVal = array.get(rootIdx);
        for (int idx = rootIdx + 1; idx < array.size(); idx++) {
            int currVal = array.get(idx);
            if (currVal >= rootVal && currVal < maxVal)
                return idx;
        }
        return -1;
    }
}
