package alg.dp;

/**
 * Longest increasing subarray using DP and bottom-up approach.
 * Naive implementation is to generate all possible subarrays and select from increasing ones the longest one.
 * The complexity for naive implementation is quadratic O(n^2).
 * Optimized DP base solution uses bottom-up with memoization and takes O(n).
 */
public class Listr {

    static int find(int[] input) {
        int maxSoFar = 1;
        int max = 1;
        for (int i = 1; i < input.length; i++) {
            if (input[i] > input[i - 1]) {
                // LIS for this element is max found so far plus 1
                max++;
            } else {
                max = 1;
            }
            maxSoFar = Math.max(maxSoFar, max);
        }
        return maxSoFar;
    }

    public static void main(String... args) {
        int lis = find(new int[] { 3, 2, 6, 4, 5, 6, 1 });
        System.out.println(lis);
    }
}
