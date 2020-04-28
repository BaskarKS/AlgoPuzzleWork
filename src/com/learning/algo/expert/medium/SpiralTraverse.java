package medium;

import java.util.ArrayList;
import java.util.List;

public class SpiralTraverse {
    /*
    write function takes n * m two dimen array that can be square shaped,
    an returns a array of one-dimensional of all elements in spiral order

    spiral order starts at top left corner of the two dimen array, goes to right
    and proceeds in a spiral pattern all the way until every element has been visited

    eg:
    array = [[  1,   2,   3,  4],            1, 2 , 3,
                   [12, 13, 14, 5],              8    9    4,
                   [11, 16, 15, 6],              7     6,   5,
                   [10,   9,   8, 7]]

    output: [1, 2, 3, 4, 5, 6, 7, 8, 9, 10, 11, 12, 13, 14, 15, 16]
    */
    public static void main(String[] args) {
        int[][] array = new int[][]{{1, 2, 3, 4}, {10, 11, 12, 5}, {9, 8, 7, 6}};
        // {{1, 2, 3, 4}, {10, 11, 12, 5}, {9, 8, 7, 6}};
            // {{1, 2, 3}, {8, 9, 4}, {7, 6, 5}};
        /*   { {1, 2, 3, 4, 5, 6, 7, 8, 9},
            {22, 23, 24, 25, 26, 27, 28, 29, 10},
            {21, 36, 35, 34, 33, 32, 31, 30, 11},
            {20, 19, 18, 17, 16, 15, 14, 13, 12}};*/
            //{{1, 2, 3}, {8, 9, 4}, {7, 6, 5}};
            //{{1, 2, 3, 4 }, {12, 13, 14, 5}, {11, 16, 15, 6}, {10, 9, 8, 7}};

        List<Integer> spiralVal = spiralTraverseRecursive(array);
        System.out.println(spiralVal);
    }

    // Time Complexity O(N) or O(n * m), Space Complexity : O(N) to store the return elements
    public static List<Integer> spiralTraverse(int[][] array) {
        int rowStart = 0, colStart = 0;
        int rowEnd = array.length - 1;
        int colEnd = array[0].length - 1;
        List<Integer> output = new ArrayList<>();
        while (rowStart <= rowEnd && colStart <= colEnd) {
            int rowIdx = rowStart;
            int colIdx = colStart;
            while(colIdx <= colEnd) { // move right
                output.add(array[rowIdx][colIdx]);
                colIdx++;
            }

            colIdx = colEnd;
            rowIdx = (rowStart + 1);
            if (rowIdx > rowEnd)
                break;
            else {
                while (rowIdx <= rowEnd) { // move down
                    output.add(array[rowIdx][colIdx]);
                    rowIdx++;
                }
            }

            rowIdx = rowEnd;
            colIdx = (colEnd - 1);
            if (colIdx < colStart)
                break;
            else {
                while (colIdx >= colStart) { // move left
                    output.add(array[rowIdx][colIdx]);
                    colIdx--;
                }
            }

            rowIdx = (rowEnd - 1);
            colIdx = colStart;
            if (rowIdx < rowStart) {
                break;
            } else {
                while (rowIdx > rowStart) { // move up
                    output.add(array[rowIdx][colIdx]);
                    rowIdx--;
                }
            }
            //decrementing the perimeter of array
            colStart++;
            colEnd--;
            rowStart++;
            rowEnd--;
        }
        return output;
    }

    // Iterative implementation
    public static List<Integer> spiralTraverseIterative(int[][] array) {
        List<Integer> result = new ArrayList<>();
        int startRow = 0, endRow = array.length - 1;
        int startCol = 0, endCol = array[0].length - 1;
        while (startRow <= endRow && startCol <= endCol) {
            for (int col = startCol; col <= endCol; col++)
                result.add(array[startRow][col]);
            for (int row = startRow + 1; row <= endRow; row++)
                result.add(array[row][endCol]);
            for (int col = endCol - 1; col >= startCol; col--) {
                if (startRow == endRow)// inner array has single row, to avoid reiterating which is already done in first loop
                    break;
                result.add(array[endRow][col]);
            }
            for (int row = endRow - 1; row > startRow; row--) {
                if (startCol == endCol) // inner array has single column, to avoid reiterating which is already done in second loop
                    break;
                result.add(array[row][startCol]);
            }
            startRow++;
            startCol++;
            endRow--;
            endCol--;
        }
        return result;
    }

    ////// Recursive Implementation
    public static List<Integer> spiralTraverseRecursive(int[][] array) {
        List<Integer> result = new ArrayList<>();
        int startRow = 0, endRow = array.length - 1;
        int startCol = 0, endCol = array[0].length - 1;
        spiralFill(array, startRow, endRow, startCol, endCol, result);
        return result;
    }
    public static void spiralFill(int[][] array, int startRow, int endRow, int startCol, int endCol, List<Integer> result) {
        if (startRow > endRow || startCol > endCol)
            return;
        for (int col = startCol; col <= endCol; col++)
            result.add(array[startRow][col]);
        for (int row = startRow + 1; row <= endRow; row++)
            result.add(array[row][endCol]);
        for (int col = endCol - 1; col >= startCol; col--) {
            if (startRow == endRow)// inner array has single row, to avoid reiterating which is already done in first loop
                break;
            result.add(array[endRow][col]);
        }
        for (int row = endRow - 1; row > startRow; row--) {
            if (startCol == endCol) // inner array has single column, to avoid reiterating which is already done in second loop
                break;
            result.add(array[row][startCol]);
        }
        spiralFill(array, startRow+1, endRow-1, startCol+1, endCol-1, result);
    }
}
