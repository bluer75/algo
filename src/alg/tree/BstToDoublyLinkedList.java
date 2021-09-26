package alg.tree;

/**
 * Convert a Binary Search Tree to a sorted Circular Doubly-Linked List in place.
 * You can think of the left and right pointers as synonymous to the predecessor and successor pointers in a
 * doubly-linked list.For a circular doubly linked list,the predecessor of the first element is the last element,
 * and the successor of the last element is the first element.
 *
 * We want to do the transformation in place.After the transformation,the left pointer of the tree node should point to
 * its predecessor, and the right pointer should point to its successor.You should return the pointer to the smallest
 * element of the linked list.
 *
 * Solution is based on Morris traversal and takes O(n) time and O(1) space.
 */

public class BstToDoublyLinkedList {
    private Node node;
    private Node head;
    private Node prev;
    private Node last;

    public Node treeToDoublyList(Node root) {
        if (root == null) {
            return null;
        }
        node = root;
        while (node != null) {
            if (node.left == null) {
                link();
            } else {
                Node pred = node.left;
                while (pred.right != null && pred.right != node) {
                    pred = pred.right;
                }
                if (pred.right == null) {
                    pred.right = node;
                    node = node.left;
                } else {
                    link();
                }
            }
        }
        last.right = head;
        head.left = last;
        return head;
    }

    private void link() {
        last = node;
        if (head == null) {
            head = node;
        }
        node.left = prev;
        if (prev != null) {
            prev.right = node;
        }
        prev = node;
        node = node.right;
    }

    public static void main(String[] args) {
        Node _1 = new Node(1);
        Node _2 = new Node(2);
        Node _3 = new Node(3);
        Node _4 = new Node(4);
        Node _5 = new Node(5);
        Node _6 = new Node(6);
        Node _7 = new Node(7);
        _4.left = _2;
        _4.right = _6;
        _2.left = _1;
        _2.right = _3;
        _6.left = _5;
        _6.right = _7;

        new BstToDoublyLinkedList().treeToDoublyList(_4);
    }

    private static class Node {
        public int val;
        public Node left;
        public Node right;

        public Node() {
        }

        public Node(int _val) {
            val = _val;
        }

        public Node(int _val, Node _left, Node _right) {
            val = _val;
            left = _left;
            right = _right;
        }
    }
}
