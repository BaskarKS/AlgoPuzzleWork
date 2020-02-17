package codesignal;

import javax.swing.plaf.basic.BasicBorders;
import java.util.HashSet;
import java.util.Set;

/*
* values inside array should be > 1 and less than array length
* eg: if value is 0 its invalid, if length of array is 5, contained
* value is 6 then its invalid
*
* find the first re-occurrence of a value
* eg : 1, 2, 3, 4, 4, 2, 3
* first re-occurrence happens at index 4 which is 4
* */
public class FirstDuplicateInteger {
    //O(n^2)
    public static int firstDuplicate(int[] array) {
        int minIndex = array.length;
        for (var i = 0; i < array.length; i++) {
            for (var j = i + 1; j < array.length; j++) {
                if (array[i] == array[j])
                    minIndex = Math.min(minIndex, j);
            }
        }

        if (minIndex == array.length)
            return -1;
        return array[minIndex];
    }
    // O(n) - Space O(n)
    public static int firstDuplicateSolOne(int[] array) {
        Set<Integer> seen = new HashSet<>();
        for (var each : array) {
            if (!seen.contains(each)) {
                seen.add(each);
                continue;
            }
            return each;
        }
        return -1;
    }
    public static int firstDuplicateSolTwo(int[] array) {
        for (int idx = 0; idx < array.length; idx++) {
            int toLookUp = Math.abs(array[idx]);
            if (array[toLookUp - 1] < 0)
                return toLookUp;
            array[toLookUp - 1] = -array[toLookUp - 1];
        }
        return -1;
    }
}
