/*
 * 2021-01-25
 * https://www.acmicpc.net/problem/3109
 * 백준 백트래킹 골드 2
 *
한 번 시간 초과, 수정 후 해결

오른쪽 위 대각선, 오르쪽, 오른쪽 아래 대각선 방향으로 탐색을 할 때 빵집을 도달하지 못해서
되돌아올 때 각각 방문했던 노드들의 방문한 사실을 되돌리는게(visits = false) 백트래킹인줄 알았으나 오히려
방문했던 사실을 냅두는게 백트래킹의 의미로 맞았다. 의미를 좀 잘 못 이해하고 있었다.

grid[ny][nx] = BLOCKED;
ret = dfs(nx, ny);
grid[ny][nx] = EMPTY;
이런 식으로 했을 때 오히려 시간 초과가 걸렸는데 이전에 탐색했던 방법을 다음 방법에서
또 탐색하게되서 낭비가 발생한다. 그래서 시간초과가 떴다.

백트래킹은 가능한 모든 조합을 시도하지만 결국은 유망하지 않다고 생각되는
많은 부분집합을 배제하는게 핵심이라

grid[ny][nx] = BLOCKED;
ret = dfs(nx, ny);

처럼 탐색했던 곳을 체크하고 다음에 접근시 한 번 접근했던 경로면 배제하여 해결하여
원하는 조건을 빠르게 찾을 수 있다.

 */

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.IOException;
import java.util.StringTokenizer;

class Main {
    // 오른쪽 위 대각선, 오른쪽, 오른쪽 아래 대각선
    static final int[] MOVE_POS_Y = new int[]{-1, 0 , 1};
    static final char BLOCKED = 'x';
    static final char EMPTY = '.';

    static char[][] grid;
    static int r;
    static int c;
    static int answer;

    public static void main(String[] args) throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        StringTokenizer st = new StringTokenizer(br.readLine());
        r = Integer.parseInt(st.nextToken());
        c = Integer.parseInt(st.nextToken());
        grid = new char[r][c];
        answer = 0;
        String input;
        for(int row = 0; row < r; row++) {
            input = br.readLine();
            for(int col = 0; col < c; col++) {
                grid[row][col] = input.charAt(col);
            }
        }

        for(int y = 0; y < r; y++) {
            dfs(0, y);
        }
        System.out.println(answer);
    }

    public static boolean dfs(int tx, int ty) {
        boolean ret = false;
        if(tx == c - 1) {
            answer++;
            return true;
        }
        int nx = tx + 1;
        int ny;

        for(int idx = 0; idx < 3; idx++) {
            ny = ty + MOVE_POS_Y[idx];
            if(nx < 0 || nx >= c || ny < 0 || ny >= r || grid[ny][nx] == BLOCKED) {
                continue;
            }
            grid[ny][nx] = BLOCKED;
            ret = dfs(nx, ny);
            if(ret) break;
        }
        return ret;
    }
}