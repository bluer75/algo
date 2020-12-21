package alg;

import java.util.HashMap;
import java.util.stream.Collectors;

/**
 * Given an integer k and a string s, find the length of the longest substring 
 * that contains at most k distinct characters.
 * There are n + (n-1) + (n-2)... +1 substrings -> 1 character substrings + 2 character substrings ... 
 * In total n(n+1)/2 -> O(n^2)
 */
public class SubstringFinder {

    /**
     * Naive implementation generates all possible substrings and finds 
     * longest one with at most k distinct characters
     * As for each substring we calculate frequency it runs in O(n^3)
     */
    static int find(String s, int k) {
        String res = ""; // max so far
        String ss;
        for (int i = 0; i < s.length(); i++) {
            for (int j = i + 1; j <= s.length(); j++) {
                ss = s.substring(i, j);
                if (countDistinct(ss) <= k && ss.length() > res.length()) {
                    res = ss;
                }
            }
        }
        return res.length();
    }

    private static int countDistinct(String s) {
        // create frequency of characters and return its size
        return s.chars().boxed().collect(Collectors.groupingBy(Integer::intValue, Collectors.counting())).size();
    }

    /**
     * This solution starts from first character and keeps adding characters as long as frequency shows max k distinct characters.
     * If there are more then k distinct characters we start removing characters from left until we reach k distinct ones.
     * Each time updated max found so far.
     * This runs in time proportional to length of string -> O(n)
     */
    static int findOptimal(String s, int k) {
        int max = 0; // max found so far
        HashMap<Character, Integer> frq = new HashMap<>();
        int start = 0;
        for (int i = 0; i < s.length(); i++) {
            // keep updating character frequency
            frq.merge(s.charAt(i), 1, (v1, v2) -> v1 + 1);
            if (frq.size() > k) {
                // update max found so far
                max = Math.max(max, i - start);
                while (frq.size() > k) {
                    // remove character from the beginning
                    frq.merge(s.charAt(start), 1, (v1, v2) -> v1 - 1 == 0 ? null : v1 - 1);
                    start++;
                }
            }
        }
        // last check for max
        max = Math.max(max, s.length() - start);
        return max;
    }

    public static void main(String... args) {
        String s = "abcba";
        int k = 2;
        System.out.println(find(s, k));
        System.out.println(findOptimal(s, k));
    }
}
