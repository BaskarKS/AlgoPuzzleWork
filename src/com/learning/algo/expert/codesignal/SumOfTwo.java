package codesignal;

import java.util.HashSet;
import java.util.Set;

public class SumOfTwo {
    public static boolean sumOfTwo(int[] first, int[] second, int target) {
        for (var i = 0; i < first.length; i++) {
            var neededValue = target - first[i];
            for (var j = 0; j < second.length; j++) {
                if (neededValue == second[j])
                    return true;
            }
        }
        return false;
    }

    public static boolean sumOfTwoSolTwo(int[] first, int[] second, int target) {
        Set<Integer> otherPair = new HashSet<>();
        for (var each : first)
            otherPair.add(each);
        for (var each : second) {
            var needValue = target - each;
            if (otherPair.contains(needValue))
                return true;
        }
        return false;
    }
}
