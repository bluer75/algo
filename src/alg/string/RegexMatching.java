package alg.string;

/**
 * Implement regular expression matching that supports '.' and '*'.
 * 
 * Simple solution that is using recursion/backtracking.
 * This takes O(s * r) time and space (s, r - length of the string, regex).
 * 
 * DP solution uses following logic:
 * Case A - ‘*’:
 *   - zero length - ignore ‘*’ and move to next character in pattern
 *   - matches one/more characters - move to next character in string
 * Case 2 - ‘?’/'.' - matches one character - move to next character in pattern and string
 * Case 3 - non-wildcard:
 *   - if current characters in string and pattern match, move to next characters
 *   - if they do not match - end with false
 *   
 * T[i][j] is true if first i characters in string match first j characters of pattern
 * This takes O(s * p) time and space (s, p - length of the string, pattern). * 
 */
public class RegexMatching {
    public boolean matches(String str, String regex) {
        if (regex.isEmpty()) {
            // base case
            return str.isEmpty();
        }
        if (str.isEmpty()) {
            // base case
            return regex.isEmpty() || regex.equals("*");
        }
        if (regex.charAt(0) != '*') {
            // matching single characters
            if (firstCharactersMatch(str, regex)) {
                return matches(str.substring(1), regex.substring(1));
            }
        } else {
            // multiple characters matching with '*', try zero length first
            if (matches(str, regex.substring(1))) {
                return true;
            }
            // try all suffixes including entire and empty string
            for (int i = 0; i <= str.length(); i++) {
                if (matches(str.substring(i), regex.substring(1))) {
                    return true;
                }
            }
        }
        return false;
    }

    private boolean firstCharactersMatch(String str, String regex) {
        // str and regex are not empty, '.' matches any character
        return regex.charAt(0) == '.' || str.charAt(0) == regex.charAt(0);
    }

    public boolean matchesDP(String str, String pattern) {
        int s = str.length();
        int p = pattern.length();
        boolean dp[][] = new boolean[s + 1][p + 1]; // initialized with false
        // fill results
        dp[0][0] = true; // empty pattern matches empty string
        for (int i = 1; i <= p; i++) {
            if (pattern.charAt(i - 1) == '*') {
                // empty string matches to '*'
                dp[0][i] = dp[0][i - 1];
            }
        }
        for (int i = 1; i <= s; i++) {
            for (int j = 1; j <= p; j++) {
                if (pattern.charAt(j - 1) == '*') {
                    // either ignore (take value from left) or move to next one (take value from above)
                    // it's true only if it was true for previous character in pattern or previous character in the
                    // string
                    dp[i][j] = dp[i][j - 1] || dp[i - 1][j];
                } else if (pattern.charAt(j - 1) == '.') {
                    // matches any character - copy result for matching previous characters
                    dp[i][j] = dp[i - 1][j - 1];
                } else if (pattern.charAt(j - 1) == str.charAt(i - 1)) {
                    // exact match - copy result for matching previous characters
                    dp[i][j] = dp[i - 1][j - 1];
                }
            }
        }

        return dp[s][p];
    }

    public static void main(String... args) {
        RegexMatching matcher = new RegexMatching();
        System.out.println("abc" + " " +  "abc");
        System.out.println(matcher.matches("abc", "abc"));
        System.out.println(matcher.matchesDP("abc", "abc"));
        System.out.println("abc" + " " + "ab.");
        System.out.println(matcher.matches("abc", "ab."));
        System.out.println(matcher.matchesDP("abc", "ab."));
        System.out.println("abcd" + " " + "ab*d");
        System.out.println(matcher.matches("abcd", "ab*d"));
        System.out.println(matcher.matchesDP("abcd", "ab*d"));
        System.out.println("abcd" + " " + "ab*d.");
        System.out.println(matcher.matches("abcd", "ab*d."));
        System.out.println(matcher.matchesDP("abcd", "ab*d."));
        System.out.println("abcd" + " " + "abc*.");
        System.out.println(matcher.matches("abcd", "abc*."));
        System.out.println(matcher.matchesDP("abcd", "abc*."));
        System.out.println("baaabab" + " " + "*ba*ab");
        System.out.println(matcher.matches("baaabab", "*ba*ab"));
        System.out.println(matcher.matchesDP("baaabab", "*ba*ab"));
    }
}
