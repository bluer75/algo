package alg.tree;

import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

/**
 * Given an integer n, return a list of all possible full binary trees with n nodes.
 * A full binary tree is a binary tree where each node has exactly 0 or 2 children.
 *
 * Solution is based on recursion with memoization and requires O(2^n) time and space.
 * For given number n we generate recursively all possible left and right subtrees with 1 to n - 1 nodes and join
 * them together using all possible combinations.
 * For example if n = 7: following calls are used:
 * - generate(1) and generate(5)
 * - generate(2) and generate(4)
 * - generate(3) and generate(3)
 * - generate(4) and generate(2)
 * - generate(5) and generate(1)
 * generate(5) -> generate(1) and generate(3), generate(2) and generate(2), generate(3) and generate(1)
 * generate(4) -> empty list as full binary tree needs odd number of nodes
 * generate(3) -> generate(1) and generate(1)
 * generate(2) -> empty list as full binary tree needs odd number of nodes
 * generate(1) -> single node
 * Memoization allows to re-use already generated trees.
 */
public class GenerateAllFullBinaryTrees {
    private Map<Integer, List<TreeNode>> memo = new HashMap<>();

    public List<TreeNode> generate(int n) {
        if (n % 2 == 0) {
            return List.of();
        }
        if (n == 1) {
            return List.of(new TreeNode(0));
        }
        if (memo.containsKey(n)) {
            return memo.get(n);
        }
        List<TreeNode> trees = new LinkedList<>();
        for (int i = 1; i < n - 1; i++) {
            List<TreeNode> leftTrees = generate(i);
            List<TreeNode> rightTrees = generate(n - i - 1);
            for (TreeNode left : leftTrees) {
                for (TreeNode right : rightTrees) {
                    trees.add(new TreeNode(0, left, right));
                }
            }
        }
        memo.put(n, trees);
        return trees;
    }

    public static void main(String[] args) {
        new GenerateAllFullBinaryTrees().generate(7);
    }
}

