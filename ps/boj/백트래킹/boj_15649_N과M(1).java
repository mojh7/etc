/*
 * 2021-01-06
 * https://www.acmicpc.net/problem/15649
 * 백준 백트래킹 실버 3
 *
 */

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.IOException;
import java.util.StringTokenizer;

class Main {
    static boolean[] visited;
    private static BufferedWriter bw;
    private static StringBuilder sb;
    private static int n;
    private static int m;
    public static void main(String[] args) throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        bw = new BufferedWriter(new OutputStreamWriter(System.out));

        StringTokenizer st = new StringTokenizer(br.readLine());
        n = Integer.parseInt(st.nextToken());
        m = Integer.parseInt(st.nextToken());
        visited = new boolean[n+1];
        sb = new StringBuilder();
        for(int start = 1; start <= n; start++) {
            dfs(start, 0);
        }
        bw.flush();
        bw.close();
    }

    public static void dfs(int cur, int cnt) throws IOException {
        sb.append(cur + " ");
        visited[cur] = true;
        if(++cnt == m) {
            bw.write(sb.toString());
            bw.newLine();
            visited[cur] = false;
            sb.delete(sb.length() - 2, sb.length());
            return;
        }

        for(int next = 1; next <= n; next++) {
            if(!visited[next]) {
                dfs(next, cnt);
            }
        }

        visited[cur] = false;
        sb.delete(sb.length() - 2, sb.length());
    }
}