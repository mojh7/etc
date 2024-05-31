/*
 * 2021-03-02
 * https://www.acmicpc.net/problem/14621
 * 백준 mst 골드 3
 *
도로에 연결된 u학교와 v학교가 남초 학교 와 여초 학교일 경우에만 우선순위 큐에 도로 추가
해서 mst 찾음.
available = isMan[u] ^ isMan[v];
if(available) edgesPQ.add(new Edge(u, v, c));

모든 학교를 연결하는 경로가 없을 경우 -1 출력을 안해서 34% 에서 틀림
if (connectedCount < n - 1) answer = -1; 코드 추가
 */

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.PriorityQueue;
import java.util.StringTokenizer;

class Main {
    static int n;
    static int m;
    public static void main(String[] args) throws IOException {
        PriorityQueue<Edge> edgesPQ = new PriorityQueue<>();
        int u, v, c;
        int answer = 0;
        boolean available;
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        StringTokenizer st = new StringTokenizer(br.readLine());
        n = Integer.parseInt(st.nextToken());
        m = Integer.parseInt(st.nextToken());
        boolean[] isMan = new boolean[n + 1];
        st = new StringTokenizer(br.readLine());
        for (int idx = 1; idx <= n; idx++) {
            isMan[idx] = st.nextToken().equals("M") ? true : false;
        }
        for (int idx = 0; idx < m; idx++) {
            st = new StringTokenizer(br.readLine());
            u = Integer.parseInt(st.nextToken());
            v = Integer.parseInt(st.nextToken());
            c = Integer.parseInt(st.nextToken());
            available = isMan[u] ^ isMan[v];
            if(available) edgesPQ.add(new Edge(u, v, c));
        }
        int[] parents = new int[n + 1];
        for (int idx = 1; idx <= n; idx++) {
            parents[idx] = idx;
        }
        int connectedCount = 0;
        int p1, p2;
        Edge edge;
        while (!edgesPQ.isEmpty() && connectedCount < n - 1) {
            edge = edgesPQ.poll();
            p1 = find(parents, edge.u);
            p2 = find(parents, edge.v);
            if (p1 != p2) {
                answer += edge.c;
                union(parents, edge.u, edge.v);
                connectedCount++;
            }
        }
        if (connectedCount < n - 1) answer = -1;
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
    public int compareTo(Edge other) {
        return c - other.c;
    }
}