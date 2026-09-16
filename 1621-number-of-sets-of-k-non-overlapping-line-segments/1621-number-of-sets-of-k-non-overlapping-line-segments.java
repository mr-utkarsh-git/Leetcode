class Solution {
    public int numberOfSets(int n, int k) {
        final int MOD = 1_000_000_007;

        long[][] dp = new long[k + 1][n];

        for (int i = 0; i < n; i++) {
            dp[0][i] = 1;
        }

        for (int segments = 1; segments <= k; segments++) {
            long active = 0;

            for (int points = 1; points < n; points++) {
                active = (active + dp[segments - 1][points - 1]) % MOD;
                dp[segments][points] = (dp[segments][points - 1] + active) % MOD;
            }
        }

        return (int) dp[k][n - 1];
    }
}