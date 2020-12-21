package alg.tree;

/**
 * Locking in a binary tree. 
 * A binary tree node can be locked or unlocked only if all of its descendants or ancestors are not locked.
 * Design a binary tree node class with the following methods:
 * - is_locked, which returns whether the node is locked
 * - lock, which attempts to lock the node. If it cannot be locked, then it should return false. Otherwise, it should lock it and return true.
 * - unlock, which unlocks the node. If it cannot be unlocked, then it should return false. Otherwise, it should unlock it and return true.
 * Each method should run in O(h), where h is the height of the tree.
 */
public class LockableBinaryTree extends BinarySearchTree<LockableBinaryTree.Node, Integer> {
    protected class Node extends BinaryTreeNode<Node, Integer> {
        private boolean nodeIsLocked;
        // Use tree augmentation (like in AVL tree).
        // stores information about locked descendant, otherwise we cannot guarantee O(h) as both path have to
        // be checked for each node and this can be in worst case O(n).
        private int numOfLockedChildren;

        protected Node(Integer key) {
            super(key);
        }

        private boolean isLocked() {
            return nodeIsLocked;
        }

        private boolean lock() {
            if (!isLocked() && numOfLockedChildren == 0) {
                return nodeIsLocked = true;
            }
            return false;
        }

        private boolean unlock() {
            if (isLocked() && numOfLockedChildren == 0) {
                return !(nodeIsLocked = false);
            }
            return false;
        }
    }

    public boolean isLocked(int key) {
        Node n = find(key, root);
        return n == null ? false : n.isLocked();
    }

    public boolean lock(int key) {
        return findAndChange(true, key, root, false);
    }

    public boolean unlock(int key) {
        return findAndChange(false, key, root, false);
    }

    /**
     * Finds matching node (if exists) and lock/unlock it if possible.
     * On the way back it updates hasLockedChildren for each parent node accordingly. 
     */
    private boolean findAndChange(boolean lock, int key, Node n, boolean parentLocked) {
        if (n == null || parentLocked) {
            return false;
        }
        if (n.key == key) {
            return lock ? n.lock() : n.unlock();
        }
        boolean locked = false;
        if (n.key <= key) {
            if (locked = findAndChange(lock, key, n.right, n.isLocked())) {
                n.numOfLockedChildren += lock ? 1 : -1;
            }
        } else if (locked = findAndChange(lock, key, n.left, n.isLocked())) {
            n.numOfLockedChildren += lock ? 1 : -1;
        }
        return locked;
    }

    private Node find(int key, Node n) {
        while (n != null && n.key != key) {
            n = (key < n.key) ? n.getLeft() : n.right;
        }
        return n;
    }

    @Override
    protected Node createNode(Integer key) {
        return new Node(key);
    }

    public static void main(String... args) {
        LockableBinaryTree lt = new LockableBinaryTree();
        lt.insert(40);
        lt.insert(20);
        lt.insert(60);
        lt.insert(10);
        lt.insert(30);
        lt.insert(50);
        lt.insert(70);
        out(lt.isLocked(40), "is 40 locked?");
        out(lt.lock(40), "locking 40");
        out(lt.isLocked(40), "is 40 locked?");
        out(lt.lock(70), "lock 70");
        out(lt.unlock(40), "unlocking 40");
        out(lt.isLocked(40), "is 40 locked?");
        out(lt.lock(70), "locking 70");
        out(lt.lock(40), "locking 40");
        out(lt.lock(10), "locking 10");
        out(lt.unlock(70), "unlocking 70");
        out(lt.lock(40), "locking 40");
        out(lt.unlock(10), "unlocking 10");
        out(lt.lock(40), "locking 40");
    }

    private static void out(boolean value, String msg) {
        System.out.println(msg + " " + value);
    }
}
