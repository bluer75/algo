package alg;

import java.util.LinkedList;
import java.util.List;

/**
 * Given n pairs of parentheses, write a function to generate all combinations of well-formed parentheses.
 * 
 * For example, given n = 3, a solution set is:
 * 
 * [
 *   "((()))",
 *   "(()())",
 *   "(())()",
 *   "()(())",
 *   "()()()"
 * ]
 * 
 * Solution is based on backtracking. To avoid generating all possible (including invalid) strings, 
 * we keep count open and close parentheses. 
 */
public class GenerateParentheses {
    public List<String> generateParenthesis(int n) {
        if (n == 1) {
            return List.of("()");
        }
        List<String> res = new LinkedList<>();
        generate(new char[n * 2], 0, 0, 0, res);
        return res;
    }

    private void generate(char[] str, int idx, int open, int close, List<String> res) {
        if (idx == str.length) {
            res.add(new String(str));
            return;
        }
        // there are two options for each position ( or )
        // ( can be used as long as we didn't hit the limit 
        if (open < str.length / 2) {
            str[idx] = '(';
            generate(str, idx + 1, open + 1, close, res);
        }
        // ) can be used only when there is non-closed opening parentheses
        if (close < open) {
            str[idx] = ')';
            generate(str, idx + 1, open, close + 1, res);
        }
    }

    public static void main(String... args) {
        System.out.println(new GenerateParentheses().generateParenthesis(3));
    }
}
