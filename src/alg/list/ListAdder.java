package alg.list;

/**
 * You are given two non-empty linked lists representing two non-negative integers. 
 * The digits are stored in reverse order and each of their nodes contain a single digit. 
 * Add the two numbers and return it as a linked list.
 * You may assume the two numbers do not contain any leading zero, except the number 0 itself.
 * 
 * Example:
 * Input: (2 -> 4 -> 3) + (5 -> 6 -> 4)
 * Output: 7 -> 0 -> 8
 * Explanation: 342 + 465 = 807.
 * 
 * Time complexity is proportional to longer list. 
 */
public class ListAdder {
    public IntNode sum(IntNode n1, IntNode n2) {
        IntNode res = new IntNode(0);
        IntNode head = res;
        int c = 0;
        int s = 0;
        int a = 0;
        int b = 0;
        while (n1 != null || n2 != null) {
            a = 0;
            b = 0;
            if (n1 != null) {
                a = n1.value;
                n1 = n1.next;
            }
            if (n2 != null) {
                b = n2.value;
                n2 = n2.next;
            }
            s = a + b + c;
            c = 0;
            if (s >= 10) {
                s -= 10;
                c = 1;
            }
            res.value = s;
            if (n1 != null || n2 != null) {
                res.next = new IntNode(0);
                res = res.next;
            }
        }
        if (c == 1) {
            res.next = new IntNode(1);
        }
        return head;
    }

    public static void main(String... args) {
        IntList l1 = new IntList(2, 4, 3);
        IntList l2 = new IntList(5, 6, 4);

        IntNode res = new ListAdder().sum(l1.head, l2.head);
        while (res != null) {
            System.out.println(res.value);
            res = res.next;
        }
    }
}
