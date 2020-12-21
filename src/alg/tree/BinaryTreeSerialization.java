package alg.tree;

import java.util.LinkedList;
import java.util.Queue;
import java.util.StringJoiner;

/**
 * Design an algorithm to serialize and deserialize a binary tree. 
 * You need to ensure that a binary tree can be serialized to a string and this string 
 * can be deserialized to the original tree structure.
 * 
 * Following tree: 
 * 
 *     1
 *    / \
 *   2   3
 *      / \
 *     4   5
 * 
 * can be serialized as "[1,2,3,null,null,4,5]"
 * 
 * Use level order traversal to convert tree to string. Add "-" values for missing children if necessary.
 */
public class BinaryTreeSerialization {

    // Encodes a tree to a single string.
    public String serialize(IntNode root) {
        if (root == null) {
            return null;
        }
        StringJoiner res = new StringJoiner(",", "[", "]");
        Queue<IntNode> queue = new LinkedList<>();
        queue.add(root);
        IntNode node = null;
        while (!queue.isEmpty()) {
            node = queue.remove();
            if (node == null) {
                res.add("-");
            } else {
                res.add(Integer.toString(node.key));
                queue.add(node.left);
                queue.add(node.right);
            }
        }
        return res.toString();
    }

    // Decodes your encoded data to tree.
    public IntNode deserialize(String data) {
        if (data == null || !data.startsWith("[") || !data.endsWith("]")) {
            return null;
        }
        String[] values = data.substring(1, data.length() - 1).split(",");
        if (values == null || values.length == 0) {
            return null;
        }
        int i = 0;
        Queue<IntNode> parents = new LinkedList<>();
        IntNode root = new IntNode(Integer.valueOf(values[i]));
        IntNode parent = null;
        IntNode child = null;
        parents.add(root);
        while (!parents.isEmpty()) {
            parent = parents.remove();
            i++;
            if (!values[i].equals("-")) {
                child = new IntNode(Integer.valueOf(values[i]));
                parent.left = child;
                parents.add(child);
            }
            i++;
            if (!values[i].equals("-")) {
                child = new IntNode(Integer.valueOf(values[i]));
                parent.right = child;
                parents.add(child);
            }
        }
        return root;
    }

    public static void main(String... args) {
        IntNode _1 = new IntNode(1);
        IntNode _2 = new IntNode(2);
        IntNode _3 = new IntNode(3);
        IntNode _4 = new IntNode(4);
        IntNode _5 = new IntNode(5);

        _1.left = _2;
        _1.right = _3;
        _3.left = _4;
        _3.right = _5;

        BinaryTreeSerialization bts = new BinaryTreeSerialization();

        System.out.println(bts.serialize(_1));
        System.out.println(bts.serialize(bts.deserialize(bts.serialize(_1))));
    }
}
