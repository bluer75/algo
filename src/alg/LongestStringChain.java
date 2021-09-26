package alg;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

/**
 * Given a list of words (english lowercase letters).
 *
 * Word1 is a predecessor of word2 if and only if we can add exactly one letter anywhere in word1 to make
 * it equal to word2. For example, "abc" is a predecessor of "abac".
 *
 * A word chain is a sequence of words [word_1, word_2, ..., word_k] with k >= 1, where word_1 is a predecessor of
 * word_2, word_2 is a predecessor of word_3, and so on.
 *
 * Return the longest possible length of a word chain with words chosen from the given list of words.
 *
 * Solution creates DAG where edges represent predecessor relation between words.
 * Then DFS algorithm allows to find the longest path for any vertex.
 *
 * Space complexity: O(n2) - where n is number of words.
 * Time complexity: O(n2) - where n is number of words
 */
public class LongestStringChain {
    public int longestStrChain(String[] words) {
        Map<Integer, Set<Integer>> dict = new HashMap();
        int len = 0;
        for (int i = 0; i < words.length; i++) {
            dict.putIfAbsent(len = words[i].length(), new HashSet<Integer>());
            dict.get(len).add(i);
        }
        Map<Integer, Set<Integer>> adj = new HashMap<>();
        for (int key : dict.keySet()) {
            for (int from : dict.get(key)) {
                for (int to : dict.getOrDefault(key + 1, Set.of())) {
                    if (isValid(words[from], words[to])) {
                        adj.putIfAbsent(from, new HashSet<Integer>());
                        adj.get(from).add(to);
                    }
                }
            }
        }
        return dfs(words.length, adj);
    }

    private boolean isValid(String from, String to) {
        int posFrom = from.length() - 1, posTo = to.length() - 1;
        while (posFrom >= 0) {
            if (from.charAt(posFrom) == to.charAt(posTo)) {
                posFrom--;
                posTo--;
            } else if (posFrom == posTo) {
                return false;
            } else {
                posTo--;
            }
        }
        return true;
    }

    private int dfs(int n, Map<Integer, Set<Integer>> adj) {
        int[] length = new int[n];
        int max = 1;
        for (int from : adj.keySet()) {
            if (length[from] == 0) {
                max = Math.max(max, dfs(from, adj, length));
            }
        }
        return max;
    }

    private int dfs(int from, Map<Integer, Set<Integer>> adj, int[] length) {
        if (length[from] > 0) {
            return length[from];
        }
        length[from] = 1;
        for (int to : adj.getOrDefault(from, Set.of())) {
            length[from] = Math.max(length[from], dfs(to, adj, length) + 1);
        }
        return length[from];
    }

    public static void main(String... args) {
        String[] words = new String[]{"a", "b", "ba", "bca", "bda", "bdca"};
        System.out.println(new LongestStringChain().longestStrChain(words));
    }
}
