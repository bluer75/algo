package alg;

import java.util.Arrays;
import java.util.Comparator;
import java.util.PriorityQueue;
import java.util.Queue;

/**
 * Given an array of strings words, return the smallest string that contains each string in words as a substring. If
 * there are multiple valid strings of the smallest length, return any of them.
 *
 * You may assume that no string in words is a substring of another string in words.
 *
 * Example ["catg","ctaagt","gcta","ttca","atgcatc"] -> "gctaagttcatgcatc".
 *
 * We calculate overlap between each pair of words and put that on max heap.
 * We start with pair - first second - with biggest overlap and greedily select next word with max overlap with second
 * one. In case no word overlaps with current one, take the max overlap from heap.
 * It continues as long as there are unused words.
 *
 * Space complexity: O(n^2) - we store just indices to input words
 *
 * NOTE - this may not return the optimal string
 * "gctaagttcatgcatc" vs "catgcatctaagttcagcta"
 */
public class ShortestSuperString {
    public String shortestSuperstring(String[] words) {
        int n = words.length;
        if (n == 1) {
            return words[0];
        }
        StringBuilder result = new StringBuilder();
        int[][] overlap = new int[n][n];
        Comparator<int[]> cmp = Comparator.comparingInt(ids -> overlap[ids[0]][ids[1]]);
        Queue<int[]> maxHeap = new PriorityQueue<>(cmp.reversed());
        boolean[] used = new boolean[n];
        int max = 0;
        int firstId = 0, secondId = 1;
        for (int first = 0; first < n; first++) {
            for (int second = 0; second < n; second++) {
                if (first != second) {
                    overlap[first][second] = findOverlap(words[first], words[second]);
                    if (overlap[first][second] > max) {
                        max = overlap[first][second];
                        firstId = first;
                        secondId = second;
                    }
                    maxHeap.offer(new int[]{first, second});
                }
            }
        }
        result.append(words[firstId]).append(words[secondId].substring(overlap[firstId][secondId]));
        used[firstId] = true;
        used[secondId] = true;
        int usedCount = 2;
        while (usedCount < n) {
            firstId = secondId;
            secondId = -1;
            max = -1;
            // find max unused overlap for firstId
            for (int id = 0; id < n; id++) {
                if (!used[id]) {
                    if (overlap[firstId][id] > max) {
                        max = overlap[firstId][id];
                        secondId = id;
                    }
                }
            }
            // if not found take max overlap for any unused word
            while (secondId == -1 || used[secondId]) {
                secondId = maxHeap.poll()[1];
            }
            result.append(words[secondId].substring(overlap[firstId][secondId]));
            used[secondId] = true;
            usedCount++;
        }
        return result.toString();
    }

    private int findOverlap(String a, String b) {
        int aid = 0, bid = 0, count = 0, len = Math.min(a.length(), b.length()) - 1;
        while (len > 0) {
            aid = a.length() - len;
            bid = 0;
            count = len;
            while (count > 0 && a.charAt(aid) == b.charAt(bid)) {
                aid++;
                bid++;
                count--;
            }
            if (aid == a.length()) {
                break;
            }
            len--;
        }
        return len;
    }

    public static void main(String... args) {
        String[] words = new String[]{"catg", "ctaagt", "gcta", "ttca", "atgcatc"};
        System.out.println(new ShortestSuperString().shortestSuperstring(words));
    }
}