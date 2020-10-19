package hard;

import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

public class PatternMatcher {
    /* pattern = "xxyxxy"
     input = "gogopowerrangergogopowerranger"

      given two non empty string (pattern and input string), check normal string matches the pattern.

      s0 is said to match a pattern if
      replacing all 'x' in the pattern with some non empty substring s1 of s0 and
      replacing all 'y' in the pattern with some non empty substring s2 of s0  yields the same string s0.

      if input string doesn't match the pattern then the function should return a empty array.
      otherwise
      it should return a array holding the strings s1 and s2 that represent 'x' and 'y' in the normal string
      in that order.
      If the pattern doesn't contain and 'x' or 'y', the respective string in result should be represented
      by empty string in the final array returned

      Note: There will never be more than one pair of strings s1 and s2 that appropriately represent
      'x' and 'y' in normal string.

     1. if pattern length is bigger than actual string length then its invalid input.
     2. check whether the input pattern starts with x or with y
     3. we are composing a logic where the pattern always starts with x
     4. Hence change the input pattern in a way that it always starts with x and maintain
            a status variable which indicates the actual provided input pattern was started with y.
     5. handle if input pattern only contains 'x', handle differently if input pattern also contains 'y'
     6. we assume x string for different length, based on x string we calculate and find a y string
     7. after finding x and y string, we replace the x and y string in input pattern and match it with
             the input string, if matches we conclude the x and y string.
    */
    public static void main(String[] args) {
        String pattern = "xx"; //""yyxyyx"; //"xxyxxy";
        String input =  "gogo"; //"gogopowerrangergogopowerranger";
        System.out.println(00-30);
        String[] result = patternMatcher(pattern, input);
        System.out.println(Arrays.toString(result));
    }
    public static String[] patternMatcher(String pattern, String str) {
        // Write your code here.
        if (pattern.length() > str.length()) // if input pattern length is more than input string length, its not valid
            return new String[0];
        char[] newPattern = getNewPattern(pattern); // if the input pattern starts with 'y', get the new pattern string which always starts with 'x'
        boolean didWeChangePattern = (newPattern[0] != pattern.charAt(0)); // have a reference whether we modified the input pattern i.e pattern("yyxyyx") changed to newPattern("xxyxxy") for processing, this reference will be helpful to compose the result
        Map<Character, Integer> patternCount = new HashMap<>(); // map holds the count of 'x' and 'y' in pattern

        int firstYPos = initPatternCountAndFirstYPos(newPattern, patternCount); // initialize the map and get the first position of 'y

        int inputStrLength = str.length();
        if (patternCount.get('y') != 0) { // if the input pattern also contain 'y', which means it has both 'x' and 'y'
            for (int idx = 1; idx < inputStrLength; idx++) {
                int xLen = idx; // have fixed x length from 0 to idx, assuming x size
                int xCount = patternCount.get('x'); // count of 'x' in pattern
                int yCount = patternCount.get('y'); // count of 'y' in pattern
                int xTotalSize = (xCount * xLen); // no of 'x' * assumed size of 'x'  => total x count size
                int yTotalSize = (inputStrLength - xTotalSize); // total 'y' size is found by (total size - total x count size)
                int yLen = yTotalSize / yCount; // ('y' length of single char is found by (total 'y' length / no of 'y' counts in pattern)
                boolean validYLen = (yTotalSize % yCount == 0); // if the 'y' total size is not properly divisible by 'y' count, means the computed 'y' length is not proper
                if (yLen <= 0 || !validYLen)
                    continue; // if the 'y' length is not valid don't further process, just continue
                //"xxyxxy" => first 'y' position in pattern is 2, in every iteration x length is varied. based on x varied length in particular iteration, y starting position is calculated
                int yPos = firstYPos * xLen; // since the pattern always starts with 'x', and first 'y' position is known. starting position of 'y' in each iteration can be calculated. in first iteration x length is 1 and first y position in pattern is 2, starting position of y is (2 * 1) ,   in second iteration x length is 2 and first y position in pattern is 2, starting position of y is (2 * 2) ,
                String x = str.substring(0, xLen); // x string is fetched
                String y = str.substring(yPos, yPos + yLen); // from yPos, 'y' string is fetched
                String[] potentialMatch = new String[newPattern.length];
                for (int chIdx = 0; chIdx < newPattern.length; chIdx++)
                    potentialMatch[chIdx] = newPattern[chIdx] == 'x' ? x : y; // substituting 'x' and 'y' in pattern with found x and y string
                String match = String.join("", potentialMatch); // replaced newly predicted x and y string in pattern and form a new string to find whether its matching with input string
                if (match.compareTo(str) ==0) {
                    return didWeChangePattern == true ? new String[]{y, x} : new String[] {x, y};
                }
            }
        } else { // if the input pattern doesn't contain 'y', contains only 'x'
            boolean canBeReplaced = (str.length() % patternCount.get('x') == 0); // if the total length is exactly divisible by 'x' count in pattern which means theres a possibility that pattern can match
            int xCount = str.length() / patternCount.get('x'); // finding the exact length of 'x' string
            if (canBeReplaced) { // if theres a possibility that the pattern can match
                String x = str.substring(0, xCount);
                String[] potentialMatch = new String[newPattern.length];
                for (int chIdx = 0; chIdx < newPattern.length; chIdx++)
                    potentialMatch[chIdx] = x;
                String match = String.join("", potentialMatch); // created a string with the x found string in pattern
                if (match.compareTo(str) == 0) // return x string if theres a proper match
                    return didWeChangePattern == true ? new String[]{"", x} : new String[]{x, ""};
            }
        }
        return new String[] {}; // pattern is not matched with input string
    }

    /*
        This method has 2 responsibilities:
        First Responsibility:
        parse the input pattern for x and y characters and find the relevant count of each characters in
        the provided input pattern and set those values in the provided hashmap.
        Second Responsibility:
            find the first position/index of 'y' in the provided input pattern

    * */
    public static int initPatternCountAndFirstYPos(char[] pattern, Map<Character, Integer> patternCount) {
        if (patternCount.isEmpty()) { // initialize the map with 0
            patternCount.put('x', 0);
            patternCount.put('y', 0);
        }
        int firstYIdx = 0;
        for (int idx = 0; idx < pattern.length; idx++) {
            char eachChar = pattern[idx];
            patternCount.put(eachChar, patternCount.get(eachChar) + 1); // increment the character count, setting count in map for 'x' and 'y'
            if (firstYIdx == 0 && eachChar == 'y') // find the first 'y' position in input pattern
                firstYIdx = idx;
        }
        return firstYIdx; // return the first position of 'y
    }

/*
    Function to generate a pattern which always start with x, if the input
    pattern is already starts with 'x' we simply return it.
    If the input pattern starts with y, we replace the 'x' with 'y' and 'y' with 'x' so
    that the input pattern is modified to always starts with 'x'
*/
    // Input : "xxyxxy" we return "xxyxxy"
    // Input: "yyxyyx" we return "xxyxxy"
    public static char[] getNewPattern(String pattern) {
        char[] newPattern = new char[pattern.length()]; // form a new pattern suitable for further processing
        if (pattern.charAt(0) == 'x') { // if the first char is 'x' means the input pattern starts with 'x', hence we return it simply
            return pattern.toCharArray();
        } else {
            int idx = 0;
            while(idx < pattern.length()) { // iterating input pattern from beg to end
                char each = pattern.charAt(idx); // inspecting each char of input pattern
                newPattern[idx] = (each == 'x') ? 'y' : 'x'; // if the input pattern char is 'x' we replace new pattern with 'y'
                idx++;                                                        // if the input pattern char is 'y' we replace new pattern with 'x'
            }
            return newPattern;
        }
    }
}
