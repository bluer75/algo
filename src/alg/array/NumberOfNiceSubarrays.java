package alg.array;

/**
 * Given an array of integers and an integer k, count number of continuous subarray with k odd numbers.
 *
 * Example:
 *
 * Input: [2,1,2,1,2], k = 2
 * Output: 4, [2,1,2,1], [1,2,1], [2,1,2,1,2],[1,2,1,2]
 *
 * Solution is based on sliding window:
 * start, odd, end identify the window where odd is the position of first odd value within the window
 * Each time count of odd values exceeds the limit the window shrinks from left to eliminate one odd value
 *
 * This takes O(n) time and O(1) space.
 */
public class NumberOfNiceSubarrays {
    public int numberOfSubarrays(int[] nums, int k) {
        int total = 0;
        for (int oddValues = 0, start = 0, odd = 0, end = 0; end < nums.length; end++) {
            if (nums[end] % 2 != 0) {
                oddValues++;
                if (oddValues == 1) { // first odd value
                    odd = end;
                } else if (oddValues > k) { // shift start of the window
                    oddValues--;
                    odd = start = odd + 1;
                    while (nums[odd] % 2 == 0) { // find next odd value
                        odd++;
                    }
                }
            }
            if (oddValues == k) {
                total += (odd - start + 1);
            }
        }
        return total;
    }

    public static void main(String[] args) {
        System.out.println(new NumberOfNiceSubarrays().numberOfSubarrays(new int[]{2, 1, 2, 1, 2}, 2));
    }
}
