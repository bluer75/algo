package alg;

import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;

/**
 * Given a string, find the length of the longest substring without repeating characters.
 * Example:
 * Input: "abcabcbb"
 * Output: 3 -> "abc", with the length of 3. 
 */
public class SubstringFinderLongestDistinct {
    /**
     * Naive implementation - tries all possible substrings and checks each one for unique characters O(n^3).
     */
    public int find(String str) {
        int maxSoFar = 0;
        String s = null;
        for (int i = 0; i < str.length(); i++) {
            for (int j = i + 1; j <= str.length(); j++) {
                s = str.substring(i, j);
                if (hasDistinctChars(s) && maxSoFar < s.length()) {
                    maxSoFar = s.length();
                }
            }
        }
        return maxSoFar;
    }

    /**
     * Checks if given string contains only distinct characters -> O(n).
     */
    private boolean hasDistinctChars(String str) {
        Set<Character> chars = new HashSet<>();
        for (char ch : str.toCharArray()) {
            if (chars.contains(ch)) {
                return false;
            }
            chars.add(ch);
        }
        return true;
    }

    /**
     * Optimal solution - create sliding window (i, j) which moves by one character each time and 
     * checks if it is distinct. If it exists already in the window, truncate the window from left 
     * to the first occurrence of that character. 
     * In the worst case it processes each character twice (when adding to the window and removing 
     * from the window) -> O(n).   
     */
    public int findOptimal(String str) {
        int maxSoFar = 0;
        Set<Character> chars = new HashSet<>();
        int i = 0;
        char[] a = str.toCharArray();
        for (int j = 0; j < a.length; j++) {
            if (chars.contains(a[j])) {
                // found duplicate, truncate from left
                do {
                    chars.remove(a[i]);
                    i++;
                } while (a[i - 1] != a[j]);
            }
            chars.add(a[j]);
            maxSoFar = maxSoFar < j - i + 1 ? j - i + 1 : maxSoFar;

        }
        return maxSoFar;
    }

    public static void main(String... args) {
        String[] strs = { "", "a", "aaa", "abcabcbb" };
        SubstringFinderLongestDistinct finder = new SubstringFinderLongestDistinct();
        Arrays.stream(strs).forEach(str -> System.out.println(finder.find(str) + " " + finder.findOptimal(str)));
    }
}
