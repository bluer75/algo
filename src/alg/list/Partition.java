package alg.list;

/**
 * Given a linked list and a value x, partition it such that all nodes less than x come before nodes greater than or equal to x.
 * You should preserve the original relative order of the nodes in each of the two partitions.
 *
 * Example:
 * 
 * Input: head = 1->4->3->2->5->2, x = 3
 * Output: 1->2->2->4->3->5
 * 
 * Solution is based on Lomuto algorithm, Iterate through the list and every smaller element move after last lower so far.
 * This takes O(n) time.
 * 
 * Easier approach would be to iterate through the list and create two new list, one with lower and one 
 * with greater element. At the end both list could be joined. 
 */
public class Partition {
    public ListNode partition(ListNode head, int x) {

        if (head == null || head.next == null) {
            return head;
        }

        // artificial head to simplify edge/null cases
        ListNode newHead = new ListNode(Integer.MIN_VALUE);
        newHead.next = head;
        ListNode lastLower = newHead;
        ListNode current = head;
        ListNode currentPrev = newHead;

        while (current != null) {
            if (current.val < x) {
                if (moveAfterLastLower(lastLower, current, currentPrev)) {
                    lastLower = current;
                    current = currentPrev.next;
                } else {
                    lastLower = current;
                    currentPrev = current;
                    current = current.next;
                }
            } else {
                currentPrev = current;
                current = current.next;
            }
        }
        return newHead.next;
    }

    private boolean moveAfterLastLower(ListNode lastLower, ListNode current, ListNode currentPrev) {

        if (lastLower.next == current) {
            // already in place
            return false;
        }
        currentPrev.next = current.next;
        current.next = lastLower.next;
        lastLower.next = current;
        return true;
    }

    public static void main(String... args) {

        ListNode head = ListNode.create(2, 1); // 1, 4, 3, 2, 5, 2);
        System.out.println(head.resolve());
        head = new Partition().partition(head, 2);
        System.out.println(head.resolve());

        head = ListNode.create(1, 4, 3, 2, 5, 2);
        System.out.println(head.resolve());
        head = new Partition().partition(head, 3);
        System.out.println(head.resolve());
    }
}
