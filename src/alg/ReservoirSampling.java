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
        ThreadLocalRandom rnd = ThreadLocalRandom.current();
        int rndValue = 0; // randomly selected value
        int value; // current value
        int n = 1; // element counter
        OfInt ints = stream.iterator();
        while (ints.hasNext()) {
            value = ints.nextInt();
            // probability of selecting first item is 1/1, second is 1/2, third is 1/3,...
            // probability of selecting each number is the same: 1 / n
            if (rnd.nextInt(n++) == 0) { // probability of selecting 0 from [0..n-1] is 1/n
                // last value selected
                rndValue = value;
            }
            System.out.printf("%d %d\n", value, rndValue);
        }
        return rndValue;
    }

    public static void main(String... args) {
        IntStream stream = IntStream.range(1, 100);
        System.out.println(pick(stream));
    }
}
