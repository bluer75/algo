package alg.array;

/**
 * Given an array with n integers, check if it could become non-decreasing by modifying at most 1 element.
 * Example:
 * Input: [4, 2, 3]
 * Output: true - modify 4 to 1 to get a non-decreasing array [1, 2, 3]
 * 
 * Input: [3, 4, 2, 3]
 * Output: false
 * 
 * Solution checks each pair and keeps track of three values: second to last , last and current.
 * In case we find first mismatch current < last we can either:
 * - set current to last
 * - or last to current if current > second to last
 * 
 * It takes O(n) time and O(1) space..
 */
public class NonDecreasingArray {
    public boolean checkPossibility(int[] nums) {
        if (nums == null || nums.length < 2) {
            return true;
        }
        boolean corrected = false;
        int secToLast = Integer.MIN_VALUE;
        int last = nums[0];
        for (int i = 1; i < nums.length; i++) {
            if (last > nums[i]) {
                if (corrected) {
                    return false;
                }
                corrected = true;
                if (secToLast <= nums[i]) {
                    last = nums[i];
                }
                secToLast = last;
            } else {
                secToLast = last;
                last = nums[i];
            }
        }
        return true;
    }

    public static void main(String... args) {
        System.out.println(new NonDecreasingArray().checkPossibility(new int[] { 3, 4, 2, 3 }));
    }
}
