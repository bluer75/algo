package alg.string;

import java.util.HashMap;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Set;

/**
 * Given a non-empty string s and a dictionary wordDict containing a list of non-empty words, 
 * add spaces in s to construct a sentence where each word is a valid dictionary word. 
 * Return all such possible sentences.
 * 
 * Note:
 * 
 * The same word in the dictionary may be reused multiple times in the segmentation.
 * You may assume the dictionary does not contain duplicate words.
 * Example:
 * 
 * Input:
 * s = "catsanddog"
 * wordDict = ["cat", "cats", "and", "sand", "dog"]
 * Output:
 * [
 * "cats and dog",
 *   "cat sand dog"
 * ]
 * 
 * Solution uses DSF/backtracking with memoization to avoid re-processing the same suffixes
 *              0: catsanddog
 *                /        \
 *           cat /          \ cats
 *              /            \
 *         3: sanddog     4: anddog
 *            /                \
 *      sand /                  \ and
 *          /                    \
 *       7: dog                7: dog
 * 
 * If n is the length of input string and w is the number of words in dictionary. 
 * Time complexity is O(n^2 + 2^n + w)
 * Space complexity is O(2^n * n + w^2) - this can be further optimized it we store indices instead of strings. 
 */
public class WordBreak {
    // valid words
    private Set<String> words = new HashSet<>();
    // valid prefixes
    private Set<String> prefixes = new HashSet<>();
    // cache, position -> valid (sub)sentences from that position
    private Map<Integer, List<String>> sentences = new HashMap<>();

    public List<String> wordBreak(String s, List<String> wordDict) {
        if (s == null || s.isEmpty() || wordDict == null || wordDict.isEmpty()) {
            return List.of();
        }
        // build set of valid words and prefixes
        for (String word : wordDict) {
            words.add(word);
            StringBuilder prefix = new StringBuilder();
            for (int i = 0; i < word.length() - 1; i++) {
                prefix.append(word.charAt(i));
                prefixes.add(prefix.toString());
            }
        }
        find(s, 0);
        return sentences.get(0) == null ? List.of() : sentences.get(0);
    }

    private List<String> find(String s, int pos) {
        // base case, no more words, sentence found
        if (pos == s.length()) {
            return List.of();
        }
        // check if this position was already analyzed 
        if (sentences.containsKey(pos)) {
            return sentences.get(pos);
        }
        StringBuilder prefix = new StringBuilder();
        String word = null;
        for (int i = pos; i < s.length(); i++) {
            prefix.append(s.charAt(i));
            word = prefix.toString();
            if (words.contains(word)) {
                // found valid word, check if it forms valid sentence
                List<String> subSentences = find(s, i + 1);
                if (subSentences != null) {
                    sentences.putIfAbsent(pos, new LinkedList<String>());
                    if (subSentences.isEmpty()) {
                        // last word in sentence
                        sentences.get(pos).add(word);
                    } else {
                        // append new word to existing sub-sentences
                        for (String subSentence : subSentences) {
                            sentences.get(pos).add(word + " " + subSentence);
                        }
                    }
                }
            }
            if (!prefixes.contains(word)) {
                // dead-end, mark as invalid and prune
                sentences.putIfAbsent(pos, null);
                break;
            }
        }
        return sentences.get(pos); // returns null if nothing found nor valid sentence can be created
    }

    public static void main(String... args) {
        //        List<String> wordDict = List.of("cat", "cats", "and", "sand", "dog");
        //        String s = "catsanddog";
        List<String> wordDict = List.of("a", "aa", "aaa", "aaaa");
        String s = "aaaa";

        System.out.println(new WordBreak().wordBreak(s, wordDict));
    }
}
