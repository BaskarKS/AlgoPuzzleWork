package medium;

public class MinimumNoOfCoinsForChange {
    public static void main(String[] args) {
        System.out.println(minNumberOfCoinsForChange(7, new int[]{1, 2, 4}));
    }

    /*
    denoms denotes the coin denominations, n represents the single non negative
    target amount. function returns the smallest no of coins needed to make change
    for that target amount using given denominations.
    If its impossible to make change, return the result with -1
    given array is unlimited amount of coins can be used for each denominations

    Ip: {1, 5, 10}
    n = 7
    Op : 3 // 5 * 1, 1 * 2
    */
    public static int minNumberOfCoinsForChange(int n, int[] denoms) {
        // Write your code here.
        if (denoms.length == 0)
            return -1;
        int[] noOfCoins = new int[n + 1];
        for (int idx = 0; idx < noOfCoins.length; idx++)
            noOfCoins[idx] = Integer.MAX_VALUE;
        noOfCoins[0] = 0;
        for (int denom : denoms) {
            for (int amount = 0; amount < noOfCoins.length; amount++) {
                if (amount >= denom) {
                    int toCompare = Integer.MAX_VALUE;
                    if (toCompare != noOfCoins[amount - denom])
                        toCompare = 1 + noOfCoins[amount - denom];
                    noOfCoins[amount] = Math.min(noOfCoins[amount], toCompare);
                }
            }
        }
        return noOfCoins[n] != Integer.MAX_VALUE ? noOfCoins[n] : -1;
    }
}
