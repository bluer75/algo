package alg.dp;

import java.util.Arrays;

/**
 * Given an integer n, your task is to count how many strings of length n can be formed under the following rules:
 *
 * Each character is a lower case vowel ('a', 'e', 'i', 'o', 'u')
 * Each vowel 'a' may only be followed by an 'e'.
 * Each vowel 'e' may only be followed by an 'a' or an 'i'.
 * Each vowel 'i' may not be followed by another 'i'.
 * Each vowel 'o' may only be followed by an 'i' or a 'u'.
 * Each vowel 'u' may only be followed by an 'a'.
 *
 * Since the answer may be too large, return it modulo 10^9 + 7.
 *
 * Solution uses bottom-up dynamic programming.
 * State dp[k][i] means number of combinations of length k ending with letter i.
 * dp[k][i] can be calculated using rules above.
 *
 * Time complexity O(n) - inner loops take O(1). Space can by reduced to O(n).
 */
public class VowelsCombination {
    public int countVowelPermutation(int n) {
        long[][] dp = new long[n][5];
        Arrays.fill(dp[0], 1);
        // rules encoded as adjacency list/arrays
        // a -> 0, e -> 1, i -> 2, o -> 3, u -> 4
        int[][] edges = new int[][]{{1, 2, 4}, {0, 2}, {1, 3}, {2}, {2, 3}};
        for (int l = 1; l < n; l++) {
            for (int v = 0; v < 5; v++) {
                for (int p : edges[v]) {
                    dp[l][v] += dp[l - 1][p];
                }
                dp[l][v] %= 1_000_000_007;
            }
        }
        return (int) (Arrays.stream(dp[n - 1]).sum() % 1_000_000_007);
    }

    public static void main(String[] args) {
        System.out.println(new VowelsCombination().countVowelPermutation(10));
    }
}
