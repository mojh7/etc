/*
 * 2021-09-21
 * https://www.acmicpc.net/problem/13459
 * 백준 bfs 골 3
 *
 * 
좌우좌우좌우 같이 반복되는 구간에서 어떻게 빠져나올까 했는데
문제 조건 자체가 10번 이하로 움직여서 빼낼 수 있으면 1 리턴이라 bfs 최대 10번만 되게 하려고함
visited 움직이는게 1개가 아니라 레드랑 블루 둘다 체크 해야되서 어떻게하지? 하고
크기가 작아서 패스하고 완탐 돌려도 될 것 같아서 하긴 했는데
다른 사람 코드 대비 메모리랑 속도 10배 차이남...

다른 사람 코드 보니 그냥 boolean[][][][] isVisited = new boolean[n][m][n][m]
해서 했는데 어차피 n과 m크기 최대 각각 10이라
10 * 10 * 10 * 10 = 10000의 경우 밖에 없어서 저렇게 해서 중복 경우 제외시키고 bfs
하는게 훨 빨랐을 듯

 */

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.LinkedList;
import java.util.Queue;
import java.util.StringTokenizer;

public class Main {

    private static final char RED = 'R';
    private static final char BLUE = 'B';
    private static final char WALL = '#';
    private static final char EMPTY = '.';
    private static final char GOAL = 'O';
    // {y, x} : 상, 하, 좌, 우
    private static final int[][] MOVE_POS = new int[][]{
            {-1, 0}, {1, 0}, {0, -1}, {0, 1}
    };
    private static final int UP = 0;
    private static final int DOWN = 1;
    private static final int LEFT = 2;
    private static final int RIGHT = 3;

    private static int n;
    private static int m;
    private static int[] red;
    private static int[] blue;
    private static boolean isSuccess;
    private static char[][] map;

    public static void main(String[] args) throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        StringTokenizer st = new StringTokenizer(br.readLine());

        n = Integer.parseInt(st.nextToken());
        m = Integer.parseInt(st.nextToken());
        String input;
        char val;
        map = new char[n][m];
        red = new int[2];
        blue = new int[2];
        isSuccess = false;

        for (int row = 0; row < n; row++) {
            input = br.readLine();
            for (int col = 0; col < m; col++) {
                val = input.charAt(col);

                if (val == RED) {
                    red[0] = row;
                    red[1] = col;
                    map[row][col] = EMPTY;
                } else if (val == BLUE) {
                    blue[0] = row;
                    blue[1] = col;
                    map[row][col] = EMPTY;
                } else {
                    map[row][col] = val;
                }
            }
        }

        bfs();
        System.out.println(isSuccess ? 1 : 0);
    }

    public static void bfs() {
        Queue<int[]> q = new LinkedList<>();

        for (int dir = 0; dir < 4; dir++) {
            // red y, red x, blue y, blue x, dir, count
            q.add(new int[]{ red[0], red[1], blue[0], blue[1], dir, 1});
        }

        int[] curr;
        int dir, count, result;
        while (!q.isEmpty()) {
            curr = q.poll();
            red[0] = curr[0];
            red[1] = curr[1];
            blue[0] = curr[2];
            blue[1] = curr[3];
            dir = curr[4];
            count = curr[5];

            result = tilt(dir);

            if (result == 1) {
                isSuccess = true;
                break;
            } else if (result == 2) {
                continue;
            }

            if(count < 10) {
                for (int nextDir = 0; nextDir < 4; nextDir++) {
                    q.add(new int[]{ red[0], red[1], blue[0], blue[1], nextDir, count + 1});
                }
            }

        }
    }

    public static int tilt(int dir) {
        boolean redResult = false;
        boolean blueResult = false;

        if(dir == UP) {
            // red가 더 위에 있으니 먼저 움직임
            if(red[0] < blue[0]) {
                redResult = move(red, dir, RED);
                blueResult = move(blue, dir, BLUE);
            } else {
                blueResult = move(blue, dir, BLUE);
                redResult = move(red, dir, RED);
            }
        } else if (dir == DOWN) {
            // red가 더 아래에 있으니 먼저 움직임
            if(red[0] > blue[0]) {
                redResult = move(red, dir, RED);
                blueResult = move(blue, dir, BLUE);
            } else {
                blueResult = move(blue, dir, BLUE);
                redResult = move(red, dir, RED);
            }
        } else if (dir == LEFT) {
            // red가 더 좌측에 있으니 먼저 움직임
            if(red[1] < blue[1]) {
                redResult = move(red, dir, RED);
                blueResult = move(blue, dir, BLUE);
            } else {
                blueResult = move(blue, dir, BLUE);
                redResult = move(red, dir, RED);
            }
        } else if (dir == RIGHT) {
            // red가 더 우측에 있으니 먼저 움직임
            if(red[1] > blue[1]) {
                redResult = move(red, dir, RED);
                blueResult = move(blue, dir, BLUE);
            } else {
                blueResult = move(blue, dir, BLUE);
                redResult = move(red, dir, RED);
            }
        }

        map[red[0]][red[1]] = EMPTY;
        map[blue[0]][blue[1]] = EMPTY;

        if(!blueResult) {
            if(!redResult) {
                return 0; // red blue 둘다 구멍에 안 빠짐
            } else {
                return 1; // red만 구멍에 빠짐 도착
            }
        } else {
            return 2; // blue 구멍에 빠짐
        }
    }

    public static boolean move(int[] obj, int dir, char mark) {
        int y, x;

        while (true) {
            y = obj[0] + MOVE_POS[dir][0];
            x = obj[1] + MOVE_POS[dir][1];
            int val = map[y][x];

            if (val == WALL || val == RED || val == BLUE) {
                map[obj[0]][obj[1]]= mark;
                return false;
            }

            if (val == GOAL) {
                return true;
            }

            obj[0] = y;
            obj[1] = x;
         }
    }
}