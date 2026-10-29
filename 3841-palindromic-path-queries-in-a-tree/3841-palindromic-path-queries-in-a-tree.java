import java.util.*;

class Solution {
    private List<Integer>[] graph;
    private int[] parent, depth, size, heavy, head, pos;
    private int[] tree;
    private int base;

    public List<Boolean> palindromePath(int n, int[][] edges, String s, String[] queries) {
        graph = new ArrayList[n];
        for (int i = 0; i < n; i++) {
            graph[i] = new ArrayList<>();
        }

        for (int[] e : edges) {
            graph[e[0]].add(e[1]);
            graph[e[1]].add(e[0]);
        }

        parent = new int[n];
        depth = new int[n];
        size = new int[n];
        heavy = new int[n];
        head = new int[n];
        pos = new int[n];

        Arrays.fill(parent, -1);
        Arrays.fill(heavy, -1);
        Arrays.fill(size, 1);

        int[] order = new int[n];
        int[] stack = new int[n];
        int top = 0;
        int count = 0;

        stack[top++] = 0;
        parent[0] = 0;

        while (top > 0) {
            int u = stack[--top];
            order[count++] = u;

            for (int v : graph[u]) {
                if (parent[v] == -1) {
                    parent[v] = u;
                    depth[v] = depth[u] + 1;
                    stack[top++] = v;
                }
            }
        }

        int[] bestSize = new int[n];

        for (int i = count - 1; i > 0; i--) {
            int u = order[i];
            int p = parent[u];

            size[p] += size[u];

            if (size[u] > bestSize[p]) {
                bestSize[p] = size[u];
                heavy[p] = u;
            }
        }

        int[] nodeStack = new int[n];
        int[] headStack = new int[n];
        top = 0;
        int timer = 0;

        nodeStack[top] = 0;
        headStack[top++] = 0;

        while (top > 0) {
            top--;
            int start = nodeStack[top];
            int chainHead = headStack[top];

            for (int u = start; u != -1; u = heavy[u]) {
                head[u] = chainHead;
                pos[u] = timer++;

                for (int v : graph[u]) {
                    if (parent[v] == u && v != heavy[u]) {
                        nodeStack[top] = v;
                        headStack[top++] = v;
                    }
                }
            }
        }

        base = 1;
        while (base < n) {
            base <<= 1;
        }

        tree = new int[base * 2];
        char[] chars = s.toCharArray();

        for (int i = 0; i < n; i++) {
            tree[base + pos[i]] = 1 << (chars[i] - 'a');
        }

        for (int i = base - 1; i > 0; i--) {
            tree[i] = tree[i << 1] ^ tree[i << 1 | 1];
        }

        List<Boolean> answer = new ArrayList<>();

        for (String q : queries) {
            String[] parts = q.split(" ");

            if (parts[0].equals("update")) {
                int u = Integer.parseInt(parts[1]);
                char c = parts[2].charAt(0);
                chars[u] = c;
                update(pos[u], 1 << (c - 'a'));
            } else {
                int u = Integer.parseInt(parts[1]);
                int v = Integer.parseInt(parts[2]);
                int mask = 0;

                while (head[u] != head[v]) {
                    if (depth[head[u]] < depth[head[v]]) {
                        int temp = u;
                        u = v;
                        v = temp;
                    }

                    mask ^= rangeXor(pos[head[u]], pos[u]);
                    u = parent[head[u]];
                }

                if (depth[u] > depth[v]) {
                    int temp = u;
                    u = v;
                    v = temp;
                }

                mask ^= rangeXor(pos[u], pos[v]);
                answer.add((mask & (mask - 1)) == 0);
            }
        }

        return answer;
    }

    private void update(int index, int value) {
        int p = base + index;
        tree[p] = value;

        for (p >>= 1; p > 0; p >>= 1) {
            tree[p] = tree[p << 1] ^ tree[p << 1 | 1];
        }
    }

    private int rangeXor(int left, int right) {
        int result = 0;
        left += base;
        right += base;

        while (left <= right) {
            if ((left & 1) == 1) {
                result ^= tree[left++];
            }

            if ((right & 1) == 0) {
                result ^= tree[right--];
            }

            left >>= 1;
            right >>= 1;
        }

        return result;
    }
}