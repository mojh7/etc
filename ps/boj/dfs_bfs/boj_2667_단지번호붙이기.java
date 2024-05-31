/*
 * 2021-02-03
 * https://www.acmicpc.net/problem/2667
 * 백준 dfs bfs 실버 1
 *
 */

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.LinkedList;
import java.util.PriorityQueue;
import java.util.Queue;

class Main {
    static int[] dy = new int[] { -1, 1, 0, 0 };
    static int[] dx = new int[] { 0, 0, -1, 1 };
    static int[][] grid = new int[25][25];

    static int n;
    static int apartmentComplexNumber;
    static PriorityQueue<Integer> pq;

    public static void main(String[] args) throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        n = Integer.parseInt(br.readLine());
        String input;
        for(int row = 0; row < n; row++) {
            input = br.readLine();
            for(int col = 0; col < n; col++) {
                grid[row][col] = input.charAt(col) - '0';
            }
        }
        apartmentComplexNumber = 2;
        pq = new PriorityQueue<>();
        for(int row = 0; row < n; row++) {
            for(int col = 0; col < n; col++) {
                if(grid[row][col] == 1) bfs(row, col);
            }
        }

        System.out.println(pq.size());
        while (!pq.isEmpty()) {
            System.out.println(pq.poll());
        }
    }

    public static void bfs(int r, int c) {
        int cnt = 1;
        Queue<int[]> q = new LinkedList<>();
        q.add(new int[] { r, c });
        int[] cur;
        grid[r][c] = apartmentComplexNumber;
        while(!q.isEmpty()) {
            cur = q.poll();
            for(int idx = 0; idx < 4; idx++) {
                r = cur[0] + dy[idx];
                c = cur[1] + dx[idx];
                if(r < 0 || r >= n || c < 0 || c >= n || grid[r][c] != 1)
                    continue;
                cnt++;
                grid[r][c] = apartmentComplexNumber;
                q.add(new int[]{ r, c });
            }
        }
        apartmentComplexNumber++;
        pq.add(cnt);
    }
}