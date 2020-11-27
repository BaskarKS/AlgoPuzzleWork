package easy;

import java.util.HashMap;
import java.util.Map;

public class RunLengthEncoding {
    /*
    write a function which takes in a non-empty string  and return its
    run-length encoding.

    run-length encoding is a form of lossless data compression in which runs of
    data are stored as a single  data value and count, rather than as the original
    run. " for this problem, a run of data is any sequence of consecutive , identical
    characters. so the run "AAA" would be run-length-encoded as "3A".

    Input string can contain all sorts of special characters including numbers, since
    encoded data must be decodable, this means that we cant naively run-length-encode
    long runs. Eg: the run "AAAAAAAAAAAA" cant be naively encoded as "12A". since this
    "12A" can be decoded as "AAAAAAAAAAAA" or "1AA". Thus , long runs (runs of 10 or more
    characters ) should be encoded  in a split fashion, like "9A3A"

    Eg: IP : "AAAAAAAAAAAAABBCCCCDD"
           OP : 9A4A2B4C2D
    */
/*
    public static String runLengthEncoding(String string) {
        Map<Character, Integer> countMap = new HashMap<>();
        for (Character ch : string.toCharArray()) {
            if (!countMap.containsKey(ch))
                countMap.put(ch, 0);
            countMap.put(ch, countMap.get(ch) + 1);
        }
        StringBuffer buffer = new StringBuffer();
        for (Character ch : countMap.keySet()) {
            Integer count = countMap.get(ch);
            while ((count > 9) && (count / 9) > 0) {
                buffer.append(Integer.toString(9) + ch);
                count = count - 9;
            }
            buffer.append(Integer.toString(count) + ch);
        }
        return buffer.toString();
    }*/

    public static String runLengthEncoding(String string) {
        char[] input = string.toCharArray();
        int[] dataCount = new int[] {input[0], 1};
        StringBuffer buffer = new StringBuffer();
        for (int idx = 1; idx < input.length; idx++) {
            if (input[idx] == dataCount[0]) {
                dataCount[1]++;
                continue;
            }
            buffer.append(getString(dataCount));
            dataCount[0] = input[idx];
            dataCount[1] = 1;
        }
        buffer.append(getString(dataCount));
        return buffer.toString();
    }
    public static String getString(int[] dataCount) {
        StringBuffer buffer = new StringBuffer();
        int count = dataCount[1];
        while ((count > 9) && (count / 9) > 0) {
            buffer.append(Integer.toString(9) + (char)dataCount[0]);
            count = count - 9;
        }
        if (count > 0)
            buffer.append(Integer.toString(count) + (char)dataCount[0]);
        return buffer.toString();
    }

    // Time : O(n) and Space : O(n) // for using StringBuffer of string length n
    public static String runLengthEncodingBetter(String string) {
        int currentLength = 1;
        StringBuilder buffer = new StringBuilder();
        int lastIdx = string.length() - 1;

        for (int idx = 1; idx <= lastIdx; idx++) {
            char currentChar = string.charAt(idx);
            char prevChar = string.charAt(idx - 1);
            if (currentChar != prevChar || currentLength == 9) {
                buffer.append(Integer.toString(currentLength));
                buffer.append(prevChar);
                currentLength = 0;
            }
            currentLength += 1;
        }
        buffer.append(Integer.toString(currentLength));
        buffer.append(string.charAt(lastIdx));
        return buffer.toString();
    }

    public static void main(String[] args) {
        String result = runLengthEncodingBetter("aAaAaaaaaAaaaAAAABbbbBBBB"); //"AAAAAAAAAAAAABBCCCCDD"
        System.out.println(result);
    }
}
