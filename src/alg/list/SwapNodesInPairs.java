package alg.list;

/**
 * Given a linked list, swap every two adjacent nodes and return its head.
 * You may not modify the values in the list's nodes, only nodes itself may be changed.
 * Given 1->2->3->4, you should return the list as 2->1->4->3.
 * 
 * Use recursion to swap each pair and return new head from each pair. After each swap, connect current pair to new head.
 * Time/Space complexity is O(n).
 */
public class SwapNodesInPairs {
    public IntNode swapPairs(IntNode head) {
        if (head == null || head.next == null) {
            return head;
        }
        return swap(head);
    }

    private IntNode swap(IntNode node) {
        // base case no more pairs or odd number of elements
        if (node == null || node.next == null) {
            return node;
        }
        IntNode head = node.next;
        IntNode next = head.next;
        head.next = node;
        node.next = swap(next); // swap recursively rest of the list and connect with this pair
        return head; // new head
    }

    public static void main(String... args) {
        IntNode head = new IntList(1, 2, 3, 4, 5).head;
        System.out.println(IntList.toString(new SwapNodesInPairs().swapPairs(head)));
    }
}
