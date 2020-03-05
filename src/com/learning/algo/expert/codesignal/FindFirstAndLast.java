package codesignal;

import java.util.Arrays;

public class FindFirstAndLast {
    public static void main(String[] args) {
        int[] array = {5, 7, 7, 8, 8, 8, 8, 8, 10, 12};
        int target = 12;
        int[] positions = findFirstAndLastPositions(array, target);
        System.out.println(Arrays.toString(positions));
    }
    public static int[] findFirstAndLastPositions(int[] array, int target) {
        int[] positions = new int[2];
        positions[0] = findStartingPosition(array, target);
        positions[1] = findEndingPosition(array, target);
        return positions;
    }
    public static int findStartingPosition(int[] array, int target) {
        int index = -1;
        int left = 0, right = array.length - 1;
        while (left <= right) {
            int mid = left + (right - left) / 2;
            if (array[mid] == target) {
                index = mid;
                right = mid - 1;
            } else if (target < array[mid])
                right = mid - 1;
            else
                left = mid + 1;
        }
        return index;
    }
    public static int findEndingPosition(int[] array, int target) {
        int index = -1;
        int left = 0, right = array.length - 1;
        while (left <= right) {
            int mid = (left + right) / 2;
            if (array[mid] == target) {
                index = mid;
            }
            if (target < array[mid])
                right = mid - 1;
            else if (target >= array[mid])
                left = mid + 1;
        }
        return index;
    }
  /*  public static int parentPosition(int position) {
        return (position - 1) / 2;
    }
    public static int leftChildPosition(int position) {
        return (position * 2) + 1;
    }
    public static int rightChildPosition(int position) {
        return (position * 2) + 2;
    }
    private static boolean hasLeftChild(int[] array, int pos) {
        return leftChildPosition(pos) < array.length;
    }
    private static boolean hasRightChild(int[] array, int pos) {
        return rightChildPosition(pos) < array.length;
    }*/
}
