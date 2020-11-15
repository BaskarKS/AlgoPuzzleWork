package veryhard;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class SquareOfZeros {

 /*
 write a function that takes in a square shaped n * n two dimensional array of only 1's and 0's
 returns a boolean representing whether the input matrix contains a square whose borders are
 made up of only 0's.

 Note: 1 * 1 square doesn't count as a valid square for the purpose of this question.
 other-words, a single 0 in input matrix doesn't constitute a square whose borders are made
 up of only 0's, a square of zero's has to be at-least 2 * 2.

Eg: Ip :
             [[1, 0, 1, 0],
              [0, 0, 0, 1],
              [0, 1, 0, 1],
              [0, 0, 0, 0]]
       Op: True
             [[  ,   ,   ,   ],
              [0, 0, 0,   ],
              [0,   , 0,   ],
              [0, 0, 0,   ]]

Hints:
step#1
    a square is defined by its topmost and bottommost rows and by its leftmost and rightmost
    columns. given a pair of rows and a pair of columns that form a valid square. can easily determine
    if the relevant square is a square of zeroes with two for loops.

step#2
    can apply step#1 on every valid square in the input matrix to solve this problem.
    To find every valid square, can either traverse matrix iteratively with 3 nested loops or start at
    the outermost square and recursively go inwards in the matrix, checking the squares obtained by
    moving each corner of a square inwards . If we go with recursive approach need to use a cache to
    avoid doing many duplicate computations.

 step#3
    operation described in step#1 is computational expensive one O(n) to be repeated for every single
    square in the matrix. is its possible to precompute certain values to make the step#1 operation
    to constant O(1)

 step#4
    can precompute 2 values for every element in the matrix, 1. number of 0's to the right of each
    element including element itself. 2. number of 0's below each element including the element itself.
    can compute these 2 values starting from bottom right corner and moving way up by traversing
    each row from right to left, applying dynamic programming will allow to build up these values.
    once these precomputed values are computed, can perform operation described in hint#1 by
    looking at the number of 0's below any square's two top corners and the number of 0's to the right
    of the same square two left corners.

 Recursive Implementation Analysis:
 ---------------------------------------------------

 [[1, 0, 0],
 [0, 0, 0],
 [0, 1, 0]]

 [[r1c1, r1c2, r1c3],
  [r2c1, r2c2, r2c3],
  [r3c1, r3c2, r3c3],]

  Initially r1c1, r1c2, r1c2, r2c3, r3c3, r3c2, r3c1, r2c1 - outer border check for all zeros, doesnt form a valid square of zeros cause of r1c1(1), it will recursively check internally (5 steps)

  Step-1 : r1c1 is pushed diagonally inward one step(avoid r1-row and c1-col) r2c2, r2c3, r3c2, r3c3 checked for zeros // again checked recursively

  Step-2 : r1c3 is pushed diagonally inward one step(avoid r1-row and c3-col) r2c1, r2c2, r3c1, r3c2 checked for zeros // again checked recursively

  Step-3 : r3c1 is pushed diagonally inward one step(avoid r3-row and c1-col) r1c2, r1c3, r2c2, r2c3 checked for zeros // again checked recursively

  Step-4 : r3c3 is pushed diagonally inward one step(avoid r3-row and c3-col) r1c1, r1c2, r2c1, r2c2 checked for zeros // again checked recursively

  Step-5 : r1c1, r1c3, r3c1, r3c3 all four corners are pushed diagonally inward one step, only r2c2 which is a single value (not a valid square) returns FALSE.

  Any of the internal submodules or subsquares form a valid square of zero then the outer square meant to have valid square of zeros internally.
  ----------------------------------------------------------
  Step-1 : r1c1 is pushed diagonally inward one step(avoid r1-row and c1-col) r2c2, r2c3, r3c2, r3c3 checked for zeros // again checked recursively
  ----------------------------------------------------------
  Checking Internal Square (bottom right is checked for zero square)
  r2c2(0), r2c3(0),
  r3c2(1), r3c3(0)

  This internal square(r2c2(0), r2c3(0), r3c2(1), r3c3(0)) doesnt form a valid square of zeros, it will recursively check internally (above 5 steps)

  r2c2 is pushed diagonally inward one step(skip r2 values, c2 values) only r3c3 remains (single value is not valid square of zeros) hence it "return False"
  r2c3 is pushed diagonally inward one step(skip r2 values, c3 values) only r3c2 remains (single value is not valid square of zeros) hence it "return False"
  r3c2 is pushed diagonally inward one step(skip r3 values, c2 values) only r2c3 remains (single value is not valid square of zeros) hence it "return False"
  r3c3 is pushed diagonally inward one step(skip r3 values, c3 values) only r2c2 remains (single value is not valid square of zeros) hence it "return False"
  r2c2, r2c3, r3c2, r3c3 is pushed diagonally inward one step(skip r2,r3,c2,c3 values) no value remains (not valid square of zeros) hence it "return False"

  result of Internal Square (bottom right is checked for zero square) will "return False" because of all its 5-step
  internal evaluation is FALSE
  r2c2(0), r2c3(0),
  r3c2(1), r3c3(0)
  ----------------------------------------------------------
  Step-2 : r1c3 is pushed diagonally inward one step(avoid r1-row and c3-col) r2c1, r2c2, r3c1, r3c2 checked for zeros // again checked recursively
  ----------------------------------------------------------

  Checking Internal Square (bottom left is checked for zero square)
  r2c1(0), r2c2(0),
  r3c1(0), r3c2(1)

  This internal square(r2c1(0), r2c2(0), r3c1(0), r3c2(1)) doesn't form a valid square of zeros cause of (r3c2(1)), it will recursively check internally (above 5 steps)

  r2c1 is pushed diagonally inward one step(skip r2 values, c1 values) only r3c2 remains (single value is not valid square of zeros) hence it "return False"
  r2c2 is pushed diagonally inward one step(skip r2 values, c2 values) only r3c1 remains (single value is not valid square of zeros) hence it "return False"
  r3c1 is pushed diagonally inward one step(skip r3 values, c1 values) only r2c2 remains (single value is not valid square of zeros) hence it "return False"
  r3c2 is pushed diagonally inward one step(skip r3 values, c2 values) only r2c1 remains (single value is not valid square of zeros) hence it "return False"
  r2c1, r2c2, r3c1, r3c2 is pushed diagonally inward one step(skip r2,r3,c1,c2 values) no value remains (not valid square of zeros) hence it "return False"

  result of Internal Square (bottom left is checked for zero square) will "return False" because of all its 5-step
  internal evaluation is FALSE
  r2c1(0), r2c2(0),
  r3c1(0), r3c2(1)
  ----------------------------------------------------------
  Step-4 : r3c3 is pushed diagonally inward one step(avoid r3-row and c3-col) r1c1, r1c2, r2c1, r2c2 checked for zeros // again checked recursively
  ----------------------------------------------------------

  Checking Internal Square (top LEFT is checked for zero square)
  r1c1(1), r1c2(0),
  r2c1(0), r2c2(0)

  This internal square(r1c1(1), r1c2(0), r2c1(0), r2c2(0)) doesn't form a valid square of zeros cause of (r1c1(1)), it will recursively check internally (above 5 steps)

  r1c1 is pushed diagonally inward one step(skip r1 values, c1 values) only r2c2 remains (single value is not valid square of zeros) hence it "return False"
  r1c2 is pushed diagonally inward one step(skip r1 values, c2 values) only r2c1 remains (single value is not valid square of zeros) hence it "return False"
  r2c1 is pushed diagonally inward one step(skip r2 values, c1 values) only r1c2 remains (single value is not valid square of zeros) hence it "return False"
  r2c2 is pushed diagonally inward one step(skip r2 values, c2 values) only r1c1 remains (single value is not valid square of zeros) hence it "return False"
  r1c1, r1c2, r2c1, r2c2 is pushed diagonally inward one step(skip r1,r2,c1,c2 values) no value remains (not valid square of zeros) hence it "return False"

  result of Internal Square (TOP LEFT is checked for zero square) will "return False" because of all its 5-step
  internal evaluation is FALSE
  r1c1(1), r1c2(0),
  r2c1(0), r2c2(0)

  ----------------------------------------------------------
  Step-3 : r3c1 is pushed diagonally inward one step(avoid r3-row and c1-col) r1c2, r1c3, r2c2, r2c3 checked for zeros // again checked recursively
  ----------------------------------------------------------

  Checking Internal Square (top RIGHT is checked for zero square)
  r1c2(0), r1c3(0),
  r2c2(0), r2c3(0)

  This internal square(r1c2(0), r1c3(0), r2c2(0), r2c3(0)) form a valid square of zeros cause all border values are zero, it will Not recursively check internally (above 5 steps) and returns TRUE immediately

  ----------------------------------------------------------
  After evaluating all internal square till depth one of the square forms a squareofzeros hence it return TRUE

 [[1, 0, 0],
 [0, 0, 0],
 [0, 1, 0]]

 [[r1c1, r1c2, r1c3],
  [r2c1, r2c2, r2c3],
  [r3c1, r3c2, r3c3],]

Since r1c2, r1c3, r2c2, r2c3 is evaluated and found that it form a squareofzeros it returns TRUE, the same is returned as a result(TRUE) for the above 3*3 matrix. r1c1 * r3c3 which is a 3 * 3 matrix has a valid squareofzeros
internally r1c2 * r2c3 (2 * 2) matrix, hence 3*3 matrix return TRUE because it contains a valid squareofzeros(2*2)
matrix.

Since for every square it will go till the depth to evaluate its result, doing this for every square will do a lot
of repetition work. Better to use a cache to avoid evaluation of repetition of internal square.
*/


    // Time Complexity : O(n^3 * n(containsSquareOfZeros) => n^4)
    //Space Complexity : O(n^3)
    // main method which fires the recursive method with a cache and four points of a square
    public static boolean squareOfZeroes(List<List<Integer>> matrix) {
        // Write your code here.
        Map<String, Boolean> cache = new HashMap<>();
        int len = matrix.size() - 1;
        return findSquareOfZeros(matrix, 0, 0, len, len, cache);
    }
    //Recursive method to find the square-of-zeros for a square of four corners provided
    public static boolean findSquareOfZeros(List<List<Integer>>matrix, int  topRow,
                                            int leftCol, int bottomRow, int rightCol, Map<String, Boolean> cache) {
        if (topRow >= bottomRow || leftCol >= rightCol)
            return false;
        String key = createKey(topRow, leftCol, bottomRow, rightCol);

        if(cache.containsKey(key))
            return cache.get(key);

        boolean resultOfSquare = containsSquareOfZeros(matrix, topRow, leftCol, bottomRow, rightCol) ||
                                                    findSquareOfZeros(matrix, topRow + 1, leftCol + 1, bottomRow - 1, rightCol - 1, cache) ||
            findSquareOfZeros(matrix, topRow + 1, leftCol + 1, bottomRow, rightCol, cache) ||
            findSquareOfZeros(matrix, topRow, leftCol + 1, bottomRow - 1, rightCol, cache) ||
            findSquareOfZeros(matrix, topRow + 1, leftCol, bottomRow, rightCol - 1, cache) ||
            findSquareOfZeros(matrix, topRow, leftCol, bottomRow - 1, rightCol - 1, cache);
        cache.put(key, resultOfSquare);

        return cache.get(key);
    }
    // creates a key from the four-corner values in String
    public static String createKey(int topRow, int leftCol, int bottomRow, int rightCol) {
        return topRow + "-" +
            leftCol + "-" +
            bottomRow + "-" +
            String.valueOf(rightCol);
    }
    // find the square contains all zeros for the provided all four corners
    //Time : O(n)
    public static boolean containsSquareOfZeros(List<List<Integer>> matrix, int topRow, int leftCol, int bottomRow, int rightCol) {
        for (int row = topRow; row <= bottomRow; row++) {
            if (matrix.get(row).get(leftCol) != 0 || matrix.get(row).get(rightCol) != 0)
                return false;
        }
        for (int col = leftCol; col <= rightCol; col++) {
            if (matrix.get(topRow).get(col) != 0 || matrix.get(bottomRow).get(col) != 0)
                return false;
        }
        return true;
    }


//Solution Two :
// Time Complexity :  O(n^3(squareOfZeroes) * n(isSquareOfZeroes) => n^4), Space : O(n^3)
public static boolean squareOfZeroesIterative(List<List<Integer>> matrix) {
    int length = matrix.size(); // eg : length == 4
    for (int topRow = 0; topRow < length; topRow++) { // topRow can go from 0, 1, 2, 3
        for (int leftCol = 0; leftCol < length; leftCol++) { // leftCol can go from 0, 1, 2, 3
            int squareSize = 2;
            while (squareSize <= length - leftCol && squareSize <= length - topRow) { // for every point of topRow and LeftCol, squareSize will grow from min size 2 to 3 to 4.. till the max index where it doesn't go out of bound of array-right and array-down
                // if topRow is 0 and leftCol is 0, squareSize can be 2 ,3, 4
                // if topRow is 0 and leftCol is 1, squareSize can be 2 ,3
                // if topRow is 0 and leftCol is 2, squareSize can be 2
                // if topRow is 1 and leftCol is 0, squareSize can be 2 ,3
                // if topRow is 2 and leftCol is 0, squareSize can be 2
                // if topRow is 3 and leftCol is 0, squareSize can be nothing // min square size 2 will go out of bound
                int bottomRow = (topRow + squareSize) - 1; // square bottom row is found here
                int rightCol = (leftCol + squareSize) - 1; // square right col is found here
                if (containsSquareOfZeros(matrix, topRow, leftCol, bottomRow, rightCol))
                    return true;
                squareSize++;
            }
        }
    }
    return false;
}

    //Solution Three :
    // Time Complexity :  O(n^3(squareOfZeroesRecursiveBetterTime) * 1(findSquareOfZerosUsingPreCompute) => n^3),
    // Space : O(n^3) // Because of around N^3 values occupy in Cache

    public static boolean squareOfZeroesRecursiveBetterTime(List<List<Integer>> matrix) {
        int lastIdx = matrix.size() - 1;
        Map<String, Boolean> cache = new HashMap<>();
        List<List<Map<String, Integer>>> preComputeMatrix = preComputeMatrixUsingDynamicProg(matrix);
        return findSquareOfZerosUsingPreCompute(preComputeMatrix, 0, 0, lastIdx, lastIdx, cache);
    }
    public static List<List<Map<String, Integer>>> preComputeMatrixUsingDynamicProg
                                                                                                        (List<List<Integer>>matrix) {
        List<List<Map<String, Integer>>> preComputeMatrix = new ArrayList<>();
        int size = matrix.size();
        final String keyRight = "numZeroesRight";
        final String keyBottom = "numZeroesBottom";
        for (int rowIdx = 0; rowIdx < size; rowIdx++) {
            preComputeMatrix.add(new ArrayList<>());
            for (int colIdx = 0; colIdx < size; colIdx++) {
                int value = matrix.get(rowIdx).get(colIdx);
                int toSet = (value == 0) ? 1 : 0;
                Map<String, Integer> eachMatrixLoc = new HashMap<>();
                eachMatrixLoc.put(keyBottom, toSet);
                eachMatrixLoc.put(keyRight, toSet);
                preComputeMatrix.get(rowIdx).add(eachMatrixLoc);
            }
        }
        int lastIdx = size - 1;
        for (int rowIdx = lastIdx; rowIdx >= 0; rowIdx--) {
            for (int colIdx = lastIdx; colIdx >= 0; colIdx--) {
                int value = matrix.get(rowIdx).get(colIdx);
                if (value == 1)
                    continue;
                if (rowIdx < lastIdx) {
                    int increaseBottomCount = preComputeMatrix.get(rowIdx).get(colIdx).get(keyBottom) +
                                                                  preComputeMatrix.get(rowIdx + 1).get(colIdx).get(keyBottom);
                    preComputeMatrix.get(rowIdx).get(colIdx).put(keyBottom, increaseBottomCount);
                }
                if (colIdx < lastIdx) {
                    int increaseRightCount = preComputeMatrix.get(rowIdx).get(colIdx).get(keyRight) +
                                                                preComputeMatrix.get(rowIdx).get(colIdx + 1).get(keyRight);
                    preComputeMatrix.get(rowIdx).get(colIdx).put(keyRight, increaseRightCount);
                }
            }
        }

        return preComputeMatrix;
    }

    public static boolean findSquareOfZerosUsingPreCompute(List<List<Map<String, Integer>>>preCompMatrix,
                                                           int topRow, int leftCol, int bottomRow, int rightCol, Map<String, Boolean> cache) {
        if (topRow >= bottomRow || leftCol >= rightCol)
            return false;

        String key = createKey(topRow, leftCol, bottomRow, rightCol);

        if (cache.containsKey(key))
            return cache.get(key);

        boolean containSquare = containsZeroesSquare(preCompMatrix, topRow, leftCol, bottomRow, rightCol) ||
            findSquareOfZerosUsingPreCompute(preCompMatrix, topRow + 1, leftCol + 1, bottomRow - 1, rightCol - 1, cache) ||
            findSquareOfZerosUsingPreCompute(preCompMatrix, topRow + 1, leftCol + 1, bottomRow, rightCol, cache) ||
            findSquareOfZerosUsingPreCompute(preCompMatrix, topRow, leftCol + 1, bottomRow - 1, rightCol, cache) ||
            findSquareOfZerosUsingPreCompute(preCompMatrix, topRow + 1, leftCol, bottomRow, rightCol - 1, cache) ||
            findSquareOfZerosUsingPreCompute(preCompMatrix, topRow, leftCol, bottomRow - 1, rightCol - 1, cache);

        cache.put(key, containSquare);

        return cache.get(key);
    }

    // Time : O(1) because of precomputed list
    public static boolean containsZeroesSquare(List<List<Map<String, Integer>>> preCompMatrix, int topRow,
                                                                            int leftCol, int bottomRow, int rightCol) {
        final String keyRight = "numZeroesRight";
        final String keyBottom = "numZeroesBottom";
        int squareSize = (rightCol - leftCol) + 1;
        boolean hasTopBorderOfZeroes = preCompMatrix.get(topRow).get(leftCol).get(keyRight) >= squareSize;
        boolean hasRightBorderOfZeroes = preCompMatrix.get(topRow).get(rightCol).get(keyBottom) >= squareSize;
        boolean hasBottomBorderOfZeroes = preCompMatrix.get(bottomRow).get(leftCol).get(keyRight) >= squareSize;
        boolean hasLeftBorderOfZeroes = preCompMatrix.get(topRow).get(leftCol).get(keyBottom) >= squareSize;
        return hasTopBorderOfZeroes && hasRightBorderOfZeroes && hasBottomBorderOfZeroes && hasLeftBorderOfZeroes;

    }


    //Solution Four :
    // Time Complexity :  O(n^3(squareOfZeroesRecursiveBetterTime) * 1(containsZeroesSquare) => n^3),
    // Space : O(n^2) // extra space is for pre-computed-matrix
    public static boolean squareOfZeroesIterativeBetterTime(List<List<Integer>> matrix) {
        List<List<Map<String, Integer>>> preComputeMatrix = preComputeMatrixUsingDynamicProg(matrix);
        int length = matrix.size(); // eg : length == 4
        for (int topRow = 0; topRow < length; topRow++) { // topRow can go from 0, 1, 2, 3
            for (int leftCol = 0; leftCol < length; leftCol++) { // leftCol can go from 0, 1, 2, 3
                int squareSize = 2;
                while (squareSize <= length - leftCol && squareSize <= length - topRow) { // for every point of topRow and LeftCol, squareSize will grow from min size 2 to 3 to 4.. till the max index where it doesn't go out of bound of array-right and array-down
                    int bottomRow = (topRow + squareSize) - 1; // square bottom row is found here
                    int rightCol = (leftCol + squareSize) - 1; // square right col is found here
                    if (containsZeroesSquare(preComputeMatrix, topRow, leftCol, bottomRow, rightCol))
                        return true;
                    squareSize++;
                }
            }
        }  
        return false;
    }


    public static void main(String[] args) {
        List<List<Integer>> matrix = List.of(List.of(1, 0, 1, 0),
                                                                        List.of(0, 0, 0, 1),
                                                                        List.of(0, 1, 0, 1),
                                                                        List.of(0, 0, 0, 0));
        boolean result = squareOfZeroesIterativeBetterTime(matrix);
        System.out.println(result);
    }
}