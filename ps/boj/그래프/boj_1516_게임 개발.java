/*
 * 2021-01-08
 * https://www.acmicpc.net/problem/1516
 * 백준 그래프 골드3
 * 
 * 위상 정렬 해서 풀어야 겠다는 생각 까진 했으나 먼저 지어져야 하는 건물들 시간 계산하는 것을
 * 잘 못 생각해냄.
4
1 -1
2 1 3 -1
3 4 -1
4 -1
일 때 2가 1번 노드의 시간 + 3번 노드의 시간으로 계산해야되나? 생각 했다가 3번 노드에서 1번 노드
에 대한 처리가 겹친다고 착각하여 헤맴
풀이 참고하여 풀음.
어떤 특정 건물이 지어지기 전에 필요한 먼저 지어져야 하는 건물들 중에서 가장 오래걸리는 것만
찾아서 시간을 더해주면 됨.

개선---
Node class에서 먼저 지어야하는 이전 건물 목록을 담은 List 삭제함
위상정렬을 하다보니 반대로 어떤 건물이 끝나면 지을 수 있는 다음 건물 목록만 가지고 있어도 되서
삭제 했는데 유의미한 속도 차이가 안남.

topologicalSort 함수 내에서 o(n)로 indegree가 0인 node를 큐에 먼저 넣어주고
while문을 돌았는데 입력 받을 때 indegree가 0인 것을 체크해서 미리 queue를 만들고
함수에 매개변수로 넘겨줘서 실행함. 이 것도 속도 차이 x
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
    public static void main(String[] args) throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        BufferedWriter bw = new BufferedWriter(new OutputStreamWriter(System.out));
        StringTokenizer st;

        int prev;
        Queue<Node> q = new LinkedList<>();
        int n = Integer.parseInt(br.readLine());
        Node[] nodes = new Node[n + 1];
        for (int idx = 1; idx <= n; idx++) {
            nodes[idx] = new Node();
        }

        for (int cur = 1; cur <= n; cur++) {
            st = new StringTokenizer(br.readLine());
            nodes[cur].time = Integer.parseInt(st.nextToken());
            prev = Integer.parseInt(st.nextToken());
            while (prev != -1) {
                nodes[prev].next.add(nodes[cur]);
                nodes[cur].indegree += 1;
                prev = Integer.parseInt(st.nextToken());
            }
            if(nodes[cur].indegree == 0) {
                q.add(nodes[cur]);
            }
        }

        topologicalSort(nodes, q, n);

        for (int idx = 1; idx <= n; idx++) {
            bw.write(Integer.toString(nodes[idx].time));
            bw.newLine();
        }
        bw.flush();
        bw.close();
    }

    public static void topologicalSort(Node[] nodes, Queue<Node> q, int n) {
        Node cur;
        while (!q.isEmpty()) {
            cur = q.poll();
            cur.time += cur.prevMaxTime;
            for(Node nextNode : cur.next) {
                if(nextNode.prevMaxTime < cur.time) {
                    nextNode.prevMaxTime = cur.time;
                }
                if(--nextNode.indegree == 0) {
                    q.add(nextNode);
                };
            }
        }
    }
}

class Node {
    int prevMaxTime;
    int time;
    int indegree;
    LinkedList<Node> next;

    public Node() {
        indegree = 0;
        prevMaxTime = 0;
        next = new LinkedList<>();
    }
}