package alg.tree;

import java.util.Collections;
import java.util.LinkedList;
import java.util.List;
import java.util.Queue;

/**
 * Given a binary tree, return the values of the nodes you can see from right side ordered from top to bottom.
 * 
 * Example:
 * 
 * Input: [1,2,3,null,5,null,4]
 * Output: [1, 3, 4]
 * Explanation:
 * 
 *     1            -> 1
 *   /   \
 *  2     3         -> 3
 *   \     \
 *    5     4       -> 4
 *   /
 *  6               -> 6   
 *  
 *  Use BFS traversal and consider only last node for each level - O(n).
 */

public class RightSideView {
    public List<Integer> rightSideView(IntNode root) {
        if (root == null) {
            return Collections.emptyList();
        }
        List<Integer> res = new LinkedList<>();
        Queue<IntNode> queue = new LinkedList<>();
        queue.add(root);
        int count = 1;
        IntNode n = null;
        while (!queue.isEmpty()) {
            n = queue.remove();
            if (n.left != null) {
                queue.add(n.left);
            }
            if (n.right != null) {
                queue.add(n.right);
            }
            if (--count == 0) {
                res.add(n.key);
                count = queue.size();
            }
        }
        return res;
    }

    public static void main(String... args) {
        IntNode _1 = new IntNode(1);
        IntNode _2 = new IntNode(2);
        IntNode _3 = new IntNode(3);
        IntNode _4 = new IntNode(4);
        IntNode _5 = new IntNode(5);
        IntNode _6 = new IntNode(6);

        _1.left = _2;
        _1.right = _3;
        _2.right = _5;
        _3.right = _4;
        _5.left = _6;

        System.out.println(new RightSideView().rightSideView(_1));
    }
}
