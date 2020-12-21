package alg.tree;

import java.util.ArrayList;
import java.util.Collections;
import java.util.LinkedList;
import java.util.List;
import java.util.Queue;

/**
 * Given a binary tree, return the level order traversal of its nodes' values. (ie, from left to right, level by level).
 * For example:
 * Given binary tree [3,9,20,null,null,15,7],
 * 
 *     3
 *    / \
 *   9  20
 *     /  \
 *    15   7
 * 
 * return its level order traversal as:
 * 
 * [
 *   [3],
 *   [9,20],
 *   [15,7]
 * ]
 * 
 * Tree is a DAG and we can apply BFS algorithm to visit nodes level by level. 
 * Complexity is O(n).
 * 
 * We keep track of number of nodes on current level. This can be implemented with 
 * internal for loop to consume all nodes from given level.
 */
public class LevelOrderTraversal {

    public List<List<Integer>> levelOrder(IntNode root) {
        if (root == null) {
            return Collections.emptyList();
        }
        List<List<Integer>> result = new ArrayList<>();
        Queue<IntNode> nodes = new LinkedList<>();
        List<Integer> level = new LinkedList<>();
        nodes.add(root);
        int count = 1;
        while (!nodes.isEmpty()) {
            IntNode node = nodes.remove();
            level.add(node.key);
            if (node.left != null) {
                nodes.add(node.left);
            }
            if (node.right != null) {
                nodes.add(node.right);
            }
            if (--count == 0) {
                result.add(level);
                level = new LinkedList<>();
                count = nodes.size();
            }
        }
        return result;
    }

    public static void main(String... args) {
        IntNode root = IntNode.of(3);
        root.left = IntNode.of(9);
        root.right = IntNode.of(20);
        root.right.left = IntNode.of(15);
        root.right.right = IntNode.of(7);

        List<List<Integer>> result = new LevelOrderTraversal().levelOrder(root);
        System.out.println(result);
    }
}
