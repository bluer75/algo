package alg.list;

import java.util.Comparator;
import java.util.PriorityQueue;
import java.util.Queue;
import java.util.StringJoiner;

/**
 * Merge k sorted linked lists and return it as one sorted list. Analyze and describe its complexity.
 * Input:
 * [
 *   1->4->5,
 *   1->3->4,
 *   2->6
 * ]
 * Output: 1->1->2->3->4->4->5->6
 */
public class MergeKLists {

    /**
     * Naive solution that finds min node, adds it to result and progresses that list.
     * It takes O(k * n) time. Each time we go through k heads.
     */
    public IntNode mergeKListsBF(IntNode[] lists) {
        if (lists == null || lists.length == 0) {
            return null;
        }
        if (lists.length == 1) {
            return lists[0];
        }
        IntNode head = min(lists);
        if (head != null) {
            IntNode n = head;
            while ((n.next = min(lists)) != null) {
                n = n.next;
            }
        }
        return head;
    }

    private IntNode min(IntNode[] lists) {
        int min = Integer.MAX_VALUE;
        int pos = -1;
        for (int i = 0; i < lists.length; i++) {
            if (lists[i] != null) {
                if (lists[i].value < min) {
                    min = lists[i].value;
                    pos = i;
                }
            }
        }
        IntNode minNode = null;
        if (pos > -1) {
            minNode = lists[pos];
            lists[pos] = lists[pos].next;
        }
        return minNode;
    }

    /**
     * Optimized solution using min heap to store heads. 
     * This allows to reduce the complexity to O(n * log k).
     */
    public IntNode mergeKListsMH(IntNode[] lists) {
        if (lists == null || lists.length == 0) {
            return null;
        }
        if (lists.length == 1) {
            return lists[0];
        }
        Queue<IntNode> heap = new PriorityQueue<>(lists.length, Comparator.comparingInt(n -> n.value));
        for (int i = 0; i < lists.length; i++) {
            if (lists[i] != null) {
                heap.add(lists[i]);
            }
        }
        IntNode head = null;
        IntNode current = null;
        IntNode n = null;
        while (!heap.isEmpty()) {
            n = heap.remove();
            if (current != null) {
                current.next = n;
            } else {
                head = n;
            }
            if (n.next != null) {
                heap.add(n.next);
            }
            current = n;
        }
        return head;
    }

    public static void main(String... args) {
        IntList l1 = new IntList(1, 4, 5);
        IntList l2 = new IntList(1, 3, 4);
        IntList l3 = new IntList(2, 6);

        IntNode res = new MergeKLists().mergeKListsMH(new IntNode[] { l1.head, l2.head, l3.head });
        StringJoiner sj = new StringJoiner("->");
        while (res != null) {
            sj.add("" + res.value);
            res = res.next;
        }
        System.out.println(sj.toString());
        l1 = new IntList(1, 4, 5);
        l2 = new IntList(1, 3, 4);
        l3 = new IntList(2, 6);
        res = new MergeKLists().mergeKListsBF(new IntNode[] { l1.head, l2.head, l3.head });
        sj = new StringJoiner("->");
        while (res != null) {
            sj.add("" + res.value);
            res = res.next;
        }
        System.out.println(sj.toString());
    }
}
