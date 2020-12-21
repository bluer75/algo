package alg.string;

import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

/**
 * DNA is composed of a series of nucleotides abbreviated as 'A', 'C', 'G', 'T',
 * Write a function to find all the 10-letter-long sequences (substrings) that occur 
 * more than once in a DNA molecule.
 * Example 1:
 * 
 * Input: s = "AAAAACCCCCAAAAACCCCCCAAAAAGGGTTT"
 * Output: ["AAAAACCCCC","CCCCCAAAAA"]
 * Example 2:
 * 
 * Input: s = "AAAAAAAAAAAAA"
 * Output: ["AAAAAAAAAA"]
 * 
 * Solution takes O(n + m) time and uses Rabin-Karp algorithm with rolling hashes:
 * - m pattern length
 * - R alphabet size
 * - RM R^(m-1)
 * - hash(w[0 .. m-1]) = w[0] * R^(m-1) + w[1] * R^(m-2) +···+ w[m-1] * R^0
 * - rehash(h, a, b) = (h - a * R^(m-1)) * R + b
 */
public class RepeatedDNASequences {
    public List<String> findRepeatedDnaSequences(String s) {
        if (s == null || s.length() < 11) {
            return List.of();
        }
        Set<String> res = new HashSet<>();
        Set<Integer> hashes = new HashSet<>();
        int m = 10; // pattern size
        int r = 4; // alphabet size
        int hash = 0;
        Map<Character, Integer> values = Map.of('A', 0, 'C', 1, 'G', 2, 'T', 3);
        int rm = 1; // r^(m-1)
        for (int i = 1; i < m; i++) {
            rm = r * rm;
        }
        // hash(w[0 .. m-1]) = w[0] * R^(m-1) + w[1] * R^(m-2) +···+ w[m-1] * R^0
        for (int i = 0; i < m; i++) {
            hash = hash * r + values.get(s.charAt(i));
        }
        hashes.add(hash);
        for (int i = 1; i <= s.length() - m; i++) {
            // rehash(h, a, b) = (h - a * R^(m-1)) * R + b
            hash = (hash - values.get(s.charAt(i - 1)) * rm) * r + values.get(s.charAt(i + m - 1));
            if (!hashes.add(hash)) {
                res.add(s.substring(i, i + m));
            }
        }
        return res.stream().collect(Collectors.toList());
    }

    public static void main(String... args) {
        System.out.println(new RepeatedDNASequences().findRepeatedDnaSequences("AAAAACCCCCAAAAACCCCCCAAAAAGGGTTT"));
        System.out.println(new RepeatedDNASequences().findRepeatedDnaSequences("AAAAAAAAAAAAA"));
    }
}
