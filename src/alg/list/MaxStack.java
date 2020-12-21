package alg.list;

import java.util.Deque;
import java.util.LinkedList;

/**
 * Implement Stack that supports getMax() method.
 */
public class MaxStack {

    private Deque<Integer> stack = new LinkedList<>();
    private Deque<Integer> max = new LinkedList<>();

    public void push(int item) {

        stack.push(item);
        // if new element is not smaller than current max add it to max structure
        if (max.isEmpty() || max.peek() <= item) {
            max.push(item);
        }
    }

    public int pop() {

        // if we remove max value remove it from max structure
        if (stack.peek() == max.peek()) {
            max.pop();
        }
        return stack.pop();
    }

    public int getMax() {

        return max.peek();
    }

    public static void main(String... args) {

        MaxStack ms = new MaxStack();
        ms.push(5);
        System.out.println(ms.getMax());
        ms.push(4);
        ms.push(7);
        ms.push(7);
        ms.push(8);
        System.out.println(ms.getMax());
    }
}
