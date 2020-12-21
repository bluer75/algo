package alg.string;

/**
 * For given text s (size n) and pattern p (size m) find first index where pattern occurs in text. 
 */
public class SubstringFinder {

    /**
     * Brute-force solution - O(n * m).
     * For every mismatch try from scratch for next char in text - backup.
     */
    public int findBF(String s, String p) {
        int n = s.length();
        int m = p.length();
        int i = 0;
        int j = 0;
        while (i < n && j < m) {
            if (s.charAt(i) == p.charAt(j)) {
                // match - move to next char in pattern 
                j++;
            } else {
                // mismatch on j-th element - move both pointers back
                i -= j;
                j = 0;
            }
            i++;
            if (j == m) {
                return i - m;
            }
        }
        return -1;
    }

    /**
     * Knuth–Morris–Pratt - O(n + m).
     * No backup - works with text as stream.
     * Uses preprocessing phase - O(r * m) time/space - to build DFA.
     * Searching phase complexity is independent from the alphabet size. 
     */
    public int findKMP(String s, String p) {
        final int r = 255; // size of alphabet
        int n = s.length();
        int m = p.length();
        int i = 0;
        int j = 0;
        int[][] dfa = buildDfa(p, r);
        while (i < n) {
            // next state/position from DFA
            j = dfa[s.charAt(i)][j];
            i++;
            if (j == m) {
                return i - m;
            }
        }
        return -1;
    }

    /**
     * Builds DFA table for given pattern and alphabet size. 
     * DFA stores for each alphabet letter and each pattern letter next valid state.
     */
    private int[][] buildDfa(String p, int r) {
        int m = p.length();
        int[][] dfa = new int[r][m];
        dfa[p.charAt(0)][0] = 1;
        for (int x = 0, j = 1; j < m; j++) {
            for (int c = 0; c < r; c++) {
                // copy mismatch cases
                dfa[c][j] = dfa[c][x];
            }
            // set match case
            dfa[p.charAt(j)][j] = j + 1;
            // update restart state 
            x = dfa[p.charAt(j)][x];
        }
        return dfa;
    }

    /**
     * Knuth–Morris–Pratt - O(n + m).
     * No backup - works with text as stream.
     * Uses preprocessing phase - O(m) time/space - to build auxiliary array that is
     * independent from the alphabet size. 
     */
    public int findKMP2(String s, String p) {
        int n = s.length();
        int m = p.length();
        int i = 0;
        int j = 0;
        int[] aux = buildAuxArray(p);
        while (i < n) {
            while (j >= 0 && s.charAt(i) != p.charAt(j)) {
                // mismatch
                j = aux[j];
            }
            // match
            i++;
            j++;
            if (j == m) {
                return i - m;
            }
        }
        return -1;
    }

    /**
     * Builds auxiliary array for given pattern.
     * Value for index i is the largest integer smaller than i such that 
     * substring 0..a[i] is a suffix of 0..i - the longest suffix that is also a prefix
     */
    private int[] buildAuxArray(String p) {
        int i = 0;
        int j = -1;
        int m = p.length();
        int[] aux = new int[m + 1];
        aux[0] = -1;
        while (i < m) {
            while (j >= 0 && p.charAt(i) != p.charAt(j)) {
                j = aux[j];
            }
            i++;
            j++;
            aux[i] = j;
        }
        return aux;
    }

    /**
     * Rabin-Karp - O(n + m).
     * No backup needed, it uses rolling hashes to find the match.
     * The hash value is computed once for pattern - O(m) - and compared with each rolling hash 
     * updated in constant - O(1) - time for text. 
     */
    public int findRK(String s, String p) {
        int n = s.length();
        int m = p.length();
        int i = 0;
        long hs = hash(s, m);
        long hp = hash(p, m);
        RM = getRm(m);
        while (i < n - m) {
            // check for string equality only if their hashes match  
            if (hp == hs && s.substring(i, i + m).equals(p)) {
                // match
                return i;
            }
            hs = rehash(hs, s.charAt(i), s.charAt(i + m));
            i++;
        }
        return -1;
    }

    private final int R = 256;
    private final long Q = 10_000_019;
    private long RM;

    /**
     * Calculates the hash for given string of size m.
     * Modular hash function (R - radix/alphabet size, Q - large prime)):
     * hash(w[0 .. m-1]) = (w[0] * R^(m-1) + w[1] * R^(m-2) +···+ w[m-1] * R^0) % Q.
     * Horner's method can be used as well.
     */
    private long hash(String s, int m) {
        long h = 0;
        for (int i = 0; i < m; i++) {
            h = (h * R + s.charAt(i)) % Q;
        }
        return h;
    }

    /**
     * Calculates new hash value based on existing hash by removing the first character (a) and adding new 
     * one at the end (b).
     * rehash(h, a, b) = ((h - a * R^(m-1)) * R + b) % Q.
     * Can be optimized if R^(m-1) % Q is precalculated.
     */
    private long rehash(long h, char a, char b) {
        return ((h - a * RM % Q) * R + b) % Q;
    }

    /**
     * Calculates constant RM R^(m-1) % Q.
     */
    private long getRm(int m) {
        long rm = 1;
        for (int i = 1; i < m; i++) {
            rm = (R * rm) % Q;
        }
        return rm;
    }

    public static void main(String... args) {
        // abcxabcdabxabcdabcdabde
        String s = "abaaabaabc";
        // abcdabd
        String p = "abaab";
        SubstringFinder sf = new SubstringFinder();
        System.out.println(sf.findBF(s, p));
        System.out.println(sf.findKMP(s, p));
        System.out.println(sf.findKMP2(s, p));
        System.out.println(sf.findRK(s, p));
    }
}
