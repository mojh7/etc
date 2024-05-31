/*
 * 2021-01-12
 * https://www.acmicpc.net/problem/1175
 * 백준 그래프 골드1
 * 
 * 푸는 중
 * 불가능 할 때 -1 출력 못 봐서 틀림
 * 한 번 방문 했던 곳 또 방문해도 되서 틀림(반례들 답이 달랐음)
 * 그래서 boolean 타입 isVisited 없앴더니 반례 테케는 맞았으나 메모리 초과(q에 너무 많이 담기고 탐색 됨.)
isVisited int형으로 바꿔서 1, 4, 5일 때 방문 안하게 했는데 틀림

3 5
C...C
#...#
.#S#.
출력 : 12

4 5
#####
#C#C#
#.S.#
##.##
출력 : 8

6 6
CC....
......
###...
......
....S.
.....#
출력 : 12

골드 1 치고 간단하고 쉬운 문제가타 보였으나
같은 방향 연속 두 번 연속으로 못가는 조건으로 인해서 어려움.

-----------
해설 보고 기존 방식에서 틀린 점 고치고 풀음

두 개의 c를 c1, c2이라고 하고 s->c1->c2 혹은 s->c2->c1 경로가 있고 그 중에서 최솟값이 정답이 되는데
기존의 방식은 s->c1->c2이 정답인 경로인 예시가 있다고 하면 s->c1으로 진입하는 시점에서 다시 bfs를 호출해서
c1->c2 경로를 찾았는데 이럴 경우 모든 s->c1->c2 모든 경로를 탐색할 수 없어서 최솟값을 못 찾는 반례가 나온다.

반례
7 4
...#
S.C#
#..#
..##
..#.
...C
....
방향 탐색을
상하좌우로 순서로 했을 때는 15
우상하좌 순서로 했을 때는 17이 나온다.

그래서 s->c1를 탐색했을 때 한 경우에 대해서만 c1->c2를 찾는 것이 아닌
s->c1를 가는 모든 경로에서 다시 c1->c2를 찾도록 바꾸고 그 중에서 최솟값을 출력하도록 바꿈.

방문 체크를 할 때 5차원 배열을 써서
x값, y값, 4방향 체크, c를 지나쳤는지 체크, d를 지나쳤는지(c 두 개 중 한 개를 d로 바꿈) 체크
boolean[][][][][] isVisited = new boolean[n][m][4][2][2];

체크를 하면서 C와 D모두 탐색했을 때의 시간 값을 기존의 time 값과 비교하여 최솟값을 출력함.
*/

// 풀이 코드

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.IOException;
import java.util.LinkedList;
import java.util.Queue;
import java.util.StringTokenizer;

class Main {
    private static final int[][] MOVE_POS = { {0, -1}, {0, 1}, {-1, 0}, {1, 0} };
    public static void main(String[] args) throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        StringTokenizer st = new StringTokenizer(br.readLine());
        int n = Integer.parseInt(st.nextToken());
        int m = Integer.parseInt(st.nextToken());
        int answer = 0;
        char[][] map = new char[n][m];
        String input;
        int[] start = new int[]{0, 0, 0, -1, 0, 0};
        boolean isFirstC = true;
        char c;
        for(int row = 0; row < n; row++) {
            input = br.readLine();
            for (int col = 0; col < m; col++) {
                c = input.charAt(col);
                map[row][col] = c;
                if(c == 'S') {
                    start[0] = col;
                    start[1] = row;
                } else if(c == 'C' && isFirstC) {
                    map[row][col] = 'D';
                    isFirstC = false;
                }
            }
        }
        answer = bfs(map, start, n, m);
        System.out.println(answer);
    }

    public static int bfs(char[][] map, int[] start, int n, int m) {
        int time = Integer.MAX_VALUE;
        // x, y, 시간, 방향, c, d
        int[] cur;
        int[] next = new int[6];
        int nx = 0;
        int ny = 0;
        // y, x, 방향, c, d
        boolean[][][][][] isVisited = new boolean[n][m][4][2][2];
        Queue<int[]> q = new LinkedList<>();
        q.add(start);
        while (!q.isEmpty()) {
            cur = q.poll();
            for(int dir = 0; dir < 4; dir++) {
                if(cur[3] == dir)
                    continue;
                nx = cur[0] + MOVE_POS[dir][0];
                ny = cur[1] + MOVE_POS[dir][1];
                if(nx < 0 || nx >= m || ny < 0 || ny >= n ||
                    map[ny][nx] == '#' || isVisited[ny][nx][dir][cur[4]][cur[5]]) {
                    continue;
                }
                isVisited[ny][nx][dir][cur[4]][cur[5]] = true;
                next = new int[]{ nx, ny, cur[2] + 1, dir, cur[4], cur[5]};
                if(map[ny][nx] == 'C') {
                    if(isVisited[ny][nx][dir][0][1]) {
                        time = next[2] < time ? next[2] : time;
                        continue;
                    }
                    next[4] = 1;
                } else if(map[ny][nx] == 'D') {
                    if(isVisited[ny][nx][dir][1][0]) {
                        time = next[2] < time ? next[2] : time;
                        continue;
                    }
                    next[5] = 1;
                }
                q.add(next);
            }
        }
        return time == Integer.MAX_VALUE ? -1 : time;
    }
}








// 틀렸던 소스 코드

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.IOException;
import java.util.LinkedList;
import java.util.Queue;
import java.util.StringTokenizer;

class Main {
    private static final int[][] MOVE_POS = { {0, -1}, {0, 1}, {-1, 0}, {1, 0}};
    public static void main(String[] args) throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        StringTokenizer st = new StringTokenizer(br.readLine());
        int n = Integer.parseInt(st.nextToken());
        int m = Integer.parseInt(st.nextToken());
        int answer = 0;
        char[][] map = new char[n][m];
        String input;
        int[] start = new int[]{0, 0, 0, -1};
        char c;
        for(int row = 0; row < n; row++) {
            input = br.readLine();
            for (int col = 0; col < m; col++) {
                c = input.charAt(col);
                if(c == 'S') {
                    start[0] = col;
                    start[1] = row;
                }
                map[row][col] = c;
            }
        }
        answer = bfs(map, start, n, m, 2);
        System.out.println(answer);
    }

    public static int bfs(char[][] map, int[] start, int n, int m, int cnt) {
        int extraTime = 0;
        // x, y, 시간, 이전 방향
        int[] cur;
        int[] next = new int[4];
        int[][] isVisited = new int[n][m];
        Queue<int[]> q = new LinkedList<>();
        q.add(start);
        while (!q.isEmpty()) {
            cur = q.poll();
            for(int idx = 0; idx < 4; idx++) {
                if(cur[3] == idx)
                    continue;
                next[0] = cur[0] + MOVE_POS[idx][0];
                next[1] = cur[1] + MOVE_POS[idx][1];
                if(next[0] < 0 || next[0] >= m || next[1] < 0 || next[1] >= n ||
                    map[next[1]][next[0]] == '#' || isVisited[next[1]][next[0]] == 5)
                    continue;
                if(map[next[1]][next[0]] == 'C') {
                    map[next[1]][next[0]] = '.';
                    if(cnt == 2) {
                        extraTime = bfs(map, new int[]{ next[0], next[1], 0, idx }, n, m, cnt - 1);
                        if(extraTime == -1) return -1;
                    }
                    return cur[2] + 1 + extraTime;
                }
                isVisited[next[1]][next[0]]++;
                q.add(new int[]{ next[0], next[1], cur[2] + 1, idx});
            }
        }
        return -1;
    }
}