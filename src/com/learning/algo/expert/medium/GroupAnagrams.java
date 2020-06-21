package medium;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/*
takes input of strings and group anagrams together

* sample ip : {"yo", "act", "flop", "tac", "cat", "oy", "olfp"}
* op : {{"yo", "oy"}, {"flop", "olfp"}, {"act", "tac", "cat"}}
* */
public class GroupAnagrams {
    /*
# Time : O(w * n * log(n) + n * w * log(w))
# w * n * log(n) => n log (n) for sorting of each letter in a word - w is no of words
# n * w * log(w) => w log(w) is for sorting indices - sorting is based on word, hence 'n' is the length of longest word

# Space : O(w * n) => 'w' is max word length and 'n' is no of words
    * */
    public static List<List<String>> groupAnagrams(List<String> words) {
        List<List<String>> result = new ArrayList<>();
        if (words.size() == 0)
            return result;
        int[] index = new int[words.size()];
        for (int idx = 0; idx < index.length; idx++)
            index[idx] = idx;
        List<WordIndex> sortedChars = new ArrayList<>();
        for (int idx = 0; idx < words.size(); idx++) {
            char[] word = words.get(idx).toCharArray();
            Arrays.sort(word);
            sortedChars.add(new WordIndex(new String(word), idx));
        }
        Collections.sort(sortedChars, new Comparator<WordIndex>() {
            @Override
            public int compare(WordIndex wordIndex, WordIndex t1) {
                return wordIndex.getWord().compareTo(t1.getWord());
            }
        });

        List<String> currentAnagrams = new ArrayList<>();
        String currentWord = sortedChars.get(0).getWord();
        for (WordIndex value : sortedChars) {
            if (value.getWord().equals(currentWord)) {
                currentAnagrams.add(words.get(value.getIndex()));
                continue;
            }
            result.add(new ArrayList<>(currentAnagrams));
            currentAnagrams.clear();
            currentAnagrams.add(words.get(value.getIndex()));
            currentWord = value.getWord();
        }
        result.add(new ArrayList<>(currentAnagrams));
        return result;
    }
    public static class WordIndex {
        private String word;
        private int idx;
        public WordIndex(String word, int idx) {
            this.word = word;
            this.idx = idx;
        }
        public int getIndex() {
            return this.idx;
        }
        public String getWord() {
            return this.word;
        }
    }

    /*
# Space : O(wn) - 'w' is max word length and 'n' is no of words
# Time : O(w * n*log(n)) - 'w' is no of words, 'n*log(n)' is sorting of characters in each word

    */
    public static List<List<String>> groupAnagramsBetter(List<String> words) {
        // Write your code here.
        List<List<String>> result = new ArrayList<>();
        if (words.size() == 0)
            return result;
        Map<String, List<String>> anagrams = new HashMap<>();
        for (String word : words) {
            char[] wordChars = word.toCharArray();
            Arrays.sort(wordChars);
            String sortedWord = new String(wordChars);
            if (anagrams.containsKey(sortedWord)) {
                anagrams.get(sortedWord).add(word);
                continue;
            }
            anagrams.put(sortedWord, new ArrayList<>());
            anagrams.get(sortedWord).add(word);
        }

        result.addAll(anagrams.values());
        return result;
    }

    public static void main(String[] args) {
        List<String> words = List.of("cat", "act", "yo", "folk", "kolf", "oy", "tac");
        List<List<String>> result = groupAnagrams(words);
        for (List<String> each: result)
            System.out.println("Anagrams : " + each);
    }
}