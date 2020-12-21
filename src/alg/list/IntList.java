package alg.list;

import java.util.stream.IntStream;

public class IntList {
    IntNode head;

    public IntList(int... elements) {
        for (int element : elements) {
            insert(new IntNode(element));
        }
    }

    /**
     * Inserts new node with given value.
     */
    protected void insert(IntNode node) {
        if (head == null) {
            head = node;
        } else {
            IntNode n = head;
            while (n.next != null) {
                n = n.next;
            }
            n.next = node;
        }
    }

    public Integer remove() {
        if (head == null) {
            return null;
        }
        int value = head.value;
        head = head.next;
        return value;
    }

    /**
     * Reverse this list and return as new instance.
     */
    public void reverse() {
        if (head == null) {
            return;
        } else {
            reverse(head).next = null; // reset next for old head
        }
    }

    private IntNode reverse(IntNode node) {
        if (node.next == null) {
            head = node;
            return head;
        }
        // get recursively to the end end return last node (new head)
        // on the way back reverse sets next for each node
        reverse(node.next).next = node;
        // last return - gives old head back
        return node;
    }

    @Override
    public String toString() {
        return toString(head);
    }

    public static String toString(IntNode head) {
        StringBuilder sb = new StringBuilder("{");
        IntNode node = head;
        while (node != null) {
            sb.append(node.value);
            if (node.next != null) {
                sb.append("->");
            }
            node = node.next;
        }
        sb.append("}");
        return sb.toString();
    }

    /**
     * Returns middle element of this list.
     */
    public Integer findMiddle() {
        if (head == null) {
            return null;
        }
        int size = 0;
        IntNode node = head;
        // count nodes
        while (node != null) {
            node = node.next;
            ++size;
        }
        node = head;
        size = size / 2;
        // iterate half elements
        while (size > 0) {
            node = node.next;
            size--;
        }
        return node.value;
    }

    public static void main(String... strings) {
        IntList ints = new IntList(IntStream.range(0, 10).toArray());
        out(ints);
        out(ints.findMiddle());
        ints.reverse();
        out(ints);
        out(ints.findMiddle());
    }

    private static void out(Object msg) {
        System.out.println(msg);
    }
}
