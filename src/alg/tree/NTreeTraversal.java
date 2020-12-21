package alg.tree;

import java.util.Collections;
import java.util.Deque;
import java.util.LinkedList;
import java.util.List;

/**
 * Given an n-ary tree, return the postorder traversal of its nodes' values.
 * Example: 
 *      1
 *     / \
 *    2   3
 *   / \   \
 *  4   5   6
 *  Postorder traversal: 4,5,2,6,3,1
 */
public class NTreeTraversal {
    private static class Node {
        public int val;
        public List<Node> children;

        public Node(int _val) {
            val = _val;
        }

        public Node(int _val, List<Node> _children) {
            val = _val;
            children = _children;
        }
    }

    /**
     * Iterative approach using two stacks.
     * It is slower than recursive solution.
     * Requires potentially more memory as the whole tree is stored ion the stack.
     */
    public List<Integer> postorderIterative(Node root) {
        if (root == null) {
            return Collections.emptyList();
        }
        Deque<Node> s1 = new LinkedList<>();
        Deque<Node> s2 = new LinkedList<>();
        List<Integer> result = new LinkedList<>();
        Node n = null;
        s1.push(root);
        while (!s1.isEmpty()) {
            n = s1.pop();
            if (n.children != null) {
                for (Node ch : n.children) {
                    s1.push(ch);
                }
            }
            s2.push(n);
        }
        while (!s2.isEmpty()) {
            n = s2.pop();
            result.add(n.val);
        }
        return result;
    }

    /**
     * Recursive solution.
     */
    public List<Integer> postorderRecursive(Node root) {
        if (root == null) {
            return Collections.emptyList();
        }
        List<Integer> result = new LinkedList<>();
        visit(root, result);
        return result;
    }

    private void visit(Node node, List<Integer> result) {
        if (node == null) {
            return;
        }
        if (node.children != null) {
            for (Node n : node.children) {
                visit(n, result);
            }
        }
        result.add(node.val);
    }

    public static void main(String... args) {
        Node _1 = new Node(1, new LinkedList<Node>());
        Node _2 = new Node(2, new LinkedList<Node>());
        Node _3 = new Node(3, new LinkedList<Node>());
        Node _4 = new Node(4);
        Node _5 = new Node(5);
        Node _6 = new Node(6);
        _2.children.add(_4);
        _2.children.add(_5);
        _3.children.add(_6);
        _1.children.add(_2);
        _1.children.add(_3);
        System.out.println(new NTreeTraversal().postorderIterative(_1));
        System.out.println(new NTreeTraversal().postorderRecursive(_1));
    }
}
