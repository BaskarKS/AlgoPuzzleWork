package medium;

import java.util.ArrayList;
import java.util.List;

public class PowerSet {

    /*
    Function takes in an array of unique integers and returns it PowerSet
    PowerSet P(x) of a "set x" is the set of all subsets of X. Eg: PowerSet of [1,2] is
    [[], [1], [2], [1,2]]
    sets in PowerSet do not need to be in any particular order
    Eg:
    Ip: [1, 2, 3]
    op: [[], [1], [2], [3], [1,2], [1,3], [2,3], [1,2,3]]
    */
    public static void main(String[] args) {
        List<List<Integer>> result = powerSetRecursive(List.of(1, 2, 3, 4));
        System.out.println(result);
    }

    //my own implementation has a bug of missing a entry
    // ip: [1,2,3,4] => will add [1,2,3], [1,3,4] but misses [1,2,4]
    public static List<List<Integer>> powerSet(List<Integer> array) {
        // Write your code here.
        List<List<Integer>> output = new ArrayList<>();
        output.add(new ArrayList<>());
        int maxSubSetSize = array.size();
        int size = 1;
        while (size <= maxSubSetSize) {
            for (int idx = 0; idx <= array.size() - size; idx++) {
                if (size == 1) {
                    output.add(List.of(array.get(idx)));
                    continue;
                }
                for (int inIdx = idx + 1; inIdx <= (array.size() - (size - 1)); inIdx++) {
                        output.add(getList(idx, inIdx, size - 1, array));
                }
            }
            size++;
        }
        return output;
    }
    public static List<Integer> getList(int fixIdx, int varIdx, int size, List<Integer> array) {
        List<Integer> newList = new ArrayList<>();
        newList.add(array.get(fixIdx));
        while(size > 0) {
                newList.add(array.get(varIdx));
                size--;
                varIdx++;
        }
        return newList;
    }

    // Simple iterative implementation, Time : O(2^n * n), Space : O(2^n * n)
    public static List<List<Integer>> powerSetIterative(List<Integer> array) {
        List<List<Integer>> output = new ArrayList<>();
        List<List<Integer>> temp;
        output.add(new ArrayList<>());
        for (int idx = 0; idx < array.size(); idx++) {
            temp = new ArrayList<>(output);
            for (List<Integer> subList : temp ) {
                List<Integer> newEntry = new ArrayList<>(subList);
                newEntry.add(array.get(idx));
                output.add(newEntry);
            }
        }
        return output;
    }

    // Simple recursive implementation, Time : O(2^n * n), Space : O(2^n * n)
    public static List<List<Integer>> powerSetRecursive(List<Integer> array) {
        return powerSetRecursiveLoop(array, array.size() - 1);
    }
    public static List<List<Integer>> powerSetRecursiveLoop(List<Integer> array, int index) {
        if (index == -1) {
            List<List<Integer>> resultList = new ArrayList<>();
            List<Integer> emptyList = new ArrayList<>();
            resultList.add(emptyList);
            return resultList;
        }
        int value = array.get(index);
        List<List<Integer>> subset = powerSetRecursiveLoop(array, index - 1);
        int loopLength = subset.size();
        for (int idx = 0; idx < loopLength; idx++) {
            List<Integer> copyOfList = new ArrayList<>(subset.get(idx));
            copyOfList.add(value);
            subset.add(copyOfList);
        }
        return subset;
    }
}
