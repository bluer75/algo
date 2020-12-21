package alg.tree;

import java.util.Collections;
import java.util.LinkedList;
import java.util.List;
import java.util.Queue;

/**
 * Two nodes in a binary tree can be called cousins if they are on the same level of the tree but have different parents. 
 * For example, in the following diagram 4 and 6 are cousins.
 * 
 *      1
 *     / \
 *    2   3
 *   / \   \
 *  4   5   6
 *  
 * Given a binary tree and a particular node, find all cousins of that node.
 * 
 * Solution would be to search three with BFS and collect nodes level by level till we find node matching the key.
 * Once we know on which level the key node is located we can finish processing nodes on that level and ignore others.
 * 
 * In worst case we need to process all nodes so complexity is O(n).
 * We can optimize the space and store only current and previous level of nodes as the queue can have at given time 
 * nodes from two different levels.  
 */
public class FindCousins {

    public List<Integer> findCousins(IntNode root, int key) {
        if (root == null || root.key == key) {
            return Collections.emptyList();
        }
        Queue<IntNode> nodes = new LinkedList<>();
        Queue<Integer> levels = new LinkedList<>();
        List<List<Integer>> result = new LinkedList<>(); // levels and elements
        int level = 0;
        nodes.add(root);
        levels.add(level);
        int keyLevel = Integer.MAX_VALUE; // level where key node was found
        while (!nodes.isEmpty() && level <= keyLevel) {
            IntNode node = nodes.remove();
            level = levels.remove();
            if (result.size() < level + 1) {
                result.add(new LinkedList<>());
            }
            result.get(level).add(node.key);
            if ((node.left != null && node.left.key == key) || (node.right != null && node.right.key == key)) {
                // we found the key, both subtrees won't be processed further, but we need to finish this level to find
                // all cousins
                keyLevel = level + 1;
            } else {
                if (node.left != null) {
                    nodes.add(node.left);
                    levels.add(level + 1);
                }
                if (node.right != null) {
                    nodes.add(node.right);
                    levels.add(level + 1);
                }
            }
        }
        return keyLevel != Integer.MAX_VALUE && result.size() > keyLevel ? result.get(keyLevel)
                : Collections.emptyList();
    }

    public static void main(String... args) {
        IntNode root = IntNode.of(1);
        root.left = IntNode.of(2);
        root.right = IntNode.of(3);
        root.left.left = IntNode.of(4);
        root.left.right = IntNode.of(5);
        root.right.right = IntNode.of(6);
        System.out.println(new FindCousins().findCousins(root, 6));
    }
}
