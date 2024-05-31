/*
 * 2021-03-01
 * https://www.acmicpc.net/problem/1922
 * 백준 mst 골드 4
 *
 */

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.PriorityQueue;
import java.util.StringTokenizer;

class Main {
    static int n;
    static int m;
    static int answer;
    public static void main(String[] args) throws IOException {
        PriorityQueue<Edge> edgesPQ = new PriorityQueue<>();
        int u, v, c;
        answer = 0;
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        StringTokenizer st;
        n = Integer.parseInt(br.readLine());
        m = Integer.parseInt(br.readLine());
        for (int idx = 0; idx < m; idx++) {
            st = new StringTokenizer(br.readLine());
            u = Integer.parseInt(st.nextToken());
            v = Integer.parseInt(st.nextToken());
            c = Integer.parseInt(st.nextToken());
            edgesPQ.add(new Edge(u, v, c));
        }
        int[] parents = new int[n+1];
        for (int idx = 1; idx <= n; idx++) {
            parents[idx] = idx;
        }
        int connectedCount = 0;
        int p1, p2;
        Edge edge;
        while (connectedCount < n - 1) {
            edge = edgesPQ.poll();
            p1 = find(parents, edge.u);
            p2 = find(parents, edge.v);
            if(p1 != p2) {
                answer += edge.c;
                union(parents, edge.u, edge.v);
                connectedCount++;
            }
        }

        System.out.println(answer);
    }

    public static int find(int[] parents, int x) {
        int p = parents[x];
        if(p == x) return p;
        p = find(parents, p);
        parents[x] = p;
        return p;
    }

    public static void union(int[] parents, int x, int y) {
        x = find(parents, x);
        y = find(parents, y);
        parents[y] = x;
    }
}

class Edge implements Comparable<Edge>{
    int u;
    int v;
    int c;
    public Edge(int u, int v, int c) {
        this.u = u;
        this.v = v;
        this.c = c;
    }

    @Override
    public int compareTo(Edge o) {
        return c - o.c;
    }
}