package alg.tree;

/**
 * Given a node in a binary search tree, find the in-order successor of that node in the BST.
 * If that node has no in-order successor, return null.
 * All nodes have unique values.
 * 
 * Example: 
 * 
 *          5
 *        /   \
 *       3     6 
 *      / \   
 *     2   4 
 *    /      
 *   1
 * 
 * find(6) should return null
 * find(4) should return 5
 * 
 * There are two cases:
 * - node has right child - find smallest value there
 * - node has no right child:
 *   - is left child of the parent -. return parent
 *   - is right child of the parent - move up till it is left child of the parent and return that parent, return null otherwise
 * 
 * It takes O(n) time and O(1) space.
 *   
 */
public class FindInOrderSuccessor {

    private static class Node {
        int val;
        Node left;
        Node right;
        Node parent;

        Node(int val, Node parent) {
            this.val = val;
            this.parent = parent;
        }
    }

    public Node inorderSuccessor(Node node) {

        if (node == null || (node.right == null && node.parent == null)) {
            return null;
        }
        Node n = node;
        if (n.right == null) {
            while (n.parent != null && n.parent.right == n) {
                // node is right child of the parent                      
                n = n.parent;
            }
            return n.parent;
        }
        n = n.right;
        while (n.left != null) {
            n = n.left;
        }
        return n;
    }

    public static void main(String... args) {

        Node _5 = new Node(5, null);
        Node _3 = new Node(3, _5);
        Node _6 = new Node(6, _5);
        Node _2 = new Node(2, _3);
        Node _4 = new Node(4, _3);
        Node _1 = new Node(1, _2);

        _5.left = _3;
        _5.right = _6;
        _3.left = _2;
        _3.right = _4;
        _2.left = _1;

        System.out.println(new FindInOrderSuccessor().inorderSuccessor(_6));
        System.out.println(new FindInOrderSuccessor().inorderSuccessor(_4).val);
    }
}
