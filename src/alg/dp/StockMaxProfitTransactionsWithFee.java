package alg.dp;

/**
 * You have an array where i-th element is the price of a given stock on day i.
 * You can perform many transaction but not parallel - sell before buying again.
 * Calculate the maximum profit given that each buy-sell transaction has a commission fee.
 * Example:
 * Input: [1, 3, 2, 8, 4, 9] fee = 2
 * Output: 8, buy at 1, sell at 8, buy at 4 and sell at 9.
 * 
 * Solution is based on {@link StockMaxProfitManyTransactions}.
 * 
 * Top-down DP with memoization requires O(n) time and space.
 * Optimized bottom-up reduces space to O(1).
 */
public class StockMaxProfitTransactionsWithFee {

    private Integer memoBuy[];
    private Integer memoSell[];
    private int fee;

    // Top-down with memoization
    public int maxProfitTopDown(int[] prices, int fee) {
        if (prices == null || prices.length < 2) {
            return 0;
        }
        memoBuy = new Integer[prices.length];
        memoSell = new Integer[prices.length];
        this.fee = fee;
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
        return memoSell[i] = Math.max(maxSell(prices, i + 1), prices[i] - fee + maxBuy(prices, i + 1));
    }

    // Bottom-up optimized
    public int maxProfitBottomUpOptimized(int[] prices, int fee) {
        // profit if last operation was buy
        int maxBuy = -prices[0]; // if we buy first there should be no profit
        // profit if last operation was sell
        int maxSell = 0; // start with no profit
        for (int i = 1; i < prices.length; i++) {
            maxSell = Math.max(maxSell, maxBuy + prices[i] - fee);
            maxBuy = Math.max(maxBuy, maxSell - prices[i]);
        }
        return maxSell;
    }

    public static void main(String... args) {
        int[] prices = new int[] { 1, 3, 2, 8, 4, 9 };
        int fee = 2;
        System.out.println(new StockMaxProfitTransactionsWithFee().maxProfitTopDown(prices, fee));
        System.out.println(new StockMaxProfitTransactionsWithFee().maxProfitBottomUpOptimized(prices, fee));
    }
}
