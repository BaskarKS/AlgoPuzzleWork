package medium;

import java.util.Arrays;

public class SearchInSortedMatrix {
    /*
    * given a 2 dimension matrix of distinct integers and a target number
    * each row in the matrix is sorted, and each column is also sorted, matrix
    * does'nt necessarily have same height and width
    *
    * write a function to return the indices array contains of row and column
    * of target number if present otherwise [-1,-1]
    * */
    public static int[] searchInSortedMatrix(int[][] matrix, int target) {
        int[] indices = new int[]{-1, -1};
        int row = 0;
        int col = matrix[row].length - 1;
        while (row < matrix.length && col >= 0) {
            if (matrix[row][col] == target) {
                indices[0] = row;
                indices[1] = col;
                return indices;
            }
            if (matrix[row][col] > target) {
                col--;
                continue;
            }
            if (matrix[row][col] < target) {
                row++;
                if (row >= matrix.length)
                    break;
                col = matrix[row].length - 1;
            }
        }
/*
        for (int row = 0; row < matrix.length; row++) {
            for (int col = 0; col < matrix[row].length; col++) {
                if (matrix[row][col] == target) {
                    indices[0] = row;
                    indices[1] = col;
                    return indices;
                }
            }
        }
*/
        return indices;
    }

    public static void main(String[] args) {
        int[][] matrix = new int[][] {{1, 4, 7, 12, 15, 1000},
                                                        {2, 5, 19, 31, 32, 1001},
                                                        {3, 8, 24, 33, 35, 1002},
                                                        {40, 41, 42, 44, 45, 1003},
                                                        {99, 100, 103, 106, 128, 1004}};
        int target = 44;
        int[] indices = searchInSortedMatrix(matrix, target);
        System.out.println(Arrays.toString(indices));
    }
}
