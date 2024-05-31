/*
 * 2023-08-11
 * https://www.acmicpc.net/problem/7569
 * bfs 골5
 *
 * 이 토마토 문제는 층(z) 까지 해서 x, y, z 좌표에서 인접한 토마토가 익어감
bfs 함수 진입전에
토마토가 모두 익지 못하면 -1 출력해야 하지만
초기에 이미 익어버린 토마토만 있으면 0으로 출력해야되서
짤 때 주의해야함

그리고 모든 토마토가 익을 때 까지 최소 며칠 걸리는지 판단할 때
큐에서 poll로 { z, y, x, day } 정보 얻어 왔을 때 계산하면 안되고
next 계산할 때 해야 올바른 결과 나옴

 */

package com.company;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.LinkedList;
import java.util.Queue;
import java.util.StringTokenizer;

// 1 익은 토마토, 0 익지 않은 토마토, -1 토마토가 없음

class Main {
    // 위, 아래, 왼쪽, 오른쪽, 앞, 뒤
    private static final int[] dy = new int[]{ -1, 1, 0, 0, 0, 0 };
    private static final int[] dx = new int[]{ 0, 0, -1, 1, 0, 0 };
    private static final int[] dz = new int[]{ 0, 0, 0, 0, -1, 1 };

    public static void main(String[] args) throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        int cnt = 0;
        StringTokenizer st = new StringTokenizer(br.readLine());
        int m = Integer.parseInt(st.nextToken());
        int n = Integer.parseInt(st.nextToken());
        int h = Integer.parseInt(st.nextToken());

        // z, y, x
        int[][][] board = new int[h][n][m];
        Queue<int[]> q = new LinkedList<>();

        for (int z = 0; z < h; z++) {
            for (int y = 0; y < n; y++) {
                st = new StringTokenizer(br.readLine());
                for (int x = 0; x < m; x++) {
                    int val = Integer.parseInt(st.nextToken());
                    if(val == 0) {
                        cnt++;
                    }else if(val == 1) {
                        // z, y, x, day
                        q.add(new int[]{ z, y, x, 0});
                    }
                    board[z][y][x] = val;
                }
            }
        }

        if(cnt == 0) {
            System.out.println(0);
        } else {
            System.out.println(bfs(board, q, cnt));
        }


    }

    private static int bfs(int[][][] board, Queue<int[]> q, int cnt) {
        int day = 0;

        int[] curr;
        int z, y, x;
        int nz, ny, nx;
        int m = board[0][0].length;
        int n = board[0].length;
        int h = board.length;

        while (!q.isEmpty()) {
            curr = q.poll();
            z = curr[0];
            y = curr[1];
            x = curr[2];
            day = curr[3];

            for(int idx = 0; idx < dy.length; idx++) {
                nz = z + dz[idx];
                ny = y + dy[idx];
                nx = x + dx[idx];

                if(nz < 0 || ny < 0 || nx < 0 || nz >= h || ny >= n || nx >= m) {
                    continue;
                }

                if(board[nz][ny][nx] == 0) {
                    cnt--;
                    if (cnt <= 0) {
                        return day + 1;
                    }
                    board[nz][ny][nx] = 1;
                    q.add(new int[]{nz, ny, nx, day + 1});
                }
            }
        }
        return -1;
    }
}
