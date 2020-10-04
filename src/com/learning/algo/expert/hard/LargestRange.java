package hard;

import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;


/*

function takes a array of numbers
return a array with first element as first number in range, second element
as last number in range.

range of numbers is defined as set of numbers that comes right after each other
in set of real integers.
Eg: Op [2, 6] represent range of [2, 3, 4, 5, 6]

numbers dont need be in sorted order or next to each other in input array.
input array can have duplicates
input array can have negative values

Assume: there will be only one largest range.

Eg:
Ip : [1, 11, 3, 0, 15, 5, 2, 4, 10, 7, 12, 6]
op: [0, 7]
* */
public class LargestRange {
    public static void main(String[] args) {
        int[] numbers = {1, 1, 1, 3, 4};
        System.out.println(Arrays.toString(numbers));
        int[] result = largestRangeSimple(numbers);
        System.out.println(Arrays.toString(numbers));
        System.out.println(Arrays.toString(result));
    }

    //Time : O(n log(n)), Space : O(1)
    public static int[] largestRange(int[] array) {
        // Write your code here.
        Arrays.sort(array);
        int idx = 0;
        int lowRange = array[idx];
        int highRange = array[idx];
        int[] result = {lowRange, highRange};
        int maxCount = 1;
        int count = 1;

        while (idx < array.length - 1) {
                if (array[idx] == array[idx + 1]) {
                    idx += 1;
                    count += 1;
                    continue;
                }
                else if (array[idx] + 1 == array[idx + 1]) {
                    idx += 1;
                    highRange = array[idx];
                    continue;
                } else {
//                    if (highRange - lowRange > result[1] - result[0]) {
                    if (count > maxCount) {
                        result[0] = lowRange;
                        result[1] = highRange;
                        maxCount = count;
                        count = 1;
                    }
                    idx += 1;
                    lowRange = array[idx];
                    highRange = array[idx];
                }
        }
//        if (highRange - lowRange > result[1] - result[0]) {
         if (count > maxCount) {
            result[0] = lowRange;
            result[1] = highRange;
        }
        return result;
    }


    public static int[] largestRangeSimple(int[] array) {
        Map<Integer, Boolean> explored = new HashMap<>();
        for (int each : array)
            explored.put(each, true);
        int maxCount = 0;
        int[] result = new int[2];

        for (int number : array) {
            if (!explored.get(number))
                continue;
            explored.put(number, false);
            int currentCount = 1;
            int left = number - 1;
            int right = number + 1;
            while (explored.containsKey(left)) {
                explored.put(left, false);
                left -= 1;
                currentCount += 1;
            }
            while (explored.containsKey(right)) {
                explored.put(right, false);
                right += 1;
                currentCount += 1;
            }
            if (currentCount > maxCount) {
                maxCount = currentCount;
                result[0] = left + 1;
                result[1] = right - 1;
            }
        }
        return result;
    }
}
