/*
 * 2021-01-08
 * https://www.acmicpc.net/problem/1967
 * 백준 트리 골드 4
 * 
 * 고민하다가 해결 방법이 떠오르지 않아서 해설 보고 풀음.
 * root 지점에서 dfs로 가장 먼 node를 찾으면 지름의 두 꼭짓점 중 하나를 찾을 수 있고
 * 그 지점에서 다시 dfs로 가장 먼 node를 찾고 거리를 계산하면 지름을 구할 수 있다.
 */

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.IOException;
import java.util.LinkedList;
import java.util.Stack;
import java.util.StringTokenizer;

class Main {
    public static void main(String[] args) throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        BufferedWriter bw = new BufferedWriter(new OutputStreamWriter(System.out));
        StringTokenizer st;
        int answer = 0;
        int u, v, d;
        int n = Integer.parseInt(br.readLine());
        Node[] nodes = new Node[n+1];
        Edge[] edges = new Edge[n-1];
        for(int idx = 1; idx <= n; idx++) {
            nodes[idx] = new Node();
        }
        for(int idx = 0; idx < n-1; idx++) {
            st = new StringTokenizer(br.readLine());
            u = Integer.parseInt(st.nextToken());
            v = Integer.parseInt(st.nextToken());
            d = Integer.parseInt(st.nextToken());
            edges[idx] = new Edge(u, v, d);
            nodes[u].addEdge(idx);
            nodes[v].addEdge(idx);
        }

        answer = dfs(nodes, edges, dfs(nodes, edges, 1)[0])[1];

        bw.write(Integer.toString(answer));
        bw.newLine();
        bw.flush();
        bw.close();
    }

    // 가장 먼 노드, 거리 리턴
    public static int[] dfs(Node[] nodes, Edge[] edges, int startNodeIdx) {
        int farthestNode = 1;
        int distMax = 0;
        boolean[] isVisitedEdge = new boolean[edges.length];
        Stack<int[]> s = new Stack<>();
        s.push(new int[]{startNodeIdx, 0});
        int[] cur;
        Node curNode;
        while(!s.isEmpty()) {
            cur = s.pop();
            curNode = nodes[cur[0]];
            for(int edgeIdx : curNode.edges) {
                if(!isVisitedEdge[edgeIdx]) {
                    isVisitedEdge[edgeIdx] = true;
                    s.push(new int[]{ edges[edgeIdx].opposite(cur[0]), cur[1] + edges[edgeIdx].dist });
                }
            }
            if(curNode.isLeafNode()) {
                // System.out.println(cur[0] + ", " + cur[1]);
                if(distMax < cur[1]) {
                    farthestNode = cur[0];
                    distMax = cur[1];
                }
            }
        }
        return new int[]{ farthestNode, distMax };
    }
}

class Node {
    LinkedList<Integer> edges;

    public Node() {
        edges = new LinkedList<>();
    }

    public void addEdge(int edgeIdx) {
        edges.add(edgeIdx);
    }

    public boolean isLeafNode() {
        return edges.size() == 1;
    }
}

class Edge {
    int parent;
    int child;
    int dist;
    public Edge(int parent, int child, int dist) {
        this.parent = parent;
        this.child = child;
        this.dist = dist;
    }

    public int opposite(int cur) {
        return cur == parent ? child : parent;
    }
}