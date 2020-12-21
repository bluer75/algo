package alg.list;

/**
 * Sort a linked list in O(n log n) time using constant space complexity.
 *
 * Input: 4->2->1->3
 * Output: 1->2->3->4
 * 
 * Simple merge sort implementation providing O(n log n) time complexity.
 */
public class MergeSortList {

    public ListNode sortList(ListNode head) {

        if (head == null || head.next == null) {
            return head;
        }
        // split the list in half
        ListNode middle = split(head);
        ListNode next = middle.next;
        middle.next = null;
        // recursively sort both lists
        return merge(sortList(head), sortList(next));
    }

    private ListNode split(ListNode node) {

        // slow/fast runner to find middle node
        ListNode slow = node;
        ListNode fast = node.next;
        while (fast != null) {
            fast = fast.next;
            if (fast != null) {
                slow = slow.next;
                fast = fast.next;
            }
        }
        return slow;
    }

    private ListNode merge(ListNode a, ListNode b) {

        if (a == null) {
            return b;
        }
        if (b == null) {
            return a;
        }
        ListNode head = null;
        if (a.val <= b.val) {
            head = a;
            a.next = merge(a.next, b);
        } else {
            head = b;
            b.next = merge(a, b.next);
        }
        return head;
    }

    public static void main(String... args) {

        ListNode _4 = new ListNode(4);
        ListNode _2 = new ListNode(2);
        ListNode _1 = new ListNode(1);
        ListNode _3 = new ListNode(3);
        _4.next = _2;
        _2.next = _1;
        _1.next = _3;

        ListNode head = new MergeSortList().sortList(_4);
        System.out.println(head.resolve());
    }
}
