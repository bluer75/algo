package alg.array;

import java.util.Arrays;

/**
 * Given a sequence of n integers, determine the lexicographically smallest sequence which may be obtained 
 * from it after performing at most k element swaps, each involving a pair of consecutive elements.
 * Example
 * for array [8, 9, 11, 2, 1], return [2, 8, 9, 11, 1]
 * We swap [11, 2], followed by [9, 2], then [8, 2].
 * 
 * Solution is based on greedy approach - we start with index 0 and find max smaller element within k steps.
 * Then we swap all pairs on the way and decrement k by difference of indices. 
 * As long as k is greater than 0 we repeat it for next position.
 * 
 * This takes O(n^2) time and O(1) space.
 */
public class MinArrayWithKSwaps {

    private int findMaxSmaller(int[] a, int i, int k) {
        int minIdx = i;
        int maxK = i + k;
        for (i = i + 1; i <= maxK; i++) {
            if (a[i] < a[minIdx]) {
                minIdx = i;
            }
        }
        return minIdx;
    }

    private void move(int[] a, int src, int dst) {
        int tmp = 0;
        while (src < dst) {
            tmp = a[dst];
            a[dst] = a[--dst];
            a[dst] = tmp;
        }
    }

    public int[] findMinArray(int[] arr, int k) {
        // Write your code here
        int[] result = new int[arr.length];
        System.arraycopy(arr, 0, result, 0, arr.length);
        int i = 0;
        int idx = i;
        while (k > 0 && i < arr.length) {
            idx = findMaxSmaller(result, i, k);
            if (idx > i) {
                move(result, i, idx);
                k -= idx;
            }
            i++;
        }
        return result;
    }

    public static void main(String... args) {
        System.out.println(Arrays.toString(new MinArrayWithKSwaps().findMinArray(new int[] { 8, 9, 11, 2, 1 }, 3)));
    }
}
