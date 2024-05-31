/*
 * 2021-01-30
 * https://www.acmicpc.net/problem/2206
 * 백준 bfs 골4
 * 
메모리 초과와 11% 에서 여러 번 틀리고 질문 글 반례 보고 풀음
질문 글 반례
5 10
0000011000
1101011010
0000000010
1111111110
1111000000

정답: 14;
오답: -1 혹은 18;


5 6
010000
010110
010110
000111
111110
답 16

--- 푼 방법
3차원 배열을 사용 하여 벽 안 부순 경로와 부순 경로 따로 두어 bfs 탐색하고
0, 0 시작해서 도착 지점 (n-1, m-1) 좌표 먼저 탐색된 최단 거리 출력한다.
불가능 할 때는 -1 출력
int[][][] map = new int[n][m][3];
인덱스 값 별로
0 : 벽 안 부순 경로 최단 거리
1 : 한 개의 벽 부순 경로 최단 거리
2 : 해당 좌표가 벽인지 아닌지

1175 배달 문제처럼 그래프, dfs, bfs 관련 문제에서
탐색해야되는 경로의 타입이 여러 개 일 경우 각각의 공간을 따로 사용하고 탐색해서
구해야 하는 경우의 문제가 종종 있다.


--- 수정 했던 것들
StringTokenizer 안 쓰고 String에서 charAt으로 짤라서 입력값 쓰려고 했는데
n과 m의 범위가 두 자리 수를 넘기는 경우가 있어서
5 10 으로 입력이 들어오면 5 1로 짤려 들어가서 틀림
String input = br.readLine();
n = input.charAt(0) - '0'
m = input.charAt(2) - '0';

n = 1, m = 1 일 때
-1이 아닌 1 출력

 */

package com.company;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.LinkedList;
import java.util.Queue;
import java.util.StringTokenizer;

class Main {
    static final int[][] MOVE_POS = {{-1, 0}, {1, 0}, {0, -1}, {0, 1}};
    static int n;
    static int m;
    static int[][][] map;
    static int answer;

    public static void main(String[] args) throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        StringTokenizer st = new StringTokenizer(br.readLine());
        String input;
        n = Integer.parseInt(st.nextToken());
        m = Integer.parseInt(st.nextToken());
        map = new int[n][m][3];
        for(int row = 0; row < n; row++) {
            input = br.readLine();
            for(int col = 0; col < m; col++) {
                map[row][col][2] = input.charAt(col) - '0';
            }
        }

        answer = bfs();
        if(n == 1 && m == 1) {
            answer = 1;
        }
        System.out.println(answer);
    }

    public static int bfs() {
        Queue<int[]> q = new LinkedList<>();
        // y, x, 벽 안 부숨 = 0, 이미 부숨 = 1
        q.add(new int[]{0, 0, 0});
        int[] cur;
        int curDist;
        int y;
        int x;
        int nextBreakCnt = 0;

        while (!q.isEmpty()) {
            cur = q.poll();
            curDist = map[cur[0]][cur[1]][cur[2]];

            for(int idx = 0; idx < 4; idx++) {
                y = cur[0] + MOVE_POS[idx][0];
                x = cur[1] + MOVE_POS[idx][1];
                if(y < 0 || y >= n || x < 0 || x >= m) continue;
                if(cur[2] == 0) {
                    if(map[y][x][2] == 0 && (map[y][x][0] == 0 ||
                        (map[y][x][0] != 0 && curDist + 1 < map[y][x][0]))) {
                        nextBreakCnt = 0;
                    } else if(map[y][x][2] == 1 && (map[y][x][1] == 0 ||
                        (map[y][x][1] != 0 && curDist + 1 < map[y][x][1]))) {
                        nextBreakCnt = 1;
                    } else continue;
                } else {
                    if(map[y][x][2] == 1) continue;
                    if(map[y][x][0] != 0 && curDist + 1 >= map[y][x][0]) continue;
                    if(map[y][x][1] != 0 && curDist + 1 >= map[y][x][1]) continue;
                    nextBreakCnt = 1;
                }

                map[y][x][nextBreakCnt] = curDist + 1;
                if(y == n - 1 && x == m - 1) {
                    return map[y][x][nextBreakCnt] + 1;
                }
                q.add(new int[]{y, x, nextBreakCnt});
            }
        }

        return -1;
    }
}