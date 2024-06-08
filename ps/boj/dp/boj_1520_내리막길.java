/*
 * 2024-06-08
 * https://www.acmicpc.net/problem/1520
 * 백준 dp + dfs 골드 3
 *

맨 좌측 상단 -> 맨 우측 하단
도착 가능한 이동 경로 수 출력

이동 가능한 조건이 현재 칸의 숫자보다 다음 칸의 숫자가 낮아야 이동 가능

내리막길로 이동하면서 도착지점 까지 단순히 dfs로 체크할 수는 있긴 하지만
당연하게 시간(구현에따라 공간)복잡도 초과

dfs + dp로 풀어야된다

각 칸마다 목적지 까지 도달 가능한 이동 경로 수를 저장하고(dp)

현재 칸에서 이동 가능한(상하좌우) 방향으로 이동하려할 때
dp 값이 0 이면 탐색을 하지만 0 이상인 경우 이미 탐색해봤던 곳이라
다음 탐색 지점에 dp값을 현재 칸에 dp 값에 더하기만 하고 더 이상
탐색 안해도 됨

if(map[nextY][nextX][1] > 0) {
    map[y][x][1] += map[nextY][nextX][1];
    continue;
}

한 33~36% 쯤에서 시간초과 다시 떴는데

이게 도착지점까지 가야 return 1; 하면서 1체크를 하다보니
가봤던 지역이지만 도착지점 못 갔던 칸은 그대로 dp가 0값으로 돼서
다른 칸에서 이러한 칸들에 진입할 때 또다시 dfs를 하게됨
이미 탐색했던 지역이라 더 이상 안해도 되나 불필요하게 해서 시간초과이고

탐색했던 지점이지만 도착 지점까지 못 갔던 곳은 dp값을 -1로 설정해서
따로 if문으로 체크해서 탐색 못하게 하니 통과

 */

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.util.StringTokenizer;

class Main {
    // {y, x}, 상 하 좌 우
    private static final int[][] MOVE_POS = new int[][]{
            {-1, 0}, {1, 0}, {0, -1}, {0, 1}
    };

    private static int[][][] map;
    private static int m;
    private static int n;

    public static void main(String[] args) throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        BufferedWriter bw = new BufferedWriter(new OutputStreamWriter(System.out));
        StringTokenizer st;
        int answer = 0;
        st = new StringTokenizer(br.readLine());
        m = Integer.parseInt(st.nextToken()); // 세로 크기
        n = Integer.parseInt(st.nextToken()); // 가로 크기
        map = new int[m][n][2];

        for(int y = 0; y < m; y++) {
            st = new StringTokenizer(br.readLine());

            for(int x = 0; x < n; x++) {
                map[y][x][0] = Integer.parseInt(st.nextToken());
            }
        }

        answer = dfs(0, 0);
        
        bw.write(Integer.toString(answer));
        bw.newLine();
        bw.flush();
        bw.close();
    }

    public static int dfs(int y, int x) {
        if(y == m - 1 && x == n - 1) {
            return 1;
        }

        for(int[] pos : MOVE_POS) {
            int nextY = y + pos[0];
            int nextX = x + pos[1];

            // 범위 넘어가는 것 체크, 현재 위치 높이 <= 다음 위치 높이면 안됨
            // 한 번 방문했지만, 도착지점 까지 못가는 루트 map[nextY][nextX][1] = -1;
            if(nextY < 0 || nextY >= m || nextX < 0 || nextX >= n ||
                map[y][x][0] <= map[nextY][nextX][0] || map[nextY][nextX][1] == -1) {
                continue;
            }

            if(map[nextY][nextX][1] > 0) {
                map[y][x][1] += map[nextY][nextX][1];
                continue;
            }

            map[y][x][1] += dfs(nextY, nextX);
        }

        if(map[y][x][1] == 0) {
            map[y][x][1] = -1;
            return 0;
        }

        return map[y][x][1];
    }
}