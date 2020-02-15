package codesignal;

/*   input         output (Rotate 90 Deg)
*   1, 2, 3       7, 4, 1
*   4, 5, 6       8, 5, 2
*   7, 8, 9       9, 6, 3
*
* Step 1: Transpose
* making row elements to column elements
* eg: 1st row elements become 1st column elements
* swap only the diagonal elements once,
* array[i][j] should be swapped with array[j][i]
* eg: array[1][2] swapped with array[2][1]
* again in loop, we should not make swap for array[2][1]
* only make swap for array[1][2]
*   1, 4, 7
*   2, 5, 8
*   3, 6, 9
*
* Step 2: Flip Horizontally
* for each row have 2 pointer , 1st pointing at beg
* 2nd pointing at the end of element of column
* 1st pointer move right, 2nd pointer move left
* swap until before both meets
*
*   7, 4, 1
*   8, 5, 2
*   9, 6, 3
*
* */
public class RotateImage {
    public static void rotateImage(int[][] array) {
        //checking row length should be equal to column length
        for (int[] each : array)
            if (each.length != array.length)
                return;

        int length = array.length;
        //Transpose Matrix
        for (int i = 0; i < length; i++) {
            for (int j = i; j < length; j++) {
                int temp = array[i][j];
                array[i][j] = array[j][i];
                array[j][i] = temp;
            }
        }
        //Flip Horizontally
        for (int i = 0; i < length; i++) {
            for (int j = 0; j < (length / 2); j++) {
                int temp = array[i][j];
                array[i][j] = array[i][length - 1 - j];
                array[i][length - 1 - j] = temp;
            }
        }
    }

}
