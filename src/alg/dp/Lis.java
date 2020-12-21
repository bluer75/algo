package alg.dp;

import java.util.Arrays;

/**
 * Longest increasing subsequence using DP and bottom-up approach.
 * Naive implementation is to generate all possible subsets and select from increasing ones the longest one.
 * The complexity for naive implementation is exponential O(2^n).
 * DP base solution uses bottom-up with memoization and takes O(n^2).
 */
public class Lis {

    static int find(int[] input) {
        int maxIndex = 1; // index where max length is
        int[] max = new int[input.length]; // max[i] stores LIS that ends at index i
        Arrays.fill(max, 1); // each element is already a subsequence
        for (int i = 1; i < input.length; i++) {
            for (int j = 0; j < i; j++) {
                // for each subsequence found so far check if it creates increasing subsequence
                // with element at index i and selects max
                if (input[i] > input[j] && max[i] < max[j] + 1) {
                    // LIS for this element is max found so far plus 1
                    max[i] = max[j] + 1;
                }
            }
            if (max[maxIndex] < max[i]) {
                maxIndex = i;
            }
        }
        return max[maxIndex];
    }

    public static void main(String... args) {
        int lis = find(new int[] { 3, 2, 6, 4, 5, 1 });
        System.out.println(lis);
    }
}
