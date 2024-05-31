/*
 * 2021-01-13
 * https://www.acmicpc.net/problem/1916
 * 백준 그래프 골드5
 *
 * 기본 다익스트라 개념으로 풀림
 */

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.PriorityQueue;
import java.util.StringTokenizer;

class Main {
    public static void main(String[] args) throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        StringTokenizer st;
        int n = Integer.parseInt(br.readLine());
        int m = Integer.parseInt(br.readLine());
        int answer = 0;
        int u, v, c;
        ArrayList<Edge>[] edges = new ArrayList[n + 1];
        for(int idx = 1; idx <= n; idx++) {
            edges[idx] = new ArrayList<>();
        }
        for(int idx = 0; idx < m; idx++) {
            st = new StringTokenizer(br.readLine());
            u = Integer.parseInt(st.nextToken());
            v = Integer.parseInt(st.nextToken());
            c = Integer.parseInt(st.nextToken());
            edges[u].add(new Edge(v, c));
        }
        st = new StringTokenizer(br.readLine());
        int start = Integer.parseInt(st.nextToken());
        int dest = Integer.parseInt(st.nextToken());

        answer = dijkstra(edges, n, m, start, dest);

        System.out.println(answer);
    }

    public static int dijkstra(ArrayList<Edge>[] edges, int n, int m, int start, int dest) {
        int[] costs = new int[n + 1];
        Arrays.fill(costs, 1000000000);
        costs[start] = 0;
        PriorityQueue<int[]> pq = new PriorityQueue<int[]>((o1, o2) -> o1[1] - o2[1] < 0 ? -1 : 1);
        pq.add(new int[]{start, 0});
        int[] cur;
        while (!pq.isEmpty()) {
            cur = pq.poll();
            if(costs[cur[0]] < cur[1])
                continue;

            for (Edge edge : edges[cur[0]]) {
                if(costs[cur[0]] + edge.c < costs[edge.v]) {
                    costs[edge.v] = costs[cur[0]] + edge.c;
                    pq.add(new int[]{ edge.v, costs[edge.v] });
                }
            }
        }
        return costs[dest];
    }
}

class Edge {
    int v;
    int c;
    public Edge (int v, int c) {
        this.v = v;
        this.c = c;
    }
}