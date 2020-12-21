package alg;

import java.util.Arrays;

/**
 * Find the longest palindrome in a given string.
 * Naive solution generates all possible substrings (n^2) and checks each one 
 * whether is is palindrome - O(n^3).
 */
public class LongestPalindrome {
    /**
     * Consider each character or two characters as middle of possible substring and checks
     * whether left and right characters form a palindrome.
     * Running time is O(n^2). 
     */
    public static String find(String str) {
        if (str.isEmpty() || str.length() == 1) {
            return str;
        }
        String maxSoFar = "";
        for (int i = 0; i < str.length(); i++) {
            maxSoFar = find(str, i, i, maxSoFar); // single character in the middle
            maxSoFar = find(str, i, i + 1, maxSoFar); // two characters in the middle
        }
        return maxSoFar;
    }

    private static String find(String str, int left, int right, String maxSoFar) {
        while (left >= 0 && right < str.length() && str.charAt(left) == str.charAt(right)) {
            left--;
            right++;
        }
        return right - (left + 1) > maxSoFar.length() ? str.substring(left + 1, right) : maxSoFar;
    }

    public String findDP(String str) {
        return findDP(str.toCharArray(), 0, str.length() - 1, new Boolean[str.length()][str.length()]);
    }

    private String findDP(char[] a, int i, int j, Boolean[][] memo) {
        if (i > j) {
            return "";
        }
        if (isPalindrome(a, i, j, memo)) {
            return new String(Arrays.copyOfRange(a, i, j + 1));
        }
        String l = findDP(a, i, j - 1, memo);
        String r = findDP(a, i + 1, j, memo);
        return l.length() >= r.length() ? l : r;
    }

    private boolean isPalindrome(char[] s, int i, int j, Boolean[][] memo) {
        if (i > j) {
            return true;
        }
        if (memo[i][j] == null) {
            memo[i][j] = s[i] == s[j] && isPalindrome(s, i + 1, j - 1, memo);
        }
        return memo[i][j];
    }

    public static void main(String... string) {
        String str = "babaddtattarrattatddetartrateedredividerb";
        System.out.println(find(str));
        System.out.println(new LongestPalindrome().findDP("babaddtattarrattatddetartrateedredividerb"));
    }
}
