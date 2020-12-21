package alg;

/**
 *  Given a 32-bit signed integer, reverse digits of an integer.
 * Example 1:
 * Input: 123
 * Output: 321
 * 
 * Example 2:
 * Input: -12
 * Output: -321
 * 
 * Example 3:
 * Input: 120
 * Output: 21
 * 
 * Note:
 * Assume we are dealing with an environment which could only store integers within the 32-bit signed integer 
 * range: [−231,  231 − 1]. For the purpose of this problem, assume that your function returns 0 when 
 * the reversed integer overflows.
*/
public class IntegerReverser {

    public int reverse(int x) {
        int y = x;
        long result = 0;
        while (y != 0) {
            int m = y % 10;
            result = (result * 10) + m;
            y /= 10;
        }
        return result > Integer.MIN_VALUE && result < Integer.MAX_VALUE ? (int) result : 0;
    }

    public static void main(String... args) {
        IntegerReverser reverser = new IntegerReverser();
        System.out.println(reverser.reverse(123));
        System.out.println(reverser.reverse(-123));
        System.out.println(reverser.reverse(120));
    }
}
