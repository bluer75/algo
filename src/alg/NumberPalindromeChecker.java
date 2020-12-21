package alg;

/**
 * Determine whether an integer is a palindrome. An integer is a palindrome when it reads the same backward as forward.
 * 
 * Example:
 * Input: 121
 * Output: true
 */
public class NumberPalindromeChecker {

    public boolean isPalindrome(int x) {
        if (x == 0) {
            return true;
        }
        // negative number is not palindrome, number with 0 at the end is not palindrome
        if (x < 0 || x % 10 == 0) {
            return false;
        }
        // revert half of the number and check if it equals the input value
        int reverted = 0;
        while (x > reverted) {
            reverted = reverted * 10 + x % 10;
            x /= 10;
        }
        // x is either equal to reverted or in case of odd number of digits reverted / 10 - ignore middle one
        return reverted == x || x == reverted / 10;
    }

    public static void main(String... args) {
        System.out.println(new NumberPalindromeChecker().isPalindrome(121));
    }
}
