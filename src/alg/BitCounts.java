package alg;

import java.util.Arrays;

/**
 * Given a non negative integer number n. For every numbers i in the range 0 <= i <= n calculate the number of 
 * bits that are set.
 * 
 * Example: Input: 2
 * Output: [0, 1, 1]
 * 
 * Solution is based on formula bits[i] = bits[i - pow2(i)] + 1;
 * From each number we subtract the closest (lower or equal) power of 2 and take the rest from table as it 
 * was already calculated.
 * 
 * It takes O(n).
 */
public class BitCounts {
    public int[] countBits(int num) {
        int[] bits = new int[num + 1];
        if (num == 0) {
            return bits;
        }
        bits[1] = 1;
        int pow2 = 1; // closest power of 2
        int nextPow2 = pow2 << 1; // next power of 2
        for (int i = 2; i <= num; i++) {
            if (i >= nextPow2) {
                pow2 <<= 1;
                nextPow2 <<= 1;
            }
            bits[i] = bits[i - pow2] + 1;
        }
        return bits;
    }

    public static void main(String... args) {
        System.out.println(Arrays.toString(new BitCounts().countBits(2)));
    }
}
