package hard;

public class MinimumRewards {
    // -----------------------------MINIMUM REWARDS------------------------------------------- //

    //Time Complexity : O(n) , Space Complexity : O(n)
    public static int minRewards(int[] scores) {
        // Write your code here.
        int[] rewards = new int[scores.length];
        for (int index = 0; index < rewards.length; index++)
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

}
