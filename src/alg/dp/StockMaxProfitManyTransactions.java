package alg.dp;

/**
 * You have an array where i-th element is the price of a given stock on day i.
 * You can perform many transaction but not parallel - sell before buying again.
 * Calculate the maximum profit.
 * Example:
 * Input: [7,1,5,3,6,4]
 * Output: 7, buy at 1, sell at 5, buy at 3 and sell at 6.
 * 
 * Brute force solution tries recursively all possible combination and takes O(2^n) time.
 * Recursive tree shows that the same combinations are calculated several times.
 * @formatter:off
 *                         maxBuy(0)
 *                        /         \
 *           +prices[0]  /           \ -prices[0]
 *                      /             \
 *             maxBuy(1)               maxSell(1)
 *  +prices[1] /     \       +prices[1] /     \
 *            /       \ -prices[1]     /       \ -prices[1]
 *           /         \              /         \
 *     maxBuy(2)    maxSell(1)  maxBuy(2)    maxSell(2)
 *     ...
 * @formatter:on
 * 
 * Top-down DP (the same as BF) with memoization reduces time to O(n) and requires O(n) space.
 * 
 * Bottom-up DP solution requires O(n) time/space. Space can be further reduced to O(1) as only last value(s) are
 * needed.
 * 
 * Optimal solution takes O(n) time and O(1) space.
 * We can simply go over prices and keep on adding the profit obtained from every consecutive transaction.
 * Keep on adding the difference between the consecutive numbers of the array if the second number is larger than the
 * first one, and at the total sum we obtain will be the maximum profit.
 * This means, we generate profit if slope is going up, otherwise we ignore the prices.
 * {@linkplain https://leetcode.com/problems/best-time-to-buy-and-sell-stock-ii/solution/}
 */
public class StockMaxProfitManyTransactions {

    private Integer memoBuy[];
    private Integer memoSell[];

    // Top-down with memoization
    public int maxProfitTopDown(int[] prices) {
        if (prices == null || prices.length < 2) {
            return 0;
        }
        memoBuy = new Integer[prices.length];
        memoSell = new Integer[prices.length];
        return maxBuy(prices, 0);
    }

    // finds max profit from element i, starting with buy operation
    private int maxBuy(int[] prices, int i) {
        if (i == prices.length) {
            // cannot buy, return no profit
            return 0;
        }
        if (memoBuy[i] != null) {
            return memoBuy[i];
        }
        // either skip current item or buy it and find max sell
        return memoBuy[i] = Math.max(maxBuy(prices, i + 1), -prices[i] + maxSell(prices, i + 1));
    }

    // finds max profit from element i, starting with sell operation
    private int maxSell(int[] prices, int i) {
        if (i == prices.length) {
            // cannot sell, return no profit
            return 0;
        }
        if (memoSell[i] != null) {
            return memoSell[i];
        }
        // either skip current item or sell it and find max buy
        return memoSell[i] = Math.max(maxSell(prices, i + 1), prices[i] + maxBuy(prices, i + 1));
    }

    // Bottom-up
    public int maxProfitBottomUp(int[] prices) {
        int[] maxBuy = new int[prices.length + 1]; // profit for i-th element, if last operation was buy
        int[] maxSell = new int[prices.length + 1]; // profit for i-th element, if last operation was sell
        // set first values - start with no profit
        maxBuy[0] = -prices[0]; // if we buy first there should be no profit
        maxSell[0] = 0; // start with no profit

        for (int i = 1; i <= prices.length; i++) {
            maxBuy[i] = Math.max(maxBuy[i - 1], maxSell[i - 1] - prices[i - 1]);
            maxSell[i] = Math.max(maxSell[i - 1], maxBuy[i - 1] + prices[i - 1]);
        }
        return maxSell[prices.length]; // return profit after last sell operation
    }

    // Bottom-up optimized
    public int maxProfitBottomUpOptimized(int[] prices) {
        // profit if last operation was buy
        int maxBuy = -prices[0]; // if we sell first there should be no profit
        // profit if last operation was sell
        int maxSell = 0; // start with no profit
        for (int i = 1; i < prices.length; i++) {
            maxSell = Math.max(maxSell, maxBuy + prices[i]);
            maxBuy = Math.max(maxBuy, maxSell - prices[i]);
        }
        return maxSell;
    }

    // Optimal
    public int maxProfitOptimal(int[] prices) {
        int maxProfit = 0;
        for (int i = 1; i < prices.length; i++) {
            if (prices[i] > prices[i - 1])
                maxProfit += prices[i] - prices[i - 1];
        }
        return maxProfit;
    }

    public static void main(String... args) {
        int[] prices = new int[] { 7, 1, 5, 3, 6, 4 };
        System.out.println(new StockMaxProfitManyTransactions().maxProfitTopDown(prices));
        System.out.println(new StockMaxProfitManyTransactions().maxProfitBottomUp(prices));
        System.out.println(new StockMaxProfitManyTransactions().maxProfitBottomUpOptimized(prices));
        System.out.println(new StockMaxProfitManyTransactions().maxProfitOptimal(prices));
    }
}
