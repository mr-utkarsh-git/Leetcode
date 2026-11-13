
class Solution {
    public long countNonDecreasingSubarrays(int[] nums, int k) {
        int n = nums.length;
        int log = 1;

        while ((1 << log) <= n) log++;

        int[] next = new int[n];
        int[] stack = new int[n];
        int top = 0;

        for (int i = n - 1; i >= 0; i--) {
            while (top > 0 && nums[stack[top - 1]] <= nums[i]) {
                top--;
            }

            next[i] = top == 0 ? n : stack[top - 1];
            stack[top++] = i;
        }

        int[][] up = new int[log][n + 1];
        long[][] sum = new long[log][n + 1];

        for (int p = 0; p < log; p++) {
            up[p][n] = n;
        }

        for (int i = 0; i < n; i++) {
            up[0][i] = next[i];
            sum[0][i] = (long) (next[i] - i) * nums[i];
        }

        for (int p = 1; p < log; p++) {
            for (int i = 0; i < n; i++) {
                int mid = up[p - 1][i];
                up[p][i] = up[p - 1][mid];
                sum[p][i] = sum[p - 1][i] + sum[p - 1][mid];
            }
        }

        long[] prefix = new long[n + 1];

        for (int i = 0; i < n; i++) {
            prefix[i + 1] = prefix[i] + nums[i];
        }

        long answer = 0;
        int left = 0;

        for (int right = 0; right < n; right++) {
            while (left <= right) {
                long maxSum = 0;
                int cur = left;

                for (int p = log - 1; p >= 0; p--) {
                    if (up[p][cur] <= right) {
                        maxSum += sum[p][cur];
                        cur = up[p][cur];
                    }
                }

                maxSum += (long) (right - cur + 1) * nums[cur];

                long operations = maxSum - (prefix[right + 1] - prefix[left]);

                if (operations <= k) {
                    break;
                }

                left++;
            }

            answer += right - left + 1;
        }

        return answer;
    }
}
