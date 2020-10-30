package veryhard;

import java.util.Arrays;
import java.util.function.Function;
import java.util.function.Supplier;

import javax.management.relation.RoleUnresolved;

public class KnuthMorrisPrattAlgorithm {
    /*
    Function takes in 2 strings, check first one contains the second one using
    knuth-morris-pratt algorithm

    this algorithm will help to identify a string presence in another string in linear time but
    not quadratic time.

    Its to iterate the substring(which is expected to be present in another one) once.
    and collect the prefix and suffix index details which is helpful to avoid
    iterating from the beginning.

    'i' will move forward finding suffix and records the relevant prefix (previous index
    location of suffix in previousEntry) using 'j'

    if chars at 'i' and 'j' location doesn't match which means 'i' position encountered
    a new suffix, hence to avoid to search from beginning, we move the 'j' current
    position to previous prefix if available (previousEntry[j - 1] > 0) + 1 and start
    finding the new suffix at 'i'

    If 'j' value is greater than zero, consecutively move to previous prefix and match the
    next entry to previous prefix with 'i' to find the new suffix. If it reaches the start of
    string at position 0, ignore exploring but to just increment 'i' pointer.
    */

    public static void main(String[] args) {
        String mainString = "aefoaefcdaefcdaed"; //"aefaefaefaedaefaedaefaefa"; // 25 0-24
        String subString = "aefcdaed"; //"aefaedaefaefa"; // 13 0-12
        System.out.println(knuthMorrisPrattAlgorithm(mainString, subString));
    }

    // Time Complexity : O(n + m) and Space : O(m)
    public static boolean knuthMorrisPrattAlgorithm(String string, String substring) {
        int[] patternPositions = getPrefixAndSuffixPattern(substring);
        return findStringPresence(string, substring, patternPositions);
    }

    public static boolean findStringPresence(String string, String substring, int[] patternPositions) {
        int i  = 0, j = 0;
        while (i + (substring.length() - j) <= string.length()) { // iterate only if theres a possibility of position of main-string and position of sub-string is less than main string length
            if (string.charAt(i) == substring.charAt(j)) { // both char positions matched in string and substring
                if (j == (substring.length() - 1)) // if the substring index reached the end, nothing more to explore in string
                    return true;
                else { // otherwise increment both the pointers at string and substring
                    i += 1;
                    j += 1;
                }
            } else if (j > 0) { // char at position 'j' doesn't match with char at position 'i'. Hence moving j to next location of its previous prefix position. to find whether it matches with char at 'i' position.
                j = patternPositions[j - 1] + 1;
            }
            else { // current position 'i' at string doesn't match with position 'j' at sub string and position 'j' cant move to its previous prefix because it already reached 0 position, hence only incrementing only pointer 'i' pointing  at string
                i += 1;
            }
        }
        return false;
    }

    public static int[] getPrefixAndSuffixPattern(String substring) {
        int[] patternIdx = new int[substring.length()];
        Arrays.fill(patternIdx, -1);
        int i = 1;
        int j = 0;
        while (i < substring.length()) {
            if (substring.charAt(i) == substring.charAt(j)) {
                patternIdx[i] = j; // if both chars at i and j matches, recording the j index position in the pattern array at 'i' th index
                i += 1; // moving ith position forward
                j += 1; // moving jth position forward
            } else if (j > 0) {
                j = patternIdx[j - 1] + 1; // char at position 'j' doesn't match with char at position 'i'. Hence moving j to next location of its previous prefix position. to find whether it matches with char at 'i' position.
            } else {
                i += 1; // if j is zero, means char at position 0 is matched with char at ith position, both chars don't match substring[0] != substring[i]. Hence just move 'i' forward to find next matching suffix
            }
        }
        return patternIdx;
    }

}

