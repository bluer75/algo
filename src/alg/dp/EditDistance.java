package alg.dp;

/**
 * Finds minimum edit distance between two given strings. Following operations with the
 * same cost are allowed: 
 * - insert character
 * - remove character
 * - replace character
 */
public class EditDistance {

    /**
     * Naive recursive solution - starts from last characters and tries recursively all possible operations in each step.
     * Finds minimum for all subproblems.
     */
    static int findRecursively(String str1, String str2) {
        return findRecursively(str1, str2, str1.length(), str2.length());
    }

    /**
     * str2/str2 are not modified, only n and m as they indicate size of both strings
     */
    private static int findRecursively(String str1, String str2, int n, int m) {
        if (n == 0) {
            // str1 is empty -> insert all characters form str2
            return m;
        }
        if (m == 0) {
            // str2 is empty -> remove all characters from str2
            return n;
        }
        if (str1.charAt(n - 1) == str2.charAt(m - 1)) {
            // characters are equal so nothing has to be changed here, proceed with next
            // character
            return findRecursively(str1, str2, n - 1, m - 1);
        }
        // if characters are not equal try all operations on str1 character and
        // recursively proceed with next one, find minimum of all operations
        return min(findRecursively(str1, str2, n, m - 1), // insert character in str1 to match character from str2
                findRecursively(str1, str2, n - 1, m), // remove non-matching character from str1
                findRecursively(str1, str2, n - 1, m - 1) // replace character in str1 to match character from str2
        ) + 1;
    }

    /**
     * Bottom-up approach with memoization.
     */
    static int findDPBottomUp(String str1, String str2) {
        int n = str1.length();
        int m = str2.length();
        int[][] dist = new int[n + 1][m + 1]; // dist[i][j] means edit distance for str1(0..i) and str2(0..j)

        // fill distance from bottom
        for (int i = 0; i <= n; i++) {
            for (int j = 0; j <= m; j++) {
                if (i == 0) {
                    // str1 is empty -> insert all characters form str2
                    dist[i][j] = j;
                } else if (j == 0) {
                    // str2 is empty -> remove all characters from str2
                    dist[i][j] = i;
                } else if (str1.charAt(i - 1) == str2.charAt(j - 1)) {
                    // characters are equal so nothing has to be changed here, distance is as for
                    // preceding distance
                    dist[i][j] = dist[i - 1][j - 1];
                } else {
                    // if characters are not equal try all operations on str1 character and
                    // add 1 to previous distance
                    dist[i][j] = 1 + min(dist[i][j - 1], // insert
                            dist[i - 1][j], // remove
                            dist[i - 1][j - 1]); // replace
                }
            }
        }
        return dist[n][m];
    }

    private static int min(int x, int y, int z) {
        return min(min(x, y), z);
    }

    private static int min(int x, int y) {
        return x < y ? x : y;
    }

    public static void main(String... args) {
        String str1 = "abc";
        String str2 = "xyz";
        print(findRecursively(str1, str2), "findRecursively");
        print(findDPBottomUp(str1, str2), "findRecursivelyWithMemo");
    }

    static void print(int value, String msg) {
        System.out.println(msg + " " + value);
    }
}
