import java.util.*;

class Solution {
    public List<String> removeInvalidParentheses(String s) {
        int left = 0;
        int right = 0;

        for (char c : s.toCharArray()) {
            if (c == '(') {
                left++;
            } else if (c == ')') {
                if (left > 0) {
                    left--;
                } else {
                    right++;
                }
            }
        }

        Set<String> result = new HashSet<>();

        backtrack(s, 0, 0, left, right,
                new StringBuilder(), result);

        return new ArrayList<>(result);
    }

    private void backtrack(String s, int index, int balance,
                            int leftRemove, int rightRemove,
                            StringBuilder current,
                            Set<String> result) {

        if (index == s.length()) {
            if (balance == 0 && leftRemove == 0 && rightRemove == 0) {
                result.add(current.toString());
            }
            return;
        }

        char c = s.charAt(index);

        if (c == '(' && leftRemove > 0) {
            backtrack(s, index + 1, balance,
                    leftRemove - 1, rightRemove,
                    current, result);
        }

        if (c == ')' && rightRemove > 0) {
            backtrack(s, index + 1, balance,
                    leftRemove, rightRemove - 1,
                    current, result);
        }

        current.append(c);

        if (c == '(') {
            backtrack(s, index + 1, balance + 1,
                    leftRemove, rightRemove,
                    current, result);
        } else if (c == ')') {
            if (balance > 0) {
                backtrack(s, index + 1, balance - 1,
                        leftRemove, rightRemove,
                        current, result);
            }
        } else {
            backtrack(s, index + 1, balance,
                    leftRemove, rightRemove,
                    current, result);
        }

        current.deleteCharAt(current.length() - 1);
    }
}