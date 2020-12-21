package alg;

import java.util.concurrent.ThreadLocalRandom;
import java.util.stream.LongStream;

/**
 * The area of a circle is defined as PIr^2. Estimate PI to 3 decimal places using a Monte Carlo method.
 * The basic equation of a circle is x2 + y2 = r2.
 * The Monte Carlo method is a method of solving problems using statistics. 
 * Given the probability, P, that an event will occur in certain conditions, a computer can be 
 * used to generate those conditions repeatedly. The number of times the event occurs divided by the 
 * number of times the conditions are generated should be approximately equal to P.
 * If a circle of radius R is inscribed inside a square with side length 2R, then the area of the circle will be 
 * PI * R^2 and the area of the square will be (2R)^2. So the ratio of the area of the circle to the area of the square will be PI/4.
 * This means that, if you pick N points at random inside the square, approximately N * PI/4 of those points should fall inside the circle.
 * This program picks points at random inside the square. It then checks to see if the point is inside the circle 
 * (it knows it's inside the circle if x^2 + y^2 < R^2, where x and y are the coordinates of the point and R is the 
 * radius of the circle). The program keeps track of how many points it's picked so far (N) and how many of those points 
 * fell inside the circle (M).
 * PI is then approximated as follows:
 * 
 *      4 * M
 * PI = -----
 *        N 
 *
 * Although the Monte Carlo Method is often useful for solving problems in physics and mathematics which cannot be solved by 
 * analytical means, it is a rather slow method of calculating PI. To calculate each significant digit there will have to be 
 * about 10 times as many trials as to calculate the preceding significant digit.
 */
public class MonteCarloPi {
    private static final long DEF_NUM_OF_TRIALS = 1000 * 1000;

    private long numOfTrials;
    private int circlePoints; // M
    private int squarePoints; // N

    MonteCarloPi() {
        this(DEF_NUM_OF_TRIALS);
    }

    MonteCarloPi(long numOfTrials) {
        this.numOfTrials = numOfTrials;
    }

    void calculate() {
        ThreadLocalRandom rnd = ThreadLocalRandom.current();
        LongStream.range(0, numOfTrials).mapToDouble(i -> calculate(rnd.nextDouble(), rnd.nextDouble()))
                .forEach(System.out::println);
    }

    private double calculate(double x, double y) {
        squarePoints++;
        if ((x * x) + (y * y) <= 1d) {
            circlePoints++;
        }
        return 4d * circlePoints / squarePoints;
    }

    public static void main(String... args) {
        new MonteCarloPi().calculate();
    }
}
