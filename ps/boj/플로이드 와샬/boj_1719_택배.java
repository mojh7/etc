/*
 * 2021-02-10
 * https://www.acmicpc.net/problem/1719
 * 백준 플로이드 와샬 골드 4 
 * solved.ac에서 다익스트라 문제로 찾은거지만 n이 200이고
 * 모든 노드에서 최단 경로를 찾아야 해서 플로이드 와샬로 품
n은 200이하 자연수, m은 10000이하 자연수
두 집하장을 오가는 시간은 1000이하

n*n 배열에 간선 정보를 입력받았으나 한 집하장에서 다른 집하장으로
가는 최단경로에서 가장 먼저 거쳐야 하는 집하장을 알아야되고 출력하는 문제라
int[][][] routeTable로 선언해서 n*n*2 배열에서 [src][dest][0] 은 가중치를 담고
[src][dest][1] 은 가장 먼저 거쳐야 하는 집하장 정보를 담았다.

 */

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.StringTokenizer;

class Main {
    static final int INF = 199001;
    static int n;
    static int m;
    static int[][][] routeTable;
    public static void main(String[] args) throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        StringTokenizer st = new StringTokenizer(br.readLine());
        n = Integer.parseInt(st.nextToken());
        m = Integer.parseInt(st.nextToken());
        int u, v, t;
        routeTable = new int[n+1][n+1][2];
        for (int row = 1; row <= n; row++) {
            for (int col = 1; col <= n; col++) {
                routeTable[row][col][0] = INF;
                routeTable[row][col][1] = -1;
            }
        }
        for (int idx = 1; idx <= n; idx++) {
            routeTable[idx][idx][0] = 0;
        }
        for (int idx = 0; idx < m; idx++) {
            st = new StringTokenizer(br.readLine());
            u = Integer.parseInt(st.nextToken());
            v = Integer.parseInt(st.nextToken());
            t = Integer.parseInt(st.nextToken());
            routeTable[u][v][0] = t;
            routeTable[u][v][1] = v;
            routeTable[v][u][0] = t;
            routeTable[v][u][1] = u;

        }

        int newTime;
        for (int mid = 1; mid <= n; mid++) {
            for (int src = 1; src <= n; src++) {
                for (int dest = 1; dest <= n; dest++) {
                    newTime = routeTable[src][mid][0] + routeTable[mid][dest][0];
                    if(routeTable[src][dest][0] > newTime) {
                        routeTable[src][dest][0] = newTime;
                        routeTable[src][dest][1] = routeTable[src][mid][1] == -1
                                                    ? dest : routeTable[src][mid][1];
                    }
                }
            }
        }

        StringBuilder sb = new StringBuilder();
        for (int row = 1; row <= n; row++) {
            for(int col = 1; col <= n; col++) {
                sb.append(routeTable[row][col][1] == -1
                        ? "-" : routeTable[row][col][1]).append(" ");
            }
            sb.append("\n");
        }
        System.out.println(sb.toString());
    }
}