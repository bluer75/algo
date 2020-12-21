package alg.dp;

import java.util.Arrays;

/**
 * You have an array where i-th element is the price of a given stock on day i.
 * You can perform at most k transactions but not parallel - sell before buying again.
 * Calculate the maximum profit.
 * Example:
 * Input: [3,2,6,5,0,3]
 * Output: 7, buy on day 2, sell on day 3 - profit = 6-2 = 4 -  buy on day 5, sell on day 6 - profit = 3-0 = 3.
 * 
 * Solution is based on {@link StockMaxProfitTwoTransactions}.
 * 
 * DP solution takes (n) time and O(1) space.
 */
public class StockMaxProfitKTransactions {

    public int maxProfit(int kmax, int[] prices) {
        if (kmax == 0 || prices.length < 2) {
            return 0;
        }
        kmax = Math.min(kmax, prices.length);
        int[] maxBuy = new int[kmax + 1]; // profit for i-th element, if last operation was buy
        int[] maxBuyPrev = new int[kmax + 1];
        int[] maxSell = new int[kmax + 1]; // profit for i-th element, if last operation was sell
        int[] maxSellPrev = new int[kmax + 1];
        Arrays.fill(maxBuyPrev, -prices[0]); // if we buy first there should be no profit
        for (int i = 1; i <= prices.length; i++) {
            for (int k = 1; k <= kmax; k++) {
                // do nothing or sell
                maxSell[k] = Math.max(maxSellPrev[k], maxBuyPrev[k] + prices[i - 1]);
                // do nothing or buy
                maxBuy[k] = Math.max(maxBuyPrev[k], maxSell[k - 1] - prices[i - 1]);
            }
            System.arraycopy(maxBuy, 0, maxBuyPrev, 0, maxBuy.length);
            System.arraycopy(maxSell, 0, maxSellPrev, 0, maxSell.length);
        }
        return maxSell[kmax]; // return profit after last sell operation        
    }

    public static void main(String... args) {
        int[] prices = new int[] { 3, 2, 6, 5, 0, 3 };
        System.out.println(new StockMaxProfitKTransactions().maxProfit(2, prices));
    }
}
