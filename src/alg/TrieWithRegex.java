package alg;

/**
 * Design a data structure that supports the following two operations:
 * 
 * void addWord(word)
 * boolean search(word)
 * search(word) can search a literal word or a regular expression string containing only letters a-z or .. 
 * A . means it can represent any one letter.
 * 
 * Example:
 * 
 * addWord("bad")
 * addWord("dad")
 * addWord("mad")
 * search("pad") -> false
 * search("bad") -> true
 * search(".ad") -> true
 * search("b..") -> true
 * 
 * Solution is based on Trie with customized search method. Whenever we encounter '.' we search recursively all 
 * children nodes from current node.
 * 
 * Search can take O(k) where there no dots or O(n^k), where is n is number of letters and k is number of dots. 
 */
public class TrieWithRegex {

    private static class TrieNode {
        private boolean isWord;
        private TrieNode[] nodes = new TrieNode[26];
    }

    private TrieNode root;

    /** Initialize your data structure here. */
    public TrieWithRegex() {
        root = new TrieNode();
    }

    /** Adds a word into the data structure. */
    public void addWord(String word) {
        if (word == null || word.length() == 0) {
            return;
        }
        TrieNode node = root;
        char ch = 0;
        for (int i = 0; i < word.length(); i++) {
            ch = word.charAt(i);
            if (node.nodes[ch - 'a'] == null) {
                TrieNode child = new TrieNode();
                node.nodes[ch - 'a'] = child;
            }
            node = node.nodes[ch - 'a'];
        }
        node.isWord = true;
    }

    /** Returns if the word is in the data structure. A word could contain the dot character '.' to represent any one letter. */
    public boolean search(String word) {
        if (word == null || word.length() == 0) {
            return false;
        }
        return search(word, 0, root);
    }

    private boolean search(String word, int index, TrieNode node) {
        char ch = 0;
        for (int i = index; i < word.length(); i++) {
            ch = word.charAt(i);
            if (ch == '.') {
                for (TrieNode n : node.nodes) {
                    if (n != null && search(word, i + 1, n)) {
                        return true;
                    }
                }
                return false;
            } else {
                if (node.nodes[ch - 'a'] == null) {
                    return false;
                }
                node = node.nodes[ch - 'a'];
            }
        }
        return node.isWord;
    }

    public static void main(String... args) {
        TrieWithRegex trie = new TrieWithRegex();
        trie.addWord("aa");
        System.out.println(trie.search("."));
        System.out.println(trie.search(".a"));

    }
}
