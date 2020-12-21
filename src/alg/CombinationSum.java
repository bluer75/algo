package alg;

import java.util.LinkedList;
import java.util.List;

/**
 * Given a set of unique candidate numbers and a target value, find all unique combinations of candidates 
 * with sum equals target. Each candidate may be chosen many times.
 * 
 * Note:
 * all candidates and target value are positive integers.
 * The solution set must not contain duplicate combinations.
 * Example:
 * candidates = [2,3,6,7], target = 7,
 * combinations: [[7], [2,2,3]]
 * 
 * Solution is checking recursively (with backtracking) all possible combinations till we either get the value greater
 * than target or equals to target.
 * 
 * This takes O(n^k). Where n is the number of candidates and k is the target value.
 * 
 * Bottom-Up approach similar to "unbounded Knapsack" reduces time to O(n * k). 
 */
public class CombinationSum {
    public List<List<Integer>> combinationSum(int[] candidates, int target) {
        if (candidates == null || candidates.length == 0) {
            return List.of();
        }
        List<List<Integer>> combinations = new LinkedList<>();
        generate(candidates, new LinkedList<Integer>(), 0, combinations, target);
        return combinations;
    }

    private void generate(int[] candidates, LinkedList<Integer> selected, int pos, List<List<Integer>> combinations,
            int left) {
        if (left < 0) {
            return;
        }
        if (left == 0) {
            combinations.add(new LinkedList<>(selected));
            return;
        }
        for (int i = pos; i < candidates.length; i++) {
            selected.addLast(candidates[i]);
            generate(candidates, selected, i, combinations, left - candidates[i]);
            selected.removeLast();
        }
    }

    public List<List<Integer>> combinationSumDP(int[] candidates, int target) {
        if (candidates == null || candidates.length == 0) {
            return List.of();
        }
        List<List<Integer>> combinations = new LinkedList<>();
        // TODO implement
        return combinations;
    }

    public static void main(String... args) {
        System.out.println(new CombinationSum().combinationSum(new int[] { 2, 3, 6, 7 }, 7));
        System.out.println(new CombinationSum().combinationSumDP(new int[] { 2, 3, 6, 7 }, 7));
    }
}
