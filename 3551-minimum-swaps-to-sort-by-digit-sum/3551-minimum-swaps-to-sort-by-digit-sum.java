
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

class Solution {
    public int minSwaps(int[] nums) {
        int n = nums.length;
        int[][] arr = new int[n][2];

        for (int i = 0; i < n; i++) {
            arr[i][0] = nums[i];
            arr[i][1] = digitSum(nums[i]);
        }

        Arrays.sort(arr, (a, b) -> {
            if (a[1] != b[1]) {
                return Integer.compare(a[1], b[1]);
            }
            return Integer.compare(a[0], b[0]);
        });

        Map<Integer, Integer> position = new HashMap<>();

        for (int i = 0; i < n; i++) {
            position.put(nums[i], i);
        }

        int swaps = 0;

        for (int i = 0; i < n; i++) {
            int target = arr[i][0];

            if (nums[i] == target) {
                continue;
            }

            int j = position.get(target);

            position.put(nums[i], j);
            position.put(target, i);

            int temp = nums[i];
            nums[i] = nums[j];
            nums[j] = temp;

            swaps++;
        }

        return swaps;
    }

    private int digitSum(int num) {
        int sum = 0;

        while (num > 0) {
            sum += num % 10;
            num /= 10;
        }

        return sum;
    }
}
