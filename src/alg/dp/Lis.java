package alg.dp;

import java.util.Arrays;

/**
 * Find length of the longest increasing subsequence.
 * Naive implementation is to generate all possible subsets and select from increasing ones the longest one.
 * The complexity for naive implementation is exponential O(2^n).
 * DP base solution uses bottom-up with memoization and takes O(n^2).
 * Optimized solution based on binary search takes O(n log n) time.
 */
public class Lis {

    static int findDP(int[] input) {
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

    static int findBS(int[] input) {
        int n = input.length, max = 0;
        int[] lis = new int[n + 1]; // lis[k] - the smallest value ending increasing sequence of length k - it's sorted
        lis[0] = Integer.MIN_VALUE; // sentinel for length 0
        for (int num : input) {
            if (num > lis[max]) {
                // add num at the end and increase max
                lis[++max] = num;
            } else {
                // replace the smallest element greater than or equal to num - use BS for left most element
                int l = 1, r = max, m = 0;
                while (l < r) {
                    m = l + (r - l) / 2;
                    if (lis[m] < num) {
                        l = m + 1;
                    } else {
                        r = m;
                    }
                }
                lis[l] = num;
            }
        }
        return max;
    }

    public static void main(String... args) {
        //int[] input = new int[]{1, 3, 6, 7, 9, 4, 10, 5, 6};
        int[] input = new int[]{0, 1, 2, 3};
        System.out.println(findDP(input));
        System.out.println(findBS(input));
    }
}
