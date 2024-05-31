/*
 * 2021-02-11
 * https://www.acmicpc.net/problem/14938
 * 백준 플로이드 와샬 골드 4
 * 이 것도 택배 문제랑 비슷하게 solved.ac에서 다익스트라 태그로 문제 찾은거지만
 * 노드 갯수가 작고(1 <= n <= 100) 각 노드마다 모든 노드로 향하는
 * 최단 거리를 찾아야 해서 플로이드 와샬로 풀었다.

입력 범위 1 <= n <= 100, 1 <= m <= 15, 1 <= r <= 100

플로이드 와샬로 모든 노드의 최단 거리를 구하고 1번 노드에서 n번 노드까지 반복문을 돌면서
각 노드에서 수색 범위 m내에 아이템 수를 더해서 가장 큰 값 출력
 */

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Arrays;
import java.util.StringTokenizer;

class Main {
    static final int INF = 1501;
    public static void main(String[] args) throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        StringTokenizer st = new StringTokenizer(br.readLine());
        int u, v, c, sum;
        int answer = 0;
        int n = Integer.parseInt(st.nextToken());
        int m = Integer.parseInt(st.nextToken());
        int r = Integer.parseInt(st.nextToken());
        int[] itemsCount = new int[n+1];
        int[][] field = new int[n+1][n+1];
        st = new StringTokenizer(br.readLine());
        for (int idx = 1; idx <= n; idx++) {
            itemsCount[idx] = Integer.parseInt(st.nextToken());
            Arrays.fill(field[idx], INF);
        }
        for (int idx = 1; idx <= n; idx++) {
            field[idx][idx] = 0;
        }
        for (int idx = 0; idx < r; idx++) {
            st = new StringTokenizer(br.readLine());
            u = Integer.parseInt(st.nextToken());
            v = Integer.parseInt(st.nextToken());
            c = Integer.parseInt(st.nextToken());
            field[u][v] = c;
            field[v][u] = c;
        }

        for (int mid = 1; mid <= n; mid++) {
            for (int src = 1; src <= n; src++) {
                for (int dest = 1; dest <= n; dest++) {
                    if (field[src][dest] > field[src][mid] + field[mid][dest]) {
                        field[src][dest] = field[src][mid] + field[mid][dest];
                    }
                }
            }
        }

        for (int src = 1; src <= n; src++) {
            sum = 0;
            for (int dest = 1; dest <= n; dest++) {
                if(field[src][dest] <= m) {
                    sum += itemsCount[dest];
                }
            }
            if(answer < sum) {
                answer = sum;
            }
        }
        System.out.println(answer);
    }
}