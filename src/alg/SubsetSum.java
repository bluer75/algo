package alg;

import java.util.Arrays;
import java.util.Deque;
import java.util.LinkedList;

/**
 * Given an array of integers and a target number k, write a function that returns a subset of integers that adds up to k. 
 * If such a subset cannot be made, then return null.
 */
public class SubsetSum {

    /**
     * Finds recursively the solution trying, in worst case, all possible subsets.
     * For each element we have two options: it is included or excluded from subset.
     * Complexity is O(2^n).
     */
    public static int[] find(int[] a, int k) {
        Deque<Integer> res = new LinkedList<>();
        if (find(a, k, 0, res)) {
            return res.stream().mapToInt(Integer::intValue).toArray();
        }
        return null;
    }

    private static boolean find(int[] a, int k, int i, Deque<Integer> set) {
        if (k == 0) {
            // base case - found subset with given sum
            return true;
        }
        if (i == a.length) {
            // base case - set not found, backtrack
            return false;
        }
        // check if current element can be included
        if (k - a[i] >= 0) {
            // include current element
            set.push(a[i]);
            if (find(a, k - a[i], i + 1, set)) {
                return true;
            }
            set.pop(); // set not found, remove current value and try without it
        }
        // exclude current element
        return find(a, k, i + 1, set);
    }

    /**
     * Uses DP bottom-up approach. It populates table with partial results.
     * Time/space complexity is O(n * k).
     */
    public static int[] findDP(int[] a, int k) {
        boolean[][] sum = new boolean[k + 1][a.length + 1]; // sum[s][i] true if sum s can be build using 0..i-th input
                                                            // elements (included or excluded)
        Arrays.fill(sum[0], true); // any element can be used to get sum 0 (exclude option)
        for (int s = 1; s <= k; s++) {
            for (int i = 1; i <= a.length; i++) {
                sum[s][i] = sum[s][i - 1]; // exclude current element
                if (a[i - 1] <= s) {
                    // include or exclude current element
                    sum[s][i] |= sum[s - a[i - 1]][i - 1];
                }
            }
        }
        System.out.println(sum[k][a.length]);
        return null;
    }

    public static void main(String... args) {
        int[] a = { 1, 2, 3 };
        int k = 4;
        System.out.println(Arrays.toString(find(a, k)));
        System.out.println(Arrays.toString(findDP(a, k)));
    }
}
