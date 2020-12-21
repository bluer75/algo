package alg.fb;

import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.PriorityQueue;
import java.util.Queue;
import java.util.Set;

/**
 * Given many coins of 3 different face values, print the combination sums of the coins up to 1000. Must be printed in order. 
 * 
 * example: coins(10, 15, 55) 
 * 10 (10) 
 * 15 (15)
 * 20 (2 * 10)
 * 25 (1* 10 + 1 * 15)
 * 30 (2 * 15 or 3 * 10)
 * .... 
 * 1000
 */
public class CombineCoins {
    /**
     * Calculate value from equation x * coin1 + y * coin2 + z * coin3.
     * Where x ,y, z goes from 0 to 1000 / x, y, z. 
     * As values they may appear out of order we sort them using heap.
     * O(n log n). 
     */
    public List<Integer> findSequenceEq(int coin1, int coin2, int coin3) {
        int max = 1000;
        int maxx = 1000 / coin1;
        int maxy = 1000 / coin2;
        int maxz = 1000 / coin3;
        int value = 0;
        Queue<Integer> heap = new PriorityQueue<>();
        List<Integer> result = new LinkedList<>();
        for (int x = 0; x <= maxx; x++) {
            for (int y = 0; y <= maxy; y++) {
                for (int z = 0; z <= maxz; z++) {
                    value = x * coin1 + y * coin2 + z * coin3;
                    if (value <= max) {
                        heap.offer(value);
                    }
                }
            }
        }
        value = 0;
        int lastValue = 0;
        while (!heap.isEmpty()) {
            value = heap.poll();
            if (value != lastValue) {
                result.add(value);
            }
            lastValue = value;
        }
        return result;
    }

    /**
     * Enumerate all values from 1 to 1000 and check if they can be created by adding any coin to previous vales.
     * O(n).
     */
    public List<Integer> findSequenceDP(int coin1, int coin2, int coin3) {
        List<Integer> result = new LinkedList<>();
        Set<Integer> nums = new HashSet<>();
        nums.add(0);
        for (int i = 1; i <= 1000; i++) {
            if (nums.contains(i - coin1) || nums.contains(i - coin2) || nums.contains(i - coin3)) {
                nums.add(i);
                result.add(i);
            }
        }
        return result;
    }

    public static void main(String... args) {
        System.out.println(new CombineCoins().findSequenceEq(10, 15, 55));
        System.out.println(new CombineCoins().findSequenceDP(10, 15, 55));
    }
}
