package medium;

import java.util.HashSet;
import java.util.Set;

/*
* given an array of positive integers representing coin
* denominations and a single non-negative integer representing
* a target amount of money, implement a function that returns
* the number of ways to make change for that target amount
* using the given coin denominations . note that an unlimited
* amount of coins is at your disposal.
*
* Ip : 6, [1, 5]
* op : 2 (1 * 1 + 1 * 5 and 6 * 1)
* */
public class NumberOfWayToMakeChange {
    public static void main(String[] args) {
        int amount = 25;
        int[] denominations = new int[] {1, 5, 10, 25};
        int noOfWays = numberOfWaysToMakeChangeExpert(amount, denominations);
        System.out.println(noOfWays);
    }

    public static int numberOfWaysToMakeChange(int n, int[] denoms) {
        if (n == 0 ||
                denoms == null ||
                denoms.length == 0)
            return 1;

        int[] ways = new int[n + 1];
        Set<Integer> denom = new HashSet<>();
        for (int coin : denoms)
            denom.add(coin);

        ways[0] = 0;
        ways[1] = denom.contains(1) ? 1 : 0;

        for (int i = 2; i < ways.length; i++) {
            if (denom.contains(i)) {
                int balance = n - i;
                if (i < n && n % i == 0)
                    ways[i] = 1 + 1 + ways[i - 1];
                else if (i <= n && (denom.contains(balance) || balance == 0))
                    ways[i] = 1 + ways[i - 1];
            }
            else
                ways[i] = ways[i - 1];
        }
        return ways[n];
    }
    /*
        each location in ways array is respective amount, eg : location 5 is 5Rs, location 8 is 8Rs
    * for each denomination we iterate the ways array and make change only for
    * locations greater than equal to denomination.

    for each denomination the respective possibilities are added on top of the
    previously calculated values

    if denominations are [1, 5, 10], target : 10
    for second denomination eg : 5, we calculate on top of the ways array containing
    results of ways of denomination 1, we addon the possibilities of 5 with possiblities
    of 1.
     Eg:
           when denomination is 1
           ways[] = {1, 1, 1, 1, 1, 1, 1, 1, 1, 1}
           when denomination is 5, idx = 5 (amount),
           ways[5] = ways[5] + ways[5 - denomination] (ways[0]) => 2
           when denomination is 5, idx = 10 (amount)
           ways[10] = ways[10](i.e: 1) + ways[10 - denomination] (ways[5] i.e: 2) => 3

    * */
    // O(N) space complexity | O(Nd) Time complexity
    public static int numberOfWaysToMakeChangeExpert(int n, int[] denoms) {
        if (n == 0 ||
                denoms == null ||
                denoms.length == 0)
            return 1;

        int[] ways = new int[n + 1];
        ways[0] = 1;

        for (int denom : denoms) {
                for (int amount = 1; amount < ways.length; amount++) {
                    if (denom <= amount) {
                        ways[amount] += ways[amount - denom];
                    }
                }
        }
        return ways[n];
    }
}
