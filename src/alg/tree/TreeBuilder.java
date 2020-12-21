package alg.tree;

/**
 * Given pre-order and in-order traversals of a binary tree, write a function to reconstruct the tree.
 * Pre-order traversal (c-l-r) indicates parent of each subtree.
 * In-order traversal (l-c-r) identifies left and right subtrees for given root.
 * We can process recursively left and right subtree.
 * Time complexity is O(n).
 */
public class TreeBuilder {

    public static IntNode build(String pot, String iot) {
        // last element in pot is root of the tree
        return build(pot, iot, 0, 0, iot.length());
    }

    private static IntNode build(String pot, String iot, int rootIdx, int startIdx, int endIdx) {
        if (startIdx >= endIdx) {
            return null;
        }
        char rootChar = pot.charAt(rootIdx);
        IntNode root = new IntNode(rootChar - 'a');
        int iotRootIdx = getIdxOf(iot, rootChar, startIdx, endIdx); // root index in iot
        int leftRootId = rootIdx + 1; // root index for left side is first character in left subtree
        int rightRootIdx = rootIdx + iotRootIdx - startIdx + 1; // root index for right side is first character after
                                                                // left subtree
        root.left = build(pot, iot, leftRootId, startIdx, iotRootIdx);
        root.right = build(pot, iot, rightRootIdx, iotRootIdx + 1, endIdx);
        return root;
    }

    private static int getIdxOf(String s, char ch, int start, int end) {
        for (int i = start; i < end; i++) {
            if (s.charAt(i) == ch) {
                return i;
            }
        }
        // throw exception
        return -1;
    }

    public static StringBuilder visitPostOrder(IntNode node, StringBuilder sb) {
        if (node == null) {
            return sb;
        }
        sb.append((char) ('a' + node.key));
        visitPostOrder(node.left, sb);
        visitPostOrder(node.right, sb);
        return sb;
    }

    public static StringBuilder visitInOrder(IntNode node, StringBuilder sb) {
        if (node == null) {
            return sb;
        }
        visitInOrder(node.left, sb);
        sb.append((char) ('a' + node.key));
        visitInOrder(node.right, sb);
        return sb;
    }

    public static void main(String... args) {
        String pot = "abdecfg";
        String iot = "dbeafcg";
        IntNode root = build(pot, iot);
        System.out.println(visitPostOrder(root, new StringBuilder()).toString());
        System.out.println(visitInOrder(root, new StringBuilder()).toString());
    }
}
