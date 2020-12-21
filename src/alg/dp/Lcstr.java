package alg.dp;

/**
 * Longest common substring.
 */
public class Lcstr {

    /**
     * Naive recursive approach - some suffixes are checked more than once.
     * Execution time is exponential - O(2^n) 
     */
    static int find(String x, String y) {
        return find(x, y, x.length() - 1, y.length() - 1, 0);
    }

    /**
     * i and j indicate characters to be checked, max is max LCStr so far 
     */
    private static int find(String x, String y, int i, int j, int max) {
        // base case - all characters have been checked in in x or y
        if (i < 0 || j < 0) {
            return max;
        }
        if (x.charAt(i) == y.charAt(j)) {
            // if characters at i and j match add one to result and check recursively preceding characters
            return max = find(x, y, i - 1, j - 1, max + 1);
        } else {
            // otherwise check recursively both subproblems and take max
            return max = Math.max(max, Math.max(find(x, y, i - 1, j, 0), find(x, y, i, j - 1, 0)));
        }
    }

    /**
     * DP with bottom-up approach.
     * Execution time is proportional to length of x and y - O(m * n)
     */
    static int findDP(String x, String y) {
        int max = 0;
        int m = x.length();
        int n = y.length();
        // res[i][j] - LCStr for x and y ending at i,j
        // array is initialized with 0
        // it is more convenient if it has size length of strings + 1 and we start from index 1
        // it is therefore safe to use i-1 and j-1
        int[][] res = new int[m + 1][n + 1];
        for (int i = 1; i <= m; i++) {
            for (int j = 1; j <= n; j++) {
                if (x.charAt(i - 1) == y.charAt(j - 1)) {
                    res[i][j] = res[i - 1][j - 1] + 1;
                    max = Math.max(res[i][j], max);
                }
            }
        }
        return max;
    }

    public static void main(String... str) {
        String x = "abcdxyzw";
        String y = "xyzabcdw";
        System.out.println(find(x, y));
        System.out.println(findDP(x, y));
    }
}
