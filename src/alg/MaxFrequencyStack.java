package alg;

import java.util.Deque;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.Map;

/**
 * Design a stack-like data structure to push elements to the stack and pop the most frequent element from the stack.
 *
 * void push(int val) pushes an integer val onto the top of the stack.
 * int pop() removes and returns the most frequent element in the stack.
 * If there is a tie for the most frequent element, the element closest to the stack's top is removed and returned.
 *
 * Solution is based on two maps where we store:
 * - value to occurrences mapping
 * - occurrence to stack mapping
 *
 * Each time we push the value, we increase its occurrence and either create new stack for that occurrence or add the
 * value to existing stack. Each time global max occurrence is updated, so we know from which stack we should pop from.
 *
 * Pop operation always uses stack with current max occurrence and pops the value from there. If stack is empty max
 * occurrence is decreased.
 *
 * push() and pop() have O(1) time complexity.
 */
public class MaxFrequencyStack {
    private Map<Integer, Integer> counts; // value -> occurrences
    private Map<Integer, Deque<Integer>> freqs; // occurrence -> stack
    private int maxFreq;

    public MaxFrequencyStack() {
        counts = new HashMap<>();
        freqs = new HashMap<>();
        maxFreq = 0;
    }

    public void push(int val) {
        int count = counts.merge(val, 1, Integer::sum);
        maxFreq = Math.max(maxFreq, count);
        freqs.computeIfAbsent(count, v -> new LinkedList<>()).push(val);
    }

    public int pop() {
        int max = freqs.get(maxFreq).pop();
        counts.compute(max, (k, v) -> v == 1 ? null : v - 1);
        if (freqs.get(maxFreq).isEmpty()) {
            maxFreq--;
        }
        return max;
    }

    public static void main(String[] args) {
        MaxFrequencyStack fs = new MaxFrequencyStack();
        fs.push(1);
        fs.push(2);
        fs.push(1);
        System.out.println(fs.pop());
        System.out.println(fs.pop());
        System.out.println(fs.pop());
    }
}
