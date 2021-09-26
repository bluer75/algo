package alg.string;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

/**
 * A gene is represented as a string of length n (where n is divisible by 4), composed of the letters A, C, G, and T. It
 * is considered to be steady if each of the four letters occurs exactly n/4 times.
 *
 * For example, GACT and AAGTGCCT are both steady genes.
 *
 * Write a function that returns an integer representing the length of the smallest substring to replace.
 *
 * For example for string GAAATAAA it should return 5 as the optimal solution is to replace AAATA with TTCCG.
 *
 * Solution requires O(n) time and is based on sliding window finding the smallest substring with all characters having
 * to many occurrences.
 */
public class SteadySequence {

    public int findMin(String gene) {
        int target = gene.length() / 4;
        Map<Character, Integer> diff = new HashMap<>();
        int min = gene.length();
        for (int i = 0; i < gene.length(); i++) {
            diff.merge(gene.charAt(i), 1, Integer::sum);
        }
        diff.entrySet().removeIf(e -> e.getValue() <= target);
        diff.entrySet().forEach(e -> e.setValue(e.getValue() - target));
        if (diff.isEmpty()) {
            return 0;
        }
        Map<Character, Integer> counts = new HashMap<>();
        for (int l = 0, r = 0; r < gene.length(); r++) {
            if (diff.containsKey(gene.charAt(r))) {
                counts.merge(gene.charAt(r), 1, Integer::sum);
            }
            while (diff.entrySet().stream()
                    .allMatch(e -> counts.containsKey(e.getKey()) && counts.get(e.getKey()) >= e.getValue())) {
                min = Math.min(min, r - l + 1);
                counts.computeIfPresent(gene.charAt(l), (k, v) -> v == 1 ? null : v - 1);
                l++;
            }
        }
        return min;
    }

    public static void main(String[] args) throws IOException {
        System.out.println(new SteadySequence().findMin("GAAATAAA"));
    }
}
