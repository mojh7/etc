/*
 * https://www.acmicpc.net/problem/4991
 * 완탐, 비트마스킹, bfs 골드1
 *

더러운 칸 어디 부터 방문해서 청소하느냐에 따라서 이동 횟수가 달라짐
최솟값을 알기 위해서 bfs로 가장 먼저 모든 더러운 곳을 청소한 경우에 이동 횟수 반환
방문할 수 없는 더러운 칸이 있으면 -1 리턴

무한 루프가 돌 수 있으니 visited 체크를 해주는데 한 번 갔던 곳이라도 다시 방문할 수 있어서
단순히 visited[y][x] 로만 하면 안됨
그리고 더러운 칸을 이미 갔던 곳이라도 재방문 할 수도 있어서 더러운 칸에 대해 0~9 넘버링을 하고
해당 더러운 칸을 방문한 적이 있는가에 대한 값은 비트 값으로 체크

y, x 좌표 값 이외에 더러운 칸을 방문 했던 갯수로 차원을 늘려서 visited 체크를 하려 했는데
n개의 더러운 칸 중에서 중간에 a칸 혹은 b칸을 먼저 방문했을 때
중간에는 a칸을 간 경우보다 b칸을 간 경우가 이동횟수가 적었지만 최종적으로 모든 더러운 칸을
방문했을 때는 a칸을 먼저 간 경우가 최솟값일 수 있어서
더러운 칸 방문을 체크하는 비트 값 기준으로 체크를 해야함

반례 찾기 위한 예시
7 7
...x...
.o.x..*
.......
xx....x
.......
*..x..*
...x...
0 0

5 5
o.x.*
..x..
x...x
..x..
*.x.*
0 0

6 1
*..o**
0 0

6 4
x*xxxx
x.xxxx
x.xxxx
*o..**
0 0
 */

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.util.LinkedList;
import java.util.Queue;
import java.util.StringTokenizer;

class Main {
    // 상 하 좌 우
    private static final int[] dy = new int[]{ -1, 1, 0, 0 };
    private static final int[] dx = new int[]{ 0, 0, -1, 1 };

    private static final char ROBOT = 'o';
    private static final char DIRTY = '*';
    private static final char BLOCK = 'x';

    public static void main(String[] args) throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        BufferedWriter bw = new BufferedWriter(new OutputStreamWriter(System.out));
        StringTokenizer st;
        char[][] room = new char[20][20];
        int[][][] visited = new int[20][20][1 << 10];
        int w, h;
        int[] start = new int[2];
        int dirtyTileCnt = 0;

        while(true) {
            st = new StringTokenizer(br.readLine());
            w = Integer.parseInt(st.nextToken());
            h = Integer.parseInt(st.nextToken());

            if(w == 0 && h == 0) {
                break;
            }

            dirtyTileCnt = 0;

            for (int y = 0; y < h; y++) {
                String line = br.readLine();
                for (int x = 0; x < w; x++) {
                    char val = line.charAt(x);
                    if (val == DIRTY) {
                        val = (char) ('0' + dirtyTileCnt++);
                    } else if(val == ROBOT) {
                        start[0] = y;
                        start[1] = x;
                    }
                    room[y][x] = val;
                }
            }

            int answer = bfs(room, visited, start, dirtyTileCnt, w, h);
            bw.write(Integer.toString(answer));
            bw.newLine();

            // clear visited
            for (int y = 0; y < h; y++) {
                for (int x = 0; x < w; x++) {
                    for (int z = 0; z < (1 << dirtyTileCnt); z++) {
                        visited[y][x][z] = 0;
                    }
                }
            }
        }

        bw.flush();
        bw.close();
    }

    static private int bfs(char[][] room, int[][][] visited, int[] start,
                           int dirtyTileCnt, int w, int h) {
        int[] curr;
        int nextY;
        int nextX;
        int nextMoveCnt;
        int nextCleaningBit;
        int dirtyTileId;
        Queue<int[]> q = new LinkedList<>();
        // y, x, 이동 횟수, 더러운 칸 청소 체크 bit
        q.add(new int[]{start[0], start[1], 0, 0});

        while (!q.isEmpty()) {
            curr = q.poll();

            for(int idx = 0; idx < 4; idx++) {
                nextY = curr[0] + dy[idx];
                nextX = curr[1] + dx[idx];

                if (nextY < 0 || nextY >= h || nextX < 0 ||
                        nextX >= w || room[nextY][nextX] == BLOCK) {
                    continue;
                }

                nextMoveCnt = curr[2] + 1;
                nextCleaningBit = curr[3];
                dirtyTileId = room[nextY][nextX] - '0';

                if (dirtyTileId >= 0 && dirtyTileId <= 9) {
                    nextCleaningBit = curr[3] | (1 << (dirtyTileId));

                    if (Integer.bitCount(nextCleaningBit) == dirtyTileCnt) {
                        return nextMoveCnt;
                    }
                }

                if (visited[nextY][nextX][nextCleaningBit] == 0 ||
                        nextMoveCnt < visited[nextY][nextX][nextCleaningBit]) {
                    visited[nextY][nextX][nextCleaningBit] = nextMoveCnt;
                    q.add(new int[]{nextY, nextX, nextMoveCnt, nextCleaningBit});
                }
            }
        }

        return -1;
    }
}