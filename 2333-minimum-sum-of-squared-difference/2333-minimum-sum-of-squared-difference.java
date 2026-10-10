class Solution {
    public long minSumSquareDiff(int[] nums1, int[] nums2, int k1, int k2) {
        int n = nums1.length;
        long k = (long) k1 + k2;
        int[] diff = new int[n];
        int maxDiff = 0;
        long total = 0;

        for (int i = 0; i < n; i++) {
            diff[i] = Math.abs(nums1[i] - nums2[i]);
            maxDiff = Math.max(maxDiff, diff[i]);
            total += diff[i];
        }

        if (k >= total) {
            return 0;
        }

        int low = 0;
        int high = maxDiff;

        while (low < high) {
            int mid = low + (high - low) / 2;
            long needed = 0;

            for (int d : diff) {
                if (d > mid) {
                    needed += d - mid;
                }
            }

            if (needed <= k) {
                high = mid;
            } else {
                low = mid + 1;
            }
        }

        int limit = low;
        int[] adjusted = new int[n];
        long remaining = k;

        for (int i = 0; i < n; i++) {
            adjusted[i] = Math.min(diff[i], limit);
            remaining -= diff[i] - adjusted[i];
        }

        for (int i = 0; i < n && remaining > 0; i++) {
            if (adjusted[i] == limit && limit > 0) {
                adjusted[i]--;
                remaining--;
            }
        }

        long answer = 0;

        for (int d : adjusted) {
            answer += (long) d * d;
        }

        return answer;
    }
}