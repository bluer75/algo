package alg.array;

/**
 * Given an unsorted integer array, find the smallest missing positive integer.
 * Input: [1,2,0] Output: 3
 * Input: [3,4,-1,1] Output: 2
 * Input: [7,8,9,11,12] Output: 1
 * 
 * This takes o(n) time and space.
 */
public class FindFirstMissing {
    public int firstMissingPositive(int[] nums) {
        int n = nums.length;
        boolean hasOne = false;
        for (int i = 0; i < n; i++) {
            if (nums[i] == 1) {
                hasOne = true;
            }
            if (nums[i] <= 0 || nums[i] > n) {
                // use 1 as marker for non-relevant values: negative, zero and greater than n
                // if nums has size n, it means there could be 1..n values there and
                // any number greater than n means there is something missing in range 1..n
                nums[i] = 1;
            }
        }
        if (!hasOne) {
            // make sure 1 is not the missing value as we use it as marker
            return 1;
        }
        for (int num : nums) {
            // there are now only values from 1..n and we can mark its presence using it as index
            nums[Math.abs(num) - 1] = -(Math.abs(nums[Math.abs(num) - 1]));
        }
        for (int i = 0; i < n; i++) {
            // first positive value in range 1..n is the missing one 
            if (nums[i] > 0) {
                return i + 1;
            }
        }
        return n + 1;
    }

    public static void main(String... args) {
        System.out.println(new FindFirstMissing().firstMissingPositive(new int[] { 1, 2, 0 }));
    }
}
