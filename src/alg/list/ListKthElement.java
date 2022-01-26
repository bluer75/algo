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
        IntNode sentinel = new IntNode(0); // use sentinel node to simplify removing first node
        sentinel.next = ll.head;
        IntNode first = sentinel;
        IntNode follower = sentinel;
        int counter = k + 1; // start counting k one node ahead so is points at the previous node
        while (first != null) {
            first = first.next;
            if (counter-- <= 0) {
                follower = follower.next;
            }
        }
        // first runner is at the end
        if (counter <= 0) {
            // follower is at k-th + 1 last element - remove k-th element
            follower.next = follower.next.next;
        }
        ll.head = sentinel.next;
    }

    public static void main(String... args) {
        IntList ll = new IntList(IntStream.rangeClosed(1, 10).toArray());
        System.out.println(ll);
        ll = new IntList(IntStream.rangeClosed(1, 10).toArray());
        remove(ll, 1); // remove 10
        System.out.println(ll);
        ll = new IntList(IntStream.rangeClosed(1, 10).toArray());
        remove(ll, 2); // remove 9
        System.out.println(ll);
        ll = new IntList(IntStream.rangeClosed(1, 10).toArray());
        remove(ll, 10); // remove 1
        System.out.println(ll);
        ll = new IntList(IntStream.rangeClosed(1, 10).toArray());
        remove(ll, 11); // nothing to remove
        System.out.println(ll);
        ll = new IntList(IntStream.rangeClosed(1, 2).toArray());
        remove(ll, 1); // remove 2
        System.out.println(ll);
        ll = new IntList(IntStream.rangeClosed(1, 2).toArray());
        remove(ll, 2); // remove 1
        System.out.println(ll);
    }
}
