package alg.math;

import java.util.Arrays;
import java.util.concurrent.ThreadLocalRandom;

/**
 * Given a list of non-overlapping axis-aligned rectangles, write a function pick which randomly and uniformly picks an integer 
 * point in the space covered by the rectangles.
 * A point on the perimeter of a rectangle is included in the space covered by the rectangles. 
 * Rectangle is defined as rects[i] = [x1,y1,x2,y2], where [x1, y1] are the integer coordinates of the bottom-left 
 * corner, and [x2, y2] are the integer coordinates of the top-right corner.
 * Example:
 * Input:  
 * ["Solution","pick","pick","pick","pick","pick"]
 * [[[[-2,-2,-1,-1],[1,0,3,0]]],[],[],[],[],[]]
 * Possible Output: 
 * [null,[-1,-2],[2,0],[-2,-1],[3,0],[-2,-2]]
 * 
 * Solution is divided into two steps: selecting a rectangle and selecting point within that rectangle.
 * Each point should have the same probability to be selected - 1 / number of points.
 * If we have 2 rectangles: A with 2 points and B with 8 points then each point has 1/10 chances to be selected.
 * Probability of selecting each rectangle is 1/2 which for points in A means 1/2 * 1/2 -> 1/4 and for points in B
 * 1/2 * 1/8 -> 1/16. This means they are not uniformly selected.
 * In order to achieve that each rectangle needs to be selected with weight that corresponds to number of points it has.
 * We calculated running sum of available points in all rectangles and then select number from 0 to that total.
 * In case of A and B that would be 2, 8 - selecting 1..2 (rectangle A with 2 points) has probability of
 * 2 * 1/10 and selecting 3..10 (rectangle B with 8 points) has probability of 8 * 1/10. This gives for each 
 * point the same probability of 1/10. 2*1/10*1/2 = 8*1/10*1/8 = 1/10.
 */
public class RandomPointInRectangles {

    private int[] pointsSum;
    private int rectsCount;
    private int[][] rectsXYWidthHeight;

    public RandomPointInRectangles(int[][] rects) {
        rectsCount = rects.length;
        pointsSum = new int[rectsCount];
        rectsXYWidthHeight = new int[rectsCount][4];
        // count all points and store their running sum in TreeSet
        int prev = 0;
        for (int i = 0; i < rectsCount; i++) {
            pointsSum[i] = prev + getPointsCount(rects[i]);
            prev = pointsSum[i];
            rectsXYWidthHeight[i][0] = rects[i][0];
            rectsXYWidthHeight[i][1] = rects[i][1];
            rectsXYWidthHeight[i][2] = rects[i][2] - rects[i][0] + 1;
            rectsXYWidthHeight[i][3] = rects[i][3] - rects[i][1] + 1;
        }
    }

    private int getPointsCount(int[] rect) {
        return (rect[2] - rect[0] + 1) * (rect[3] - rect[1] + 1);
    }

    public int[] pick() {
        ThreadLocalRandom tlr = ThreadLocalRandom.current();
        // select rectangle
        int rectIdx = Arrays.binarySearch(pointsSum, tlr.nextInt(pointsSum[rectsCount - 1]));
        rectIdx = (rectIdx < 0) ? (rectIdx + 1) * -1 : rectIdx;
        // select point within rectangle        
        return new int[] { rectsXYWidthHeight[rectIdx][0] + tlr.nextInt(rectsXYWidthHeight[rectIdx][2]),
                rectsXYWidthHeight[rectIdx][1] + tlr.nextInt(rectsXYWidthHeight[rectIdx][3]) };
    }

    public static void main(String... args) {
        RandomPointInRectangles rp = new RandomPointInRectangles(new int[][] { { -2, -2, -1, -1 }, { 1, 0, 3, 0 } });
        for (int i = 0; i < 10; i++) {
            System.out.println(Arrays.toString(rp.pick()));
        }
    }
}
