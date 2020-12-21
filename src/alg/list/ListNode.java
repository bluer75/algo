package alg.list;

import java.util.StringJoiner;

/**
 * ListNode class.
 */
public class ListNode {
    int val;
    ListNode next;

    ListNode(int x) {

        val = x;
    }

    static ListNode create(int... values) {

        if (values == null || values.length == 0) {
            return null;
        }
        ListNode head = new ListNode(values[0]);
        if (values.length > 1) {
            ListNode node = head;
            for (int i = 1; i < values.length; i++) {
                node.next = new ListNode(values[i]);
                node = node.next;
            }
        }
        return head;
    }

    @Override
    public String toString() {

        return String.valueOf(val);
    }

    public String resolve() {

        ListNode node = this;
        StringJoiner sj = new StringJoiner("->");
        while (node != null) {
            sj.add(node.toString());
            node = node.next;
        }
        return sj.toString();
    }
}