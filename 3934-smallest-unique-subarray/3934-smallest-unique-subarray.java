import java.util.*;

class Solution {
    public int smallestUniqueSubarray(int[] nums) {
        int n = nums.length;
        int N = n + 1;

        int[] a = new int[N];
        for (int i = 0; i < n; i++) {
            a[i] = nums[i];
        }
        a[n] = 0;

        int[] sa = new int[N];
        int[] rank = new int[N];
        int[] count = new int[Math.max(N, 100001)];

        for (int x : a) {
            count[x]++;
        }

        for (int i = 1; i < count.length; i++) {
            count[i] += count[i - 1];
        }

        for (int i = N - 1; i >= 0; i--) {
            sa[--count[a[i]]] = i;
        }

        int classes = 1;
        rank[sa[0]] = 0;

        for (int i = 1; i < N; i++) {
            if (a[sa[i]] != a[sa[i - 1]]) {
                classes++;
            }
            rank[sa[i]] = classes - 1;
        }

        int[] shifted = new int[N];
        int[] newRank = new int[N];

        for (int len = 1; len < N; len <<= 1) {
            for (int i = 0; i < N; i++) {
                shifted[i] = sa[i] - len;
                if (shifted[i] < 0) {
                    shifted[i] += N;
                }
            }

            Arrays.fill(count, 0, classes, 0);

            for (int i = 0; i < N; i++) {
                count[rank[shifted[i]]]++;
            }

            for (int i = 1; i < classes; i++) {
                count[i] += count[i - 1];
            }

            for (int i = N - 1; i >= 0; i--) {
                sa[--count[rank[shifted[i]]]] = shifted[i];
            }

            newRank[sa[0]] = 0;
            int newClasses = 1;

            for (int i = 1; i < N; i++) {
                int cur = sa[i];
                int prev = sa[i - 1];

                if (rank[cur] != rank[prev] ||
                    rank[(cur + len) % N] != rank[(prev + len) % N]) {
                    newClasses++;
                }

                newRank[cur] = newClasses - 1;
            }

            int[] temp = rank;
            rank = newRank;
            newRank = temp;

            classes = newClasses;

            if (classes == N) {
                break;
            }
        }

        int[] suffix = new int[n];
        for (int i = 0; i < n; i++) {
            suffix[i] = sa[i + 1];
        }

        int[] pos = new int[n];
        for (int i = 0; i < n; i++) {
            pos[suffix[i]] = i;
        }

        int[] lcp = new int[n];
        int h = 0;

        for (int i = 0; i < n; i++) {
            int r = pos[i];

            if (r == 0) {
                h = 0;
                continue;
            }

            int j = suffix[r - 1];

            while (i + h < n && j + h < n &&
                   nums[i + h] == nums[j + h]) {
                h++;
            }

            lcp[r] = h;

            if (h > 0) {
                h--;
            }
        }

        int ans = n;

        for (int i = 0; i < n; i++) {
            int r = pos[i];
            int maxLcp = lcp[r];

            if (r + 1 < n) {
                maxLcp = Math.max(maxLcp, lcp[r + 1]);
            }

            if (maxLcp < n - i) {
                ans = Math.min(ans, maxLcp + 1);
            }
        }

        return ans;
    }
}