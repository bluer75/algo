package alg.string;

/**
 * Given an input string (s) and a pattern (p), implement regular expression matching with support for '.' and '*'.
 * '.' Matches any single character
 * '*' Matches zero or more of the preceding element.
 * The matching should cover the entire input string (not partial).
 * s could be empty and contains only lowercase letters a-z.
 * p could be empty and contains only lowercase letters a-z, and characters like . or *.
 * 
 * Example:
 * Input:
 * s = "ab"
 * p = ".*"
 * Output: true
 * Explanation: ".*" means "zero or more (*) of any character (.)".
 * 
 * Example:
 * Input:
 * s = "aab"
 * p = "c*a*b"
 * Output: true
 * Explanation: c can be repeated 0 times, a can be repeated 1 time. Therefore, it matches "aab".
 * 
 * Recursive solution with possible repeated work (use memoization).
 * When processing x* we can either ignore it (p+2) or if x matches current character in string, 
 * consume x in string and recursively check next characters (s+1).
 * 
 * Time complexity is O((S + P) * 2^(T + (P / 2)))
 * Space complexity is O(T^2 + P^2) 
 */
public class RegexMatching2 {
    public boolean isMatch(String s, String p) {
        return check(s, p, 0, 0);
    }

    private boolean check(String s, String p, int si, int pi) {
        if (si == s.length()) {
            // check if we can consume patter without matching - has only *
            while ((pi <= p.length() - 2) && p.charAt(pi + 1) == '*') {
                pi += 2;
            }
            return pi == p.length();
        }
        if (pi == p.length()) {
            return si == s.length();
        }
        char sch = s.charAt(si);
        char pch = p.charAt(pi);
        if (pi <= p.length() - 2 && p.charAt(pi + 1) == '*') {
            // next is *
            if (check(s, p, si, pi + 2)) {
                // ignore *
                return true;
            }
            if ((sch == pch || pch == '.') && check(s, p, si + 1, pi)) {
                // match *
                return true;
            }
        }
        if ((sch == pch || pch == '.') && check(s, p, si + 1, pi + 1)) {
            return true;
        }
        return false;
    }

    public static void main(String... args) {
        RegexMatching2 matcher = new RegexMatching2();
        System.out.println(matcher.isMatch("aa", "a*b*.*"));
        System.out.println(matcher.isMatch("aab", "c*a*b"));
        System.out.println(matcher.isMatch("mississippi", "mis*is*p*."));
    }
}
