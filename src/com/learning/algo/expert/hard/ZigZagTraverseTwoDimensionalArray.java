package hard;

import java.awt.print.PrinterGraphics;
import java.util.ArrayList;
import java.util.List;

/*
function takes in a m * n two dimensional array (can be square shaped, n == m)
returns a one dimensional array of all elements in zig-zag order

zigzag starts at top left corner of the two dimensional array goes down by one
element and proceeds in zigzag pattern all the way to bottom right corner

sample ip :
array =    {{1, 3, 4, 10},
                   {2, 5, 9, 11},
                   {6, 8, 12, 15},
                   {7, 13, 14, 16}}
op :
       {1, 2, 3, 4 ,5 , 6, 7, 8, 9, 10, 11, 12, 13, 14, 15, 16}
 */
public class ZigZagTraverseTwoDimensionalArray {
    public static void main(String[] args) {
        List<List<Integer>> array = new ArrayList<>();
        array.add(List.of(1, 3, 4, 10));
        array.add(List.of(2, 5, 9, 11));
        array.add(List.of(6, 8, 12, 15));
        array.add(List.of(7, 13, 14, 16));
//        array.add(List.of(1, 3, 4, 9));
//        array.add(List.of(2, 5, 8, 10));
//        array.add(List.of(6, 7, 11, 12));


        List<Integer> result = zigzagTraverseBetter(array);
        System.out.println(result);
    }
    public static List<Integer> zigzagTraverse(List<List<Integer>> array) {
        // Write your code here.
        List<Integer> result = new ArrayList<>();
        if (array == null || array.size() == 0)
            return result;
        int totalLength = 0;
        for (List<Integer> rowItem : array)
            totalLength += rowItem.size();
        int rowIdx = 0, colIdx = 0;
        int rowMax = array.size(), colMax = array.get(0).size();
        while(result.size() < totalLength) {
            // process node go diagonal down
            while (colIdx >= 0 && rowIdx < rowMax) {
                result.add(array.get(rowIdx).get(colIdx));
                rowIdx += 1;
                colIdx -= 1;
                if (rowIdx > 0 && rowIdx < rowMax)
                    colMax = array.get(rowIdx).size();
            }
            rowIdx -= 1;
            colIdx += 1;
            // process node go down, cant go down go right
            if (canTraverseDown(rowIdx, rowMax) || canTraverseRight(colIdx, colMax)) {
                if (canTraverseDown(rowIdx, rowMax)) {
                    rowIdx += 1;
                    if (rowIdx >= 0 && rowIdx < rowMax)
                        colMax = array.get(rowIdx).size();
                }
                else {
                    if (canTraverseRight(colIdx, colMax))
                        colIdx += 1;
                }
            } else {
                break;
            }
            // process node go diagonal up
            while (rowIdx >= 0 && colIdx < colMax) {
                result.add(array.get(rowIdx).get(colIdx));
                rowIdx -= 1;
                colIdx += 1;
                if (rowIdx > 0 && rowIdx < rowMax)
                    colMax = array.get(rowIdx).size();
            }
            rowIdx += 1;
            colIdx -= 1;
            // process node go right, cant go right, go down
            if (canTraverseRight(colIdx, colMax) || canTraverseDown(rowIdx, rowMax)) {
                if (canTraverseRight(colIdx, colMax))
                    colIdx += 1;
                else {
                    if (canTraverseDown(rowIdx, rowMax)) {
                        rowIdx += 1;
                        if (rowIdx > 0 && rowIdx < rowMax)
                            colMax = array.get(rowIdx).size();
                    }
                }
            } else {
                break;
            }
        }
        return result;
    }
    public static boolean canTraverseDown(int rowIdx, int rowMax) {
        return rowIdx + 1< rowMax;
    }
    public static boolean canTraverseRight(int colIdx, int colMax) {
        return colIdx  + 1 < colMax;
    }

    // -------------------------------- Better Code ----------------------------------------------------
    private static boolean isOutOfBounds(int row, int col, int height, int width) {
        return row < 0 || row > height || col < 0 || col > width;
    }
    public static List<Integer> zigzagTraverseBetter(List<List<Integer>> array) {
        List<Integer> result = new ArrayList<>();
        int height = array.size() - 1;
        int width = array.get(0).size() - 1;
        int row = 0, col = 0;
        boolean goingDown = true;
        while (!isOutOfBounds(row, col, height, width)) {
            result.add(array.get(row).get(col));
            if (goingDown) {
                if (col == 0 || row == height) {
                    goingDown = false;
                    if (row == height)
                        col += 1;
                    else
                        row += 1;
                } else {
                    row += 1;
                    col -= 1;
                }
            } else {
                if (row == 0 || col == width) {
                    goingDown = true;
                    if (col == width)
                        row += 1;
                    else
                        col += 1;
                } else {
                    row -= 1;
                    col += 1;
                }
            }
        }
        return result;
    }
}
