package alg.tree;

import java.util.LinkedList;
import java.util.List;

/**
 * Given an integer n, return all the structurally unique binary search trees. Each has exactly n nodes of
 * unique values from 1 to n.
 *
 * Example:
 *
 * Input: n = 3
 * Output: [[1,null,2,null,3],[1,null,3,2],[2,1,3],[3,1,null,null,2],[3,2,null,1]]
 *
 * Solution is based on recursion. For each value i in lo,..., i - 1, i, i + 1, ..., hi it generates left (lo, ..., i
 * - 1) and right (i + 1, ..., hi) subtrees and creates all possible subtrees based on them.
 * Time/Space complexity is calculated as Catalan number - O((4^n)/n^(1/2)).
 */
public class UniqueBSTGenerator {
    private final static TreeNode EMPTY_NODE = new TreeNode(0);

    public List<TreeNode> generateTrees(int n) {
        return generate(1, n);
    }

    private List<TreeNode> generate(int lo, int hi) {
        if (lo > hi) {
            return List.of(EMPTY_NODE);
        }
        List<TreeNode> trees = new LinkedList<>();
        for (int i = lo; i <= hi; i++) {
            List<TreeNode> leftTrees = generate(lo, i - 1);
            List<TreeNode> rightTrees = generate(i + 1, hi);
            for (TreeNode left : leftTrees) {
                for (TreeNode right : rightTrees) {
                    trees.add(new TreeNode(i, left == EMPTY_NODE ? null : left, right == EMPTY_NODE ? null : right));
                }
            }
        }
        return trees;
    }

    public static void main(String[] args) {
        new UniqueBSTGenerator().generateTrees(3);
    }
}
