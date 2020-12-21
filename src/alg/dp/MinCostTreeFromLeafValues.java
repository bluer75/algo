package alg.dp;

import java.util.Arrays;

/**
 * Given an array of positive integers, consider all binary trees such that:
 * Each node has either 0 or 2 children.
 * The values of array correspond to the values of each leaf in an in-order traversal of the tree.  
 * Recall that a node is a leaf if and only if it has 0 children.
 * The value of each non-leaf node is equal to the product of the largest leaf value in its 
 * left and right subtree respectively.
 * Among all possible binary trees considered, return the smallest possible sum of the values of 
 * each non-leaf node.
 * 
 * Example:
 * Input: [6, 2, 4]
 * Output: 32
 * Explanation:
 * There are two possible trees. The first has non-leaf node sum 36, and the second has non-leaf node sum 32.
 * 
 *      24            24
 *     /  \          /  \
 *    12   4        6    8
 *   /  \               / \
 *  6    2             2   4
 *  
 * In DP bottom-up solution, consider all sub-arrays of input leaves.
 * Solution for any sub-array i..j is defined as:
 * dp[i][j] = min(dp[i][j], dp[i][k] + dp[k + 1, j] + max(leaves[i..k]) * max(leaves[k + 1..j])) 
 * 
 * This take O(n^2) time and O(n^2) space.
 * 
 * Optimal solution (non-DP)) uses stack and finds the solution in O(n) time/space.
 * It simplifies the problem to following one: given an array, choose two neighbors a and b.
 * We can remove the smaller one min(a, b) and the cost is a * b.
 * What is the minimum cost to remove the whole array until only one element left? 
 * 
 * Example: [6, 4, 2]
 * min(2, 4) -> remove 2, cost: 8
 * min(6, 4) -> remove 4, cost 24
 * total cost -> 32 
 */
public class MinCostTreeFromLeafValues {
    public int mctFromLeafValues(int[] leaves) {
        int n = leaves.length;
        int[][] dp = new int[n][n];
        int max[][] = new int[n][n];

        for (int i = 0; i < n; i++) {
            max[i][i] = leaves[i];
            for (int j = i + 1; j < n; j++) {
                max[i][j] = Math.max(max[i][j - 1], leaves[j]);
            }
        }

        // evaluate value for each segment - order is relevant - start with smaller segments
        for (int l = 1; l < n; l++) {
            for (int i = 0; i < n - l; i++) {
                int j = i + l;
                if (l == 1) {
                    dp[i][j] = leaves[i] * leaves[j];
                } else {
                    dp[i][j] = Integer.MAX_VALUE;
                    for (int k = i; k < j; k++) {
                        dp[i][j] = Math.min(dp[i][j], calculate(max, dp, i, j, k));
                    }
                }
            }
        }
        return dp[0][n - 1];
    }

    private int calculate(int[][] max, int[][] dp, int i, int j, int k) {
        return dp[i][k] + dp[k + 1][j] + max[i][k] * max[k + 1][j];
    }

    public static void main(String... args) {
        System.out.println(new MinCostTreeFromLeafValues().mctFromLeafValues(new int[] { 6, 2, 4 }));
    }
}
