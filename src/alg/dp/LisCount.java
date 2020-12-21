package alg.dp;

/**
 * Given an unsorted array of integers, find the number of longest increasing subsequence.
 * Example:
 * Input: [1,3,5,4,7]
 * Output: 2
 * Explanation: The two longest increasing subsequence are [1, 3, 4, 7] and [1, 3, 5, 7].
 * 
 * Solution is an extension of standard LIS. 
 * We keep additional array count where count[i] represents number of LIS ending at i-th position. 
 * Input: [1, 3, 5, 4, 7]
 * lis:   [1, 2, 3, 3, 4]
 * count: [1, 1, 1, 1, 2]
 * 
 * It takes O(n^2) time and O(n) space.
 */
public class LisCount {

    public int findNumberOfLIS(int[] nums) {
        int[] lis = new int[nums.length];
        int[] count = new int[nums.length];
        int maxSoFar = 0;
        for (int i = 0; i < nums.length; i++) {
            lis[i] = 1;
            count[i] = 1;
            for (int j = 0; j < i; j++) {
                if (nums[j] < nums[i]) {
                    if (lis[i] < lis[j] + 1) {
                        // new max length found, count is the same as count[j]
                        count[i] = count[j];
                    } else if (lis[i] == lis[j] + 1) {
                        // the same length found, add new count to existing one 
                        count[i] += count[j];
                    }
                    lis[i] = Math.max(lis[i], lis[j] + 1);
                }
            }
            maxSoFar = Math.max(maxSoFar, lis[i]);
        }
        int maxCount = 0;
        for (int i = 0; i < nums.length; i++) {
            if (lis[i] == maxSoFar) {
                maxCount += count[i];
            }
        }
        return maxCount;
    }

    public static void main(String... args) {
        System.out.println(new LisCount().findNumberOfLIS(new int[] { 1, 3, 5, 4, 7 }));
    }
}
