package alg;

/**
 * Rotate all bits in given number.
 * Treat the number as unsigned value.
 */
public class BitRotator {
    // you need treat n as an unsigned value
    public int reverseBits(int n) {
        int res = 0;
        // rotate 31 bits
        for (int i = 0; i < 31; i++) {
            res |= (n >> i) & 1;
            res <<= 1;
        }
        // handle sign bit
        return n < 0 ? res | 1 : res;
    }

    public static void main(String... args) {
        int num = 43261596; // 00000010100101000001111010011100
        int rotated = new BitRotator().reverseBits(num); // 964176192 -> 00111001011110000010100101000000
        System.out.println(Integer.toBinaryString(num));
        System.out.println(rotated);
        num = -3; // 11111111111111111111111111111101
        rotated = new BitRotator().reverseBits(num); // -1073741825 -> 10111111111111111111111111111111
        System.out.println(Integer.toBinaryString(num));
        System.out.println(rotated);
    }
}
