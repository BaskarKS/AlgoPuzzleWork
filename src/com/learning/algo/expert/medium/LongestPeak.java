package medium;

import com.sun.nio.sctp.PeerAddressChangeNotification;

public class LongestPeak {
    public static void main(String[] args) {
        //int[] array = {1, 2, 3, 3, 4, 0, 10, 6, 5, -1, -3, 2, 3};
        //int[] array = {1,2,3,4,5,1};
        int[] array = {5, 4, 3, 2, 1, 2, 1};
        System.out.println(longestPeak(array));
    }
    public static int longestPeak(int[] array) {
        // Write your code here.
        if (array == null || array.length < 3)
            return 0;
        int max = Integer.MIN_VALUE;
        for (int idx = 1; idx < array.length - 1; idx++) {
            int maxPeak = getMaxPeak(array, idx);
            if (maxPeak > max)
                max = maxPeak;
        }
        return max;
    }
    public static int getMaxPeak(int[] array, int idx) {
        int peakCount = 1;
        int left = idx - 1;
        int right = idx + 1;
        boolean hasLeftSlope = false;
        boolean hasRightSlope = false;
        while (left >= 0 || right < array.length) {
            if ((left < 0 || array[left] >= array[left + 1]) &&
                (right >= array.length || array[right] >= array[right - 1]))
                break;
            if ((left >= 0) && array[left] < array[left + 1]) {
                peakCount += 1;
                left -= 1;
                hasLeftSlope = true;
            }
            if ((right < array.length) && array[right] < array[right - 1]) {
                peakCount += 1;
                right += 1;
                hasRightSlope = true;
            }
            if (!(hasLeftSlope && hasRightSlope))
                break;
        }
        return (peakCount < 3) ? 0 : peakCount;
    }
}
