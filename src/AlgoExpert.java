public class AlgoExpert {
// -----------------------------MINIMUM REWARDS------------------------------------------- //

    //Time Complexity : O(n) , Space Complexity : O(n)
    public static int minRewards(int[] scores) {
        // Write your code here.
        int[] rewards = new int[scores.length];
        for(int index = 0; index < rewards.length; index++)
            rewards[index] = 1;

        for (int i = 1; i < scores.length; i++)
            if (scores[i] > scores[i - 1])
                rewards[i] = rewards[i - 1] + 1;

        for (int j = scores.length - 2; j >= 0; j--)
            if (scores[j] > scores[j + 1])
                rewards[j] = Math.max(rewards[j], rewards[j + 1] + 1);
        return sum(rewards);
    }

    public static int sum(int[] array) {
        int sum = 0;
        for (int eachVal : array)
            sum += eachVal;
        return sum;
    }
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
    public static void main(String[] args) {
        int[] sortedArray = {0, 1, 21, 33, 45, 45, 61, 71, 72, 73};
        int itemToSearch = 33;
    }
}
