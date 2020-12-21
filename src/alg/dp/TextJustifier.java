package alg.dp;

import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;
import java.util.stream.Collectors;

/**
 * Write an algorithm to justify text. 
 * Given a sequence of words and an integer line width, return strings which represents each line.
 */
public class TextJustifier {

    /**
     * Recursive approach finds best solution trying all combinations.
     * Time complexity is O(n^2).
     */
    static String[] justify(String[] words, int width) {
        int[] lineEndIdx = new int[words.length]; // lineEndIdx[i] - where ends line starting at index i
        justify(words, width, 0, lineEndIdx);
        List<String> text = new LinkedList<>();
        int startIdx = 0;
        int endIdx = 0;
        // use stored indices to construct justified text
        while (startIdx < words.length) {
            endIdx = lineEndIdx[startIdx];
            text.add(getLine(startIdx, endIdx, words));
            startIdx = endIdx;
        }
        return text.stream().toArray(String[]::new);
    }

    /**
     * Finds best solution for line starting with word at index i.
     * It recursively tries all combinations starting from that word.
     * For each combination it calculates the badness and chooses the one with minimum value. 
     */
    static int justify(String[] words, int width, int i, int[] lineEndIdx) {
        if (i == words.length) {
            // base case
            return 0;
        }
        int min = Integer.MAX_VALUE;
        int value;
        for (int j = i + 1; j <= words.length; j++) {
            value = badness(i, j, words, width) + justify(words, width, j, lineEndIdx);
            if (value < min) {
                min = value;
                lineEndIdx[i] = j;
            }
        }
        return min;
    }

    /**
     * Calculates badness (opposite of good fit) for given line/words.
     */
    static int badness(int from, int to, String[] words, int width) {
        int totalLength = 0;
        for (int i = from; i < to; i++) {
            totalLength += words[i].length();
        }
        totalLength += to - from - 1;
        if (totalLength > width) {
            return Integer.MAX_VALUE;
        }
        return (int) Math.pow(width - totalLength, 3);
    }

    /**
     * Creates line from words within given range and puts spaces to separate them.
     * It puts just one space as delimiter - if full justification is required it can be done here. 
     */
    private static String getLine(int startIdx, int endIdx, String[] words) {
        return Arrays.stream(words).skip(startIdx).limit(endIdx - startIdx).collect(Collectors.joining(" "));
    }

    public static void main(String... args) {
        String[] words = { "bla1", "bla2", "bla3", "bla4", "reallylongword" };
        int width = 14;
        String[] text = justify(words, width);
        for (String line : text) {
            System.out.println(line);
        }
    }
}
