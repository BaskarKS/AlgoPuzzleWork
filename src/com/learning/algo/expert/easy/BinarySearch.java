package easy;

public class BinarySearch {

    // -----------------------------BINARY SEARCH------------------------------------------- //
    // Iterative Solution => Time Complexity - O(log n) , Space Complexity - O(1)
    public static int binarySearch(int[] array, int target) {
        // Write your code here.
        int start = 0;
        int end = array.length - 1;
        int ret = -1;
        while (start <= end) {
            int mid = (start + end) / 2;
            if (array[mid] == target) {
                ret = mid;
                break;
            }
            if (array[mid] > target)
                end = mid - 1;
            else
                start = mid + 1;
        }
        return ret;
    }

}
