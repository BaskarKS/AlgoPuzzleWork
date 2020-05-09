package hard;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

public class LongestCommonSubsequence {
// Time complexity: O(nm * min(n,m)), n is length of str1, m is length of str2
// Space complexity: O(nm * min(n,m)), n is length of str1, m is length of str2

    public static List<Character> longestCommonSubsequence(String str1, String str2) {
        // Write your code here.
        String[][] values = new String[str2.length() + 1][str1.length() + 1];
        for (int row = 0; row < values.length; row ++)
            for(int col = 0; col < values[0].length; col++)
                values[row][col] = "";

        for (int row = 1; row < values.length; row ++) {
            for (int col = 1; col < values[0].length; col++) {
                if (str2.charAt(row - 1) == str1.charAt(col - 1))
                    values[row][col] = values[row - 1][col - 1] + str2.charAt(row - 1);
                else {
                    values[row][col] = getMax(values[row][col - 1], values[row - 1][col]);
                }
            }
        }
        String result = values[str2.length()][str1.length()];
        List<Character> retVal = new ArrayList<>();
        for(int idx = 0; idx < result.length(); idx++)
            retVal.add(result.charAt(idx));
        return retVal;
    }
    public static String getMax(String str1, String str2) {
        return str1.length() >= str2.length() ? str1 : str2;
    }

    public static void main(String[] args) {
        List<Character> lcs = longestCommonSubsequenceBetterTime("ZXVVYZW", "XKYKZPW");
        System.out.println(lcs);
    }
// ---------------------------------------------- LCS Using Collections List ---------------------------------------
    public static List<Character> longestCommonSubsequenceUsingList(String str1, String str2) {
        // Write your code here.
        List<List<List<Character>>> lcs = new ArrayList<List<List<Character>>>();
        for (int row = 0; row < str2.length() + 1; row++) {
            lcs.add(new ArrayList<List<Character>>());
            for (int col = 0; col < str1.length() + 1; col++) {
                lcs.get(row).add(new ArrayList<Character>());
            }
        }

        for (int row = 1; row < lcs.size(); row++) {
            for (int col = 1; col < lcs.get(0).size(); col++) {
                if (str2.charAt(row - 1) == str1.charAt(col - 1)) {
                    List<Character> copy = new ArrayList<>(lcs.get(row - 1).get(col - 1));
                    copy.add(str2.charAt(row - 1));
                    lcs.get(row).set(col, copy);
                } else {
                    if (lcs.get(row - 1).get(col).size() >= lcs.get(row).get(col - 1).size()) {
                        lcs.get(row).set(col, lcs.get(row - 1).get(col));
                    } else {
                        lcs.get(row).set(col, lcs.get(row).get(col - 1));
                    }
                }
            }
        }
        return lcs.get(lcs.size() - 1).get(lcs.get(0).size() - 1);
    }
    // ---------------------------------------------- LCS Using Collections List ---------------------------------------

// Time complexity: O(nm), n is length of str1, m is length of str2
// Space complexity: O(nm), n is length of str1, m is length of str2
// -------------- LCS Using integerArray reduces time and space complexity --------------------
    public static List<Character> longestCommonSubsequenceBetterTime(String str1, String str2) {
        int[][][] lcs = new int[str2.length() + 1][str1.length() + 1][];
        for (int row = 0; row < str2.length() + 1; row++)
            for (int col = 0; col < str1.length() + 1; col++)
                lcs[row][col] = new int[] {0, 0, 0, 0};

        for (int row = 1; row < str2.length() + 1; row++) {
            for (int col = 1; col < str1.length() + 1; col++) {
                if (str2.charAt(row - 1) == str1.charAt(col - 1)) {
                    lcs[row][col] = new int[] {str2.charAt(row - 1), lcs[row - 1][col - 1][1] + 1, row - 1, col - 1};
                }
                else {
                    if (lcs[row - 1][col][1] >= lcs[row][col - 1][1]) {
                        lcs[row][col] = new int[] {-1, lcs[row - 1][col][1], row - 1, col};
                    } else {
                        lcs[row][col] = new int[] {-1, lcs[row][col - 1][1], row, col - 1};
                    }
                }
            }
        }
        return buildSequence(lcs);
    }
    public static List<Character> buildSequence(int[][][] lcs) {
        List<Character> sequence = new ArrayList<>();
        int i = lcs.length - 1;
        int j = lcs[0].length - 1;
        while (i != 0 && j != 0) {
            int[] currentEntry = lcs[i][j];
            if (currentEntry[0] != -1)
                sequence.add(0, (char) currentEntry[0]);
            i = currentEntry[2];
            j = currentEntry[3];
        }
        return sequence;
    }
}

