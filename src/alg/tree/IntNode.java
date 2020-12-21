package alg.tree;

public class IntNode extends BinaryTreeNode<IntNode, Integer> {

    public static IntNode of(int key) {
        return new IntNode(key);
    }

    protected IntNode(Integer key) {
        super(key);
    }
}
