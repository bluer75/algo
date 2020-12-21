package alg.dp;

/**
 * There exists a staircase with N steps, and you can climb up either 1 or 2 steps at a time. 
 * Given N, write a function that returns the number of unique ways you can climb the staircase. 
 * The order of the steps matters.
 * This is really variation of Fibonacci problem.  
 */
public class Steps {
    static int find(int n) {
        if (n < 2) {
            return 1;
        }
        return find(n - 1) + find(n - 2);
    }

    /**
     * Bottom-up. Start with level 3 -> steps(3) = steps(2) + steps(1)
     */
    static int findDP(int n) {
        int n_1 = 2; // steps(2) -> 2
        int n_2 = 1; // steps(1) -> 1
        int res = 0;
        for (int i = 3; i <= n; i++) {
            res = n_1 + n_2;
            n_2 = n_1;
            n_1 = res;
        }
        return res;
    }

    public static void main(String... args) {
        System.out.println(find(4));
        System.out.println(findDP(4));
    }
}
