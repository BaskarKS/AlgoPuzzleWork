package medium;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class RiverSizes {
    public static void main(String[] args) {

        int[][] matrix =   {{1,0,0,},
            {1,0,1,0,0},
            {0,0,1,0},
            {1,0,1,0},
            {1,0,1,1,0}};
        //[1,2,2,2,5]
        System.out.println(Arrays.deepToString(matrix));
        List<Integer> riverSize = riverSizesBetter(matrix);
        System.out.println(riverSize);

    }
    // ----------------------------- River Sizes ------------------------------//
    /* two dimension array of unequal row and col sizes
     *  contains 1 or 0 => 1 is part of river and 0 is land
     *  a river contains any no of 1's that are either horizontally or vertically
     * adjacent but not diagonal - no of adjacent 1's forming a river determine
     * its size
     *
     * return all the sizes of river (no ordering is required)
     * */
    public static List<Integer> riverSizes(int[][] matrix) {
        // Write your code here.
        boolean[][] visited = new boolean[matrix.length][];
        int index = 0;
        while (index < matrix.length) {
            visited[index] = new boolean[matrix[index].length];
            index++;
        }
        List<Integer> returnRiverSize = new ArrayList<>();
        for (int row = 0; row < matrix.length; row++) {
            for( int col = 0; col < matrix[row].length; col++) {
                int val = matrix[row][col];
                if (val == 0 || visited[row][col])
                    continue;
                if (!visited[row][col]) {
                    int riverSize = 1;
                    visited[row][col] = true;
                    riverSize += traverse(row, col, matrix, visited);
                    returnRiverSize.add(riverSize);
                }
            }
        }
        return returnRiverSize;
    }
    public static boolean inBounds(int row, int col, int[][] matrix) {
        return (row >= 0 && row < matrix.length) && (col >= 0 && col < matrix[row].length);
    }
    public static int traverse(int row, int col, int[][] matrix, boolean[][] visited)
    {
        int riverSize = 0;
        //traverseUp
        int traverseRow = row, traverseCol = col;
        while (traverseRow > 0) {
            if (inBounds(traverseRow - 1, traverseCol, matrix) &&
                !visited[traverseRow - 1][traverseCol] &&
                matrix[traverseRow - 1][traverseCol] == 1) {
                riverSize++;
                visited[traverseRow - 1][traverseCol] = true;
                riverSize += traverse(traverseRow - 1, traverseCol, matrix, visited);
            } else
                break;
            traverseRow--;
        }
        //traverseDown
        traverseRow = row; traverseCol = col;
        while (traverseRow < matrix.length + 1) {
            if (inBounds(traverseRow + 1, traverseCol, matrix) &&
                !visited[traverseRow + 1][traverseCol] &&
                matrix[traverseRow + 1][traverseCol] == 1) {
                riverSize++;
                visited[traverseRow + 1][traverseCol] = true;
                riverSize += traverse(traverseRow + 1, traverseCol, matrix, visited);
            }
            else
                break;
            traverseRow++;
        }
        //TraverseLeft
        traverseRow = row; traverseCol = col;
        while (traverseCol > 0) {
            if (inBounds(traverseRow, traverseCol - 1, matrix) &&
                !visited[traverseRow][traverseCol - 1] &&
                matrix[traverseRow][traverseCol - 1] == 1) {
                riverSize++;
                visited[traverseRow][traverseCol - 1] = true;
                riverSize += traverse(traverseRow, traverseCol - 1, matrix, visited);
            }
            else
                break;
            traverseCol--;
        }
        //TraverseRight
        traverseRow = row; traverseCol = col;
        while (traverseCol < matrix[traverseRow].length - 1) {
            if (inBounds(traverseRow, traverseCol + 1, matrix) &&
                !visited[traverseRow][traverseCol + 1] &&
                matrix[traverseRow][traverseCol + 1] == 1) {
                riverSize++;
                visited[traverseRow][traverseCol + 1] = true;
                riverSize += traverse(traverseRow, traverseCol + 1, matrix, visited);
            }
            else
                break;
            traverseCol++;
        }
        return riverSize;
    }

    // Different Implementation
    //Time Complexity : O(wh) or O(n) //Space Complexity : O(wh)
    //h is matrix height, w is matrix width , n is the total items in matrix
    public static List<Integer> riverSizesBetter(int[][] matrix) {
        //Initialization
        List<Integer> riverSizes = new ArrayList<>();
        boolean[][] visited = new boolean[matrix.length][];
        int index = 0;
        while(index < matrix.length) {
            visited[index] = new boolean[matrix[index].length];
            index++;
        }
        //Visiting each element
        for (int row = 0; row < matrix.length; row++) {
            for (int col = 0; col < matrix[row].length; col++) {
                if (visited[row][col])
                    continue;
                int riverSize = traverseGetRiverSize(row, col, matrix, visited);
                if (riverSize > 0)
                    riverSizes.add(riverSize);
            }
        }
        return riverSizes;
    }
    public static int traverseGetRiverSize(int row,int col,int[][] matrix, boolean[][] visited) {
        int riverSize = 0;
        List<Integer[]> traverse = new ArrayList<>();

        //Visiting requested node and neighbouring nodes and find possibility of river
        traverse.add(new Integer[]{row, col});
        while(!traverse.isEmpty()) {
            Integer[] current = traverse.remove(traverse.size() - 1);
            int currentValue = matrix[current[0]][current[1]];
            //if already visited just skip it
            if (visited[current[0]][current[1]])
                continue;
            visited[current[0]][current[1]] = true;
            //if the node is not a river, just skip it
            if (currentValue == 0) {
                continue;
            }
            //possibility of river, add its neighbours to investigate the river size
            riverSize++;
            List<Integer[]> neighbours = new ArrayList<>();
            getUnvisitedNeighbours(current[0], current[1], matrix, visited, neighbours);
            for (Integer[] each : neighbours)
                traverse.add(each);
        }
        return riverSize;
    }

    public static List<Integer[]> getUnvisitedNeighbours(int row, int col,
                                                         int[][] matrix,
                                                         boolean[][] visited,
                                                         List<Integer[]> neighbours) {
        //get the unvisited neighbours
        //Top neighbour
        if (row > 0 && !visited[row - 1][col])
            neighbours.add(new Integer[]{row - 1, col});
        //bottom neighbour
        if (row < matrix.length - 1 && !visited[row + 1][col])
            neighbours.add(new Integer[]{row + 1, col});
        //left neighbour
        if (col > 0 && !visited[row][col - 1])
            neighbours.add(new Integer[]{row, col - 1});
        //right neighbour
        if (col < matrix[row].length - 1 && !visited[row][col + 1])
            neighbours.add(new Integer[]{row, col + 1});
        return neighbours;
    }
// ----------------------------- River Sizes ------------------------------//

}
