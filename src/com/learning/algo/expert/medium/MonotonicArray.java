package medium;
/*
takes an array and returns boolean representing is monotonic

array said to be monotonic if entire elements from left to right are non-increasing
or non-decreasing

ip: [-1, -5, -10, -1100, -1100, -1101, -1102, -9001]
op : true

* */
public class MonotonicArray {
    public static void main(String[] args) {
        int[] array = {1,1,1,2,3,4,4,5};
        System.out.println(isMonotonic(array));

    }
    // ----------------------- start solution 1 ----------------------------------------------
    public static boolean isMonotonicOne(int[] array) {
        if (array.length < 2)
            return true;
        int direction = array[1] - array[0];
        for (int idx = 2; idx < array.length; idx++) {
            if (direction == 0) {
                direction = array[idx] - array[idx - 1];
                continue;
            }
            if (breaksDirection(direction, array[idx - 1], array[idx]))
                return false;
        }
        return true;
    }
    public static boolean breaksDirection(int direction, int previousVal, int currentVal) {
        int difference = currentVal - previousVal;
        if (direction > 0)
            return difference < 0;
        return difference > 0;
    }
    // ----------------------- end solution 1 ----------------------------------------------
    // ----------------------- start solution 2 ----------------------------------------------
    public static boolean isMonotonic(int[] array) {
        // Write your code here.
        if (array.length == 1)
            return true;
        boolean isTrendingDown = true;
        boolean isTrendingUp = true;
        for (int idx = 1; idx < array.length; idx++) {
            if (!(array[idx] >= array[idx - 1]))
                isTrendingUp = false;
            if (!(array[idx] <= array[idx - 1]))
                isTrendingDown = false;
        }
        return isTrendingDown | isTrendingUp;
    }
    // ----------------------- end solution 2 ----------------------------------------------
}
