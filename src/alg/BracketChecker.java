package alg;

import java.util.Arrays;
import java.util.Deque;
import java.util.LinkedList;

/**
 * Given a string of round, curly, and square open and closing brackets, return whether the brackets are balanced (well-formed).
 */
public class BracketChecker {
    private static boolean check(String str) {
        Deque<Character> stack = new LinkedList<>();
        for (char ch : str.toCharArray()) {
            Bracket b = Bracket.valueOf(ch);
            if (b.isOpening(ch)) {
                stack.push(ch);
            } else {
                b.isCLosing(ch);
                if (stack.isEmpty() || stack.pop() != b.opening) {
                    return false;
                }
            }
        }
        return stack.isEmpty();
    }

    private enum Bracket {
        ROUND('(', ')'), CURLY('{', '}'), SQUARE('[', ']');

        private char opening, closing;

        Bracket(char opening, char closing) {
            this.opening = opening;
            this.closing = closing;
        }

        boolean isOpening(char ch) {
            return opening == ch;
        }

        boolean isCLosing(char ch) {
            return closing == ch;
        }

        static Bracket valueOf(char ch) {
            for (Bracket b : values()) {
                if (b.opening == ch || b.closing == ch) {
                    return b;
                }
            }
            throw new IllegalArgumentException("invalid bracket character " + ch);
        }

    }

    public static void main(String... args) {
        String[] str = { "([])[]({})", "([)]", "((()" };
        Arrays.stream(str).forEach(s -> System.out.println(s + " " + check(s)));
    }
}
