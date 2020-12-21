package alg.list;

/**
 * Reverse a singly linked list.
 * Use three extra nodes to do it in one pass - O(n).
 */
public class ReverseList {
    public IntNode reverseList(IntNode head) {
        if (head == null) {
            return null;
        }
        IntNode current = head;
        IntNode next = null;
        IntNode newHead = null;
        while (current != null) {
            next = current.next;
            current.next = newHead;
            newHead = current;
            current = next;
        }
        return newHead;
    }

    public static void main(String... args) {
        IntNode res = new ReverseList().reverseList(new IntList(1, 2, 3).head);
        while (res != null) {
            System.out.println(res.value);
            res = res.next;
        }
    }
}
