package alg.amz;

import java.util.HashMap;
import java.util.Map;

/**
 * Design a data structure that follows the constraints of a Least Recently Used (LRU) cache.
 *
 * Implement the LRUCache class:
 *
 * LRUCache(int capacity) Initialize the LRU cache with positive size capacity.
 * int get(int key) Return the value of the key if the key exists, otherwise return -1.
 * void put(int key, int value) Update the value of the key if the key exists. Otherwise, add the key-value pair to
 * the cache. If the number of keys exceeds the capacity from this operation, evict the least recently used key.
 *
 * The functions get and put must each run in O(1) average time complexity.
 *
 * Solution is based on HashMap and doubly linked that keeps track of accessing order.
 * There are predefined first and last nodes to avoid checking for null.
 */
class LRUCache {

    private static class Node {
        Node prev;
        Node next;
        int key;
        int value;

        Node(Node prev, Node next, int key, int value) {
            this.prev = prev;
            this.next = next;
            this.key = key;
            this.value = value;
        }
    }

    private int capacity;
    private Node first;
    private Node last;
    private Map<Integer, Node> cache;

    public LRUCache(int capacity) {
        cache = new HashMap<>();
        this.capacity = capacity;
        first = new Node(null, null, -1, -1);
        last = new Node(null, null, -1, -1);
        first.next = last;
        last.prev = first;
    }

    public int get(int key) {
        Node node = cache.get(key);
        if (node == null) {
            return -1;
        }
        removeFromList(node);
        addToList(node);
        return node.value;
    }

    public void put(int key, int value) {
        Node node = cache.get(key);
        if (node == null) {
            node = new Node(null, null, key, value);
            if (cache.size() == capacity) {
                cache.remove(last.prev.key);
                removeFromList(last.prev);
            }
        } else {
            node.value = value;
            removeFromList(cache.get(key));
        }
        addToList(node);
        cache.put(key, node);
    }

    private void removeFromList(Node node) {
        Node next = node.next;
        Node prev = node.prev;
        prev.next = next;
        next.prev = prev;
    }

    private void addToList(Node node) {
        Node second = first.next;
        first.next = node;
        node.prev = first;
        second.prev = node;
        node.next = second;
    }

    public static void main(String[] args) {
        LRUCache cache = new LRUCache(2);
        cache.put(1, 1);
        cache.put(2, 2);
        System.out.println(cache.get(1));
        cache.put(3, 3);
        System.out.println(cache.get(2));
        cache.put(4, 4);
        System.out.println(cache.get(1));
        System.out.println(cache.get(3));
        System.out.println(cache.get(4));
    }
}

