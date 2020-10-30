package veryhard;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.stream.IntStream;

public class PalindromePartioningMinCuts {
    /*
    Given non empty string, write a function that returns the minimum number of
    cuts needed to perform on the string, such that each remaining substring is a
    palindrome.

    Palindrome is a string that's written same forward as backward
    Note: single characters are palindrome.

    Input : "noonabbad"
    output : 2 : noon | abba | d
     */

    public static void main(String[] args) {
        String palindrome =   "abb"; //"noonabbad"; //
        int result = palindromePartitioningMinCuts(palindrome);
        //System.out.println(result);

    }
    public static int myOwnPalindromePartitioningMinCuts(String str) {
        if (str == null || str.length() < 3)
            return 0;
        // Write your code here.
        List<String> palindromes = new ArrayList<>();
        for (int begIdx = 0; begIdx < str.length(); begIdx++) {
            for (int endIdx = begIdx+1; endIdx <= str.length(); endIdx++) {
                char[] strChars = str.toCharArray();
                if (isPalindrome(strChars, begIdx, endIdx)) {
                    palindromes.add(str.substring(begIdx, endIdx));
                }
            }
        }
        List<String> finalList = new ArrayList<>();
        StringBuilder result = new StringBuilder();

        for (int idx = 0; idx < palindromes.size(); idx++) {
            if (palindromes.get(idx).length() == 1) {
                continue;
            }
            else {
                boolean state = false;
                String temp = palindromes.get(idx);
                for (char ch : temp.toCharArray()) {
                    if (temp.charAt(0) != ch) {
                        state = true;
                        break;
                    }
                }
                if (state == false) {
                    continue;
                }
            }
            finalList.add(palindromes.get(idx));
            result.append(palindromes.get(idx));
        }
        int count = finalList.size();
        if (result.toString().compareTo(str) == 0)
            count -= 1;
        System.out.println(palindromes);
        return count;
    }

    // Time : O(N^3) and Space : O(N^2)
    public static int palindromePartitioningMinCuts(String str) {
        int length = str.length();
        char[] input = str.toCharArray();
        boolean[][] palindrome = new boolean[length][length];
/*        for (int startIdx = 0; startIdx < length; startIdx++) {
            for (int endIdx = startIdx; endIdx < length; endIdx++) {
                palindrome[startIdx][endIdx] = isPalindrome(input, startIdx, endIdx);
            }
        }*/
        IntStream.range(0, str.length()).forEachOrdered(startIdx -> {
            IntStream.range(startIdx, str.length()).forEachOrdered(endIdx -> {
                palindrome[startIdx][endIdx] = isPalindrome(input, startIdx, endIdx);
            });
        });
        //"noonabbad"
        int[] minCuts = new int[str.length()]; // to keep track of the min cuts needed for each string length
        for (int strIdx = 0; strIdx < str.length(); strIdx++) {
            if (palindrome[0][strIdx] == true) { // if the string length between 0 and the strIdx(3),
                minCuts[strIdx] = 0; // minsCuts[3] which is for string "noon" is 0 because its palindrome
            } else {
                // for iteration 6, newly added character str[6] which is "b" with input string "noonab". This else case is reached because "noonabb" is not a palindrome, have to find min cuts within the string if it had a palindrome, hence the prior string "noonab" is combined in multi combination with newly encountered char "b" for palindrome possibilities
                minCuts[strIdx] = minCuts[strIdx - 1] + 1; // Eg: for strIdx(6) checking min cuts for "noonabb", pre-assumption that just a cut before it, cut between "noonab" and "b", minCuts[5](already has min-cut value for string "noonab")  + 1 (pre-assumption-cut which we did now because of the newly encountered character "b")
                for (int subIdx = 1; subIdx < strIdx; subIdx++) { // we progress only till index 5, because the value at index 6 is processed in previous  step.(making a cut till index 5 and adding 1 to it). assuming cut at 1 and checking 1 to 6(including newly encountered char "b" with rest) is palindrome, assuming cut at 2 and checking 2 to 6 is palindrome,..., assuming cut at 5 and checking 5 to 6 is palindrome,
                    if (palindrome[subIdx][strIdx] && minCuts[subIdx - 1] < minCuts[strIdx]) // if the split string(minIdx to strIdx) is a palindrome and the min-cuts before the cut + 1 is lesser than min-cuts at current encounter character "b" at index 6 we change the value because making a cut before bears minimum value with the inclusion of current character.
                        minCuts[strIdx] = minCuts[subIdx - 1] + 1;
                }
            }
        }
        return minCuts[minCuts.length - 1];
    }

    // Time : O(N^2) and Space : O(N^2)
    public static int palindromePartitioningMinCutsBetter(String str) {
        int length = str.length();
        char[] input = str.toCharArray();
        boolean[][] palindrome = new boolean[length][length];

        // we are building the palindrome array without the external function call to isPalindrome method, using dynamic programming
        for (int charIdx = 0 ; charIdx < str.length(); charIdx++)
            palindrome[charIdx][charIdx] = true;

        for (int len = 2; len <= str.length(); len++) {
            for (int i = 0; i <= length - len; i++) {
                int j = (i + len) - 1;
                if (len == 2)
                    palindrome[i][j] = (input[i] == input[j]);
                else
                    palindrome[i][j] = (input[i] == input[j] && palindrome[i + 1][j - 1]); // using previous results.
            }
        }

        //"noonabbad"
        int[] minCuts = new int[str.length()]; // to keep track of the min cuts needed for each string length
        for (int strIdx = 0; strIdx < str.length(); strIdx++) {
            if (palindrome[0][strIdx] == true) { // if the string length between 0 and the strIdx(3),
                minCuts[strIdx] = 0; // minsCuts[3] which is for string "noon" is 0 because its palindrome
            } else {
                // for iteration 6, newly added character str[6] which is "b" with input string "noonab". This else case is reached because "noonabb" is not a palindrome, have to find min cuts within the string if it had a palindrome, hence the prior string "noonab" is combined in multi combination with newly encountered char "b" for palindrome possibilities
                minCuts[strIdx] = minCuts[strIdx - 1] + 1; // Eg: for strIdx(6) checking min cuts for "noonabb", pre-assumption that just a cut before it, cut between "noonab" and "b", minCuts[5](already has min-cut value for string "noonab")  + 1 (pre-assumption-cut which we did now because of the newly encountered character "b")
                for (int subIdx = 1; subIdx < strIdx; subIdx++) { // we progress only till index 5, because the value at index 6 is processed in previous  step.(making a cut till index 5 and adding 1 to it). assuming cut at 1 and checking 1 to 6(including newly encountered char "b" with rest) is palindrome, assuming cut at 2 and checking 2 to 6 is palindrome,..., assuming cut at 5 and checking 5 to 6 is palindrome,
                    if (palindrome[subIdx][strIdx] && minCuts[subIdx - 1] < minCuts[strIdx]) // if the split string(minIdx to strIdx) is a palindrome and the min-cuts before the cut + 1 is lesser than min-cuts at current encounter character "b" at index 6 we change the value because making a cut before bears minimum value with the inclusion of current character.
                        minCuts[strIdx] = minCuts[subIdx - 1] + 1;
                }
            }
        }
        return minCuts[minCuts.length - 1];
    }

    public static boolean isPalindrome(char[] str, int start, int end) {
        int left = start;
        int right = end;
        while (left < right) {
            if (str[left] != str[right])
                return false;
            left++;
            right--;
        }
        return true;
    }
}
