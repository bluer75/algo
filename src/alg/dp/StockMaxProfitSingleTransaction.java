package alg.dp;

/**
 * You have an array where i-th element is the price of a given stock on day i.
 * You can perform at most one transaction - buy one and sell one share of the stock.
 * Calculate the maximum profit.
 * Note that you cannot sell a stock before you buy one.
 * Example:
 * Input: [7,1,5,3,6,4]
 * Output: 5 buy at 1 sell at 6.
 * 
 * Simple O(n) solution keeps track of min price so far and max profit so far.
 */
public class StockMaxProfitSingleTransaction {

    public int maxProfit(int[] prices) {
        if (prices == null || prices.length < 2) {
            return 0;
        }
        int maxProfit = 0;
        int minBuyAt = prices[0];
        for (int i = 1; i < prices.length; i++) {
            maxProfit = Math.max(maxProfit, prices[i] - minBuyAt);
            minBuyAt = Math.min(minBuyAt, prices[i]);
        }
        return maxProfit;
    }

    public static void main(String... args) {
        System.out.println(new StockMaxProfitSingleTransaction().maxProfit(new int[] { 7, 1, 5, 3, 6, 4 }));
    }
}
