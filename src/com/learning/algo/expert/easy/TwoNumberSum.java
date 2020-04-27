package easy;

import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;

public class TwoNumberSum {

    // ----------------------------- Two Number Sum ------------------------------//
    // TimeComplexity : O(n ^ 2) - SpaceComplexity : O(1)
    public static int[] twoNumberSum(int[] array, int targetSum) {
        // Write your code here.
        for (int eachNo = 0; eachNo < array.length - 1; eachNo++) {
            int firstNumber = array[eachNo];
            for(int otherNumber = eachNo + 1; otherNumber < array.length; otherNumber++) {
                int secondNumber = array[otherNumber];
                if (targetSum == (firstNumber + secondNumber))
                    return new int[] {firstNumber, secondNumber};
            }
        }
        return new int[]{};
    }
    /* the strategy is all numbers are distinct numbers (cant have duplicates)
     *  if the number / 2 is equal to target then the possibility of having the
     * output pair with the same number itself (if target is 10, number contains is 5
     * output value may come up with [5,5] if the five is encountered first instead
     * of the actual numbers). Its better to avoid the (number / 2 == target) in the
     * HashSet to check. because it cant be part of the solution*/

    // TimeComplexity : O(n) - SpaceComplexity : O(n)
    public static int[] twoNumberSumBetter(int[] array, int targetSum) {
        Set<Integer> otherNoPool = new HashSet<>();
        for (int each : array) {
            if ((targetSum / 2 ) != each)
                otherNoPool.add(each);
        }
        for (int index = 0; index < array.length; index++) {
            int firstNumber = array[index];
            int otherNumber = targetSum - firstNumber;
            if (otherNoPool.contains(otherNumber))
                return new int[] {firstNumber, otherNumber};
        }
        return new int[]{};
    }

    // TimeComplexity : O(n) - SpaceComplexity : O(n)
    public static int[] twoNumberSumBest(int[] array, int targetSum) {
        Set<Integer> otherNoPool = new HashSet<>();
        for (int index = 0; index < array.length; index++) {
            int firstNumber = array[index];
            int otherNumber = targetSum - firstNumber;
            if (otherNoPool.contains(otherNumber))
                return new int[] {firstNumber, otherNumber};
            else
                otherNoPool.add(firstNumber);
        }
        return new int[]{};
    }
    // TimeComplexity : O(n log n) - SpaceComplexity : O(1)
    public static int[] twoNumberSumBestSpace(int[] array, int targetSum) {
        Arrays.sort(array);
        int left = 0;
        int right = array.length - 1;
        while (left < right) {
            int firstNumber = array[left];
            int secondNumber = array[right];
            int localSum = firstNumber + secondNumber;
            if (localSum == targetSum) {
                return new int[] {firstNumber, secondNumber};
            } else if (localSum < targetSum) {
                left++;
            } else if (localSum > targetSum){
                right++;
            }
        }
        return new int[]{};
    }
// ----------------------------- Two Number Sum ------------------------------//

    public static void main(String[] args) {

        int[] twoNumberSum = {3, 5, -4, 8, 11, 1, -1, 6};
        int targetSum = 10;
        int[] result = twoNumberSumBestSpace(twoNumberSum, targetSum);
        System.out.println(Arrays.toString(result));

    }
}
