package alg.list;

/**
 * You are given a singly-linked list that contains N integers. A subpart of the list is a contiguous 
 * set of even elements, bordered either by the end of the list or an odd element. 
 * For example, if the list is [1, 2, 8, 9, 12, 16], the subparts of the list are [2, 8] and [12, 16].
 * Then, for each subpart, the order of the elements is reversed. 
 * In the example, this would result in the new list, [1, 8, 2, 9, 16, 12].
 * The goal is to determine the original order of the elements.
 * 
 * It goes through the list and when first even element is encountered it reverses subpart from there to 
 * either end of the list of first odd element. It returns head of reverted list so it can be linked to previous element. 
 * Current element is at the end of reverted list so it can carry on. 
 * 
 * It takes O(n) time and O(1) space.
 */
public class ReorderList2 {

    public ListNode reverse(ListNode head) {
        ListNode node = head;
        ListNode prev = new ListNode(0);
        prev.next = head;
        ListNode fHead = prev;
        while (node != null) {
            if (node.val % 2 == 0) {
                // reverse from node to end of the list or first odd element
                // linked head of reverted part and continue with the node that is now at the end 
                prev.next = doReverse(node);
            }
            prev = node;
            node = node.next;
        }
        return fHead.next;
    }

    private ListNode doReverse(ListNode node) {
        ListNode current = node;
        ListNode next = null;
        ListNode prev = null;
        while (current != null && current.val % 2 == 0) {
            next = current.next;
            current.next = prev;
            prev = current;
            current = next;
        }
        node.next = current;
        return prev;
    }

    public static void main(String... args) {
        ListNode l = ListNode.create(2, 18, 24, 3, 5, 7, 9, 6, 12);
        //ListNode l = ListNode.create(1, 2, 8, 9, 12, 16);
        System.out.println(l.resolve());
        l = new ReorderList2().reverse(l);
        System.out.println(l.resolve());
    }
}
