package alg;

public class BitCounter {

    // Checks all bits one by one.
    public int count(int value) {
        value = convertNegative(value);
        int count = 0;
        while (value > 0) {
            count += value & 0b1;
            value >>= 1;
        }
        return count;
    }

    // Checks all bits recursively.
    public int countRec(int value) {
        return countRecInt(convertNegative(value));
    }

    private int countRecInt(int value) {
        if (value == 0) {
            return 0;
        }
        return (value & 0b1) + countRecInt(value >> 1);
    }

    /**
     * Brian Kernighan’s Algorithm.
     * Subtracting 1 from a number flips all the bits after the rightmost set bit(which is 1) including the rightmost set bit.
     * So if we subtract a number by 1 and do bitwise & with itself (n & (n-1)), we unset the rightmost set bit. 
     * If we do n & (n-1) in a loop and count the no of times loop executes we get the set bit count.
     * The beauty of this solution is the number of times it loops is equal to the number of set bits in a given integer.
     */
    public int countBK(int value) {
        value = convertNegative(value);
        int count = 0;
        while (value > 0) {
            value &= (value - 1);
            count++;
        }
        return count;
    }

    private static int convertNegative(int value) {
        if (value < 0) {
            // converts from 2 complement
            value = ~value + 1;
        }
        return value;
    }

    public static void main(String... args) {
        System.out.println(new BitCounter().count(0));
        System.out.println(new BitCounter().count(28));
        System.out.println(new BitCounter().count(-28));

        System.out.println(new BitCounter().countRec(0));
        System.out.println(new BitCounter().countRec(28));
        System.out.println(new BitCounter().countRec(-28));

        System.out.println(new BitCounter().countBK(0));
        System.out.println(new BitCounter().countBK(28));
        System.out.println(new BitCounter().countBK(-28));
    }
}
