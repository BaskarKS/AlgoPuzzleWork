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
        String str1 = "abd";
        String str2 = "";
        int minEdits = levenshteinDistance(str1, str2);
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
    public static int min(int pos1, int pos2, int pos3) {
        return (pos1 <= pos2) ?
                     ((pos1 <= pos3) ? pos1 : pos3) :
                        (pos2 <= pos3) ? pos2 : pos3;
    }
}
