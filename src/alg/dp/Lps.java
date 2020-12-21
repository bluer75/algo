package alg.dp;

/**
 * Longest Palindromic Subsequence.
 * Naive implementation generates all possible subsequences (n^2), ignore non-palindromic ones and finds the longest one.  
 */
public class Lps {

    /**
     * Recursive implementation (top-down) with memoization.
     * Starts checking first and last character and then moves to second and second to last ones.
     * It takes O(n). 
     */
    public static int find(String str) {
        int[][] memo = new int[str.length()][str.length()];
        int res = find(str.toCharArray(), 0, str.length() - 1, memo);
        return res;
    }

    private static int find(char[] str, int i, int j, int[][] memo) {
        if (i > j) {
            return 0;
        }
        if (i == j) {
            return memo[i][j] = 1;
        }
        if (memo[i][j] != 0) {
            return memo[i][j];
        }
        if (str[i] == str[j]) {
            // characters are the same, check substring without them
            return memo[i][j] = (find(str, i + 1, j - 1, memo) + 2);
        }
        // characters don't match, check substring without first one and substring without last one
        return memo[i][j] = Math.max(find(str, i, j - 1, memo), find(str, i + 1, j, memo));
    }

    public static void main(String... args) {
        String str = "turboventilator";
        System.out.println(find(str));
    }
}
