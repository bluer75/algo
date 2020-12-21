package alg.dp;

/**
 * Given a amount A and a set of c distinct coins, 
 * find minimum numbers of coins required to make the change for the amount A.
 * 
 * This is known as "unbounded knapsack problem" — a classic dynamic programming problem.
 * Keep in mind: in some cases, it might not be worth using our optimal dynamic programming solution. 
 * It's a pretty slow algorithm—without any context (not knowing how many coins [c] we have, what our amount [n] is,
 * or just how they compare) it's easy to see O(n*c) growing out of control quickly if n or c is large.
 */
public class MinimumCoins {

    /**
     * Recursive solution tries all coins and finds minimum number.
     * This is exponential complexity - O(c^n)
     */
    static int find(int amount, int[] coins) {
        return find(amount, coins, 0);
    }

    private static int find(int amount, int[] coins, int numOfCoins) {
        if (amount == 0) {
            // base case - we found correct combination of coins to make a change
            return numOfCoins;
        }
        int min = Integer.MAX_VALUE;
        for (int i = 0; i < coins.length; i++) {
            // check if we can use that coin
            if (amount - coins[i] >= 0) {
                // decrease amount and find minimum
                min = Math.min(min, find(amount - coins[i], coins, numOfCoins + 1));
            }
        }
        return min;
    }

    /**
     * DP bottom-up approach.
     * Calculate minimum number of coins for each amount starting form 0.
     * Time complexity is reduced to O(n * c)
     */
    static int findDP(int amount, int[] coins) {
        int[] minCoins = new int[amount + 1]; // minCoins[i] represents minimum number of coins for amount i
        minCoins[0] = 0;
        // calculate number of coins for each value a
        for (int a = 1; a <= amount; a++) {
            int min = Integer.MAX_VALUE;
            // try all coins
            for (int i = 0; i < coins.length; i++) {
                // check if we can use that coin
                if (coins[i] <= a) {
                    // check if using that coin we get smaller number for amount a
                    min = Math.min(min, minCoins[a - coins[i]] + 1);
                }
            }
            minCoins[a] = min;

        }
        return minCoins[amount];
    }

    public static void main(String... args) {
        int amount = 10;
        int[] coins = new int[] { 1, 2, 3 };
        System.out.println(find(amount, coins));
        System.out.println(findDP(amount, coins));
    }
}
