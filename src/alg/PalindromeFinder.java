package alg;

import java.util.Comparator;
import java.util.PriorityQueue;

/**
 * Given a string, find the palindrome that can be made by inserting the fewest 
 * number of characters as possible anywhere in the word. If there is more than 
 * one palindrome of minimum length that can be made, return the lexicographically 
 * earliest one (the first one alphabetically).
 */
public class PalindromeFinder {
    /**
     * Finds recursively minimum length palindrome.
     * Running time - O(2^n).
     */
    public static String find(String str) {
        PriorityQueue<String> res = new PriorityQueue<String>(Comparator.comparingInt(String::length));
        find(str.toCharArray(), 0, str.length() - 1, "", "", res);
        return res.peek();
    }

    /**
     * Compares recursively left and right character.
     * It they are equal checks next pair otherwise find minimum when inserting one character on left or one on right.
     * Pass updated prefix and suffix accordingly to create palindrome at the end.
     */
    private static int find(char[] chars, int left, int right, String prefix, String suffix,
            PriorityQueue<String> res) {
        if (left == right) {
            // base case - last characters left
            res.add(prefix + chars[left] + suffix);
            return 0;
        }
        if (left > right) {
            // base case - no characters left
            res.add(prefix + suffix);
            return 0;
        }
        if (chars[left] == chars[right]) {
            return find(chars, left + 1, right - 1, prefix + chars[left], chars[right] + suffix, res);
        }
        // characters don't match - insert matching one either on left or on right side and take minimum
        return Math.min(find(chars, left, right - 1, prefix + chars[right], chars[right] + suffix, res),
                find(chars, left + 1, right, prefix + chars[left], chars[left] + suffix, res)) + 1;
    }

    public static void main(String... args) {
        System.out.println(find("race"));
        System.out.println(find("google"));
    }
}
