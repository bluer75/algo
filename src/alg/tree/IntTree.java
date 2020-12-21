package alg.tree;

public class IntTree extends BinarySearchTree<IntNode, Integer> {

    @Override
    protected IntNode createNode(Integer key) {
        return new IntNode(key);
    }

}
