package alg.array;

import java.util.Stack;

/**
 * Given an array of integers, find the sum of min over every (contiguous) subarray.
 * Since the answer may be large, return the answer modulo 10^9 + 7.
 * 
 * Example:
 * Input: [3, 1, 2, 4]
 * Output: 17
 * Explanation: subarrays are [3], [1], [2], [4], [3,1], [1,2], [2,4], [3,1,2], [1,2,4], [3,1,2,4]. 
 * Minimums are 3, 1, 2, 4, 1, 1, 2, 1, 1, 1 -> 17.
 * 
 * Brute force solution requires O(n^3).
 * DP solution takes O(n^2) and O(1) space - space optimized from O(n^2) where dp[i][j] is min for subarray i..j.
 * 
 * Optimal solution requires O(n) time/space.
 * The idea is to focus on each number - i - in the input array and determine in how many subarrays it 
 * is the minimum value - f(i) - for all number it should sum up to total number of subarrays. 
 * If we know f(i) for every i, then result is sum(A[i] * f(i))
 * 
 * To get f(i), we need to find out:
 * left[i], the length of strict bigger numbers on the left of A[i],
 * right[i], the length of bigger numbers on the right of A[i].
 * Then, left[i] + 1 is the number of subarrays ending with A[i] and right[i] + 1 is the number of subarrays starting 
 * with A[i] and A[i] is the minimum in both cases.
 * 
 * Finally f(i) = (left[i] + 1) * (right[i] + 1)
 * 
 * For [3, 1, 2, 4]
 * left + 1 = [1, 2, 1, 1]
 * right + 1 = [1, 3, 2, 1]
 * f = [1, 6, 2, 1]
 * res = 3 * 1 + 1 * 6 + 2 * 2 + 4 * 1 = 17
 */
public class SumOfSubarrayMinimums {

    public int sumSubarrayMinsBF(int[] array) {
        long sum = 0;
        for (int len = 1; len <= array.length; len++) {
            for (int pos = 0; pos <= array.length - len; pos++) {
                long min = Integer.MAX_VALUE;
                for (int i = pos; i < pos + len; i++) {
                    min = Math.min(min, array[i]);
                }
                sum += min;
            }
        }
        return (int) (sum % (long) (Math.pow(10, 9) + 7));
    }

    public int sumSubarrayMinsDP(int[] array) {
        int min = 0;
        long sum = 0;
        for (int i = 0; i < array.length; i++) {
            for (int j = i; j < array.length; j++) {
                if (i == j) {
                    min = array[j];
                } else {
                    min = Math.min(min, array[j]);
                }
                sum += min;
            }
        }
        return (int) (sum % (int) (10e9 + 7));
    }

    // this process can be optimized to one pass and one stack in total
    public int sumSubarrayMins(int[] A) {
        int res = 0, n = A.length, mod = (int) 1e9 + 7;
        int[] left = new int[n], right = new int[n];
        Stack<int[]> s1 = new Stack<>(), s2 = new Stack<>();
        for (int i = 0; i < n; ++i) {
            int count = 1;
            while (!s1.isEmpty() && s1.peek()[0] > A[i])
                count += s1.pop()[1];
            s1.push(new int[] { A[i], count });
            left[i] = count;
        }
        for (int i = n - 1; i >= 0; --i) {
            int count = 1;
            while (!s2.isEmpty() && s2.peek()[0] >= A[i])
                count += s2.pop()[1];
            s2.push(new int[] { A[i], count });
            right[i] = count;
        }
        for (int i = 0; i < n; ++i)
            res = (res + A[i] * left[i] * right[i]) % mod;
        return res;
    }

    public static void main(String... args) {
        System.out.println(new SumOfSubarrayMinimums().sumSubarrayMinsBF(new int[] { 3, 1, 2, 4 }));
        System.out.println(new SumOfSubarrayMinimums().sumSubarrayMinsDP(new int[] { 3, 1, 2, 4 }));
        System.out.println(new SumOfSubarrayMinimums().sumSubarrayMins(new int[] { 71, 55, 82, 55 }));
    }
}
