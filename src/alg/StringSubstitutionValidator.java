package alg;

import java.util.ArrayDeque;
import java.util.Deque;

/**
 * We are given that the string "abc" is valid.
 * From any valid string V, we may split V into two pieces X and Y such that X + Y (X concatenated with Y) i
 * s equal to V, X or Y may be empty.  
 * Then, X + "abc" + Y is also valid.
 * 
 * Examples of valid strings: "abc", "aabcbc", "abcabc", "abcabcababcc".
 * Examples of invalid strings: "abccba", "ab", "cababc", "bac".
 * 
 * Write function to validate input string.
 * 
 * Solution processes each character and stores a and b on stack. For c it pops values from stack and validates them.
 * Because c is never on stack we can simplify the validation and detect easily invalid string.
 * This takes O(n) time and space.
 * If we operate directly on underlying array of characters using indices we can reduce space to O(1).
 */
public class StringSubstitutionValidator {
    public boolean isValid(String s) {
        Deque<Character> stack = new ArrayDeque<>(s.length());
        for (int i = 0; i < s.length(); i++) {
            switch (s.charAt(i)) {
                case 'a', 'b' -> stack.push(s.charAt(i));
                case 'c' -> {
                    if (stack.size() < 2 || stack.pop() != 'b' || stack.pop() != 'a') {
                        return false;
                    }
                }
            }
        }
        return stack.isEmpty();
    }

    public static void main(String... args) {
        System.out.println(new StringSubstitutionValidator().isValid("aabcbc"));
    }
}
