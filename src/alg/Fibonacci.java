package alg;

import java.util.concurrent.TimeUnit;

public class Fibonacci {
    public long fibRec(int n) {
        if (n < 0)
            throw new IllegalArgumentException("Must be > 0");
        if (n == 0)
            return 0;
        if (n == 1)
            return 1;
        return fibRec(n - 2) + fibRec(n - 1);
    }

    public long fibDP(int n) {
        long fib[] = new long[n + 1];
        fib[0] = 0;
        fib[1] = 1;
        // bottom-up
        for (int i = 2; i < n + 1; i++) {
            fib[i] = fib[i - 1] + fib[i - 2];
        }
        return fib[n];
    }

    public static void main(String... strings) {
        long start = System.nanoTime();
        System.out.println(new Fibonacci().fibRec(10));
        System.out.println(TimeUnit.NANOSECONDS.toMillis(System.nanoTime() - start));
        start = System.nanoTime();
        System.out.println(new Fibonacci().fibDP(1150));
        System.out.println(TimeUnit.NANOSECONDS.toMillis(System.nanoTime() - start));
    }
}
