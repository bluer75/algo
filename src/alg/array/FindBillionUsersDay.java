package alg.array;

/**
 * We have N different apps with different user growth rates. At a given time t, measured in days, 
 * the number of users using an app is g^t (for simplicity we'll allow fractional users), where g is the growth rate for that app. 
 * These apps will all be launched at the same time and no user ever uses more than one of the apps.
 * After how many full days will we have 1 billion total users across the N apps?
 * Example
 * growthRates = [1.1, 1.2, 1.3]
 * output = 79
 * 
 * Solution finds first max value in input array. Calculates number of days for that value and starts binary search
 * to find min value between 1 and maxDays that is greater/equals than billion.
 * 
 * It takes O(log maxDays) time.  
 */
public class FindBillionUsersDay {
    private double calculate(float[] growthRates, int days) {
        double result = 0;
        for (double growthRate : growthRates) {
            result += Math.pow(growthRate, days);
        }
        return result;
    }

    int getBillionUsersDay(float[] growthRates) {
        // Write your code here
        double maxGrowthRate = 0;
        for (double growthRate : growthRates) {
            maxGrowthRate = Math.max(maxGrowthRate, growthRate);
        }
        int maxDays = (int) Math.ceil(9 * Math.log(10) / Math.log(maxGrowthRate));
        int left = 1;
        int right = maxDays;
        int mid = 0;
        double value = 0;
        while (left < right) {
            mid = (right + left) / 2;
            value = calculate(growthRates, mid);
            if (value < 1_000_000_000) {
                left = mid + 1;
            } else if (value > 1_000_000_000) {
                right = mid;
            } else {
                return mid;
            }
        }
        return left;
    }

    public static void main(String... args) {
        System.out.println(new FindBillionUsersDay().getBillionUsersDay(new float[] { 1.1f, 1.2f, 1.3f }));
    }
}
