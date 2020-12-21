package alg.string;

/**
 * Given a non-empty string check if it can be constructed by taking a substring of it and 
 * appending multiple copies of the substring together. 
 * You may assume the given string consists of lowercase English letters only and its length will not exceed 10000.
 * 
 * Example1:
 * Input: "abab"
 * Output: True
 * 
 * Example 2:
 * Input: "aba"
 * Output: False
 * 
 * Solution tries each possible substring of lengths from 1 (maximal repetition) to length/2 (one repetition).
 * For each such substring of length l it check whether characters in each repeated substring are the same: 
 * - 0, d, 2d ...
 * - 1, d + 1, 2d + 1 ...
 * - l, d + l, 2d + l ...
 * 
 * This takes O(n^2) time.
 * For each possible length of substring (1..n_/2) we check all n characters.
 *  
 * Using Knuth-Morris-Pratt Algorithm (KMP) can find the answer in O(n) time.
 */
public class RepeatedSubstring {
    public boolean repeatedSubstringPattern(String s) {
        if (s.length() < 2) {
            return false;
        }
        for (int d = 1; d <= s.length() / 2; d++) {
            if (check(s, d)) {
                return true;
            }
        }
        return false;
    }

    private boolean check(String s, int d) {
        if (s.length() % d != 0) {
            return false;
        }
        for (int i = 0; i < d; i++) {
            char ch = s.charAt(i);
            for (int j = i; j < s.length(); j += d) {
                if (s.charAt(j) != ch) {
                    return false;
                }
            }
        }
        return true;
    }
    
    public boolean repeatedSubstringPatternKMP(String s) {
        int n = s.length();
        int[] dp = new int[n];
        // Construct partial match table (lookup table).
        // It stores the length of the proper prefix that is also a proper suffix.
        // ex. ababa --> [0, 0, 1, 2, 1]
        // ab --> the length of common prefix / suffix = 0
        // aba --> the length of common prefix / suffix = 1
        // abab --> the length of common prefix / suffix = 2
        // ababa --> the length of common prefix / suffix = 1
        for (int i = 1; i < n; ++i) {
            int j = dp[i - 1];
            while (j > 0 && s.charAt(i) != s.charAt(j)) {
                j = dp[j - 1];    
            }
            if (s.charAt(i) == s.charAt(j)) {
                ++j;
            }
            dp[i] = j;    
        }

        int l = dp[n - 1];
        // check if it's repeated pattern string
        return l != 0 && n % (n - l) == 0;
    }

    public static void main(String... args) {
        System.out.println(new RepeatedSubstring().repeatedSubstringPatternKMP("abdabe"));
        System.out.println(new RepeatedSubstring().repeatedSubstringPatternKMP("aba"));
    }
}
