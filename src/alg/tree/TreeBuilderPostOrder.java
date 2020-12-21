package alg.tree;

import java.util.HashMap;

/**
 * Given inorder and postorder traversal of a tree, construct the binary tree.
 * Note: You may assume that duplicates do not exist in the tree.
 * For example, given
 * inorder = [9,3,15,20,7]
 * postorder = [9,15,7,20,3]
 * Return the following binary tree:
 *
 *     3
 *    / \
 *   9  20
 *     /  \
 *    15   7
 * 
 * InOrder - left, parent, right   
 * PostOrder - left, right, parent
 * 
 * In postorder sequence, root of the tree is at the end. Knowing the index of that element in inorder 
 * sequence we can count elements in the left and right subtrees. Each subtree can be then processed
 * recursively.
 * 
 * Finding index in inorder sequence takes O(n). In worst case it has to be done n times thus complexity is O(n^2).
 * Using hashmap would reduce the search to O(1) and total time complexity to O(n) plus extra space O(n). 
 */
public class TreeBuilderPostOrder {

    private HashMap<Integer, Integer> iomap;

    public IntNode buildTree(int[] io, int[] po) {
        iomap = new HashMap<>();
        for (int i = 0; i < io.length; i++) {
            iomap.put(io[i], i);
        }
        return build(io, 0, io.length - 1, po, 0, po.length - 1);
    }

    private IntNode build(int[] io, int ioStart, int ioEnd, int[] po, int poStart, int poEnd) {
        // base case(s)
        if (ioStart > ioEnd) {
            // no element
            return null;
        } else if (ioEnd == ioStart) {
            // one element
            return new IntNode(io[ioStart]);
        }
        // root if this subtree is at the end of postorder sequence - left-subtree, right-subtree, root
        int rootValue = po[poEnd];
        // index of the root in inorder sequence
        int rootIndex = findIndexWithMap(rootValue); // findIndex(io, ioStart, ioEnd, rootValue);
        int leftSubTreeSize = rootIndex - ioStart;
        IntNode root = new IntNode(rootValue);
        root.left = build(io, ioStart, ioStart + leftSubTreeSize - 1, po, poStart, poStart + leftSubTreeSize - 1);
        root.right = build(io, rootIndex + 1, ioEnd, po, poStart + leftSubTreeSize, poEnd - 1);
        return root;
    }

    @SuppressWarnings("unused")
    private int findIndex(int[] a, int start, int end, int value) {
        // linear search
        for (int i = start; i <= end; i++) {
            if (a[i] == value) {
                return i;
            }
        }
        throw new IllegalArgumentException("Element " + value + " not found");
    }

    private int findIndexWithMap(int value) {
        // constant time search
        Integer index = iomap.get(value);
        if (index != null) {
            return index;
        }
        throw new IllegalArgumentException("Element " + value + " not found");
    }

    public static void printTree(IntNode root) {
        System.out.println(new LevelOrderTraversal().levelOrder(root));
    }

    public static void main(String... args) {
        int[] io = { 9, 3, 15, 20, 7 };
        int[] po = { 9, 15, 7, 20, 3 };

        IntNode root = new TreeBuilderPostOrder().buildTree(io, po);
        printTree(root);
    }
}
