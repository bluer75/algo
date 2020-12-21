package alg.array;

import java.util.ArrayDeque;
import java.util.Arrays;
import java.util.Deque;

/**
 * You are given an array array of N integers. 
 * For each index i, you are required to determine the number of contiguous subarrays that 
 * fulfills the following conditions:
 * The value at index i must be the maximum element in the contiguous subarrays, and these 
 * contiguous subarrays must either start from or end on index i.
 * Example:
 * arr = [3, 4, 1, 6, 2]
 * output = [1, 3, 1, 5, 1]
 * Explanation:
 * For index 0 - [3] is the only contiguous subarray that starts (or ends) with 3, and the maximum value in this subarray is 3.
 * For index 1 - [4], [3, 4], [4, 1]
 * For index 2 - [1]
 * For index 3 - [6], [6, 2], [1, 6], [4, 1, 6], [3, 4, 1, 6]
 * For index 4 - [2]
 * So, the answer for the above input is [1, 3, 1, 5, 1]
 * 
 * We calculate left and right arrays with indices of first greater/equal element on the left/right for given index.
 * The the count of subarrays can be calculated as count[i] = 1 + (i - left[i] - 1) + (right[i] - i - 1);
 * It takes O(n) time and space.
 */
public class SumOfSubarraysWithMax {
    int[] countSubarrays(int[] arr) {
        int n = arr.length;
        int[] left = new int[n];
        int[] right = new int[n];
        int[] count = new int[n];
        Deque<Integer> stack = new ArrayDeque<>(n);
        for (int i = 0; i < n; i++) {
            while (!stack.isEmpty() && arr[stack.peek()] < arr[i]) {
                stack.pop();
            }
            left[i] = stack.isEmpty() ? -1 : stack.peek();
            stack.push(i);
        }
        stack.clear();
        for (int i = n - 1; i >= 0; i--) {
            while (!stack.isEmpty() && arr[stack.peek()] < arr[i]) {
                stack.pop();
            }
            right[i] = stack.isEmpty() ? n : stack.peek();
            stack.push(i);
        }
        for (int i = 0; i < n; i++) {
            count[i] = 1 + (i - left[i] - 1) + (right[i] - i - 1);
        }
        return count;
    }

    public static void main(String... args) {
        System.out.println(Arrays.toString(new SumOfSubarraysWithMax().countSubarrays(new int[] { 3, 4, 1, 6, 2 })));
    }
}
