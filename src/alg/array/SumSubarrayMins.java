package alg.array;

import java.util.ArrayDeque;
import java.util.Deque;

/**
 * Given an array of integers A, find the sum of min(B), where B ranges over every (contiguous) subarray of A.
 * Since the answer may be large, return the answer modulo 10^9 + 7.
 * Example 1:
 * Input: [3,1,2,4]
 * Output: 17
 * Explanation: Subarrays are [3], [1], [2], [4], [3,1], [1,2], [2,4], [3,1,2], [1,2,4], [3,1,2,4]. 
 * Minimums are 3, 1, 2, 4, 1, 1, 2, 1, 1, 1.  Sum is 17.
 */
public class SumSubarrayMins {
    public int sumSubarrayMins(int[] array) {
        int[] right = new int[array.length]; // number of bigger values on the right
        int[] left = new int[array.length]; // number of bigger values on the left 
        Deque<int[]> stack = new ArrayDeque<>(array.length);
        int count = 0;
        for (int i = 0; i < array.length; i++) {
            count = 1;
            while (!stack.isEmpty() && array[stack.peek()[0]] > array[i]) {
                count += stack.pop()[1];
            }
            stack.push(new int[] { i, count });
            left[i] = count;
        }
        while (!stack.isEmpty()) {
            stack.pop();
        }
        for (int i = array.length - 1; i >= 0; i--) {
            count = 1;
            while (!stack.isEmpty() && array[stack.peek()[0]] >= array[i]) {
                count += stack.pop()[1];
            }
            stack.push(new int[] { i, count });
            right[i] = count;
        }
        while (!stack.isEmpty()) {
            stack.pop();
        }
        int res = 0;
        int mod = (int) 1e9 + 7;
        for (int i = 0; i < array.length; i++)
            res = (res + array[i] * left[i] * right[i]) % mod;
        return res;
    }

    public static void main(String... args) {
        System.out.println(new SumSubarrayMins().sumSubarrayMins(new int[] { 71,55,82,55}));
    }
}
