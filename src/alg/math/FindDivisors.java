package alg.math;

import java.util.LinkedList;
import java.util.List;

/**
 * Find all divisors of given number.
 * Brute force solution checks each number from 1 to n and takes O(n) time.
 * Optimized solution uses the fact that divisors exists in pairs - if i is divisor then n / i is also divisor.
 * This takes O(sqrt(n)) time.  
 */
public class FindDivisors {
    public List<Integer> getDivisors(int n) {
        List<Integer> divisors = new LinkedList<>();
        for (int i = 1; i < Math.sqrt(n) + 1; i++) {
            if (n % i == 0) {
                divisors.add(i);
                if (i * i != n) {
                    divisors.add(n / i);
                }
            }
        }
        return divisors;
    }

    public static void main(String... args) {
        System.out.println(new FindDivisors().getDivisors(12));
        System.out.println(new FindDivisors().getDivisors(144));

    }
}
