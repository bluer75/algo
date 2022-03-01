package alg.string;

import java.util.HashMap;
import java.util.Map;
import java.util.function.Function;
import java.util.stream.Collectors;

/**
 * Given two strings s1 and s2, return true if s2 contains a permutation of s1, or false otherwise.
 * For example: s1: ab, s2: eidbaooo - the result is true as s2 contains string ba which is a permutation of s1
 *
 * Solution is based on frequency count and sliding window and takes O(n + m) time and O(1) space - unique letters in
 * s1.
 *
 * In order to avoid comparison of both frequency counters each time sliding windows moves, there is a balance
 * calculated that determines how many characters are missing.
 */
public class PermutationInString {

    public boolean checkInclusion(String s1, String s2) {
        Map<Integer, Long> freq1 = s1.chars().boxed().collect(
            Collectors.groupingBy(Function.identity(), Collectors.counting()));
        Map<Integer, Integer> freq2 = new HashMap<>();
        int ch = 0;
        int balance = s1.length(); // it determines how many characters are missing
        for (int l = 0, r = 0; r < s2.length() && balance > 0; r++) {
            // remove character from left and updated frequency counter and balance if needed
            if (r - l + 1 > s1.length()) {
                ch = s2.charAt(l++);
                if (freq1.containsKey(ch) && freq2.compute(ch, (k, v) -> v - 1) < freq1.get(ch)) {
                    balance++;
                }
            }
            // add character to right and updated frequency counter and balance if needed
            ch = s2.charAt(r);
            if (freq1.containsKey(ch) && freq2.merge(ch, 1, Integer::sum) <= freq1.get(ch)) {
                balance--;
            }
        }
        return balance == 0;
    }

    public static void main(String[] args) {
        System.out.println(new PermutationInString().checkInclusion("ab", "eidbaooo"));
        System.out.println(new PermutationInString().checkInclusion("abe", "eidbaooo"));
    }
}
