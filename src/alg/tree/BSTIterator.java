package alg.tree;
/**
 * Implement the BSTIterator class that represents an iterator over the in-order
 * traversal of a binary search tree (BST): You may assume that next() calls
 * will always be valid. It can be used without calling hasNext(). You can
 * always call hasNext().
 * 
 * 7 / \ 3 15 / \ 9 20
 * 
 * Should return 3, 7,9, 15, 20
 * 
 * Solution is based on Morris traversal and next()/hasNext() take on average
 * O(1) time/space.
 */
public class BSTIterator {
	private TreeNode current;
	private TreeNode next;

	public BSTIterator(TreeNode root) {
		this.next = root;
	}

	public int next() {
		TreeNode p = null;
		TreeNode node = next;
		while (node != null) {
			if (node.left == null) {
				current = node;
				next = node.right;
				break;
			} else {
				p = node.left;
				while (p.right != null && p.right != node) {
					p = p.right;
				}
				if (p.right == null) {
					p.right = node;
					node = node.left;
				} else {
					p.right = null;
					current = node;
					next = node.right;
					break;
				}
			}
		}
		return current.val;
	}

	public boolean hasNext() {
		return next != null;
	}

	public static void main(String... args) {
		TreeNode _3 = new TreeNode(3);
		TreeNode _9 = new TreeNode(9);
		TreeNode _20 = new TreeNode(20);
		TreeNode _15 = new TreeNode(15, _9, _20);
		TreeNode _7 = new TreeNode(7, _3, _15);
		BSTIterator i = new BSTIterator(_7);
		System.out.println(i.hasNext());
		System.out.println(i.hasNext());
		System.out.println(i.next());
		System.out.println(i.next());
		while (i.hasNext()) {
			System.out.println(i.next());
		}
	}
}

/**
 * Your BSTIterator object will be instantiated and called as such: BSTIterator
 * obj = new BSTIterator(root); int param_1 = obj.next(); boolean param_2 =
 * obj.hasNext();
 */
