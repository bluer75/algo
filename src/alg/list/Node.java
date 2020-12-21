package alg.list;

class Node {
    int val;
    Node next;
    Node random;

    public Node(int val) {
        this.val = val;
        this.next = null;
        this.random = null;
    }

    @Override
    public String toString() {
        return "[" + val + "|" + (next == null ? null : next.val) + "|" + (random == null ? null : random.val) + "]";
    }

    public static void main(String... args) {
        System.out.println(new Node(1));
    }
}