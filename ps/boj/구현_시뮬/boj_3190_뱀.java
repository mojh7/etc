/*
 * 2021-10-22
 * https://www.acmicpc.net/problem/3190
 * 백준 구현, 시뮬레이션 골드 5
 *
처음에 순환배열 짤 때
currDir = (currDir + ((dir[currIdx] == 'D') ? 1 : -1) + 4) % 4;
이렇게 +전체 크기 안 더해주니
0에서 -1될 때 3값이 안되고 -1로 index 참조해서 에러 떴음
 */

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Deque;
import java.util.LinkedList;
import java.util.StringTokenizer;

public class Main {

    private static final char[][] map = new char[101][101];
    private static final char EMPTY = '.';
    private static final char APPLE = 'A';
    private static final char SNAKE = 'S';

    // 우, 하, 좌, 상 {y, x}
    private static final int[][] MOVE = new int[][]{
            {0, 1}, {1, 0}, {0, -1}, {-1, 0}
    };

    public static void main(String[] args) throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        StringTokenizer st;
        Deque<int[]> deque = new LinkedList<>();
        int n = Integer.parseInt(br.readLine());
        int y, x;

        for (int row = 1; row <= n; row++) {
            for (int col = 1; col <= n; col++) {
                map[row][col] = EMPTY;
            }
        }

        map[1][1] = SNAKE;

        int k = Integer.parseInt(br.readLine());
        for (int idx = 0; idx < k; idx++) {
            st = new StringTokenizer(br.readLine());
            y = Integer.parseInt(st.nextToken());
            x = Integer.parseInt(st.nextToken());
            map[y][x] = APPLE;
        }

        int l = Integer.parseInt(br.readLine());
        int[] time = new int[l];
        char[] dir = new char[l];

        for (int idx = 0; idx < l; idx++) {
            st = new StringTokenizer(br.readLine());
            time[idx] = Integer.parseInt(st.nextToken());
            dir[idx] = st.nextToken().charAt(0);
        }

        int currIdx = 0;
        int currTime = 0;
        int currDir = 0;
        y = 1;
        x = 1;
        int[] tail;
        deque.addFirst(new int[]{y, x});

        while (true) {
            currTime++;
            y = y + MOVE[currDir][0];
            x = x + MOVE[currDir][1];

            // 벽 부딪힐 때
            if (x <= 0 || x > n || y <= 0 || y > n) {
                break;
            }

            // 자기자신의 몸과 부딪힐 때
            if (map[y][x] == SNAKE) {
                break;
            }

            // 이동할 칸에 사과가 없으면 꼬리 움직이기
            if (map[y][x] != APPLE) {
                tail = deque.pollLast();
                map[tail[0]][tail[1]] = EMPTY;
            }

            // 머리 이동
            map[y][x] = SNAKE;
            deque.addFirst(new int[]{y, x});

            // 방향 전환, 오른쪽 : D
            if (currIdx < l && currTime >= time[currIdx]) {
                currDir = (currDir + ((dir[currIdx] == 'D') ? 1 : -1) + 4) % 4;
                currIdx++;
            }
        }

        System.out.println(currTime);
    }
}