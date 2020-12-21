package alg.tree;

public abstract class BinarySearchTree<N extends BinaryTreeNode<N, T>, T extends Comparable<T>> {
    protected N root;

    public void insert(T value) {
        if (root == null) {
            root = createNode(value);
            return;
        }
        N node = root;
        while (node != null) {
            int res = node.getKey().compareTo(value);
            if (res == 0) {
                // duplicates not allowed
                return;
            }
            if (res > 0) {
                // value < node.key 
                if (node.getLeft() == null) {
                    // insert here
                    node.setLeft(createNode(value));
                    return;
                }
                node = node.getLeft();
            } else {
                // value > node.key
                if (node.getRight() == null) {
                    // insert here
                    node.setRight(createNode(value));
                    return;
                }
                node = node.getRight();
            }
        }
    }

    abstract protected N createNode(T key);
}
