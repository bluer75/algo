package alg;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

/**
 * An n-bit gray code sequence is a sequence of 2^n integers where:
 *
 * Every integer is in the inclusive range [0, 2^n - 1],
 * The first integer is 0,
 * An integer appears no more than once in the sequence,
 * The binary representation of every pair of adjacent integers differs by exactly one bit, and
 * The binary representation of the first and last integers differs by exactly one bit.
 * Given an integer n, return any valid n-bit gray code sequence.
 *
 * Solution uses following algorithm -> https://www.eetimes.com/gray-code-fundamentals-part-2/#
 * 1 Commence with the simplest Gray code possible; that is, for a single bit.
 * 2 Create a mirror image of the existing Gray code below the original values.
 * 3 Prefix the original values with 0s and the mirrored values with 1s.
 * 4 Repeat steps 2) and 3) until the desired width is achieved.
 */
public class GrayCodeGenerator {
    public List<Integer> grayCode(int n) {
        int[] nums = new int[1 << n];
        int size = 0, bits = 0;
        while (bits < n) {
            size = 1 << bits;
            for (int a = size - 1, b = size; a >= 0; a--, b++) {
                // mirror
                nums[b] = nums[a];
                // add prefix
                nums[b] |= 1 << bits;
            }
            bits++;
        }
        return Arrays.stream(nums).boxed().collect(Collectors.toList());
    }

    public static void main(String... args) {
        new GrayCodeGenerator().grayCode(4).stream().map(Integer::toBinaryString).forEach(System.out::println);
    }
}
