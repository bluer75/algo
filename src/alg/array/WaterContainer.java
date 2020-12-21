package alg.array;

/**
 * Given n non-negative integers a1, a2, ..., an , where each represents a point at coordinate (i, ai). 
 * n vertical lines are drawn such that the two endpoints of line i is at (i, ai) and (i, 0). 
 * Find two lines, which together with x-axis forms a container, such that the container contains the most water.
 * 
 * Example:
 * Input: [1,8,6,2,5,4,8,3,7]
 * Output: 49
 */
public class WaterContainer {
    /**
     * Processing array from both ends at the same time. Lower end limit the water.
     * It keeps moving lower end and calculating water for that pair - O(n). 
     */
    public int maxArea(int[] a) {
        if (a == null || a.length < 2) {
            return 0;
        }
        int max = 0;
        int i = 0;
        int j = a.length - 1;
        while (i < j) {
            max = Math.max(max, (j - i) * Math.min(a[i], a[j]));
            if (a[i] < a[j]) {
                i++;
            } else {
                j--;
            }
        }
        return max;
    }

    /**
     * Brute force checking all possible combinations - 0(n^2).
     */
    public int maxArea2(int[] a) {
        if (a == null || a.length < 2) {
            return 0;
        }
        int max = 0;
        int area = 0;
        for (int i = 0; i < a.length - 1; i++) {
            for (int j = i + 1; j < a.length; j++) {
                area = (j - i) * Math.min(a[i], a[j]);
                max = Math.max(max, area);
            }
        }
        return max;
    }

    public static void main(String... args) {
        int[] a = { 1, 8, 6, 2, 5, 4, 8, 3, 7 };
        System.out.println(new WaterContainer().maxArea(a));
    }
}
