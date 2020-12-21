package alg;

/**
 * Find a contiguous subarray with the largest product.
 * This is similar to Kadane's algorithm with running time O(n).
 * We calculate max and min to handle negative numbers.
 */
public class MaxSubArrayProduct {
    public int maxProduct(int[] nums) {
        int currentMax = nums[0];
        int currentMin = nums[0];
        int prevMax = nums[0];
        int prevMin = nums[0];
        int maxSoFar = nums[0];
        for (int i = 1; i < nums.length; i++) {
            // max(nums[i], nums[¡] * prevMax, nums[¡] * prevMin) 
            currentMax = Math.max(prevMax * nums[i], Math.max(prevMin * nums[i], nums[i]));
            // min(nums[i], nums[¡] * prevMax, nums[¡] * prevMin)  
            currentMin = Math.min(prevMax * nums[i], Math.min(prevMin * nums[i], nums[i]));
            maxSoFar = Math.max(maxSoFar, currentMax);
            prevMax = currentMax;
            prevMin = currentMin;
        }
        return maxSoFar;
    }

    public static void main(String... args) {
        System.out.println(new MaxSubArrayProduct().maxProduct(new int[] { 2, 3, -2, 4 }));
    }
}
