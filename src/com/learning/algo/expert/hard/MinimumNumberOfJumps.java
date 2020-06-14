package hard;
/*
Description : non-empty array of positive integers , each integer represents the maximum
number of steps can take forward in array.
Eg :   element at index 1 is 3, you can go from index 1 to index 2, 3, 4.
write a function returns minimum number of jumps needed to reach final index
Jumping from index i to index i + x constitute a one jump, no matter how large x is
Eg: Ip : array = [3, 4, 2, 1, 2, 3, 7, 1, 1, 1, 3]
Op: 4 // 3 --> (4 or 2) --> (2 or 3) --> 7 --> 3
 */
public class MinimumNumberOfJumps {
    public static void main(String[] args) {
        int[] input = new int[] {3, 4, 2, 1, 2, 3, 7, 1, 1, 1, 3};

        int jumps = minNumberOfJumpsBetterTime(input);
        System.out.println(jumps);
    }
    public static int minNumberOfJumps(int[] array) {
        if (array == null || array.length == 0)
            return -1;
        if (array.length == 1)
            return 0;
        int max = array.length;
        int jumps = 0;
        int outerIdx = 0;
        while (outerIdx < max) {
            int canMakeJump = array[outerIdx];
            if (outerIdx + canMakeJump >= max - 1) {
                jumps += 1;
                break;
            }

            int maxIndex = outerIdx + 1;
            for (int innerIdx = outerIdx + 2;
                 innerIdx <= outerIdx + canMakeJump; innerIdx++) {
                if (array[innerIdx] + innerIdx >= array[maxIndex] + maxIndex)
                    maxIndex = innerIdx;
            }
            outerIdx = maxIndex;
            jumps += 1;
        }
        return jumps;
    }
    // Time Complexity : O(N^2), Space Complexity : O(N)
    public static int minNumberOfJumpsDynamic(int[] array) {
        int[] jumps = new int[array.length];
        jumps[0] = 0;
        for (int idx = 1; idx < array.length; idx++)
            jumps[idx] = Integer.MAX_VALUE;
        for (int outer = 1; outer < array.length; outer++) {
            for (int inner = 0; inner < outer; inner++) {
                if (array[inner] >= (outer - inner))
                    jumps[outer] = Math.min(jumps[inner] + 1, jumps[outer]);
            }
        }
        return jumps[jumps.length - 1];
    }
    // Time Complexity : O(N), Space Complexity : O(1)
    public static int minNumberOfJumpsBetterTime(int[] array) {
        if (array.length == 1)
            return 0;
        int steps = array[0];
        int maxReach = array[0];
        int jumps = 0;
        for (int idx = 1; idx < array.length - 1; idx++) {
            maxReach = Math.max(maxReach, array[idx] + idx);
            steps -= 1;
            if (steps == 0) {
                steps = maxReach - idx;
                jumps += 1;
            }
        }
        return jumps + 1;
    }
}
