
package alg.array;

import java.util.Arrays;
import java.util.concurrent.ThreadLocalRandom;
import java.util.stream.IntStream;

/**
 * Shuffle a set of numbers without duplicates.
 * Each permutation of input array should equally likely.
 * 
 * Solution uses Fisher-Yates Algorithm - in-place shuffling - and runs in O(n) time and O(1) space.
 */
public class ShuffleArray {

    public void shuffle(int[] nums) {
        ThreadLocalRandom rnd = ThreadLocalRandom.current();
        // array is divided in unordered and ordered part
        // in each iteration we extend unordered part and generate index that should take current position        
        for (int i = 0; i < nums.length; i++) {
            int pos = rnd.nextInt(i, nums.length);
            if (i != pos) {
                int t = nums[i];
                nums[i] = nums[pos];
                nums[pos] = t;
            }
        }
    }

    public static void main(String... args) {
        int[] nums = IntStream.range(0, 10).toArray();
        new ShuffleArray().shuffle(nums);
        System.out.println(Arrays.toString(nums));
    }
}
