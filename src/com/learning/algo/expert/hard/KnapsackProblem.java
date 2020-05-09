package hard;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

/*
  given inputs as array of arrays and a maximum capacity of knapsack
  each sub-array represents an item, holds two integer values
  first integer it item's value, second integer represents item's weight

  goal is to fit items in knapsack without having the sum of their weights exceed
  the knapsack's capacity, items filling in knapsack combined value should be maximum

  return maximum combined value of picked items, as well as index of picked items

  assume, there's only one combination of items that maximizes the total value in knapsack

  Eg:
  items: [[1, 2], [4, 3], [5, 6], [6, 7]]
  capacity: 10
  op: [10, [1, 3]] // [1, 3] is index of item index 1 and 3 - should be in sorted order
* */
public class KnapsackProblem {
    public static List<List<Integer>> knapsackProblem(int[][] items, int capacity) {
        int[][] knapsackValues = new int[items.length + 1][capacity + 1]; // create a matrix to compute the maximum value for given capacity - have a extra first row for zeros and first column for zeros
        for (int i = 1; i < items.length + 1; i++) { // start computing from row 1, because row 0 value is needed to compute row 1, so we have extra row at position 0 filled with zeros
            int currentWeight = items[i - 1][1]; // to fetch correct value from input list, have to decrement 1 from i because of extra row at position zero
            int currentValue = items[i - 1][0];
            for (int c = 0; c < capacity + 1; c ++) { // calculate for all columns
                if (currentWeight > c) // from 0 to till current item weight col value, fill the previous row column values
                    knapsackValues[i][c] = knapsackValues[i - 1][c];
                else {
                    knapsackValues[i][c] = Math.max(knapsackValues[i - 1][c],
                        knapsackValues[i - 1][c - currentWeight] + currentValue); // find max among previous row, current col value and value of col - current items weight value
                }
            }
        }

        var result = new ArrayList<List<Integer>>();
        result.add(Arrays.asList(knapsackValues[items.length][capacity])); // last value - bottom-right
        result.add(getKnapsackPairsIndexes(knapsackValues, items));
        return result;
    }
    private static List<Integer> getKnapsackPairsIndexes(int[][] knapsackValues, int[][] items) {
        List<Integer> result = new ArrayList<>();
        int i = knapsackValues.length - 1; // index of last row
        int c = knapsackValues[0].length - 1; // index of last column
        while (i > 0) { // iterate till row falls to 0
            if (knapsackValues[i][c] == knapsackValues[i - 1][c]) // if the previous row, col value is same as current row, col value just skip the current row
                i -= 1; // skip current row
            else {
                result.add(i - 1); // if the previous row, col value is different as current row, col value. add the current item index to output result ( decrementing index - 1 is because of knapsackValues matrix is larger in row and col by 1 larger in row and col by one for 0
                c -= items[i - 1][1]; // move col to left by decrementing current col value with current item location weight value
                i -= 1; // go to previous row
            }
            if (c == 0) // break if col falls to 0
                break;
        }
        Collections.sort(result);
        return result;
    }

    public static void main(String[] args) {
        List<List<Integer>>result = knapsackProblem(new int[][]{{1, 2}, {4, 3}, {5, 6}, {6, 7}}, 10);
        System.out.println(result.get(0));
        System.out.println(result.get(1));

    }
}
