/*
 * 2021-02-02
 * https://www.acmicpc.net/problem/14503
 * 백준 구현, 시뮬레이션 골드5
 *
 * 주어진 로직대로 짜고 틀린 점 고쳐나가면서 풀음

방향 0 북, 1 동, 2 남, 3 서

로봇 청소기는 다음과 같이 작동한다.

1. 현재 위치를 청소한다.
2. 현재 위치에서 현재 방향을 기준으로 왼쪽방향부터 차례대로 탐색을 진행한다.
  a. 왼쪽 방향에 아직 청소하지 않은 공간이 존재한다면, 그 방향으로 회전한 다음 한 칸을 전진하고 1번부터 진행한다.
  b. 왼쪽 방향에 청소할 공간이 없다면, 그 방향으로 회전하고 2번으로 돌아간다.
  c. 네 방향 모두 청소가 이미 되어있거나 벽인 경우에는, 바라보는 방향을 유지한 채로 한 칸 후진을 하고 2번으로 돌아간다.
  d. 네 방향 모두 청소가 이미 되어있거나 벽이면서, 뒤쪽 방향이 벽이라 후진도 할 수 없는 경우에는 작동을 멈춘다.

 */

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.StringTokenizer;

class Main {
    static final char DIRTY = '0';
    static final char WALL = '1';
    static final char CLEAN = '2';

    static int n;
    static int m;
    static int answer;
    // 북, 동, 남, 서
    static int[] dy = new int[]{ -1, 0, 1, 0 };
    static int[] dx = new int[]{ 0, 1, 0, -1 };
    static char[][] grid;


    public static void main(String[] args) throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        StringTokenizer st = new StringTokenizer(br.readLine());
        n = Integer.parseInt(st.nextToken());
        m = Integer.parseInt(st.nextToken());
        grid = new char[n][m];
        st = new StringTokenizer(br.readLine());
        int r = Integer.parseInt(st.nextToken());
        int c = Integer.parseInt(st.nextToken());
        int d = Integer.parseInt(st.nextToken());
        for(int row = 0; row < n; row++) {
            st = new StringTokenizer(br.readLine());
            for(int col = 0; col < m; col++) {
                grid[row][col] = st.nextToken().charAt(0);
            }
        }

        answer = simulation(r, c, d);
        System.out.println(answer);
    }

    public static int simulation(int r, int c, int d) {
        int count = 0;
        int ny = 0;
        int nx = 0;
        int back;
        boolean existsDirtyPlace;
        if(grid[r][c] == DIRTY) {
            count++;
            grid[r][c] = CLEAN;
        }
        while(true) {
            existsDirtyPlace = false;
            for(int idx = 0; idx < 4; idx++) {
                d = (d + 3) % 4; // (d - 1 + 4) % 4
                ny = r + dy[d];
                nx = c + dx[d];
                if(grid[ny][nx] == DIRTY) {
                    r = ny;
                    c = nx;
                    count++;
                    grid[r][c] = CLEAN;
                    existsDirtyPlace = true;
                    break;
                }
            }
            if(existsDirtyPlace) continue;
            back = (d + 2) % 4;
            r += dy[back];
            c += dx[back];
            if(grid[r][c] == WALL) break;
        }
        return count;
    }
}