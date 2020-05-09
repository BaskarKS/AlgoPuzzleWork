package veryhard;

public class MaximumProfitWithKTransactions {
    /*
    * given an array of positive integers representing the prices of a single stock on various
    * days (each index in the array represent a different day), you were also given an
    * integer 'k' which represent the number of transactions you are allowed to make.
    * one transaction consist of buying a stock on a given day and selling it on another / later
    * day.
    *
    * write a function that returns the maximum profit that you can make by buying and
    * selling the stock, given 'k' transactions
    *
    * note that you can only hold one share of the stock at a time. in other words you cant
    * buy more than one share of the stock  on any given day , and you cant buy a share
    * of the stock if you are still holding another share . Also, you don't need to use all 'k'
    * transactions that you are allowed.
    *
    * ip: prices = [5, 11, 3, 50, 60, 90]
    * k = 2
    * op : 93 // buy : 5, sell : 11, buy: 3, sell : 90
    * */


    // Time Complexity : O(nk) , SpaceComplexity : O(nk)
    public static int maxProfitWithKTransactions(int[] prices, int k) {
        // Write your code here.
        if (prices == null || prices.length == 0)
            return -1;
        int[][] profits = new int[k + 1][prices.length];
        for (int row = 1; row <= k; row++) {
            int maxUntil = Integer.MIN_VALUE;
            for (int col = 1; col < profits[0].length; col++) {
                maxUntil = Math.max(maxUntil, (-prices[col - 1] + profits[row - 1][col - 1]));
                profits[row][col] = Math.max(profits[row][col - 1], prices[col] + maxUntil);
            }
        }
        return profits[profits. length - 1][profits[0].length - 1];
    }

    public static void main(String[] args) {
        int[] prices = new int[] {5, 11, 3, 50, 60, 90};
        int kthTransaction = 2;
        int maxProfit = maxProfitWithKTransactionsLessSpace(prices, kthTransaction);
        System.out.println(maxProfit);
    }
    //Time Complexity : O(nk) , SpaceComplexity : O(n)
    public static int maxProfitWithKTransactionsLessSpace(int[] prices, int k) {
        // Write your code here.
        if (prices == null || prices.length == 0)
            return -1;
        int[] evenProfits = new int[prices.length];
        int[] oddProfits = new int[prices.length];
        int[] currentProfits = null;
        int[] previousProfits = null;
        for (int row = 1; row <= k; row++) {
            if (row % 2 == 1) {
                currentProfits = oddProfits;
                previousProfits = evenProfits;
            } else {
                currentProfits = evenProfits;
                previousProfits = oddProfits;
            }
            int maxUntil = Integer.MIN_VALUE;
            for (int col = 1; col < prices.length; col++) {
                maxUntil = Math.max(maxUntil, (-prices[col - 1] + previousProfits[col - 1]));
                currentProfits[col] = Math.max(currentProfits[col - 1], prices[col] + maxUntil);
            }
        }
        return (k % 2 == 1) ? oddProfits[oddProfits.length - 1] : evenProfits[evenProfits.length - 1];
    }
}
