package alg;

import java.util.LinkedList;
import java.util.List;

/**
 * It generates all possible substrings for given string.
 * There are n + (n-1) + (n-2)... +1 substrings -> 1 character substrings + 2 character substrings ... 
 * In total n(n+1)/2 
 * Running time is O(n^2)
 */
public class SubstringGenerator {

    static List<String> generate(String s) {
        List<String> ss = new LinkedList<>();
        for (int i = 0; i < s.length(); i++) {
            for (int j = i + 1; j <= s.length(); j++) {
                ss.add(s.substring(i, j));
            }
        }
        return ss;
    }

    public static void main(String... args) {
        System.out.println(generate("abcd"));
    }
}
