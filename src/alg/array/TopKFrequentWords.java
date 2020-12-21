package alg.array;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.PriorityQueue;

/**
 * Given a non-empty list of words, return the k most frequent elements.
 * The answer should be sorted by frequency from highest to lowest. 
 * If two words have the same frequency, then the word with the lower alphabetical order comes first.
 * Example:
 * 
 * Input: ["the", "day", "is", "sunny", "the", "the", "the", "sunny", "is", "is"], k = 4
 * Output: ["is", "the", "sunny", "day"]
 * Explanation: "is" and "the" have the same frequency but "is" comes before "the" alphabetically.
 * 
 * Solution counts first the frequencies and uses min heap to store k words.
 * It takes O(n log k) time and O(n) space for map and heap.
 */
public class TopKFrequentWords {
    public List<String> topKFrequent(String[] words, int k) {
        if (words == null || words.length < k) {
            return List.of();
        }
        List<String> result = new ArrayList<>(k);
        Map<String, int[]> frequencies = new HashMap<>();
        for (int i = 0; i < words.length; i++) {
            frequencies.merge(words[i], new int[] { i, 1 }, (a, b) -> {
                a[1]++;
                return a;
            });
        }
        int[][] wordInfos = frequencies.values().toArray(new int[frequencies.size()][]);
        PriorityQueue<int[]> minHeap = new PriorityQueue<>(Comparator.<int[]>comparingInt(a -> a[1])
                .thenComparing(Comparator.<int[], String>comparing(a -> words[a[0]]).reversed()));
        for (int[] wordInfo : frequencies.values()) {
            if (minHeap.size() < k) {
                minHeap.offer(wordInfo);
            } else {
                if (minHeap.comparator().compare(minHeap.peek(), wordInfo) < 0) {
                    minHeap.poll();
                    minHeap.offer(wordInfo);
                }
            }
        }
        while (!minHeap.isEmpty()) {
            result.add(0, words[minHeap.poll()[0]]);
        }
        return result;
    }

    public static void main(String... args) {
        String[] words = { "the", "day", "is", "sunny", "the", "the", "sunny", "is", "is" };
        int k = 4;
        System.out.println(new TopKFrequentWords().topKFrequent(words, k));
    }
}
