/*
 * 2021-01-25
 * https://www.acmicpc.net/problem/1987
 * 백준 백트래킹 골드 4
 *
지금까지 지나온 모든 칸에 적혀 있는 알파벳 체크 -> HashSet<Character> pastAlphabets
(0, 0) 위치를 시작으로 상하좌우 dfs로 탐색하고 백트래킹하면서 완탐하여 말이 지날 수 있는
최대의 칸수 출력

시간 2088ms 나왔는데 자바 1페이지 보면 200~500으로 해결함
다른 사람 코드 보고 비교하면
dfs보단 bfs로 하고 지나온 모든 길에 적혀 있던 알파벳을 체크하니깐 isVisited 없어도 될 것 같다.
내껀 dfs 재귀로 짜서 더 느렸던 것 같음.
 */

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.IOException;
import java.util.HashSet;
import java.util.StringTokenizer;

class Main {
    // 상하좌우, {y, x}
    static final int[][] MOVE_POS = new int[][]{{-1, 0}, {1, 0}, {0, -1}, {0, 1}};
    static char[][] grid;
    static boolean[][] isVisited;
    static HashSet<Character> pastAlphabets;
    static int r;
    static int c;
    static int answer;
    public static void main(String[] args) throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        StringTokenizer st = new StringTokenizer(br.readLine());
        r = Integer.parseInt(st.nextToken());
        c = Integer.parseInt(st.nextToken());
        grid = new char[r][c];
        isVisited = new boolean[r][c];
        answer = 0;
        pastAlphabets = new HashSet<>();
        String input;
        for(int row = 0; row < r; row++) {
            input = br.readLine();
            for(int col = 0; col < c; col++) {
                grid[row][col] = input.charAt(col);
            }
        }

        isVisited[0][0] = true;
        pastAlphabets.add(grid[0][0]);
        dfs(0, 0, 1);
        System.out.println(answer);
    }

    public static void dfs(int tx, int ty, int cnt) {
        if(answer < cnt) {
            answer = cnt;
        }
        int nx;
        int ny;

        for(int idx = 0; idx < 4; idx++) {
            ny = ty + MOVE_POS[idx][0];
            nx = tx + MOVE_POS[idx][1];
            if(nx < 0 || nx >= c || ny < 0 || ny >= r ||
                isVisited[ny][nx] || pastAlphabets.contains(grid[ny][nx])) {
                continue;
            }

            isVisited[ny][nx] = true;
            pastAlphabets.add(grid[ny][nx]);
            dfs(nx, ny, cnt + 1);
            pastAlphabets.remove(grid[ny][nx]);
            isVisited[ny][nx] = false;
        }
    }
}