package veryhard;

import java.util.Arrays;
import java.util.stream.Collectors;

public class WaterFallStreams {
    /*
    You are given a TwoDimensional Array, that represents the structure of an indoor
    waterfall and a positive integer that represents the column that the waterfall's
    water source will start at. More Specifically, the water source will start directly
    above the structure and will flow downwards.

    Each row in the array  contains 0's and 1's, where a 0 represents a free space and a
    1 represents a block that water can't pass through. You can imagine that the last row
    of the array contains buckets that the water will eventually flow into; thus the last row
    of the array will always contain only 0's. You can also imagine that there are walls on
    both sides of the structure, meaning that water will never leave the structure ; it will either be
    trapped against a wall or flow into one of the buckets in the last row.

    As water flows downwards. if it hits a block, it splits evenly to the left and right-hand
    side of that block. In other words, 50% of the water flows left and 50% of it flows right.
    If a water stream is unable to flow to the left or to the right (because of a block or a wall),
    the water stream in question becomes trapped and can no longer continue to flow in that
    direction; it effectively gets stuck in the structure and can no longer flow downwards, meaning
    that 50% of the previous water stream is forever lost.

    Lastly, the input array will always contain at least two rows and one column, and the space
    directly below the water source (in the first row of the array) will always be empty, allowing
    the water to start flowing downwards.

    write a function that returns the percentage of water inside each of the bottom buckets
    after the water has flowed through the entire structure.

    you can refer to the first  4.5 minutes of this question visual explanation.

    Eg:
    Input
    array : [
        [0, 0, 0, 0, 0, 0, 0],
        [1, 0, 0, 0, 0, 0, 0],
        [0, 0, 1, 1, 1, 0, 0],
        [0, 0, 0, 0, 0, 0, 0],
        [1, 1, 1, 0, 0, 1, 0],
        [0, 0, 0, 0, 0, 0, 1],
        [0, 0, 0, 0, 0, 0, 0]
        ]
        source = 3

    output
        [0, 0, 0, 25, 25, 0, 0]

        the water will flow as follows,
        [
        [0 , 0 , 0 , .  , 0 , 0 , 0],
        [1 , .  , .  ,  .  , .  , .  , 0],
        [0 , .  , 1 , 1  , 1 , .  , 0],
        [ . , .  , .  , .   , .   , .  , . ],
        [1 , 1 , 1 , .   , .  , 1 , 0],
        [0 , 0 , 0 , .   , .  , 0 , 1],
        [0 , 0 , 0 , .   , .  , 0 , 0]
        ]
    */
//     Time : O(w^2 * h), Space O(w) , w is width of the row, h is height of array,
//     w^2 is because on worst case scenario, if entire row is filled with blocks and entire row above has water
//     -1, -1, -1, -1, -1, -1, -1 // row above is full of water
//      1,  1,  1,  1,  1,  1,  1 // this row is full of blocks , for each item in row, have to check to end of left and right. Hence w^2
//
//      Average case for Time : O(w * h)

    // At a time we traverse a row of array by evaluating with the values of row above it, then the evaluated row
    // will become the rowAbove and with this values we evaluate the row below it.
    // In a instance we check 2 rows at a time, using rowAbove values we compute the current row
    public static double[] waterfallStreams(double[][] array, int source) {
        // Write your code here.
        double[] result = null;
        double[] rowAbove = Arrays.copyOf(array[0], array[0].length);
        rowAbove[source] = -1; // presence of water stream is tracked using -1 value
        double[] currentRow = null;
        for (int row = 1; row < array.length; row++) {
            currentRow = Arrays.copyOf(array[row], array[row].length);
            for (int col = 0; col < currentRow.length; col++) { // iterate all column value for each row
                double valueAbove = rowAbove[col]; // current col location of rowAbove value
                boolean hasWaterAbove = (valueAbove < 0); // check the aboveRow value has water stream
                boolean hasBlock = currentRow[col] == 1; // currentRow col item has a block
                if (!hasWaterAbove) // if theres no water stream above current col location we continue to check for next column value
                    continue;
                // has water above
                if (!hasBlock) { //current col location's RowAbove value has a stream, if the currentRow doesn't have a block we just set the entire stream value to currentRow col location
                    currentRow[col] += valueAbove;
                    continue;
                }
                // has water above and contains block
                double splitWater = valueAbove / 2; // split the water stream value into separate half
                int rightPtr = col; // since the currentRow col location has a block we move to right and left to find possibility of stream to set in currentRow
                  // rowAbove : [0, 0, 0, -1, 1, 0, 0]
                // currentRow : [0, 0, 0,  1, 0, 0, 0]
                while (rightPtr + 1 < currentRow.length) {                          // rowAbove : [0, 0, 0, -1, 1, 0, 0]
                    rightPtr += 1;                                                                   // currentRow : [0, 0, 0,  1, 0, 0, 0]
                    if (rowAbove[rightPtr] == 1) // currentRow[col] has a block, checking the right location of rowAbove is free, if it has a block, stream is trapped
                        break;
                    if (currentRow[rightPtr] != 1) {
                        currentRow[rightPtr] += splitWater;
                        break;
                    }
                }
                int leftPtr = col;
                while (leftPtr - 1 >= 0) {
                    leftPtr -= 1;
                    if (rowAbove[leftPtr] == 1)
                        break;
                    if (currentRow[leftPtr] != 1) {
                        currentRow[leftPtr] += splitWater;
                        break;
                    }
                }
            }
            rowAbove = currentRow;
        }
        result = Arrays.stream(rowAbove).map(value -> (value < 0)? value * -100: 0).toArray();
        return result;
    }

    public static void main(String[] args) {
        double[][] array = new double[][]{{0, 0, 0, 0, 0, 0, 0},
                                                                  {1, 0, 0, 0, 0, 0, 0},
                                                                  {0, 0, 1, 1, 1, 0, 0},
                                                                  {0, 0, 0, 0, 0, 0, 0},
                                                                  {1, 1, 1, 0, 0, 1, 0},
                                                                  {0, 0, 0, 0, 0, 0, 1},
                                                                  {0, 0, 0, 0, 0, 0, 0}};
        int source = 3;
        double[] result = waterfallStreams(array, source);
        System.out.println(Arrays.toString(result));
    }
}
