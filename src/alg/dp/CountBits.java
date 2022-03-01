package alg.dp;

import java.util.Arrays;

/**
 * Given an integer n, return an array of length n + 1 such that for each i (0 <= i <= n), ans[i] is the number
 * of 1's in the binary representation of i.
 *
 * Example:
 *
 * Input: n = 4
 * Output: [0,1,1,2,1]
 * 0 --> 0
 * 1 --> 1
 * 2 --> 1
 * 3 --> 2
 * 4 --> 1
 *
 * Naive solution counts bits for each number, and it takes O(n log n) time.
 *
 * DP based solution uses the fact that x & (x - 1) differ just by one bit - the least significant bit, and we can use
 * already calculated value: dp[x] = dp[x & (x - 1)] + 1
 * This takes O(n) time.
 */
public class CountBits {

    public int[] countBits(int n) {
        int[] counts = new int[n + 1];
        for (int v = 0, i = 0; i <= n; i++, v = i) {
            while (v > 0) {
                v = v & (v - 1);
                counts[i]++;
            }
        }
        return counts;
    }

    public int[] countBitsDP(int n) {
        int[] counts = new int[n + 1];
        for (int i = 1; i <= n; i++) {
            counts[i] = counts[i & (i - 1)] + 1;
        }
        return counts;
    }

    public static void main(String[] args) {
        System.out.println(Arrays.toString(new CountBits().countBits(4)));
        System.out.println(Arrays.toString(new CountBits().countBitsDP(4)));
    }
}
