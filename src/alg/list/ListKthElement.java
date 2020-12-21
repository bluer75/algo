package alg.list;

import java.util.stream.IntStream;

/**
 * Given a singly linked list and an integer k, remove the k-th last element from the list. 
 * k = 1 means last element, k = 2 means second to last element
 */
public class ListKthElement {

    /**
     * Use two runners, second is k elements behind first one.
     * When first reaches the end the second one is at k-th last element,
     * so k-th element can be removed. 
     * Running time is O(n).
     */
    public static void remove(IntList ll, int k) {
        if (k <= 0) {
            return;
        }
        IntNode node = ll.head;
        IntNode nodeK = ll.head;
        int i = k + 1; // start counting k one node ahead so is always points at the previous one
        while (node != null) {
            node = node.next;
            if (i > 0) {
                i--;
            } else {
                nodeK = nodeK.next;
            }
        }
        // first runner is at the end, second is at k-th + 1 last element
        if (i == 0) {
            // nodeK points at k - 1 element
            nodeK.next = nodeK.next.next;
        }
        if (i == 1) {
            // nodeK points at head
            ll.head = ll.head.next;
        } else {
            // k is greater than length of the list;
        }
    }

    public static void main(String... args) {
        IntList ll = new IntList(IntStream.range(1, 11).toArray());
        System.out.println(ll);
        ll = new IntList(IntStream.range(1, 11).toArray());
        remove(ll, 1); // remove 10
        System.out.println(ll);
        ll = new IntList(IntStream.range(1, 11).toArray());
        remove(ll, 2); // remove 9
        System.out.println(ll);
        ll = new IntList(IntStream.range(1, 11).toArray());
        remove(ll, 10); // remove 1
        System.out.println(ll);
        ll = new IntList(IntStream.range(1, 11).toArray());
        remove(ll, 11); // nothing to remove
        System.out.println(ll);
        ll = new IntList(IntStream.range(1, 3).toArray());
        remove(ll, 1); // remove 2
        System.out.println(ll);
        ll = new IntList(IntStream.range(1, 3).toArray());
        remove(ll, 2); // remove 1
        System.out.println(ll);

    }
}
