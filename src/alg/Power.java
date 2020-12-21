package alg;

/**
 * Implement pow(x, n), which calculates x raised to the power n.
 * Naive recursive approach would crash with stack overflow.
 * We calculate x^(n/2) and use it in formula:
 * x^n = x^(n/2) * x^(n/2) or if n is odd x^(n/2) * x^(n/2) * x -> O(log n).  
 */
public class Power {
    public double myPow(double x, int n) {
        if (n == 0) {
            return 1;
        }
        if (n < 0) {
            return 1 / pow(x, n * (-1));
        }
        return pow(x, n);
    }

    // n is positive
    private double pow(double x, int n) {
        if (n == 0) {
            return 1;
        }
        double tmp = pow(x, n / 2);
        if (n % 2 == 0) {
            return tmp * tmp;
        } else if (n > 0) {
            return x * tmp * tmp;
        } else {
            return (tmp * tmp) / x; // the same as 1 / (x * tmp * tmp)
        }
    }

    public static void main(String... args) {
        System.out.println(new Power().pow(2, 24));
    }
}
