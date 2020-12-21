package alg;

/**
 * Revert all bits in given number.
 */
public class BitReverter {
    public int revert(int n) {
        if (n < 0) {
            n = ~n + 1;
        }
        int mask = 0x1;
        while (n > mask - 1) {
            mask = mask << 1;
        }
        mask = mask - 1;
        return n ^ mask;
    }

    public static void main(String... args) {
        int num = -12;
        System.out.println(Integer.toBinaryString(num));
        System.out.println(Integer.toBinaryString(new BitReverter().revert(num)));
        num = 12;
        System.out.println(Integer.toBinaryString(num));
        System.out.println(Integer.toBinaryString(new BitReverter().revert(num)));
    }
}
