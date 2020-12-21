package alg.dp;

/**
 * Count the number of ways one can make change for an amount N from an infinite supply of coins of given values.
 * Example: 
 * coins = {1, 2, 3}
 * amount N = 5
 * The result is 5 since N can be obtained from the following set of solutions
 * 
 * 1 + 1 + 1 + 1 + 1
 * 1 + 1 + 1 + 2
 * 1 + 1 + 3
 * 1 + 2 + 2
 * 3 + 2
 * 
 * Time complexity is O(n * m).
 */
public class NumChangesForAmount {
    public int count(int[] coins, int amount) {
        int[] res = new int[amount + 1];
        res[0] = 1;
        for (int c : coins) { // use each coin
            for (int i = c; i <= amount; i++) { // use it for each sum to amount
                res[i] += res[i - c];
            }
        }
        return res[amount];
    }

    public static void main(String... args) {
        System.out.println(new NumChangesForAmount().count(new int[] { 1, 2, 3 }, 6));
    }
}
