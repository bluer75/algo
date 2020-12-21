package alg.tree;

public class BinaryTreeNode<N extends BinaryTreeNode<N, T>, T extends Comparable<T>> {

    protected T key;
    protected N left;
    protected N right;

    protected BinaryTreeNode(T key) {
        this.key = key;
    }

    protected N getLeft() {
        return left;
    }

    protected N getRight() {
        return right;
    }

    protected void setLeft(N left) {
        this.left = left;
    }

    protected void setRight(N right) {
        this.right = right;
    }

    protected T getKey() {
        return key;
    }

    public String toString() {
        return key.toString();
    }
}
