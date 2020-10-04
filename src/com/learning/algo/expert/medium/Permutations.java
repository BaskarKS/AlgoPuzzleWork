package medium;

import java.util.ArrayList;
import java.util.List;

/*
* function takes in an array of unique integers returns an array of all permutations
* of integers in no particular order
*
* if input array is empty, returns a empty array
*
* Eg:
* ip : {1, 2, 3}
* op {{1, 2, 3}, {1, 3, 2}, {2, 1, 3}, {2, 3, 1}, {3, 1, 2}, {3, 2, 1}}
* */
public class Permutations {
    public static void main(String[] args) {
        System.out.println(getPermutationsBetter(List.of(1, 2, 3)));
    }

    //Time : O(n! * n^2)
    //Space : O(n! * n)
    public static List<List<Integer>> getPermutations(List<Integer> array) {
        // Write your code here.
        List<List<Integer>> result = new ArrayList<>();
        getPermutationHelper(array,new ArrayList<>(), result);
        return result;
    }

    private static void getPermutationHelper(List<Integer> array,
                                             List<Integer> composed, List<List<Integer>> result) {
        if (array.size() == 0  && composed.size() > 0) {
            result.add(composed);
            return;
        }

        for (int idx = 0; idx < array.size(); idx++) {
            List<Integer> newArray = new ArrayList<>(array);
            newArray.remove(idx);
            List<Integer> newComposed = new ArrayList<>(composed);
            newComposed.add(array.get(idx));
            getPermutationHelper(newArray, newComposed, result);
        }
    }

    public static List<List<Integer>> getPermutationsBetter(List<Integer> array) {
        List<List<Integer>> result = new ArrayList<>();
        getPermutationHelperBetter(0, array, result );
        return result;
    }

    private static void getPermutationHelperBetter(int i, List<Integer> array, List<List<Integer>> result) {
        if (i == array.size() - 1) {
            result.add(new ArrayList<>(array));
            return;
        } else {
            for (int j = i; j < array.size(); j++) {
                swap(array, i, j);
                getPermutationHelperBetter(i + 1, array, result);
                swap(array, i, j);
            }
        }
    }
    public static void swap(List<Integer> array, int i, int j) {
        if (i == j) return;
        int temp = array.get(i);
        array.set(i,  array.get(j));
        array.set(j, temp);
    }
}
