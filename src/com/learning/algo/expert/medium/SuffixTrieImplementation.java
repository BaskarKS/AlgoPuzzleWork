package medium;

import java.util.HashMap;
import java.util.Map;


/*
ip : "babc" -> suffix are ("babc", "abc", "bc", "c")
op :
{   "c" : {"*": true},
     "b" : {
        "c" : {"*": true},
        "a" : {"b" : { "c" : {"*" : true}}},
       },
     "a": {"b": {"c": {"*": true}}},
}
* */
public class SuffixTrieImplementation {
    public static void main(String[] args) {
        SuffixTrie trie = new SuffixTrie("babc");
        System.out.println(trie.contains("bab"));
    }
    static class TrieNode {
        Map<Character, TrieNode> children = new HashMap<Character, TrieNode>();
    }

    static class SuffixTrie {
        TrieNode root = new TrieNode();
        char endSymbol = '*';

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
        private void addToTrie(TrieNode node, String str, int idx) {
            if (idx >= str.length()) {
                node.children.put(endSymbol, null);
                return;
            }
            if (!node.children.containsKey(str.charAt(idx)))
                node.children.put(str.charAt(idx), new TrieNode());
            addToTrie(node.children.get(str.charAt(idx)), str, idx + 1);
        }
        public boolean contains(String str) {
            // Write your code here.
            TrieNode current = root;
            for (int idx = 0; idx < str.length(); idx++) {
                char ch = str.charAt(idx);
                if (!current.children.containsKey(ch))
                    return false;
                current = current.children.get(ch);
            }
            return current.children.containsKey(endSymbol);
        }
    }
}
