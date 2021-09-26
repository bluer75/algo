package alg.string;

import java.util.Comparator;
import java.util.Map;
import java.util.function.Function;
import java.util.stream.Collectors;

/**
 * Given a string s, sort it in decreasing order based on the frequency of characters, and return the sorted string.
 *
 * Solution builds frequency map character->count and sorts it descending by values.
 */
public class FrequencySort {
    public String frequencySort(String s) {
        Map<Character, Long> freq = s.chars().mapToObj(i -> (char) i).
            collect(Collectors.groupingBy(Function.identity(), Collectors.counting()));
        StringBuilder sb = new StringBuilder();
        Comparator<Map.Entry<Character, Long>> cmp = Comparator.comparingLong(Map.Entry::getValue);
        freq.entrySet().stream().sorted(cmp.reversed()).forEach(e -> {
            for (long i = e.getValue(); i > 0; i--) {
                sb.append(e.getKey());
            }
        });
        return sb.toString();
    }

    public static void main(String... args) {
        System.out.println(new FrequencySort().frequencySort("tree"));
    }
}
