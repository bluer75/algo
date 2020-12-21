package alg;

import java.util.LinkedList;
import java.util.List;

/**
 * Given a string containing digits from 2-9 inclusive, return all possible letter combinations that the number could represent.
 * A mapping of digit to letters (just like on the telephone buttons) is given below. Note that 1 does not map to any letters.
 * 
 * Example:
 * Input: "23"
 * Output: ["ad", "ae", "af", "bd", "be", "bf", "cd", "ce", "cf"].
 * 
 * Solution is recursively (with backtracking) generating all possible combinations.
 * This takes O(3^n * 4^m) time and space - m [7, 9] n - [2 - 6, 8].. 
 */
public class StringsFromPhoneNumber {
    public List<String> letterCombinations(String digits) {
        List<String> results = new LinkedList<>();
        generate(digits, new StringBuilder(), 0, results);
        return results;
    }

    private void generate(String digits, StringBuilder prefix, int i, List<String> combinations) {
        if (i == digits.length()) {
            combinations.add(prefix.toString());
            return;
        }
        int digit = 9 - ('9' - digits.charAt(i));
        generate(digits, prefix.append(getChar(digit, 0)), i + 1, combinations);
        prefix.deleteCharAt(i);
        generate(digits, prefix.append(getChar(digit, 1)), i + 1, combinations);
        prefix.deleteCharAt(i);
        generate(digits, prefix.append(getChar(digit, 2)), i + 1, combinations);
        prefix.deleteCharAt(i);
        if (digit == 7 || digit == 9) {
            generate(digits, prefix.append(getChar(digit, 3)), i + 1, combinations);
            prefix.deleteCharAt(i);
        }
    }

    private char getChar(int digit, int pos) {
        if (digit < 8) {
            return (char) ('a' + pos + 3 * (digit - 2));
        }
        return (char) ('a' + pos + 1 + 3 * (digit - 2));
    }

    public static void main(String... args) {
        System.out.println(new StringsFromPhoneNumber().letterCombinations("23"));
    }
}
