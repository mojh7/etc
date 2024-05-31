/*
 * 2021-01-14
 * https://www.acmicpc.net/problem/11779
 * 백준 다익스트라 골드3
 *
 * 많이 틀림. 해설 보고 내 코드봐도 왜 틀렸는지 몰랐었다.

해당 문제는 기존의 다익스트라에서 출발 도시에서 도착 도시까지 가는데 최소비용가 경로를 출력해야한다.

Node 클래스에 minCostPathPrevNode 변수를 추가해서
다익스트라에서 현재 노드에서 다음 노드를 방문할 때 최소 비용으로 갱신될 때
다음 노드에 가기전 들려야하는 이전 노드를 저장해줬다.
그리고 도착 도시에서 역으로 순회하면서 경로를 출력하려고 했는데 여기까지 생각은 맞았으나 구현 방법이 틀렸다.

stack을 먼저 써야겠다 생각했다가 어차피 string으로 출력해야되니 stringbuilder에다가 도착 도시에서 역으로 출발 도시까지 순회하면서
node의 id를 append하고 마지막에 reverse 해서 출력하는 방식으로 했는데 계속 틀렸었다. 왜 틀렸었는지 몰라서 이것저것
바꿔도 안되니(stringbuilder로 된 부분은 냅두고) stack으로 바꾸니 정답으로 처리 됐었다.
순서를 바꿔줄 목적으로 reverse를 썼던건데 10이상의 숫자가 string으로 들어가면 10 15 20은 02 51 01 이런식으로 누가봐도 안되는 건데
멍청해서 틀렸다. 

// stringbuilder로 했었던 코드
StringBuilder path = new StringBuilder();
cur = nodes[dest];
int pathSize = 0;
while (cur.id != start) {
        path.append(cur.id).append(" ");
        cur = nodes[cur.minCostPathPrevNode];
        pathSize++;
}
path.append(cur.id);
pathSize++;

 */

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.PriorityQueue;
import java.util.Stack;
import java.util.StringTokenizer;

class Main {
    private static int INF = 100000000;
    public static void main(String[] args) throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        StringTokenizer st;
        int n = Integer.parseInt(br.readLine());
        int m = Integer.parseInt(br.readLine());
        int u, v, c;
        Node[] nodes = new Node[n + 1];
        for(int idx = 1; idx <= n; idx++) {
            nodes[idx] = new Node(idx, INF + 1);
        }
        for(int idx = 0; idx < m; idx++) {
            st = new StringTokenizer(br.readLine());
            u = Integer.parseInt(st.nextToken());
            v = Integer.parseInt(st.nextToken());
            c = Integer.parseInt(st.nextToken());
            nodes[u].edges.add(new Edge(v, c));
        }
        st = new StringTokenizer(br.readLine());
        int start = Integer.parseInt(st.nextToken());
        int dest = Integer.parseInt(st.nextToken());

        System.out.println(dijkstra(nodes, start, dest));
    }

    public static String dijkstra(Node[] nodes, int start, int dest) {
        PriorityQueue<Node> pq = new PriorityQueue<>();
        nodes[start].c = 0;
        pq.add(nodes[start]);
        Node cur;
        boolean[] isVisited = new boolean[nodes.length];
        while (!pq.isEmpty()) {
            cur = pq.poll();
            if(isVisited[cur.id] || cur.id == dest) continue;
            isVisited[cur.id] = true;
            for (Edge edge : cur.edges) {
                if(cur.c + edge.c < nodes[edge.v].c) {
                    nodes[edge.v].c = cur.c + edge.c;
                    nodes[edge.v].minCostPathPrevNode = cur.id;
                    pq.add(nodes[edge.v]);
                }
            }
        }

        StringBuilder res = new StringBuilder();
        Stack<Integer> s = new Stack<>();
        cur = nodes[dest];
        int pathSize = 0;
        while (cur.id != start) {
            s.push(cur.id);;
            pathSize++;
            cur = nodes[cur.minCostPathPrevNode];
        }
        s.push(cur.id);
        pathSize++;

        res.append(nodes[dest].c)
            .append("\n")
            .append(pathSize)
            .append("\n");
        while (!s.isEmpty()) {
            res.append(s.pop()).append(" ");
        }

        return res.toString();
    }
}

class Node implements Comparable<Node>{
    int id;
    int c;
    ArrayList<Edge> edges;
    int minCostPathPrevNode;
    public Node (int id, int c) {
        this.id = id;
        this.c = c;
        this.edges = new ArrayList<>();
        this.minCostPathPrevNode = 0;
    }

    @Override
    public int compareTo(Node o) {
        return c - o.c;
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