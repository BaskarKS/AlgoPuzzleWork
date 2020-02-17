package codesignal;

import java.util.Arrays;

public class Main {
    public static void main(String[] args) {
/*
        int[][] flipArray = {{1, 2, 3}, {4, 5, 6}, {6, 7, 8}};
        System.out.println(Arrays.deepToString(flipArray));
        RotateImage.rotateImage(flipArray);
        System.out.println(Arrays.deepToString(flipArray));
*/

        String firstNonRepeat = "abcbad";
        System.out.println(FirstNonRepeatingCharacter.
                firstNonRepeatingCharSolThree(firstNonRepeat));

        int[] array = {1, 2, 3, 4, 4, 6, 7, 8};
        int duplicate = FirstDuplicateInteger.firstDuplicateSolTwo(array);
        System.out.println(duplicate);
    }
}
