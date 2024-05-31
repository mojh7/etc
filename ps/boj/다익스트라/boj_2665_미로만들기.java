/*
 * 2021-02-11
 * https://www.acmicpc.net/problem/2665
 * 백준 다익스트라 골드 4 (bfs 문제 이긴함.)
 *
오랜만에 1제출로 푼 문제. 이전 dfs, bfs 문제들 중 경로 종류에 따라서 배열 값
여러 개 썼던 것 생각나서 비슷하게 품. 벽을 부순 횟수를 따로 저장해서(int[][] changedRoomCount)
다음 방을 갈 때 부순 횟수가 적으면 이동하도록 함(이게 없으면 중복되게 탐색하는 경우가 매우 많아짐)
그런데 changedRoomCount 값이 처음에 0으로 두었더니 벽을 부수지 않은 경우(시작 지점)
에서 탐색할 때 갱신 문제가 조금 껄끄러워서 4가지 경우로 나눠서 처리 했는데
(검은 방, 흰 방, 검은 방 한 번이상 방문, 흰 방 한 번 이상 방문)

다른 사람이 푼 것 보니깐 저렇게 안나누고 changedRoomCount 값을 처음에
MAX 값으로 셋팅하고 벽 부순 횟수 적으면 다음 방 탐색되게 했으면 분기
안나누고 짧게 코딩 할 수 있었음.

*/

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.LinkedList;
import java.util.Queue;

class Main {
    static final int INF = 2555;
    static int[] dy = new int[]{ -1, 1, 0, 0 };
    static int[] dx = new int[]{ 0, 0, -1, 1 };
    static final char BLACK_ROOM = '0';
    static final char WHITE_ROOM = '1';
    static final char BLACK_VISITED = '2';
    static final char WHITE_VISITED = '3';
    static int n;
    static int answer;
    static char[][] rooms;
    static int[][] changedRoomCount;
    public static void main(String[] args) throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        n = Integer.parseInt(br.readLine());
        rooms = new char[n][n];
        changedRoomCount = new int[n][n];
        String input;
        for(int row = 0; row < n; row++) {
            input = br.readLine();
            for(int col = 0; col < n; col++) {
                rooms[row][col] = input.charAt(col);
            }
        }
        answer = INF;
        bfs();
        System.out.println(answer == INF ? 0 : answer);
    }

    public static void bfs() {
        Queue<int[]> q = new LinkedList<>();
        q.add(new int[]{ 0, 0, 0 });
        rooms[0][0] = WHITE_VISITED;
        int[] cur;
        int nx, ny, currentCount, nextCount;
        while (!q.isEmpty()) {
            cur = q.poll();
            currentCount = changedRoomCount[cur[0]][cur[1]];
            if(currentCount < cur[2]) continue;
            for(int idx = 0; idx < 4; idx++) {
                ny = cur[0] + dy[idx];
                nx = cur[1] + dx[idx];
                if(nx < 0 || nx >= n || ny < 0 || ny >= n) continue;
                nextCount = currentCount;
                if(rooms[ny][nx] == WHITE_VISITED) {
                    if(changedRoomCount[ny][nx] <= nextCount) continue;
                } else if(rooms[ny][nx] == WHITE_ROOM) {
                    rooms[ny][nx] = WHITE_VISITED;
                } else if(rooms[ny][nx] == BLACK_VISITED) {
                    if(changedRoomCount[ny][nx] <= ++nextCount) continue;
                } else {
                    nextCount++;
                    rooms[ny][nx] = BLACK_VISITED;
                }
                changedRoomCount[ny][nx] = nextCount;
                if(ny == n - 1 && nx == n - 1) {
                    if(answer > nextCount) {
                        answer = nextCount;
                    }
                    continue;
                }
                q.add(new int[]{ ny, nx, nextCount});
            }
        }
    }
}