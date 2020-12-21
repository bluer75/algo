package alg.list;

public class IntNode {
    int value;
    IntNode next;

    public IntNode(int value) {
        this.value = value;
    }

    @Override
    public String toString() {
        return Integer.toString(value);
    }
}
