package alg.list;

/**
 * Given a linked list, rotate the list to the right by k places, where k is non-negative.
 * 
 * Example 1:
 * 
 * Input: 1->2->3->4->5->NULL, k = 2
 * Output: 4->5->1->2->3->NULL
 * 
 * Example 2:
 * 
 * Input: 0->1->2->NULL, k = 4
 * Output: 2->0->1->NULL
 * 
 * Use two pointers and take care of case where k is greater than length of the list.
 * Complexity is O(n).
 */
public class RotateListRight {
    public ListNode rotateRight(ListNode head, int k) {
        if (head == null || k <= 0) {
            return head;
        }
        ListNode n1 = head;
        int n = 0;
        while (n1.next != null && n < k) {
            n++;
            n1 = n1.next;
        }
        if (n1.next == null && n == k - 1) {
            // k equals the length of the list
            return head;
        }
        if (n1.next == null && n != k) {
            // k is greater than length of the list
            return rotateRight(head, k % (n + 1));
        }
        ListNode n2 = head;
        // move both pointers
        while (n1.next != null) {
            n1 = n1.next;
            n2 = n2.next;
        }
        // n1 points at last element
        // n2 points at k + 1 element to the last
        ListNode newHead = n2.next; // new head
        n2.next = null; // end of the list
        n1.next = head; // connect tail to old head
        return newHead;
    }

    private static class ListNode {
        int val;
        ListNode next;

        ListNode(int x) {
            val = x;
        }

        @Override
        public String toString() {
            return val + "";
        }
    }

    public static void main(String... args) {
        ListNode head = new ListNode(0);
        ListNode n = head;
        for (int i = 1; i <= 5; i++) {
            n.next = new ListNode(i);
            n = n.next;
        }
        n = new RotateListRight().rotateRight(head, 13);
        while (n != null) {
            System.out.print(n.val + "->");
            n = n.next;
        }
    }
}
