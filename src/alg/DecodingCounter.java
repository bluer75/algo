package alg;

import java.util.Arrays;

public class DecodingCounter {
    static int countDecoding(char[] digits, int n) {
        System.out.println(Arrays.copyOfRange(digits, 0, n));
        // base cases
        if (n == 0 || n == 1)
            return 1;

        // Initialize count
        int count = 0;

        // If the last digit is not 0, then
        // last digit must add to
        // the number of words
        if (digits[n - 1] > '0')
            count = countDecoding(digits, n - 1);

        // If the last two digits form a number
        // smaller than or equal to 26,
        // then consider last two digits and recur
        if (digits[n - 2] == '1' || (digits[n - 2] == '2' && digits[n - 1] < '7'))
            count += countDecoding(digits, n - 2);

        return count;
    }

    public static void main(String... args) {
        char digits[] = { '1', '2', '1'};
        int n = digits.length;
        System.out.printf("Count is %d", countDecoding(digits, n));
    }
}
