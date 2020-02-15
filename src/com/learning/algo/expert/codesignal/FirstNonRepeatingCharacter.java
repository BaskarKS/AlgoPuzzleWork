package codesignal;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

/*
* ip : "aaabcccdeeef" op: 'b'
* ip : "abcbad" op : 'c'
* ip : "abcabcabc" op : ''
* */
public class FirstNonRepeatingCharacter {
    public static char firstNonRepeatingChar(String word) {
        char retValue = '-';
        Set<Character> visited = new HashSet<>();
        for (int idx = 0; idx < word.length(); idx++) {
            char ch = word.charAt(idx);
            int nextIdx = word.indexOf(ch,idx + 1);
            if (nextIdx == -1 && !visited.contains(ch))
                return ch;
            visited.add(ch);
        }
        return retValue;
    }

    public static char firstNonRepeatingCharSolOne(String word) {
        char cantFind = '-';
        final int ALPHABET_SIZE  = 26;
        int[] counts = new int[ALPHABET_SIZE];
        for (char ch : word.toCharArray())
            counts[ch - 'a']++;
        for (char ch : word.toCharArray())
            if (counts[ch - 'a'] == 1)
                return ch;
        return cantFind;
    }

    public static char firstNonRepeatingCharSolTwo(String word) {
        char cantFind = '-';
        Map<Character, Integer> count = new HashMap<>();
        for (char ch : word.toCharArray()) {
            if (!count.containsKey(ch))
                count.put(ch, 1);
            else
                count.put(ch, count.get(ch) + 1);
        }
        for (char ch : word.toCharArray()) {
            if (count.get(ch) == 1)
                return ch;
        }
        return cantFind;
    }

    public static char firstNonRepeatingCharSolThree(String word) {
        char cantFind = '-';
        for (int idx = 0; idx < word.length(); idx++ ) {
            if (word.indexOf(word.charAt(idx)) ==
                            word.lastIndexOf(word.charAt(idx)))
                return word.charAt(idx);
        }
        return cantFind;

    }
}
