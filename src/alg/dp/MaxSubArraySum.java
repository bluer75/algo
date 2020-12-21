package alg.dp;

/**
 * Find a contiguous subarray with the largest sum.
 * This is Kadane's algorithm with running time O(n).
 */
public class MaxSubArraySum {

    /**
     * This is really DP approach with optimized memoization.
     * maxSum[i] = max(a[i], maxSum[i - 1] + a[i]).
     */
    static int find(int[] a) {
        int maxHere = a[0]; // max for subarray ending at current index
        int maxSoFar = a[0];
        for (int i = 1; i < a.length; i++) {
            // either start the sum here or add current element to sum ending at previous element
            maxHere = Math.max(a[i], maxHere + a[i]);
            maxSoFar = Math.max(maxSoFar, maxHere);
        }
        return maxSoFar;
    }

    public static void main(String... args) {
        int[] a = new int[] { 34, -50, 42, 14, -5, 86 };
        System.out.println(find(a));
    }
}
