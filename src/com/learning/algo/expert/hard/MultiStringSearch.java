package hard;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import medium.SuffixTrieImplementation;

/*
* ip : bigString : "this is a big string"
* smallStrings = {"this", "yo", "is", "a", "bigger", "string", "kappa"}
* op : {true, false, true, true, false, true, false}
* */
public class MultiStringSearch {

    public static void main(String[] args) {
        String bigString = "this is a big string";
        String[] smallStrings = {"this", "is", "yo", "a", "bigger", "string", "kappa"};
        List<Boolean> presence = multiStringSearchSolutionThree(bigString, smallStrings);
        System.out.println(presence);
    }
    // ---------------------------- Solution One Start - BruteForce Approach ----------------------------
    // Time : O(bns), Space: O(n)
    public static List<Boolean> multiStringSearch(String bigString, String[] smallStrings) {
        // Write your code here.
        List<Boolean> result = new ArrayList<>();
        for (int idx = 0; idx < smallStrings.length; idx++) {
            String smallString = smallStrings[idx];
            result.add(checkSmallStringInBigString(bigString, smallString));
        }
        return result;
    }

    private static boolean checkSmallStringInBigString(String bigString, String smallString) {
        boolean result = false;
        int bigLength = bigString.length();
        for (int idx = 0; idx < bigLength; idx++) {
            if (idx + smallString.length() > bigLength)
                break;
            if (isInBigString(bigString, smallString, idx))
                return true;
        }
        return false;
    }

    private static boolean isInBigString(String bigString, String smallString, int idx) {
        int bigStartIdx = idx;
        int bigEndIdx = idx + (smallString.length() - 1);
        int smallStartIdx = 0;
        int smallEndIdx = (smallString.length() - 1);
        while (smallStartIdx <= smallEndIdx) {
            if (bigString.charAt(bigStartIdx) != smallString.charAt(smallStartIdx) ||
                    bigString.charAt(bigEndIdx) != smallString.charAt(smallEndIdx))
                return false;
            bigStartIdx += 1;
            bigEndIdx -= 1;
            smallStartIdx += 1;
            smallEndIdx -= 1;
        }
        return true;
    }
    // ---------------------------- Solution One End - BruteForce Approach ----------------------------
    // ---------------------------- Solution Two Start - SuffixTrie Approach ----------------------------
    //build a suffixTrie for big string which take O(b^2) time
    // check n small string in the trie will take O(ns) time, s is length of longest string in trie
    //Total Time complexity is : O(b^2 + ns)
    //Space Complexity is : O(b^2 + n) , b^2 is for building trie, n is the result boolean
    public static List<Boolean> multiStringSearchSolutionTwo(String bigString, String[] smallStrings) {
        List<Boolean> result = new ArrayList<>();
        SuffixTrie trie = new SuffixTrie(bigString);
        for (String smallString : smallStrings)
            result.add(trie.contains(smallString));
        return result;
    }
    static class TrieNode {
        Map<Character, MultiStringSearch.TrieNode> children = new HashMap<Character, MultiStringSearch.TrieNode>();
        String word = null;
        char endSymbol = '*';
    }

    static class SuffixTrie {
        MultiStringSearch.TrieNode root = new MultiStringSearch.TrieNode();


        public SuffixTrie(String str) {
            populateSuffixTrieFrom(str);
        }

        public void populateSuffixTrieFrom(String str) {
            // Write your code here.
            for (int idx = 0; idx < str.length(); idx++) {
                String suffix = str.substring(idx, str.length());
                addToTrie(root, suffix, 0);
            }
        }
        private void addToTrie(MultiStringSearch.TrieNode node, String str, int idx) {
            if (idx >= str.length()) {
                return;
            }
            if (!node.children.containsKey(str.charAt(idx)))
                node.children.put(str.charAt(idx), new MultiStringSearch.TrieNode());
            addToTrie(node.children.get(str.charAt(idx)), str, idx + 1);
        }
        public boolean contains(String str) {
            // Write your code here.
            MultiStringSearch.TrieNode current = root;
            for (int idx = 0; idx < str.length(); idx++) {
                char ch = str.charAt(idx);
                if (!current.children.containsKey(ch))
                    return false;
                current = current.children.get(ch);
            }
            return true;
        }
    }

    // ---------------------------- Solution Two end - SuffixTrie Approach ----------------------------

    // ---------------------------- Solution Three Start - Trie Approach ----------------------------
/*
 Solution 3: using a suffix trie for small strings and check the big string for words in trie
 Time : O(bs + ns)
 b - length of big string, s - traversals in trie
 n - no of small string, s - length of the largest small string - ns is for building tire
 Space: O(ns) - n is no of small string to return status of present or not with True / False
*/

    public static class Trie {
        TrieNode root = new TrieNode();


        public void insert(String str) {
            TrieNode current = root;
            for (char ch : str.toCharArray()) {
                if (!current.children.containsKey(ch))
                    current.children.put(ch, new TrieNode());
                current = current.children.get(ch);
            }
            current.children.put(current.endSymbol, new TrieNode());
            current.children.get(current.endSymbol).word = str;
        }
    }
    public static List<Boolean> multiStringSearchSolutionThree(String bigString, String[] smallStrings) {
        List<Boolean> result = new ArrayList<>(smallStrings.length);
        Trie trie = new Trie();
        for (String smallString : smallStrings)
            trie.insert(smallString);
        Set<String> containedWords = new HashSet<>();
        for (int idx = 0; idx < bigString.length(); idx++) {
            getContainedWords(bigString, trie, idx, containedWords);
        }
        for (int idx = 0; idx < smallStrings.length; idx++) {
            if (containedWords.contains(smallStrings[idx]))
                result.add(idx, true);
            else
                result.add(idx, false);
        }
        return result;
    }

    private static void getContainedWords(String bigString, Trie trie, int idx, Set<String> containedWords) {
        TrieNode current= trie.root;
        for (int i = idx; i < bigString.length(); i++) {
            char ch = bigString.charAt(i);
            if (!current.children.containsKey(ch))
                return;
            current = current.children.get(ch);
            if (current.children.containsKey(current.endSymbol))
                containedWords.add(current.children.get(current.endSymbol).word);
        }
    }
}
