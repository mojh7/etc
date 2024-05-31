/*
 * 2021-01-04
 * https://www.acmicpc.net/problem/2146
 * 백준 BFS
 *
 * n x n 크기 이차원 평면상에서 섬 구분을 해야되서 O(n^2) 걸리고
 * 섬 구분 후 bfs로 다리를 만들어 짧은 다리의 길이 찾아야 되서 O(n^2) 걸림.
 */


import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.IOException;
import java.util.LinkedList;
import java.util.Queue;
import java.util.StringTokenizer;

class Main {
    private static final int[][] MOVE_POS = {
            {1, 0}, {-1, 0}, {0, 1}, {0, -1}
    };

    public static void main(String[] args) throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        BufferedWriter bw = new BufferedWriter(new OutputStreamWriter(System.out));
        StringTokenizer st = null;
        int answer = 0;
        int n = Integer.parseInt(br.readLine());
        Point[][] map = new Point[n][n];
        Queue<Point> q = new LinkedList<>();
        int cost;
        for (int row = 0; row < n; row++) {
            st = new StringTokenizer(br.readLine());
            for (int col = 0; col < n; col++) {
                cost = Integer.parseInt(st.nextToken());
                map[row][col] = new Point(col, row, cost);
                if(cost == 1) {
                    q.add(map[row][col]);
                }
            }
        }

        for (int row = 0; row < n; row++) {
            for (int col = 0; col < n; col++) {
                if(map[row][col].islandId == 0 && map[row][col].cost == 1) {
                    fillIdAdjacentPoint(map[row][col], map);
                }
            }
        }

        answer = bfs(map, q);
        bw.write(Integer.toString(answer));
        bw.flush();
        bw.close();
    }

    public static void fillIdAdjacentPoint(Point startPoint, Point[][] map) {
        Queue<Point> q = new LinkedList<>();
        q.add(startPoint);
        startPoint.islandId = Point.GenerateID();
        Point curPoint;
        Point nextPoint;
        int nextPosX;
        int nextPosY;
        int len = map.length;
        while (!q.isEmpty()) {
            curPoint = q.poll();
            for(int idx = 0; idx < 4; idx++) {
                nextPosX = curPoint.x + MOVE_POS[idx][1];
                nextPosY = curPoint.y + MOVE_POS[idx][0];
                if(nextPosX < 0 || nextPosX >= len ||
                        nextPosY < 0 || nextPosY >= len) {
                    continue;
                }
                nextPoint = map[nextPosY][nextPosX];
                if(nextPoint.cost == 1 && nextPoint.islandId == 0) {
                    nextPoint.islandId = startPoint.islandId;
                    q.add(nextPoint);
                }
            }
        }
    }

    public static int bfs(Point[][] map, Queue<Point> q) {
        int result = 1000000;
        Point curPoint;
        Point nextPoint;
        int nextPosX;
        int nextPosY;
        int diff;
        int len = map.length;
        while (!q.isEmpty()) {
            curPoint = q.poll();
            for(int idx = 0; idx < 4; idx++) {
                nextPosX = curPoint.x + MOVE_POS[idx][1];
                nextPosY = curPoint.y + MOVE_POS[idx][0];
                if(nextPosX < 0 || nextPosX >= len ||
                    nextPosY < 0 || nextPosY >= len) {
                    continue;
                }
                nextPoint = map[nextPosY][nextPosX];
                if(nextPoint.cost == 0) {
                    nextPoint.cost = curPoint.cost + 1;
                    nextPoint.islandId = curPoint.islandId;
                    q.add(nextPoint);
                } else if(curPoint.islandId != nextPoint.islandId){
                    diff = curPoint.cost + nextPoint.cost - 2;
                    result = diff < result ? diff : result;
                }
            }
        }
        return result;
    }
}

class Point {
    private static int NEXT_ISLAND_ID = 1;
    int islandId; // 0은 미지정
    int cost;
    int x;
    int y;

    public Point(int x, int y, int cost) {
        this.x = x;
        this.y = y;
        this.cost = cost;
    }

    public static int GenerateID() {
        return NEXT_ISLAND_ID++;
    }
}