package alg;

import java.util.Arrays;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import java.util.stream.IntStream;
import static java.util.stream.Collectors.counting;

/**
 * Given a word and a string str, find all starting indices in str which are anagrams of word.
 * For example, given that word is "ab", and S is "abxaba", return 0, 3, and 4.
 */
public class AnagramIndices {
    /**
     * Brute-force approach.
     * Processes input string by one character. 
     * In each step it checks if next (length of word) characters are anagram of word.
     * This takes time proportional to size of word and input string O(W*S)
     */
    static int[] findNaive(String word, String str) {
        if (word.length() > str.length()) {
            return new int[0];
        }
        List<Integer> res = new LinkedList<>();
        for (int i = 0; i < str.length() - word.length() + 1; i++) {
            if (isAnagram(word, str.substring(i, i + word.length()))) {
                res.add(i);
            }
        }
        return res.stream().mapToInt(i -> i).toArray();
    }

    /**
     * It checks if s1 is anagram of s2.
     * Use PermutationGenerator instead of counting char frequencies each time.
     */
    private static boolean isAnagram(String s1, String s2) {
        if (s1.length() != s2.length()) {
            return false;
        }
        // collect frequency of characters in s1 and s2
        Map<Integer, Long> frq1 = s1.chars().boxed().collect(Collectors.groupingBy(Integer::intValue, counting()));
        Map<Integer, Long> frq2 = s2.chars().boxed().collect(Collectors.groupingBy(Integer::intValue, counting()));
        return frq1.equals(frq2);
    }

    /**
     * Instead of calculating frequencies of characters for each character in input string,
     * it uses increase/decrease function for each character added/removed to the sliding window and 
     * compares frequency to frequency of characters from word.
     * Similar idea to rolling hashes in Rabin Karp Algorithm.
     * This takes time proportional to size of input string O(S)
     */
    static int[] find(String word, String str) {
        if (word.length() > str.length()) {
            return new int[0];
        }
        List<Integer> res = new LinkedList<>();
        // frequency for word
        Map<Integer, Integer> frqWord = new HashMap<>(word.length());
        word.chars().forEach(i -> increment(frqWord, i));
        // frequency for current window (size of length of word)
        Map<Integer, Integer> frqWnd = new HashMap<>(word.length());
        IntStream.range(0, word.length()).forEach(i -> increment(frqWnd, str.charAt(i)));
        // keep sliding the window through input string by one character
        // if frequencies of word and current window match each other we have found a match
        // each time add/increase character from current window and
        // remove/decrease last character from previous window
        for (int i = word.length(); i < str.length(); i++) {
            // check previous window
            if (frqWord.equals(frqWnd)) {
                res.add(i - word.length());
            }
            // add character to current window
            increment(frqWnd, str.charAt(i));
            // remove last character from previous window
            decrement(frqWnd, str.charAt(i - word.length()));
        }
        // final check for last window
        if (frqWord.equals(frqWnd)) {
            res.add(str.length() - word.length());
        }
        return res.stream().mapToInt(i -> i).toArray();
    }

    private static void increment(Map<Integer, Integer> frq, int ch) {
        frq.merge(ch, 1, (i, j) -> i + 1);
    }

    private static void decrement(Map<Integer, Integer> frq, int ch) {
        // remove entry if counter is 0 so we can use equals to verify if we have a match
        frq.merge(ch, 1, (i, j) -> i - 1 == 0 ? null : i - 1);
    }

    public static void main(String... args) {
        String word = "ab";
        String str = "abxaba";

        print(findNaive(word, str));
        print(find(word, str));
    }

    private static void print(int[] arr) {
        System.out.println(Arrays.toString(arr));
    }
}