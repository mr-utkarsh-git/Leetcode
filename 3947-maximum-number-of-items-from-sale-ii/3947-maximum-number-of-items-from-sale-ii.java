import java.util.*;

class Solution {
    public int maximumSaleItems(int[][] items, int budget) {
        int n = items.length;
        int[] freq = new int[n + 1];
        int minPrice = Integer.MAX_VALUE;

        for (int[] item : items) {
            freq[item[0]]++;
            minPrice = Math.min(minPrice, item[1]);
        }

        int[] multiples = new int[n + 1];

        for (int f = 1; f <= n; f++) {
            for (int m = f; m <= n; m += f) {
                multiples[f] += freq[m];
            }
        }

        long[] order = new long[n];

        for (int i = 0; i < n; i++) {
            order[i] = ((long) items[i][1] << 32) | (i & 0xffffffffL);
        }

        Arrays.sort(order);

        long spent = 0;
        long bonus = 0;

        for (long entry : order) {
            int i = (int) entry;
            int factor = items[i][0];
            int price = items[i][1];

            int eligible = multiples[factor] - 1;
            long gain = 2L * minPrice - price;

            if (gain <= 0 || eligible == 0) {
                continue;
            }

            long take = Math.min((long) eligible, (budget - spent) / price);

            spent += take * price;
            bonus += take * gain;
        }

        return (int) ((budget + bonus) / minPrice);
    }
}