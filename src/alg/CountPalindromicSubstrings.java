package alg;

/**
 * Given a string, your task is to count how many palindromic substrings in this string.
 * 
 * The substrings with different start indexes or end indexes are counted as different substrings 
 * even they consist of same characters.
 * 
 * Example 1:
 * 
 * Input: "abc"
 * Output: 3
 * Explanation: Three palindromic strings: "a", "b", "c".
 *  
 * Example 2:
 * 
 * Input: "aaa"
 * Output: 6
 * Explanation: Six palindromic strings: "a", "a", "a", "aa", "aa", "aaa".
 * 
 * For each position in input string we try to find palindrome starting with one and two character
 * as middle of the string.
 * 
 * This takes O(n^2).
 */
public class CountPalindromicSubstrings {
    public int countSubstrings(String s) {
        if (s == null || s.length() == 0) {
            return 0;
        }
        int count = 0;
        for (int i = 0; i < s.length(); i++) {
            count += find(s, i, i);
            count += find(s, i, i + 1);
        }
        return count;
    }

    private static int find(String str, int left, int right) {
        int count = 0;
        while (left >= 0 && right < str.length() && str.charAt(left) == str.charAt(right)) {
            left--;
            right++;
            count++;
        }
        return count;
    }

    public static void main(String... args) {
        System.out.println(new CountPalindromicSubstrings().countSubstrings("abb"));
        System.out.println(new CountPalindromicSubstrings().countSubstrings("aaa"));
    }
}
