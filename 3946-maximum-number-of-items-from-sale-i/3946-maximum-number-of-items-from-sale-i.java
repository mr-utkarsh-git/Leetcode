class Solution {
    public int maximumSaleItems(int[][] items, int budget) {
        int n = items.length;
        int minPrice = Integer.MAX_VALUE;
        int[] value = new int[n];

        for (int i = 0; i < n; i++) {
            minPrice = Math.min(minPrice, items[i][1]);

            int freebies = 0;
            for (int j = 0; j < n; j++) {
                if (i != j && items[j][0] % items[i][0] == 0) {
                    freebies++;
                }
            }

            value[i] = freebies + 1;
        }

        int[] dp = new int[budget + 1];

        for (int i = 0; i < n; i++) {
            int price = items[i][1];

            for (int b = budget; b >= price; b--) {
                dp[b] = Math.max(dp[b], dp[b - price] + value[i]);
            }
        }

        int ans = budget / minPrice;

        for (int spent = 0; spent <= budget; spent++) {
            if (dp[spent] >= 0) {
                int extra = (budget - spent) / minPrice;
                ans = Math.max(ans, dp[spent] + extra);
            }
        }

        return ans;
    }
}