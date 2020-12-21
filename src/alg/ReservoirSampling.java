package alg;

import java.util.PrimitiveIterator.OfInt;
import java.util.concurrent.ThreadLocalRandom;
import java.util.stream.IntStream;

/**
 * Pick a Random Element from an Infinite Stream.
 * Naive implementation would be to store elements populated so far and picking up randomly an index. 
 * This would mean space complexity O(n).
 * Using Reservoir Sampling algorithm this can be done with O(1) space.
 * Variation of this problem is to store k random elements which requires O(k) space.
 */
public class ReservoirSampling {
    public static int pick(IntStream stream) {
        int rndValue = 0; // randomly selected value
        int value; // current value
        int n = 0; // element counter
        int p;
        OfInt ints = stream.iterator();
        while (ints.hasNext()) {
            n++;
            value = ints.nextInt();
            // generate random number from 1 to n
            // probability of selecting each number is the same: 1 / n
            // probability of selecting
            p = ThreadLocalRandom.current().nextInt(0, n) + 1; // [1..n]
            if (p == n) {
                // last value selected
                rndValue = value;
            }
            System.out.printf("%d %d %d\n", value, p, rndValue);
        }
        return rndValue;
    }

    public static void main(String... args) {
        IntStream stream = IntStream.range(1, 100);
        System.out.println(pick(stream));
    }
}
