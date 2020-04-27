package hard;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class NumbersInPi {

    // ---------------------------- Numbers in Pi ----------------------------------------- //
    static class StringInfo {
        int length;
        String string;

        public int getLength() {
            return length;
        }

        public void setLength(int length) {
            this.length = length;
        }

        public void setString(String string) {
            this.string = string;
        }

        public String getString() {
            return this.string;
        }

        public StringInfo(int length, String string) {
            this.length = length;
            this.string = string;
        }
    }

    public static int numbersInPiBetter(String pi, String[] numbers) {
        HashMap<Integer, List<StringInfo>> indexSizeData = new HashMap<>();
        for (String each : numbers) {
            int index = pi.indexOf(each);
            int length = each.length();
            if (!indexSizeData.containsKey(index))
                indexSizeData.put(index, new ArrayList<>());
            indexSizeData.get(index).add(new StringInfo(length, each));
            while (index != -1) {
                index = pi.indexOf(each, index + 1);
                if (index != -1) {
                    if (!indexSizeData.containsKey(index))
                        indexSizeData.put(index, new ArrayList<>());
                    indexSizeData.get(index).add(new StringInfo(length, each));
                }
            }
        }
        List<StringInfo> startStrings = indexSizeData.get(0);
        int min = Integer.MAX_VALUE;
        for (StringInfo startString : startStrings) {
            int count = 0;
            String finalString = startString.string;
            int index = startString.length;
            while (index <= pi.length() && indexSizeData.containsKey(index)) {
                finalString += indexSizeData.get(index).get(0).getString();
                index += indexSizeData.get(index).get(0).getLength();
                count++;
            }
            if (finalString.equals(pi) && count < min)
                min = count;
        }
        return (min == Integer.MAX_VALUE) ? -1 : min;
    }
    // O(n^3 + m)time ( n - n time for prefix, and again n times for suffix, n time for slicing(subString)
    // n is no of digits in pi, m is no of favourite numbers
    //O(n + m) space - n is for space of cache for n numbers, m is size of numbers in HashSet

    //Forward Way - big to solving smaller chunks
    public static int numbersInPiBest1(String pi, String[] numbers) {
        Set<String> numberData = new HashSet<>();
        for (String number : numbers)
            numberData.add(number);
        HashMap<Integer,Integer> cache = new HashMap<>();
        int minimum = getMinimumSpaces(pi, cache, numberData, 0);
        return (minimum == Integer.MAX_VALUE) ? -1 : minimum;
    }
    // Reverse way - solving smaller chunks first and finding solution for bigger then
    public static int numbersInPiBest2(String pi, String[] numbers) {
        Set<String> numberData = new HashSet<>();
        for (String number : numbers)
            numberData.add(number);
        HashMap<Integer,Integer> cache = new HashMap<>();
        for (int i = pi.length() - 1; i >= 0; i--)
            getMinimumSpaces(pi, cache, numberData, i);
        return (cache.get(0) == Integer.MAX_VALUE) ? -1 : cache.get(0);
    }
    public static int getMinimumSpaces(String pi, HashMap<Integer,Integer> cache, Set<String> numberData, int idx ) {
        //Base condition
        if (idx == pi.length())
            return -1;
        if (cache.containsKey(idx))
            return cache.get(idx);
        int minimum = Integer.MAX_VALUE;
        for (int index = idx; index < pi.length(); index++) {
            String prefix = pi.substring(idx, index + 1);
            if (numberData.contains(prefix)) {
                int minimumSuffix = getMinimumSpaces(pi, cache, numberData, index + 1);
                if (minimumSuffix == Integer.MAX_VALUE)
                    minimum = Math.min(minimum, minimumSuffix);
                else
                    minimum = Math.min(minimum, minimumSuffix + 1);
            }
        }
        cache.put(idx, minimum);
        return cache.get(idx);
    }

    public static void main(String[] args) {

        String pi = "3141592653589793238462643383279";
        String[] numbers = {"314159265358979323846", "26433", "8", "3279", "314159265",
            "35897932384626433832", "79"};
        String[] numbers1 = {"3", "314", "49", "9001", "15926535897", "14", "9323", "8462643383279",
            "4", "793"};
        String[] numbers2 = {"3", "1", "4", "592", "65", "55", "35", "8", "9793", "2384626", "83279"};
        int minSeparators = numbersInPiBest2(pi, numbers2);
    }
}
