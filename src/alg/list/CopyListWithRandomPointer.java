package alg.list;

/**
 * A linked list is given such that each node contains an additional random pointer which could 
 * point to any node in the list or null.
 * Return a deep copy of the list.
 * 
 * Solution:
 * - first pass - go through list and create new node placed between original nodes
 * - second pass - go through list and set random pointers for new nodes
 * - third pass - go through list and disconnect original nodes from new nodes
 * 
 * This takes O(n) time.
 */
public class CopyListWithRandomPointer {

    public Node copyRandomList(Node head) {
        if (head == null) {
            return null;
        }
        Node n = head;
        // create new nodes and place them in between existing ones
        while (n != null) {
            Node nn = new Node(n.val);
            nn.next = n.next;
            n.next = nn;
            n = n.next.next;
        }
        // remember new head
        Node newHead = head.next;
        // set random link for new nodes
        n = head;
        while (n != null) {
            n.next.random = (n.random == null) ? null : n.random.next;
            n = n.next.next;
        }
        // separate new nodes from original ones
        n = head;
        Node nn = null;
        while (n != null) {
            nn = n.next;
            n.next = n.next.next;
            if (nn.next != null) {
                nn.next = nn.next.next;
            }
            n = n.next;
        }
        return newHead;
    }

    public static void main(String... args) {
        Node _7 = new Node(7);
        Node _13 = new Node(13);
        Node _11 = new Node(11);
        Node _10 = new Node(10);
        Node _1 = new Node(1);
        _7.next = _13;
        _7.random = null;
        _13.next = _11;
        _13.random = _7;
        _11.next = _10;
        _11.random = _1;
        _10.next = _1;
        _10.random = _11;
        _1.next = null;
        _1.random = _7;
        Node n = _7;
        while (n != null) {
            System.out.println(n);
            n = n.next;
        }
        System.out.println("----------");
        n = new CopyListWithRandomPointer().copyRandomList(_7);
        while (n != null) {
            System.out.println(n);
            n = n.next;
        }
    }
}
