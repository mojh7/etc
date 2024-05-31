/*
 * 2021-01-15
 * https://www.acmicpc.net/problem/11781
 * 백준 다익스트라 골드2
 *
 * --- 정리
 * double, float 타입으로 우선순위큐 Comparable 직접 짜서 쓰다보면 의도치 않은 답이 나와 틀릴 수 있다.
 * -> 이 문제에 경우에는 소수가 0.5 만 나와서 입력받은 값을 *2로 long 으로 처리해서 우선순위 큐 쓰면 된다.
 * -> double, float 타입으로 처리할 때 아예 매번 min 거리값을 찾아서 o(n^2)의 다익스트라로 구현해서 처리한다.
 * -> Comparable을 직접 구현하지 말고 Comparator의 이미 정의된 comparingDouble 함수를 쓰자
 *
 * 백준에서 스폐셜 져지가 아닌 경우 double 타입의 답을 출력할 때 3.5E9 이런 값이 아닌 3500000000만 정답으로 처리 될 수 있으니
 * 출력을 알맞게 바꿔서 해줘야 한다.
 *--------------------
1% 부터 틀렸다. 
System.out.println() 로 출력할 때 다익스트라 결과로 가장 늦게 도착하게 되는 지점까지의 도착 시각을 double로 리턴 받아서
출력하니 3.5E9 같은 형태로 출력되는데 다양한 형태의 출력값을 인정해주는 스폐셜져지 문제가 아니라서
4 3 0 1000000000
1 2 1000000000 1 0
2 3 1000000000 0 0
3 4 1000000000 0 0
입력일 때
3.5E9가 아닌 3500000000가 찍혀야 된다.

이렇게 바꾸니 1~7%까진 되고 8%에서 틀렸다.

----- 개선

해설보며 코드 고쳤을 때
우선순위 큐 말고 매번 n번 만큼 순회해서 거리가 최소인 노드를 찾고
그 노드와 연결된 엣지로 이어진 노드들을 탐색하는 방법으로 하니 정답이 됐다.

그 후 틀린 부분을 찾아봤는데

// 0 <= s < e <= 10억, 1 <= L <= 10억
// 15억 + 10억 * (5000 - 2) = 4조 9995억
private static double INF = 4999500000001L;

문제에서 주어질 수 있는 무한대 값도 잘 찾았고
도로가 혼잡할 때 아닐 때에 시간 처리 로직도 틀리지 않았다.

우선순위큐로 작성했을 때 틀림

while 진입하고
cur = pq.poll();
if(isVisited[cur.id]) continue;
isVisited[cur.id] = true;
// 이하 혼잡, 비혼잡 시간 계산 로직

로 했을 때는 10% 쯤에서 틀렸었고 isVisited 구문을 없애니
93% 에서 틀렸다.

----------- 개선 2

우선순위큐에서 비교 부분을 Node class에 Comparable을 구현하여
@Override
public int compareTo(Node o) {
    return time - o.time < 0 ? -1 : time == o.time ? 0 : 1;
}
이거나
return time < o.time ? -1 : 1;
로 해놓았을 때는 계속 틀렸었는데

PriorityQueue<Node> pq = new PriorityQueue<Node>(Comparator.comparingDouble((node) -> node.time));

Comparator의 내장된 double 비교 함수형 인터페이스를 넣으니 정답으로 처리 됐음.

double 정밀도 관련해서 의도치 않은 결과가 나와서 틀렸던 것 같다.

다른 사람 코드를 보면 node에 long타입 cost를 가지고(입력 받을 때 도로 길이 자체를 *2로 정수 처리하여 했음)
Comparable인터페이스를 구현하여 compareTo로 처리함.

*/

// 해결 코드
import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.StringTokenizer;

class Main {
    // 0 <= s < e <= 10억, 1 <= L <= 10억
    // 15억 + 10억 * (5000 - 2) = 4조 9995억
    private static double INF = 4999500000001L;
    public static void main(String[] args) throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        StringTokenizer st = new StringTokenizer(br.readLine());
        int n = Integer.parseInt(st.nextToken());
        int m = Integer.parseInt(st.nextToken());
        double s = Integer.parseInt(st.nextToken());
        double e = Integer.parseInt(st.nextToken());
        int u, v, len, t1, t2;
        Road road;
        ArrayList<Road>[] roads = new ArrayList[n+1];
        for(int idx = 1; idx <= n; idx++) {
            roads[idx] = new ArrayList<>();
        }
        for(int idx = 0; idx < m; idx++) {
            st = new StringTokenizer(br.readLine());
            u = Integer.parseInt(st.nextToken());
            v = Integer.parseInt(st.nextToken());
            len = Integer.parseInt(st.nextToken());
            t1 = Integer.parseInt(st.nextToken());
            t2 = Integer.parseInt(st.nextToken());
            road = new Road(u, v, len, t1, t2);
            roads[u].add(road);
            roads[v].add(road);
        }

        double maxDistance = dijkstra(roads, n, s, e);
        if(maxDistance == (long)maxDistance) {
            System.out.printf("%.0f", maxDistance);
        } else {
            System.out.printf("%.1f", maxDistance);
        }
    }

    public static double dijkstra(ArrayList<Road>[] roads, int n, double s, double e) {
        double max = 0.0;
        boolean[] isVisited = new boolean[n+1];
        boolean isCongestion;
        double nw;
        double remainingDist;
        double congestionEntrytime;
        Node[] nodes = new Node[n+1];
        for(int idx = 0; idx < nodes.length; idx++) {
            nodes[idx] = new Node(idx, INF);
        }
        nodes[1].time = 0;
        Node cur;
        Node next;

        for(int i = 0; i < n; i++) {
            double time = INF;
            int minNodeIdx = 0;
            for (int j = 1; j <= n; j++) {
                if (!isVisited[j] && time > nodes[j].time) {
                    time = nodes[j].time;
                    minNodeIdx = j;
                }
            }
            if (minNodeIdx == 0) {
                continue;
            }
            isVisited[minNodeIdx] = true;
            cur = nodes[minNodeIdx];

            for(Road road : roads[cur.id]) {
                next = nodes[road.opposite(cur.id)];
                isCongestion = road.isCongestion(cur.id);
                nw = cur.time + road.dist;
                if(isCongestion && s < nw && cur.time < e) {
                    remainingDist = road.dist;
                    if(cur.time < s) {
                        remainingDist -= s - cur.time;
                        congestionEntrytime = s;
                    } else {
                        congestionEntrytime = cur.time;
                    }
                    if(congestionEntrytime + remainingDist * 2.0 <= e) {
                        nw = congestionEntrytime + remainingDist * 2.0;
                    } else {
                        remainingDist -= (e - congestionEntrytime) / 2.0;
                        nw = e + remainingDist;
                    }
                }

                if(nw < next.time) {
                    next.time = nw;
                }
            }
        }

        for(Node node : nodes) {
            if(node.time != INF && max < node.time) {
                max = node.time;
            }
        }
        return max;
    }
}

class Node {
    int id;
    double time;
    public Node(int id, double time) {
        this.id = id;
        this.time = time;
    }
}

class Road {
    int u;
    int v;
    double dist;
    int t1;
    int t2;
    public Road(int u, int v, double dist, int t1, int t2) {
        this.u = u;
        this.v = v;
        this.dist = dist;
        this.t1 = t1;
        this.t2 = t2;
    }

    public int opposite(int id) {
        if(id == u) {
            return v;
        } else if(id == v) {
            return u;
        }
        return -1;
    }

    public boolean isCongestion(int start) {
        if(t1 == 1 && start == u) {
            return true;
        } else if(t2 == 1 && start == v) {
            return true;
        }
        return false;
    }
}