
class Solution {
    static final long MOD = 1_000_000_007L;

    static class SegmentTree {
        int n;
        long[] sum;
        long[] squareSum;
        long[] lazy;

        SegmentTree(int n) {
            this.n = n;
            sum = new long[4 * n];
            squareSum = new long[4 * n];
            lazy = new long[4 * n];
        }

        void apply(int node, int left, int right, long value) {
            long length = right - left + 1L;
            squareSum[node] = (squareSum[node]
                    + 2L * value % MOD * sum[node] % MOD
                    + length % MOD * value % MOD * value % MOD) % MOD;
            sum[node] = (sum[node] + length % MOD * value % MOD) % MOD;
            lazy[node] = (lazy[node] + value) % MOD;
        }

        void push(int node, int left, int right) {
            if (lazy[node] == 0 || left == right) {
                return;
            }

            int mid = (left + right) / 2;
            apply(node * 2, left, mid, lazy[node]);
            apply(node * 2 + 1, mid + 1, right, lazy[node]);
            lazy[node] = 0;
        }

        void update(int node, int left, int right, int ql, int qr, long value) {
            if (ql > right || qr < left) {
                return;
            }

            if (ql <= left && right <= qr) {
                apply(node, left, right, value);
                return;
            }

            push(node, left, right);

            int mid = (left + right) / 2;
            update(node * 2, left, mid, ql, qr, value);
            update(node * 2 + 1, mid + 1, right, ql, qr, value);

            sum[node] = (sum[node * 2] + sum[node * 2 + 1]) % MOD;
            squareSum[node] =
                    (squareSum[node * 2] + squareSum[node * 2 + 1]) % MOD;
        }

        void update(int left, int right, long value) {
            if (left <= right) {
                update(1, 0, n - 1, left, right, value);
            }
        }

        long querySquares() {
            return squareSum[1];
        }
    }

    public int sumCounts(int[] nums) {
        int n = nums.length;
        SegmentTree tree = new SegmentTree(n);
        int[] last = new int[100001];
        long answer = 0;

        for (int i = 0; i < n; i++) {
            int previous = last[nums[i]] - 1;
            tree.update(previous + 1, i, 1);
            last[nums[i]] = i + 1;
            answer = (answer + tree.querySquares()) % MOD;
        }

        return (int) answer;
    }
}
