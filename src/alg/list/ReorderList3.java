package alg.list;

/**
 * Given a linked list, reverse the nodes of a linked list in groups of k elements.
 *
 * k is a positive integer and is less than or equal to the length of the linked list. If the number of nodes is not
 * a multiple of k then left-out nodes, in the end, should remain as it is.
 *
 * Algorithm reverses chunk of k elements and links it to previous chunk. To make sure the chunk should be reversed we
 * check first if there are enough elements.
 *
 * It takes O(n) time and O(1) space.
 */
public class ReorderList3 {

    public ListNode reverseKGroup(ListNode head, int k) {
        ListNode preHead = new ListNode(0);
        preHead.next = head;
        ListNode node = head;
        ListNode next = null, prev = preHead;
        while (node != null) {
            next = reverse(node, k);
            if (next == null) {
                break;
            }
            prev.next = next;
            prev = node;
            node = node.next;
        }
        return preHead.next;
    }

    private ListNode reverse(ListNode node, int k) {
        ListNode curr = node;
        ListNode prev = null;
        ListNode next = null;
        int size = k;
        while (curr != null && size-- > 0) {
            curr = curr.next;
        }
        if (size > 0) {
            return null;
        }
        curr = node;
        while (curr != null && k-- > 0) {
            next = curr.next;
            curr.next = prev;
            prev = curr;
            curr = next;
        }
        node.next = curr;
        return prev;
    }

    public static void main(String... args) {
        ListNode l = ListNode.create(1, 2, 3, 4, 5, 6, 7, 8);
        System.out.println(l.resolve());
        l = new ReorderList3().reverseKGroup(l, 3);
        System.out.println(l.resolve());
    }
}
