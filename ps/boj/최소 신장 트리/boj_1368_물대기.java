/*
 * 2021-03-03
 * https://www.acmicpc.net/problem/1368
 * 백준 mst 골드 2
 *
프림 알고리즘으로 풀어보고 있는데 계속 18%에서 틀린다. 

해설 보고도 내가 짠 알고리즘에 반례를 못 찾아서 크루스칼 알고리즘으로 바꾸고
해설에 나온 방식으로 짤까 하다가 다시 반례 찾아 보고 틀린점 찾아서 프림으로 해결
반례
3
2
50
3
0 48 100
48 0 15
100 15 0
틀렸던 출력 : 53
답 : 20

해설에 나왔던 방식은 직접 논에 우물을 파는 비용과 끌어다 쓰는 간선 비용을 같이
정렬하여 사용해서 위와 같은 반례에서 1번 노드에서 +2, 3번 노드에서 +3,
3번 노드와 2번 노드와 연결된 간선 비용 15 순으로 처리 했는데
이전에 잘 못 짰던 알고리즘은 직접 우물을 파는 값이 낮은 노드의 값을 더하고
연결된 간선을 pq에 넣고 모두 순회하면 다시 탐색 안하던 노드 중 값이 낮은 걸 찾아서
반복했는데 이러면 2 + 48 + 3 이 되서 틀렸다.
 */

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.PriorityQueue;
import java.util.StringTokenizer;

class Main {
    public static void main(String[] args) throws IOException {
        PriorityQueue<Edge> pq = new PriorityQueue<>();
        int answer = 0;
        int cost;
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        StringTokenizer st;
        int n = Integer.parseInt(br.readLine());
        boolean[] hasWater = new boolean[n];
        int[] nodes = new int[n]; // 논에 우물 파는 비용
        Edge[][] edges = new Edge[n][n];

        for (int idx = 0; idx < n; idx++) {
            nodes[idx] = Integer.parseInt(br.readLine());
            pq.add(new Edge(idx, idx, nodes[idx]));
        }
        for (int row = 0; row < n; row++) {
            st = new StringTokenizer(br.readLine());
            for (int col = 0; col < n; col++) {
                cost = Integer.parseInt(st.nextToken());
                if(row == col) continue;
                if(row < col) {
                    edges[row][col] = new Edge(row, col, cost);
                } else {
                    edges[row][col] = edges[col][row];
                }
            }
        }

        Edge curEdge, nextEdge;
        while (!pq.isEmpty()) {
            curEdge = pq.poll();
            if (curEdge.isVisited || hasWater[curEdge.dest]) continue;
            curEdge.isVisited = true;
            hasWater[curEdge.dest] = true;
            answer += curEdge.c;

            for (int nextIdx = 0; nextIdx < n; nextIdx++) {
                if (curEdge.dest == nextIdx) continue;
                nextEdge = edges[curEdge.dest][nextIdx];
                if (!nextEdge.isVisited && !hasWater[nextIdx] && nextEdge.c < nodes[nextIdx]) {
                    nextEdge.setOppositeFrom(curEdge.dest);
                    pq.add(nextEdge);
                }
            }
        }

        System.out.println(answer);
    }
}

class Edge implements Comparable<Edge> {
    int u;
    int v;
    int c;
    int dest;
    boolean isVisited;

    public Edge(int u, int v, int c) {
        this.u = u;
        this.v = v;
        this.c = c;
        this.dest = v;
        isVisited = false;
    }

    public void setOppositeFrom(int cur) {
        this.dest = cur == u ? v : u;
    }

    @Override
    public int compareTo(Edge o) {
        return c - o.c;
    }
}