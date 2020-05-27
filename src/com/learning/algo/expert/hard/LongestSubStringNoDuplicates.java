package hard;

import java.util.HashMap;
import java.util.Map;

public class LongestSubStringNoDuplicates {
    /*
    function takes in a string, returns a longest substring without duplicate characters
    Eg:
    Ip: "clementisacap"
    op: "mentisac"
    * */
    public static void main(String[] args) {
        String stringHasLongestSubString = "baskarkadarisuribabu";
        System.out.println(longestSubstringWithoutDuplicationBetter(stringHasLongestSubString));
    }

    // Time Complexity : O(n) ,
    // Space Complexity : O(min(N, A)) - A is total number of distinct alphabets in input string
    public static String longestSubstringWithoutDuplication(String str) {
        // Write your code here
        if (str == null || str.length() == 0)
            return str;
        Map<Character, Integer> charLocations = new HashMap<>();
        StringBuilder longestSubString = new StringBuilder();
        int position = 0, startIdx = 0;
        while (position < str.length()) {
            char charAtPosition = str.charAt(position);
            if (charLocations.containsKey(charAtPosition)) {
                startIdx = Math.max(startIdx, charLocations.get(charAtPosition) + 1);
            }
            charLocations.put(charAtPosition, position);
            String subString = str.substring(startIdx, position + 1);
            if (longestSubString.length() < subString.length()) {
                longestSubString.setLength(0);
                longestSubString.append(subString);
            }
            position++;
        }
        return longestSubString.toString();
    }

    public static String longestSubstringWithoutDuplicationBetter(String str) {
        // Write your code here
        if (str == null || str.length() == 0)
            return str;
        Map<Character, Integer> charLocations = new HashMap<>();
        int position = 0, startIdx = 0;
        int[] locations = new int[] {0, 1};
        while (position < str.length()) {
            char charAtPosition = str.charAt(position);
            if (charLocations.containsKey(charAtPosition)) {
                startIdx = Math.max(startIdx, charLocations.get(charAtPosition) + 1);
            }
            charLocations.put(charAtPosition, position);
            if (locations[1] - locations[0] < position + 1 - startIdx) {
                locations[0] = startIdx;
                locations[1] = position + 1;
            }
            position++;
        }
        return str.substring(locations[0], locations[1]);
    }
}
