package alg.array;

import java.util.ArrayDeque;
import java.util.Deque;

/**
 * Given an array of integers heights representing the histogram's bar height where the width of each bar is 1, return
 * the area of the largest rectangle in the histogram.
 *
 * Example: for [2,1,5,6,2,3] the max area is 10 (bar 5 and 6).
 *
 * Brute force solution takes O(n^2).
 * It goes through all bars and for each one, finds area between current bar and each previous bar.
 *
 * Optimal solution requires O(n) time/space.
 * It uses monotonic increasing stack with initial value -1 to mark the end.
 * We start with the leftmost bar and keep pushing the current bar's index onto the stack as long as values create
 * increasing sequence.
 * If the current value not greater than top of the stack, we start popping the numbers until we restore stack
 * invariant.
 * Every time we pop, we calculate the area of rectangle formed between pop-ed value/index current index - 1.
 */
public class LargestRectangleInHistogram {
    public int largestRectangleArea(int[] heights) {
        Deque<Integer> stack = new ArrayDeque<>();
        stack.push(-1);
        int length = heights.length;
        int maxArea = 0;
        for (int i = 0; i < length; i++) {
            while ((stack.peek() != -1)
                && (heights[stack.peek()] >= heights[i])) {
                int currentHeight = heights[stack.pop()];
                int currentWidth = i - stack.peek() - 1;
                maxArea = Math.max(maxArea, currentHeight * currentWidth);
            }
            stack.push(i);
        }
        while (stack.peek() != -1) {
            int currentHeight = heights[stack.pop()];
            int currentWidth = length - stack.peek() - 1;
            maxArea = Math.max(maxArea, currentHeight * currentWidth);
        }
        return maxArea;
    }

    public static void main(String[] args) {
        System.out.println(new LargestRectangleInHistogram().largestRectangleArea(new int[]{2, 1, 5, 6, 2, 3}));
    }
}

