package alg.dp;

import java.util.stream.Collectors;

/**
 * Given a string, find the length of the smallest window that contains every distinct character. 
 * Characters may appear more than once in the window. 
 * For example, given "jiujitsu", you should return 5, corresponding to the final five letters.
 * 
 * Naive implementation is to generate all possible substring in O(n^2) time and find the smallest one containing all characters.
 * Checking each substring takes O(n) so in total this takes O(n^3).
 * 
 * Recursive dynamic programming approach starts with whole string and recursively checks string without first and last character.
 * The complexity is to O(n2^n). O(2^n) for recursive tree and O(n) for counting distinct characters.
 * This can be further reduced using memoization.
 */
public class DistinctCharacters {

    public static int find(String str) {
        String minSoFar = str;
        int k = countDistinct(str);
        // consider only strings of length k or more
        for (int i = 0; i <= str.length() - k; i++) {
            for (int j = i + k; j <= str.length(); j++) {
                String s = str.substring(i, j);
                if (countDistinct(s) == k && s.length() < minSoFar.length()) {
                    minSoFar = s;
                }
            }
        }
        System.out.println(minSoFar);
        return minSoFar.length();
    }

    public static int findDP(String str) {
        int k = countDistinct(str);
        return findDP(str, 0, str.length(), k);
    }

    private static int findDP(String str, int i, int j, int k) {
        if (j - i < k || countDistinct(str.substring(i, j)) < k) {
            return Integer.MAX_VALUE;
        }
        return Math.min(j - i, Math.min(findDP(str, i + 1, j, k), findDP(str, i, j - 1, k)));
    }

    private static int countDistinct(String str) {
        return str.chars().boxed().collect(Collectors.groupingBy(Integer::intValue, Collectors.counting())).size();
    }

    public static void main(String... args) {
        System.out.println(findDP("jiujitsu"));
        System.out.println(findDP("abcaa"));
    }
}
