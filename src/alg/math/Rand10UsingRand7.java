package alg.math;

import java.util.concurrent.ThreadLocalRandom;

/**
 * Given a function rand7 which generates a uniform random integer in the range 1 to 7, 
 * write a function rand10 which generates a uniform random integer in the range 1 to 10.
 * 
 * Solution:
 * If we call rand7() twice, we can get results from 1,1 to 7,7 - 49 in total.
 * We can map each tuple to value from range 1..49 using base formula: (roll1 - 1) * 7 + roll2
 * In order return values 1..10 we can consider only values from 1..40 as 40 is divisible by 10.
 * To make rand10() uniform random we need to ignore results greater than 40 - keep trying till we get value 1..40.    
 */
public class Rand10UsingRand7 {
    private int rand7() {
        return ThreadLocalRandom.current().nextInt(7) + 1;
    }

    public int rand10() {
        int outcome = 0;
        int roll1 = 0;
        int roll2 = 0;
        while (true) {
            roll1 = rand7();
            roll2 = rand7();
            // this should work as well roll1 * 7 + roll2 - 7
            outcome = (roll1 - 1) * 7 + roll2;
            if (outcome <= 40) {
                return outcome % 10 + 1;
            }
        }
    }

    public static void main(String... args) {
        Rand10UsingRand7 rand = new Rand10UsingRand7();
        for (int i = 0; i < 10; i++) {
            System.out.println(rand.rand10());
        }
    }
}
