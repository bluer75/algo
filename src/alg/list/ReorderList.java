package alg.list;

/**
 * Given a singly linked list L: L0→L1→…→Ln-1→Ln, reorder it to: L0→Ln→L1→Ln-1→L2→Ln-2→…
 * You may not modify the values in the list's nodes, only nodes itself may be changed.

 * Example:
 * 
 * Given 1->2->3->4->5, reorder it to 1->5->2->4->3.
 * 
 * Solution is combination of three problems:
 * 1. finding middle of the list
 * 2. reversing list in place
 * 3. merging two list
 * 
 * It takes O(n) time and O(1) space.
 */
public class ReorderList {
    public void reorderList(ListNode head) {
        if (head == null || head.next == null) {
            return;
        }
        // find middle
        ListNode p1Prev = null;
        ListNode p1 = head;
        ListNode p2 = head;
        while (p2 != null && p2.next != null) {
            p1Prev = p1;
            p1 = p1.next;
            p2 = p2.next.next;
        }
        // split into two
        p1Prev.next = null;
        // reverse second part
        ListNode current = p1;
        ListNode next = null;
        ListNode prev = null;
        while (current != null) {
            next = current.next;
            current.next = prev;
            prev = current;
            current = next;
        }
        // merge two parts
        current = head;
        p1 = head.next;
        p2 = prev;
        while (p1 != null && p2 != null) {
            current.next = p2;
            current = current.next;
            p2 = p2.next;
            current.next = p1;
            current = current.next;
            p1 = p1.next;
        }
        if (p1 != null) {
            current.next = p1;
        }
        if (p2 != null) {
            current.next = p2;
        }
    }

    public static void main(String... args) {
        ListNode l = ListNode.create(1, 2, 3, 4, 5);
        System.out.println(l.resolve());
        new ReorderList().reorderList(l);
        System.out.println(l.resolve());
    }
}
