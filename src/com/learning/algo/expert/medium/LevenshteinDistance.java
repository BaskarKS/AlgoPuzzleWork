package medium;
/*
* Write a function that takes in two strings, and returns the
* minimum number of edit operations that need to be performed
* on the first string to obtain the second string.
* There are three edit operations
* 1. Insertion of a character.
* 2. Deletion of a character.
* 3. Substitution of a character for another.
*
* ip : "abc", "yabd"
*                       a -> y -> 1 -> edit y to a
*                       a -> ya -> 1 -> add y
*                       a -> yab -> 2 -> (add y and b)
*                       a -> yabd -> 3 -> (add b, d, y)
*                       ab -> y -> 2 -> (edit a to y and del b)
*                       ab -> ya -> 2 -> (del b, add y)
*                       ab -> yab -> 1 -> (add y)
*                       ab -> yabd -> 2 -> (add y, add d)
*                      abc -> y -> 3 (del a and b, edit c to y)
*                      abc -> ya -> 3 (del b and c, add y)
*                      abc -> yab -> 2 (del c, add y)
*                      abc -> yabd -> 2 (add y, edit c to d)
* op : 2 (insert "y"; substitute "c" for "d")
*               0,  1,  2,  3,  4
*              ""  "y" "a" "b" "d"
*      0  ""    0,  1,  2,  3,  4
*      1 "a"    1,  1,  1,  2,  3
*      2 "b"    2,  2,  2,  1,  2
*      3 "c"    3,  3,  3,  2,  2
* */
public class LevenshteinDistance {
    public static void main(String[] args) {
        String str1 = "baskar";
        String str2 = "jaasritha";
        int minEdits = levenshteinDistanceMinSpace(str1, str2);
        System.out.println(minEdits);
    }
    public static int levenshteinDistance(String str1, String str2) {
        if ((str1 == null || str1.isEmpty())
                && (str2 == null || str2.isEmpty()))
            return 0;
        if (str1.equals(str2))
            return 0;
        int[][] minEdits = new int[str1.length() + 1][str2.length() + 1];
        for (int row = 0; row <= str1.length(); row++)
            minEdits[row][0] = row;
        for (int col = 0; col <= str2.length(); col++)
            minEdits[0][col] = col;
        for (int row = 1; row <= str1.length(); row++) {
            for (int col = 1; col <= str2.length(); col++) {
                if (str1.charAt(row - 1) == str2.charAt(col - 1)) {
                    minEdits[row][col] = minEdits[row - 1][col - 1];
                } else {
                    minEdits[row][col] = 1 + min(minEdits[row - 1][col - 1],
                                                 minEdits[row][col - 1],
                                                  minEdits[row - 1][col]);
                }

            }
        }
        return minEdits[str1.length()][str2.length()];
    }

    // O(NM) time complexity | space complexity is O(min(N,M))
    public static int levenshteinDistanceMinSpace(String str1, String str2) {
        if ((str1 == null || str1.isEmpty()) &&
                str2 == null || str2.isEmpty())
            return 0;
        if (str1.equals(str2))
            return 0;
        String small = getBigString(false, str1, str2);
        String big = getBigString(true, str1, str2);
        int[] oddEdits = new int[small.length() + 1]; //each row length
        int[] evenEdits = new int[small.length() + 1];
        //we start iteration with odd value, we use even-array in beginning, initializing it
        for (int idx = 0; idx <= small.length(); idx++) {
            evenEdits[idx] = idx;
        }

        int[] currentEdits = null, prevEdits = null;
        for (int row = 1; row <= big.length(); row++) {
            if (row % 2 == 1) {
                currentEdits = oddEdits;
                prevEdits = evenEdits;
            } else {
                currentEdits = evenEdits;
                prevEdits = oddEdits;
            }
            currentEdits[0] = row; // position of big string char in bigString
            for (int col = 1; col <= small.length(); col++) {
                if (big.charAt(row - 1) == small.charAt(col - 1)) {
                    currentEdits[col] = prevEdits[col - 1];
                } else {
                    currentEdits[col] = 1 + min(currentEdits[col - 1], prevEdits[col],
                                                        prevEdits[col - 1]);
                }
            }
        }
        //if the length of big string is even the loop finishes with evenEdits as current
        //if the length of big string is odd the loop finishes with oddEdits as current
        return (big.length() % 2 == 0) ? evenEdits[small.length()] : oddEdits[small.length()];
    }

    public static String getBigString(boolean needBigOrSmall, String str1, String str2) {
        return needBigOrSmall ? (str1.length() >= str2.length()) ? str1 : str2 :
                str1.length() >= str2.length() ? str2 : str1;
    }

    public static int min(int pos1, int pos2, int pos3) {
        return (pos1 <= pos2) ?
                     ((pos1 <= pos3) ? pos1 : pos3) :
                        (pos2 <= pos3) ? pos2 : pos3;
    }
}
