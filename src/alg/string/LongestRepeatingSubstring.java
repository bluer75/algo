package alg.string;

import java.util.HashSet;
import java.util.Set;

/**
 * Given a string s, find out the length of the longest repeating substring(s). Return 0 if no repeating substring
 * exists.
 *
 * For "abbabba" the longest repeated substring is "abba" of length 4.
 *
 * Naive solution takes O(n^2) time/space and checks every substring.
 * Optimal solution uses binary search and rolling hash to find the longest substring occurring twice.
 * It requires O(n log n) time and O(n) space.
 */
public class LongestRepeatingSubstring {
    public int longestRepeatingSubstring(String s) {
        int l = 1, r = s.length(), m = 0;
        while (l <= r) {
            m = l + (r - l) / 2;
            if (find(s, m)) {
                l = m + 1;
            } else {
                r = m - 1;
            }
        }
        return r;
    }

    private boolean find(String s, int l) {
        int n = s.length();
        long mod = (long) Math.pow(2, 24);
        int r = 26;
        long rl = 1;
        long hash = 0;
        Set<Long> subs = new HashSet<>();
        for (int i = 0; i < l; i++) {
            rl = (rl % mod * r % mod) % mod;
        }
        for (int i = 0; i < l; i++) {
            hash = (hash * r + (s.charAt(i) - 'a')) % mod;
        }
        subs.add(hash);
        for (int i = 1; i < n - l + 1; i++) {
            hash = ((hash * r - (s.charAt(i - 1) - 'a') * rl) % mod + mod) % mod;
            hash = (hash + s.charAt(i + l - 1) - 'a') % mod;
            if (subs.contains(hash)) {
                return true;
            }
            subs.add(hash);
        }
        return false;
    }

    public static void main(String[] args) {
        System.out.println(new LongestRepeatingSubstring().longestRepeatingSubstring("cabbabbac"));
    }
}
