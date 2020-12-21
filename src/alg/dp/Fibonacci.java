package alg.dp;

import java.util.concurrent.TimeUnit;
import java.util.function.IntFunction;

/**
 * Binets Method (matrix multiplication) and Fibonacci Formula (math equation) can solve this in O(log n) time.
 */
public class Fibonacci {
    /**
     * Recursively calculate value for n - O(2^n)
     */
    static long fibRecursive(int n) {
        if (n <= 1) {
            return n;
        }
        return fibRecursive(n - 1) + fibRecursive(n - 2);
    }

    /**
     * Start from n, recursively calculate n-1 and n-2, keep already calculated
     * values - O(n)
     */
    static long fibDPTopDown(int n, long[] fib) {
        if (n <= 1) {
            return n;
        }
        if (fib[n] != 0) {
            return fib[n];
        } else {
            // recursively calculate n-1 n-2 values and store their values for future
            fib[n] = fibDPTopDown(n - 1, fib) + fibDPTopDown(n - 2, fib);
            return fib[n];
        }
    }

    /**
     * Start from bottom, store calculated values up to n - O(n)
     */
    static long fibDPBottomUp(int n) {
        // bottom-up approach
        long[] fib = new long[n + 1];
        fib[0] = 0;
        fib[1] = 1;
        for (int i = 2; i <= n; i++) {
            fib[i] = fib[i - 1] + fib[i - 2];
        }
        return fib[n];
    }

    /**
     * Start from bottom, store only last two calculated values up to n - O(n)
     */
    static long fibDPOptimal(int n) {
        if (n < 2) {
            return n;
        }
        // bottom-up approach - only two last values n-1 and n-2 are really needed
        long fib_n_2 = 0; // n = 0
        long fib_n_1 = 1; // n = 1
        long fib_n = 0;
        for (int i = 2; i <= n; i++) {
            fib_n = fib_n_1 + fib_n_2;
            fib_n_2 = fib_n_1;
            fib_n_1 = fib_n;
        }
        return fib_n;
    }

    public static void main(String... args) {
        int n = 42;
        time((i) -> fibRecursive(i), n, "fibRecursive(" + n + ")");
        time((i) -> fibDPTopDown(i, new long[n + 1]), n, "fibDPTopDown(" + n + ")");
        time((i) -> fibDPBottomUp(i), n, "fibDPBottomUp(" + n + ")");
        time((i) -> fibDPOptimal(i), n, "fibDPOptimal(" + n + ")");
    }

    private static void time(IntFunction<Long> s, int n, String msg) {
        long nanos = System.nanoTime();
        long value = s.apply(n);
        nanos = System.nanoTime() - nanos;
        System.out.println(msg + "->" + value + " in [" + TimeUnit.NANOSECONDS.toMillis(nanos) + "]ms");
    }
}
