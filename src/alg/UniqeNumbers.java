package alg;

import java.util.Arrays;

/**
 * Find two unique numbers in an array where each other number exists twice.
 * Solution is based in XOR operation to find bits occurring odd number of times.
 * The result represents a ^ b and cannot be zero.
 * Take last bit set that belongs to either a or b exclusively and use it to find a, b canbe extracted from a ^ b.
 *
 * This takes O(n) time and O(1) space.
 */
public class UniqeNumbers {
    public int[] singleNumber(int[] nums) {
        int axorb = 0;
        for (int n : nums) {
            axorb ^= n; // represents a ^ b, each 1 comes either from a or b exclusively
        }
        int diff = axorb & -axorb; // find rightmost 1
        int a = 0;
        for (int n : nums) {
            if ((n & diff) != 0) { // find numbers with that rightmost 1 set, including a or b
                a ^= n; // reduces duplicated number, result contains just a or b
            }
        }
        return new int[]{a, axorb ^ a};
    }

    public static void main(String[] args) {
        System.out.println(Arrays.toString(new UniqeNumbers().singleNumber(new int[]{1, 1, 2, 2, 3, 5})));
    }
}
