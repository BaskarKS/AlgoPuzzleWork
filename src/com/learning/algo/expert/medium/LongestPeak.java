package medium;

import com.sun.nio.sctp.PeerAddressChangeNotification;
/*
function takes in a array of integers and returns a longest peak
peak is defined as adjacent integers are strictly increasing until they
reach a tip(highest value in peak), at which point they become
strictly decreasing. Atleast 3 integers are required to form a peak
integers {1, 4, 10, 2} form a peak
integers {4, 0, 10} dont form
integers {1, 2, 2, 0} dont form
integers {1, 2, 3} dont form, not decreasing after 3

Ip : {1, 2, 3, 3, 4, 0, 10, 6, 5, -1, -3, 2, 3};
Op : 6 // {0, 10, 6, 5, -1, -3}
* */
public class LongestPeak {
    public static void main(String[] args) {
        //int[] array = {1, 2, 3, 3, 4, 0, 10, 6, 5, -1, -3, 2, 3};
        //int[] array = {1,2,3,4,5,1};
        int[] array = {1, 2, 3, 1};
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

    // Better Implementation Time O(n), Space O(1)
    public static int longestPeakBetter(int[] array) {
        int longestPeakLength = 0;
        int idx = 1;
        while (idx < array.length - 1) {
            boolean isPeak = array[idx - 1] < array[idx] &&
                                            array[idx + 1] < array[idx];
            if (!isPeak) {
                idx += 1;
                continue;
            }
            int leftIdx = idx - 2;
            while (leftIdx >= 0 && array[leftIdx] < array[leftIdx + 1])
                leftIdx -= 1;
            int rightIdx = idx + 2;
            while (rightIdx < array.length && array[rightIdx] < array[rightIdx - 1])
                rightIdx += 1;
            int peakLength = rightIdx - leftIdx - 1;
            if (peakLength > longestPeakLength)
                longestPeakLength = peakLength;
            idx = rightIdx;
        }
        return longestPeakLength;
    }
}
