package alg.array;

/**
 * Given a binary array, find the maximum number of consecutive 1s in this array if you can flip at most one 0.
 * Example:
 * Input: [1,0,1,1,0]
 * Output: 4
 * Explanation: Flip the first zero will get the the maximum number of consecutive 1s.
 * After flipping, the maximum number of consecutive 1s is 4.
 * 
 * Solution takes O(n) time and space and requires pre-computing array where we store for each 0 number of 
 * consecutive 1s on the right.
 * For [1,0,1,1,0] it is [0,2,0,0,0]
 * 
 * Alternatively this can be solved with O(1) space if we use sliding window.
 */
public class MaxConsecutiveOnes {
    public int findMaxConsecutiveOnes(int[] nums) {
        int[] ones = new int[nums.length];
        int count = 0;
        for (int i = nums.length - 1; i >= 0; i--) {
            if (nums[i] == 0) {
                ones[i] = count;
                count = 0;
            } else {
                count++;
            }
        }
        int maxHere = 0;
        int maxSoFar = 1;
        for (int i = 0; i < nums.length; i++) {
            if (nums[i] == 0) {
                maxSoFar = Math.max(maxSoFar, maxHere + ones[i] + 1);
                maxHere = 0;
            } else {
                maxHere++;
            }
        }
        return Math.max(maxSoFar, maxHere);
    }

    /**
     * Generic solution finding longest subarray of 1s with up to K modifications.
     * It keep count of zeros in the window - if we hit the limit (K) we shrink it on the left side.
     */
    public int longestOnes(int[] A, int K) {
        int zeros = 0;
        int start = 0;
        int max = 0;
        int i = 0;
        for (; i < A.length; i++) {
            if (A[i] == 0) {
                if (zeros == K) {
                    max = Math.max(max, i - start);
                    while (A[start] == 1) {
                        start++;
                    }
                    start++;
                    zeros--;
                }
                zeros++;
            }
        }
        return Math.max(max, i - start);
    }

    public static void main(String... args) {
        System.out.println(new MaxConsecutiveOnes().findMaxConsecutiveOnes(new int[] { 1, 0, 1, 1, 0 }));
        System.out.println(new MaxConsecutiveOnes().longestOnes(new int[] { 1, 0, 1, 1, 0 }, 1));
    }
}
