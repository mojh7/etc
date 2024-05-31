/*
 * 2021-01-31
 * https://www.acmicpc.net/problem/2573
 * 백준 구현, dfs bfs 골드 4
 *

6퍼에서 틀림 -> 시간 초과 -> 해결

--- 수정 한 것
3 5
0 0 0 0 0
0 10 0 10 0
0 0 0 0 0
답 0
같이 빙산이 분리되는 최초의 시간을 출력할 때 빙산에 대한 처리를 하고 나서 체크를 해서
답이 0이 아닌 1이 출력되게 하는 점 고침.

5 7
0 0 0 0 0 0 0
0 2 4 5 3 0 0
0 3 0 2 5 2 0
0 7 6 2 4 0 0
0 0 0 0 0 0 0
답 2

주어진 예제 입력을 1년 후 2년 후 빙산의 높이를 출력해봤는데 0이어야 할 곳에서
0 이상의 값이 나옴. 빙산과 바다 정보 n*m 행렬에서 0과 1 index를 현재 정보와
다음 년도 정보로 구분하여 사용하고 있는데
int[][][] grid = new int[n][m][2];

빙산 높이가 줄어들어 0 이하가 될 때
grid[y][x][0] = 0;
grid[y][x][1] = 0;

현재 년도와 다음 년도 정보 모두 0으로 만들어 줘야 되고 한 연도의 시뮬이 완전히 끝나고 나서
다음 년도의 바다가 될 위치에대한 처리를 해줘야한다.



--- 시간 초과
칸의 개수가 총 1만개 이하이고 시간 제한이 1초라 o(n^2)으로 해 놓은 곳이 있어야
시간 초과가 발생할 것이라고 생각했는데 아무리 봐도 안보임.

두 개의 빙산 덩어리로 나뉘어졌는지 체크할 때 갯수를 출력했는데 몇 개로 나뉘어 졌나가
중요한게 아니라 두 개의 빙산 덩어리로 나뉘어졌는지가 중요한 거라 boolean 리턴 타입으로
바꾸고 bfs로 검사할 때 두 개의 빙산 덩어리로 진입할 때 retrun true 해서 시간을 줄여봤음.
 -> 그러나 시간 초과

다시 보니 빙산 위치를 담았던 List<int[]> icebergs를 LinkedList로 구현 해놓아서
빙산 정보를 담은 리스트를 처음 인덱스부터 마지막 인덱스를 참조할 때
get 부분에서 o(n^2)가 발생 ArrayList로 바꾸니 시간 내에 해결
while (icebergsIdx < icebergs.size()) {
    cur = icebergs.get(icebergsIdx);
---

사용했던 반례
3 5
0 0 0 0 0
0 10 0 10 0
0 0 0 0 0
답 0

3 5
0 0 0 0 0
0 10 5 10 0
0 0 0 0 0
답 3

 */

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.Queue;
import java.util.StringTokenizer;

class Main {
    static final int[][] ADJACENT_POS = {{-1, 0}, {1, 0}, {0, -1}, {0, 1}};
    static int answer;
    static int n;
    static int m;
    static int[][][] grid;
    static boolean[][] visited;
    static List<int[]> icebergs;
    static int curYearIdx;
    static int nextYearIdx;

    public static void main(String[] args) throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        StringTokenizer st = new StringTokenizer(br.readLine());
        n = Integer.parseInt(st.nextToken());
        m = Integer.parseInt(st.nextToken());
        grid = new int[n][m][2];
        visited = new boolean[n][m];
        icebergs = new ArrayList<>();
        curYearIdx = 0;
        nextYearIdx = 1;

        for(int row = 0; row < n; row++) {
            st = new StringTokenizer(br.readLine());
            for(int col = 0; col < m; col++) {
                grid[row][col][0] = Integer.parseInt(st.nextToken());
                if(grid[row][col][0] > 0) {
                    icebergs.add(new int[]{ row, col });
                }
            }
        }

        answer = simulation();
        System.out.println(answer);
    }

    public static int simulation() {
        int year = 0;
        int adjacentSeaSpacesCnt = 0;
        int[] cur;
        int[] next = new int[2];
        int icebergsIdx = 0;

        Queue<int[]> willTurnIntoSeaQueue = new LinkedList<>();

        while (!icebergs.isEmpty()) {
            if(isTwoDetachedIcebergs()) return year;
            icebergsIdx = 0;

            while (icebergsIdx < icebergs.size()) {
                cur = icebergs.get(icebergsIdx);
                adjacentSeaSpacesCnt = 0;
                for(int idx = 0; idx < 4; idx++) {
                    next[0] = cur[0] + ADJACENT_POS[idx][0];
                    next[1] = cur[1] + ADJACENT_POS[idx][1];
                    if(next[0] < 0 || next[0] >= n || next[1] < 0 ||
                        next[1] >= m || grid[next[0]][next[1]][curYearIdx] != 0)
                        continue;
                    adjacentSeaSpacesCnt++;
                }

                grid[cur[0]][cur[1]][nextYearIdx] = grid[cur[0]][cur[1]][curYearIdx] - adjacentSeaSpacesCnt;
                if(grid[cur[0]][cur[1]][nextYearIdx] <= 0) {
                    willTurnIntoSeaQueue.add(icebergs.remove(icebergsIdx--));
                }
                icebergsIdx++;
            }

            while (!willTurnIntoSeaQueue.isEmpty()) {
                cur = willTurnIntoSeaQueue.poll();
                grid[cur[0]][cur[1]][0] = 0;
                grid[cur[0]][cur[1]][1] = 0;
            }

            year++;
            swapYearIdx();
        }

        return 0;
    }

    public static boolean isTwoDetachedIcebergs() {
        int cnt = 0;
        int[] next = new int[2];
        Queue<int[]> q = new LinkedList<>();

        for(int[] pos : icebergs) {
            visited[pos[0]][pos[1]] = false;
        }

        for(int[] cur : icebergs) {
            if(visited[cur[0]][cur[1]]) continue;
            q.add(cur);
            visited[cur[0]][cur[1]] = true;
            if(++cnt >= 2) return true;

            while (!q.isEmpty()) {
                cur = q.poll();
                for(int idx = 0; idx < 4; idx++) {
                    next[0] = cur[0] + ADJACENT_POS[idx][0];
                    next[1] = cur[1] + ADJACENT_POS[idx][1];
                    if(next[0] < 0 || next[0] >= n || next[1] < 0 || next[1] >= m ||
                        visited[next[0]][next[1]] || grid[next[0]][next[1]][curYearIdx] == 0)
                        continue;
                    visited[next[0]][next[1]] = true;
                    q.add(new int[]{ next[0], next[1] });
                }
            }
        }

        return false;
    }

    public static void swapYearIdx() {
        curYearIdx = curYearIdx == 0 ? 1 : 0;
        nextYearIdx = nextYearIdx == 1 ? 0 : 1;
    }
}