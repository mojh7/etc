/*
 * 2021-01-06
 * https://www.acmicpc.net/problem/2194
 * 백준 DFS 골드5
 * N, M(1 ≤ N, M ≤ 500), A, B(1 ≤ A, B ≤ 10), K(0 ≤ K ≤ 100,000)
 *
 */

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.IOException;
import java.util.LinkedList;
import java.util.Queue;
import java.util.StringTokenizer;

class Main {
    // 상, 하, 좌, 우
    private static final int[][] MOVE_POS = new int[][]{{-1, 0}, {1, 0}, {0, -1}, {0, 1}};
    private static final int X = 1;
    private static final int Y = 0;

    private static int n;
    private static int m;
    private static int a;
    private static int b;
    private static int k;
    private static boolean[][] map;
    private static boolean[][] isVisited;
    public static void main(String[] args) throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        BufferedWriter bw = new BufferedWriter(new OutputStreamWriter(System.out));
        StringTokenizer st = new StringTokenizer(br.readLine());
        n = Integer.parseInt(st.nextToken());
        m = Integer.parseInt(st.nextToken());
        a = Integer.parseInt(st.nextToken());
        b = Integer.parseInt(st.nextToken());
        k = Integer.parseInt(st.nextToken());

        int answer = 0;
        int[] start = new int[2];
        int[] end = new int[2];
        map = new boolean[n+1][m+1];
        isVisited = new boolean[n+1][m+1];
        int col, row;

        for(int idx = 0; idx < k; idx++) {
            st = new StringTokenizer(br.readLine());
            row = Integer.parseInt(st.nextToken());
            col = Integer.parseInt(st.nextToken());
            map[row][col] = true;
        }
        st = new StringTokenizer(br.readLine());
        row = Integer.parseInt(st.nextToken());
        col = Integer.parseInt(st.nextToken());
        start[Y] = row;
        start[X] = col;
        st = new StringTokenizer(br.readLine());
        row = Integer.parseInt(st.nextToken());
        col = Integer.parseInt(st.nextToken());
        end[Y] = row;
        end[X] = col;

        answer = bfs(start, end);
        bw.write(Integer.toString(answer));
        bw.newLine();
        bw.flush();
        bw.close();
    }

    public static int bfs(int[] start, int[] end) {
        int[] cur = new int[3];
        int[] next = new int[3];
        isVisited[start[Y]][start[X]] = true;
        Queue<int[]> q = new LinkedList<>();
        q.add(new int[]{start[Y], start[X], 0});
        while (!q.isEmpty()) {
            cur = q.poll();
            if (cur[X] == end[X] && cur[Y] == end[Y]) {
                return cur[2];
            }
            for (int idx = 0; idx < 4; idx++) {
                next[X] = cur[X] + MOVE_POS[idx][X];
                next[Y] = cur[Y] + MOVE_POS[idx][Y];
                if (isAllowedPos(next[Y], next[X])) {
                    isVisited[next[Y]][next[X]] = true;
                    q.add(new int[]{next[Y], next[X], cur[2] + 1});
                }
            }
        }

        return -1;
    }

    public static boolean isAllowedPos(int baseY, int baseX) {
        if (baseX <= 0 || baseX > m || baseY <= 0 || baseY > n || isVisited[baseY][baseX]) {
            return false;
        }
        for (int row = baseY; row < baseY + a; row++) {
            for (int col = baseX; col < baseX + b; col++) {
                if (col <= 0 || col > m || row <= 0 || row > n || map[row][col]) {
                    return false;
                }
            }
        }
        return true;
    }
}