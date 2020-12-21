package alg.tree;

import java.util.function.BiFunction;

public class HeightFinder {

    /**
     * Finds max height in given binary tree.
     * Height is number of edges for the longest path.
     * Height of the root is 0.
     */
    public static int find(IntTree tree) {
        return find(tree.root);
    }

    static int find(IntNode node) {
        if (node == null) {
            return -1;
        }
        return Math.max(find(node.left), find(node.right)) + 1;
    }

    /**
     * Finds node with max height in given binary tree.
     * There can by more than one so return first found.
     */
    public static IntNode findNode(IntTree tree) {
        return findMaxHeighNode(tree.root).node;
    }

    /**
     * Values holder as recursive call has to return height and the node.
     */
    private static class Holder {
        int height;
        IntNode node;

        Holder(int height, IntNode node) {
            this.height = height;
            this.node = node;
        }

        Holder incHeight() {
            height++;
            return this;
        }
    }

    private static final BiFunction<Holder, Holder, Holder> maxFinder = (h1, h2) -> h1.height > h2.height ? h1 : h2;

    static Holder findMaxHeighNode(IntNode node) {
        if (node.left == null && node.right == null) {
            return new Holder(0, node);
        }
        if (node.left == null) {
            return findMaxHeighNode(node.right).incHeight();
        }
        if (node.right == null) {
            return findMaxHeighNode(node.left).incHeight();
        }
        return maxFinder.apply(findMaxHeighNode(node.left), findMaxHeighNode(node.right)).incHeight();
    }

    public static void main(String... args) {
        IntTree lt = new IntTree();
        lt.insert(40);
        lt.insert(20);
        lt.insert(60);
        lt.insert(10);
        lt.insert(30);
        lt.insert(50);
        lt.insert(70);
        lt.insert(80);
        lt.insert(90);
        System.out.println(find(lt));
        System.out.println(findNode(lt).getKey());
    }
}
