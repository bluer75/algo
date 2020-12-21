package alg;

class Trie {
    private static class TrieNode {
        boolean wholeWord;
        private TrieNode[] nodes = new TrieNode[26];
    }

    private TrieNode root;

    /** Initialize your data structure here. */
    public Trie() {
        root = new TrieNode();
    }

    /** Inserts a word into the trie. */
    public void insert(String word) {
        TrieNode n = root;
        for (char ch : word.toCharArray()) {
            if (n.nodes[ch - 'a'] == null) {
                TrieNode nn = new TrieNode();
                n.nodes[ch - 'a'] = nn;
            }
            n = n.nodes[ch - 'a'];
        }
        n.wholeWord = true;
    }

    /** Returns if the word is in the trie. */
    public boolean search(String word) {
        TrieNode n = root;
        for (char ch : word.toCharArray()) {
            if (n.nodes[ch - 'a'] == null) {
                return false;
            }
            n = n.nodes[ch - 'a'];
        }
        return n.wholeWord;
    }

    /** Returns if there is any word in the trie that starts with the given prefix. */
    public boolean startsWith(String prefix) {
        TrieNode n = root;
        for (char ch : prefix.toCharArray()) {
            if (n.nodes[ch - 'a'] == null) {
                return false;
            }
            n = n.nodes[ch - 'a'];
        }
        return true;
    }
}