import java.util.*;

class Solution {
    public int maxSelectedElements(int[] nums) {
        Arrays.sort(nums);

        Map<Integer, Integer> dp = new HashMap<>();
        int ans = 0;

        for (int x : nums) {
            int prevX = dp.getOrDefault(x, 0);
            int prevXMinus1 = dp.getOrDefault(x - 1, 0);
            int prevXPlus1 = dp.getOrDefault(x + 1, 0);

            dp.put(x, Math.max(prevX, prevXMinus1 + 1));
            dp.put(x + 1, Math.max(prevXPlus1, prevX + 1));

            ans = Math.max(ans, Math.max(dp.get(x), dp.get(x + 1)));
        }

        return ans;
    }
}