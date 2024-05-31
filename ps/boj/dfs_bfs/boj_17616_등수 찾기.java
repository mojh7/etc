/*
 * 2021-01-05
 * https://www.acmicpc.net/problem/17616
 * 백준 DFS
 *
 * x 학생이 이기는 학생 수와 지는 학생 수를 알아야함.
 * 이기는 경우와 지는 경우 따로 edges를 만들어 dfs해서 구함.
 *
 * 입력값 범위가 2 ≤ N ≤ 105, 1 ≤ M ≤ min(N(N-1)/2, 5×105), 1 ≤ X ≤ N 인데
 * N이 커서 인접행렬로 하면 안된다.
 * 습관적으로 ArrayList로 해서 풀었는데 1300ms 나왔고
 * LinkedList로 바꾼 후 제출하니 936ms 나왔다.
 */


import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.IOException;
import java.util.LinkedList;
import java.util.Stack;
import java.util.StringTokenizer;

class Main {
    public static void main(String[] args) throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        BufferedWriter bw = new BufferedWriter(new OutputStreamWriter(System.out));
        StringTokenizer st = new StringTokenizer(br.readLine());
        int n = Integer.parseInt(st.nextToken());
        int m = Integer.parseInt(st.nextToken());
        int x = Integer.parseInt(st.nextToken());
        int[] answer = new int[2];
        LinkedList<Integer>[] winEdges = new LinkedList[n+1];
        LinkedList<Integer>[] loseEdges = new LinkedList[n+1];
        for(int idx = 1; idx <= n; idx++) {
            winEdges[idx] = new LinkedList<>();
            loseEdges[idx] = new LinkedList<>();
        }
        int a, b;
        for (int idx = 0; idx < m; idx++) {
            st = new StringTokenizer(br.readLine());
            a = Integer.parseInt(st.nextToken());
            b = Integer.parseInt(st.nextToken());
            winEdges[a].add(b);
            loseEdges[b].add(a);
        }

        answer[0] = 1 + dfs(loseEdges, n, x);
        answer[1] = n - dfs(winEdges, n, x);

        bw.write(Integer.toString(answer[0]) + " " + Integer.toString(answer[1]));
        bw.newLine();
        bw.flush();
        bw.close();
    }

    public static int dfs(LinkedList<Integer>[] edges, int n, int x) {
        int cnt = 0;
        boolean[] visited = new boolean[n+1];
        Stack<Integer> s = new Stack<>();
        s.push(x);
        int cur = 0;
        while (!s.isEmpty()) {
            cur = s.pop();
            for(int next : edges[cur]) {
                if(!visited[next]) {
                    visited[next] = true;
                    s.push(next);
                    cnt++;
                }
            }
        }
        return cnt;
    }
}