package alg.array;

import java.util.ArrayDeque;
import java.util.Deque;

/**
 * Given an array of non-negative integers representing an elevation map, 
 * where each element is one unit width wall and the integer is the height. 
 * Suppose it will rain and all spots between two walls get filled up.
 * Compute how much water can be trapped after raining.
 */
public class TrappedWater {

    /**
     * Processes input array from both sides simultaneously. 
     * It keeps track on maximum heights on the left and right side.
     * Calculates trapped water for the side with lower height as it guarantees the water
     * doesn't overflow on the other side.
     * O(n) time and O(1) space.
     */
    public static int compute(int[] map) {
        int sum = 0;
        int l = 0;
        int r = map.length - 1;
        int maxL = 0;
        int maxR = 0;
        while (l <= r) {
            if (map[l] < map[r]) {
                if (map[l] > maxL) {
                    // found new max on the left - no water can be trapped here
                    maxL = map[l];
                } else {
                    // calculate trapped water between max on left and max on right
                    sum += maxL - map[l];
                }
                l++;
            } else {
                if (map[r] > maxR) {
                    // found new max on right side - no water can be trapped here
                    maxR = map[r];
                } else {
                    // calculate trapped water between max on left and max on right
                    sum += maxR - map[r];
                }
                r--;
            }
        }
        return sum;
    }

    /**
     * Solution using stack.
     * We iterate over the array and add current index of the bar to the stack if bar is smaller than or equal to the bar 
     * at top of stack (non-increasing values), which means that the current bar is bounded  by the previous bar on the stack. 
     * If current bar is greater than current top on stack, water is trapped there.  
     * We can pop all smaller/equal values from stack and compute trapped water using indices from stack and values from input.
     * O(n) time and space. 
     */
    public static int computeStack(int[] map) {
        int sum = 0;
        int current = 0;
        Deque<Integer> stack = new ArrayDeque<>(map.length);
        while (current < map.length) {
            while (!stack.isEmpty() && map[current] > map[stack.peek()]) {
                int top = stack.pop();
                if (stack.isEmpty())
                    break;
                int distance = current - stack.peek() - 1; // distance between bars
                int difference = Math.min(map[current], map[stack.peek()]) - map[top]; // height difference
                sum += distance * difference;
            }
            stack.push(current++);
        }
        return sum;
    }

    public static void main(String... args) {
        int[] map = { 0, 1, 0, 2, 1, 0, 1, 3, 2, 1, 2, 1 };
        System.out.println(compute(map));
        System.out.println(computeStack(map));
    }
}
