/*
 * 2021-09-20
 * https://www.acmicpc.net/problem/17406
 * 백준 브루트포스 골드 4
 *

처음에 주어진 회전 명령어들 순서대로 실행하고 주어진 조건(행들 더한 값 중 최소)
만족하는 최소 값 찾는 줄 알았는데 1% 에서 바로 틀림

문제 다시 읽으니 회전 명령어들 순서 임의대로 해도됐음

순열 + 완탐 + 구현(시계, 반시계 방향 회전) 문제
각 순열에서 아직 안 사용한 회전 명령어 정보 대로 시계 방향 회전하고 isVisited 체크하고
permutation(depth + 1) 재귀 돌음 k개랑 같을 때 주어진 조건 판별해서 최소값 체크함
함수에서 빠져나올 때 같은 map 배열을 쓰기 때문에 시계 방향으로 회전 했던 걸 다시
반시계로 돌려줘야되고 isVisitied 도 false로 돌려놔야됨.


 */

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.StringTokenizer;

public class Main {
    // 시계 방향, 우 하 좌 상 {y, x}
    private static int[][] CW_MOVE_POS = new int[][] {
            {0, 1}, {1, 0}, {0, -1}, {-1, 0}
    };

    // 반시계 방향, 하 우 상 좌 {y, x}
    private static int[][] CCW_MOVE_POS = new int[][] {
            {1, 0}, {0, 1}, {-1, 0}, {0, -1}
    };

    private static int[][] map;
    private static int minSum;
    private static int[][] rotateInfo;
    private static boolean[] isVisited;
    private static int n;
    private static int m;
    private static int k;

    public static void main(String[] args) throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        StringTokenizer st = new StringTokenizer(br.readLine());
        int answer = 0;
        minSum = Integer.MAX_VALUE;
        n = Integer.parseInt(st.nextToken());
        m = Integer.parseInt(st.nextToken());
        k = Integer.parseInt(st.nextToken());

        map = new int[n+1][m+1];
        rotateInfo = new int[k][3];
        isVisited = new boolean[k];

        for (int row = 1; row <= n; row++) {
            st = new StringTokenizer(br.readLine());
            for (int col = 1; col <= m; col++) {
                map[row][col] = Integer.parseInt(st.nextToken());
            }
        }

        for(int infoIdx = 0; infoIdx < k; infoIdx++) {
            st = new StringTokenizer(br.readLine());
            for (int idx = 0; idx < 3; idx++) {
                rotateInfo[infoIdx][idx] = Integer.parseInt(st.nextToken());
            }
        }

        permutation(0);
        System.out.println(minSum);
    }

    public static void permutation(int depth) {
        if (depth >= k) {
            int sum = 0;
            for (int row = 1; row <= n; row++) {
                sum = 0;
                for (int col = 1; col <= m; col++) {
                    sum += map[row][col];
                }
                if (sum < minSum) {
                    minSum = sum;
                }
            }

            return;
        }

        for(int idx = 0; idx < k; idx++) {
            if (!isVisited[idx]) {
                rotate(rotateInfo[idx], true);
                isVisited[idx] = true;

                permutation(depth + 1);

                rotate(rotateInfo[idx], false);
                isVisited[idx] = false;
            }
        }
    }

    // isClockwise : true = 시계 방향, false = 반시계 방향
    public static void rotate(int[] info, boolean isClockwise) {
        int y, x, ny, nx, dir, count, currVal, nextVal;
        int r = info[0];
        int c = info[1];
        int s = info[2];

        while (s >= 1) {
            count = 8 * s;
            dir = 0;
            y = r - s;
            x = c - s;
            nextVal = map[y][x];

            while (count > 0) {
                if(isClockwise) {
                    ny = y + CW_MOVE_POS[dir][0];
                    nx = x + CW_MOVE_POS[dir][1];
                } else {
                    ny = y + CCW_MOVE_POS[dir][0];
                    nx = x + CCW_MOVE_POS[dir][1];
                }

                currVal = nextVal;

                if (ny < r - s || ny > r + s || nx < c - s || nx > c + s) {
                    dir++;
                    continue;
                }

                nextVal = map[ny][nx];
                map[ny][nx] = currVal;
                y = ny;
                x = nx;
                count--;
            }

            s--;
        }
    }
}