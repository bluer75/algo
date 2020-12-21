package alg.array;

import java.util.ArrayDeque;
import java.util.Arrays;
import java.util.Deque;

/**
 * Given a circular array (the next element of the last element is the first element of the array), 
 * print the Next Greater Number for every element. The Next Greater Number of a number x is the first 
 * greater number to its traversing-order next in the array, which means you could search circularly 
 * to find its next greater number. 
 * If it doesn't exist, output -1 for this number.
 * 
 * Example:
 * Input: [1,2,1]
 * Output: [2,-1,2]
 * Explanation: The first 1's next greater number is 2; 
 * The number 2 can't find next greater number; 
 * The second 1's next greater number needs to search circularly, which is also 2.
 * 
 * Solution requires two passes due to the fact that array is circular.
 * On the stack are indices indicating values in descending order. Once we process greater value, all smaller values/indices 
 * can be removed from the stack and new value/index is added on top. 
 * Time/space is O(n).
 */
public class NextGreaterElement {

    public int[] nextGreaterElements(int[] nums) {
        int[] res = new int[nums.length];
        Arrays.fill(res, -1);
        Deque<Integer> stack = new ArrayDeque<>(nums.length * 2);
        int len = nums.length;
        for (int i = 0; i < len * 2; i++) {
            while (!stack.isEmpty() && nums[stack.peek() % len] < nums[i % len]) {
                res[stack.pop() % len] = nums[i % len];
            }
            stack.push(i % len);
        }
        return res;
    }

    public static void main(String... args) {
        System.out.println(Arrays.toString(new NextGreaterElement().nextGreaterElements(new int[] { 1, 2, 1 })));
    }
}
