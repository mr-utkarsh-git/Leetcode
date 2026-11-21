
import java.util.*;

class Solution {
    public int[] findSubtreeSizes(int[] parent, String s) {
        int n = parent.length;

        List<Integer>[] tree = new ArrayList[n];
        for (int i = 0; i < n; i++) {
            tree[i] = new ArrayList<>();
        }

        for (int i = 1; i < n; i++) {
            tree[parent[i]].add(i);
        }

        int[] newParent = parent.clone();
        int[] last = new int[26];
        Arrays.fill(last, -1);

        int[] stackNode = new int[n];
        int[] stackIndex = new int[n];
        int[] previous = new int[n];

        int top = 0;
        stackNode[0] = 0;
        last[s.charAt(0) - 'a'] = 0;

        while (top >= 0) {
            int node = stackNode[top];

            if (stackIndex[top] < tree[node].size()) {
                int child = tree[node].get(stackIndex[top]++);
                int c = s.charAt(child) - 'a';

                int ancestor = last[c];
                previous[child] = ancestor;

                if (ancestor != -1) {
                    newParent[child] = ancestor;
                }

                last[c] = child;

                top++;
                stackNode[top] = child;
                stackIndex[top] = 0;
            } else {
                last[s.charAt(node) - 'a'] = previous[node];
                top--;
            }
        }

        List<Integer>[] newTree = new ArrayList[n];
        for (int i = 0; i < n; i++) {
            newTree[i] = new ArrayList<>();
        }

        for (int i = 1; i < n; i++) {
            newTree[newParent[i]].add(i);
        }

        int[] order = new int[n];
        int count = 0;

        int[] queue = new int[n];
        int head = 0, tail = 0;
        queue[tail++] = 0;

        while (head < tail) {
            int node = queue[head++];
            order[count++] = node;

            for (int child : newTree[node]) {
                queue[tail++] = child;
            }
        }

        int[] answer = new int[n];
        Arrays.fill(answer, 1);

        for (int i = n - 1; i > 0; i--) {
            int node = order[i];
            answer[newParent[node]] += answer[node];
        }

        return answer;
    }
}
