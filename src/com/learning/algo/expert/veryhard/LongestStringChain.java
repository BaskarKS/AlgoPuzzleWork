package veryhard;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class LongestStringChain {

    //Time Complexity : O(N * M^2 * N log n)
    // ------------------------- Longest String Chain ----------------------- //
    public static List<String> sortStringOnLength(List<String> array) {
        Comparator<String> compareStrings = new Comparator<String>() {
            @Override
            public int compare(String s, String t1) {
                int ret = 1;
                if (s.length() == t1.length())
                    ret = 0;
                else if (s.length() < t1.length())
                    ret = -1;
                else
                    ret = 1;
                return ret;
            }
        };
        Object[] wordsInObject = array.toArray();
        String[] words = Arrays.copyOf(wordsInObject, wordsInObject.length, String[].class);
        Arrays.sort(words, compareStrings);
        return Arrays.asList(words);
    }

    static class StringEntry {
        String nextString = "";
        int count = 1;

        public String getNextString() {
            return nextString;
        }

        public int getCount() {
            return count;
        }

        public void setNextString(String nextString) {
            this.nextString = nextString;
        }

        public void setCount(int count) {
            this.count = count;
        }
    }

    public static List<String> longestStringChain(List<String> strings) {
        // Write your code here.
        List<String> sortedList = new ArrayList<>(strings);
        sortedList.sort(new Comparator<String>() {
            @Override
            public int compare(String s, String t1) {
                int ret = 1;
                if (s.length() == t1.length())
                    ret = 0;
                else if (s.length() < t1.length())
                    ret = -1;
                else
                    ret = 1;
                return ret;
            }
        });
        //List<String> sortedList = sortStringOnLength(strings);
        Map<String, StringEntry> longestStringMap = new HashMap<>();
        for (String string : sortedList) {
            longestStringMap.put(string, new StringEntry());
            findLongestStringChain(string, longestStringMap);
        }
        //calculate the output string list
        return getFinalList(longestStringMap);
    }

    public static List<String> getFinalList(Map<String, StringEntry> longestStringMap) {
        List<String> outputList = new ArrayList<>();
        int maxCount = 0;
        String maxWord = "";
        for (String word : longestStringMap.keySet()) {
            int currCount = longestStringMap.get(word).getCount();
            if (currCount > maxCount) {
                maxCount = currCount;
                maxWord = word;
            }
        }
        while (!maxWord.isEmpty()) {
            outputList.add(maxWord);
            maxWord = longestStringMap.get(maxWord).getNextString();
        }
        return outputList.size() == 1 ? new ArrayList<>() : outputList;
    }

    public static void findLongestStringChain(String string, Map<String, StringEntry> longestStringMap) {
        for (int index = 0; index < string.length(); index++) {
            String subString = getSubString(string, index);
            if (!longestStringMap.containsKey(subString))
                continue;
            StringEntry currentStringObj = longestStringMap.get(string);
            StringEntry subStringObj = longestStringMap.get(subString);
            if (subStringObj.getCount() + 1 > currentStringObj.getCount()) {
                currentStringObj.setNextString(subString);
                currentStringObj.setCount(subStringObj.getCount() + 1);
                longestStringMap.put(string, currentStringObj);
            }
        }
    }

    public static String getSubString(String string, int index) {
        return string.substring(0, index) + string.substring(index + 1);
    }

    // ------------------------- Longest String Chain ----------------------- //

    public static void main(String[] args) {

        List<String> wordList = new ArrayList<>();
        wordList.add("abde");
        wordList.add("abc");
        wordList.add("abd");
        wordList.add("abcde");
        wordList.add("ade");
        wordList.add("ae");
        wordList.add("1abde");
        wordList.add("abcdef");
        longestStringChain(wordList);

    }
}
