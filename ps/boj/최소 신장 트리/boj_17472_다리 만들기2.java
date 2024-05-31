/*
 * 2021-01-24
 * https://www.acmicpc.net/problem/17472
 * 백준 mst 골드 3
 *
배열을 좌측 상단부터 우측 방향으로 읽어나가고 다시 좌측 상단에서 하단 방향으로 읽어나가서
섬들 라벨링(bfs)과 만들 수 있는 모든 다리의 경우를 만들어 준다.(브루트포스)

그 후 kruskal 알고리즘으로 모든 섬을 연결하는 다리 길이의 최솟값 찾기
모든 섬 연결 불가능 하면 -1

union 함수 잘 못 구현하여 틀렸음.

----

채점 시작하자 틀렸다. 범위 문제인가 싶어 사용하는 배열과 INF 값을 더 올려봤지만 문제가 아니였고
아래 처럼 모든 섬 연결 못하는 경우에 -1 출력 못하나 싶었는데 그것도 아니였다.
1 6
1 0 0 1 0 1

1 6
1
0
1
0
0
1

https://www.acmicpc.net/board/view/41802 글에서 다음 반례를 통해 문제 발견
3 10
1 1 0 0 0 0 0 0 1 1
1 0 0 0 0 1 0 0 0 1
0 0 1 0 0 0 1 0 1 1
output: 16
correct answer: -1

union find에서 union 함수 잘 못 구현함.

public static void union(int x, int y) {
    int xp = find(x);
    int yp = find(y);
    if(xp < yp) {
        parents[y] = xp; // 여기 틀림
    } else {
        parents[x] = yp; // 여기 틀림
    }
}

find로 찾은 x, y의 각각 루트노드인 xp와 yp를 연결해야 되는데
루트노드 일지 아닐지 모르는 엄한 x와 y를 가지고 연결함.
3과 4를 연결해서 parent[3] = 3, parent[4] = 3 해놓고
2와 4를 연결할 때 parent[2] = 2, parent[3] = 3, parent[4] = 2 처럼 되버리게 해서
3과 4의 연결을 끊어 놓음 그래서 위에 반례에서 첫번 째 줄을 다시 이으게 만들어버림.

--- 올바른 코드
int xp = find(x);
int yp = find(y);
if(xp < yp) {
    parents[yp] = xp;
} else {
    parents[xp] = yp;
}

 */

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.IOException;
import java.util.LinkedList;
import java.util.PriorityQueue;
import java.util.Queue;
import java.util.StringTokenizer;

class Main {
    // 상하좌우, {y, x}
    static final int[][] MOVE_POS = new int[][]{{-1, 0}, {1, 0}, {0, -1}, {0, 1}};
    static final int INF = 9;

    static int[][] map = new int[10][10];
    static int[][] edges = new int[8][8];
    static int[] parents = new int[8];
    static int islandId;
    static int n;
    static int m;

    public static void main(String[] args) throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        StringTokenizer st = new StringTokenizer(br.readLine());
        n = Integer.parseInt(st.nextToken());
        m = Integer.parseInt(st.nextToken());
        for(int row = 0; row < n; row++) {
            st = new StringTokenizer(br.readLine());
            for(int col = 0; col < m; col++) {
                map[row][col] = Integer.parseInt(st.nextToken());
            }
        }
        initialize();

        int answer = connectAllIsland();
        System.out.println(answer);
    }

    public static void initialize() {
        islandId = 0;
        for(int row = 2; row < 8; row++) {
            for (int col = 2; col < 8; col++) {
                edges[row][col] = INF;
            }
        }
        for(int idx = 2; idx < 8; idx++) {
            parents[idx] = idx;
        }
    }

    public static int connectAllIsland() {
        PriorityQueue<Bridge> pq = new PriorityQueue<>();
        int prevIsland;
        int len;
        int curCellType;

        // 가로 다리
        for(int row = 0; row < n; row++) {
            prevIsland = -1;
            len = 0;
            for(int col = 0; col < m; col++) {
                curCellType = map[row][col];
                if(curCellType == 1) {
                    fillIslandId(map, new int[]{ row, col });
                    curCellType = map[row][col];
                }
                if(prevIsland == -1) {
                    if(curCellType > 1) {
                        prevIsland = curCellType;
                    }
                } else if(curCellType == 0) {
                    len++;
                } else {
                    if(len >= 2 && prevIsland != curCellType && edges[prevIsland][curCellType] > len) {
                        edges[prevIsland][curCellType] = len;
                    }
                    len = 0;
                    prevIsland = curCellType;
                }
            }
        }

        // 세로 다리
        for(int col = 0; col < m; col++) {
            prevIsland = -1;
            len = 0;
            for(int row = 0; row < n; row++) {
                curCellType = map[row][col];
                if(prevIsland == -1) {
                    if(curCellType > 1) {
                        prevIsland = curCellType;
                    }
                } else if(curCellType == 0) {
                    len++;
                } else {
                    if(len >= 2 && prevIsland != curCellType && edges[prevIsland][curCellType] > len) {
                        edges[prevIsland][curCellType] = len;
                    }
                    len = 0;
                    prevIsland = curCellType;
                }
            }
        }

        for(int row = 0; row < islandId; row++) {
            for (int col = 0; col < islandId; col++) {
                if(edges[row+2][col+2] != INF) {
                    pq.add(new Bridge(row+2, col+2, edges[row+2][col+2]));
                }
            }
        }

        return kruskal(pq);
    }

    public static void fillIslandId(int[][] map, int[] pos) {
        int id = 2 + islandId++;
        Queue<int[]> q = new LinkedList<>();
        q.add(pos);
        map[pos[0]][pos[1]] = id;
        int[] cur;
        int[] next = new int[2];
        while (!q.isEmpty()) {
            cur = q.poll();
            for(int idx = 0; idx < 4; idx++) {
                next[0] = cur[0] + MOVE_POS[idx][0];
                next[1] = cur[1] + MOVE_POS[idx][1];
                if(next[0] < 0 || next[0] >= n || next[1] < 0 ||
                        next[1] >= m || map[next[0]][next[1]] != 1) {
                    continue;
                }
                map[next[0]][next[1]] = id;
                q.add(new int[]{next[0], next[1]});
            }
        }
    }

    public static int kruskal(PriorityQueue<Bridge> pq) {
        int lenSum = 0;
        int islandCnt = islandId;
        int selectedBridgesCnt = 0;
        Bridge cur;

        while(!pq.isEmpty()) {
            cur = pq.poll();
            if(selectedBridgesCnt >= islandCnt - 1) break;
            if(find(cur.u) != find(cur.v)) {
                union(cur.u, cur.v);
                lenSum += cur.len;
                selectedBridgesCnt++;
            }
        }

        return selectedBridgesCnt >= islandCnt - 1 ? lenSum : -1;
    }

    public static void union(int x, int y) {
        x = find(x);
        y = find(y);
        if(x < y) {
            parents[y] = x;
        } else {
            parents[x] = y;
        }
    }

    public static int find(int x) {
        int p = parents[x];
        if(x == p) {
            return p;
        }
        p = find(p);
        parents[x] = p;
        return p;
    }
}

class Bridge implements Comparable<Bridge> {
    int u;
    int v;
    int len;

    public Bridge(int u, int v, int len) {
        this.u = u;
        this.v = v;
        this.len = len;
    }

    @Override
    public int compareTo(Bridge o) {
        return this.len - o.len;
    }
}