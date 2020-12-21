package alg.array;

import java.util.Arrays;

/**
 * Given an array nums of n integers where n > 1,  return an array output such that 
 * output[i] is equal to the product of all the elements of nums except nums[i].
 * 
 * Example:
 * 
 * Input:  [1,2,3,4]
 * Output: [24,12,8,6]
 * 
 * Solution pre-computes two auxiliary arrays product from left to right and from right to left.
 * This takes O(n) time and space.
 * Space can be further reduced to just pre-compute both arrays directly in result array - O(n).
 */
public class ProductExceptSelf {
    public int[] productExceptSelf(int[] nums) {
        int[] lr = new int[nums.length];
        int[] rl = new int[nums.length];
        int[] res = new int[nums.length];

        lr[0] = 1;
        for (int i = 1; i < nums.length; i++) {
            lr[i] = lr[i - 1] * nums[i - 1];
        }
        rl[nums.length - 1] = 1;
        for (int i = nums.length - 2; i >= 0; i--) {
            rl[i] = rl[i + 1] * nums[i + 1];
        }
        for (int i = 0; i < nums.length; i++) {
            res[i] = lr[i] * rl[i];
        }
        return res;
    }

    public int[] productExceptSelfOptimal(int[] nums) {
        int[] res = new int[nums.length];

        res[0] = 1;
        for (int i = 1; i < nums.length; i++) {
            res[i] = res[i - 1] * nums[i - 1];
        }
        int product = 1;
        for (int i = nums.length - 1; i >= 0; i--) {
            res[i] *= product;
            product *= nums[i];
        }
        return res;
    }

    public static void main(String... args) {
        int[] nums = new int[] { 1, 2, 3, 4 };
        System.out.println(Arrays.toString(new ProductExceptSelf().productExceptSelf(nums)));
        System.out.println(Arrays.toString(new ProductExceptSelf().productExceptSelfOptimal(nums)));
    }
}
