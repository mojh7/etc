/*
 * 2021-02-13
 * https://www.acmicpc.net/problem/1939
 * 백준 이분 탐색 골드 4
 * 첫 풀이 71% 에서 시간 초과, 이분 탐색 개념으로 수정 후 정답

--- 첫 풀이
이분 탐색 문제로 찾았으나 알고리즘 분류에 bfs도 있어서 bfs로 풀어보는 방향을 먼저 생각함.
시작점에서 도착지점까지 bfs 하며 현재까지 지나온 경로에서 최소 중량제한과 현재 위치에서
연결된 다리의 중량제한에서 작은 값이 다음 지점의 지금까지의 중량제한 값 보다 크면
다음 지점을 queue에 넣어서 bfs 진행

--- 두번 째 풀이
질문란 보고 이분 탐색 개념으로 품
첫번 째 풀이는 어떻게 보면 브루트포스 개념으로 푼거라 시간 초과가 나온 것 같다
이분 탐색개념을 어디에 적용해야 하나 감이 안왔는데 질문란에 글들을 보고 감을 잡음.
1 부터 주어진 다리의 중량제한 최대치까지 범위를 잡고 중간값인 mid가 답이라는 가정하에
bfs 탐색해서 src에서 dest까지 경로가 존재하면 mid ~ end 값으로 범위를 잡아나가며
이분 탐색 진행, 경로가 존재하지 않으면 범위를 줄이며(start ~ mid) 탐색한다.
-> 정답이긴 한데 메모리 162208KB 시간 1328ms으로 메모리를 상대적으로 많이쓰고 속도도 느림

--- bfs 개선
방문 체크를 int[] maxWeights = new int[n+1]; 로 놓고 이미 지나간 경로 중 중량 제한 최대치보다
해당 노드를 탐색하러 들어올 때 새로운 경로의 중량 제한가 더 크면 새롭게 queue에 넣고 탐색했는데
애초에 desiredWeight 값 보다 같거나 큰 중량제한의 경로가 있으면 되서
boolean[] isVisited = new boolean[n + 1]; 로 놓고 경로 탐색할 때 
if(edge.c < desiredWeight || isVisited[next]) continue;
다리의 중량 제한이 원하는 중량제한치 보다 작거나 이미 방문했던 노드면 스킵해서 좀 더
빠르고 적은 메모리를 써서 문제 해결
메모리 65420KB 시간 588ms
 */

// bfs 개선 후 코드
public static boolean bfs(int desiredWeight) {
        boolean[] isVisited = new boolean[n + 1];
        isVisited[src] = true;
        Queue<Integer> q = new LinkedList<>();
        q.add(src);
        int current;
        int next;
        while (!q.isEmpty()) {
            current = q.poll();

            for(Edge edge : bridges[current]) {
                next = edge.opposite(current);
                if(edge.c < desiredWeight || isVisited[next]) continue;
                isVisited[next] = true;
                if(next == dest) {
                    return true;
                }
                q.add(next);
            }
        }
        return false;
    }
}

// 두번 째 풀이
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.Queue;
import java.util.StringTokenizer;

class Main {
    static int n, m, src, dest, maxWeight;
    static ArrayList<Edge>[] bridges;

    public static void main(String[] args) throws IOException {
        int answer = 0;
        int a, b, c;
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        StringTokenizer st = new StringTokenizer(br.readLine());
        n = Integer.parseInt(st.nextToken());
        m = Integer.parseInt(st.nextToken());
        bridges = new ArrayList[n+1];
        for(int idx = 1; idx <= n; idx++) {
            bridges[idx] = new ArrayList<>();
        }
        for (int idx = 0; idx < m; idx++) {
            st = new StringTokenizer(br.readLine());
            a = Integer.parseInt(st.nextToken());
            b = Integer.parseInt(st.nextToken());
            c = Integer.parseInt(st.nextToken());
            if(maxWeight < c) {
                maxWeight = c;
            }
            Edge edge = new Edge(a, b, c);
            bridges[a].add(edge);
            bridges[b].add(edge);
        }
        st = new StringTokenizer(br.readLine());
        src = Integer.parseInt(st.nextToken());
        dest  = Integer.parseInt(st.nextToken());

        answer = binarySearch();
        System.out.println(answer);
    }

    public static int binarySearch() {
        int start = 1;
        int end = maxWeight;
        int mid = 1;
        while (start <= end) {
            mid = (start + end) / 2;
            if(bfs(mid)) {
                start = mid + 1;
            } else {
                end = mid - 1;
            }
        }

        return end;
    }

    public static boolean bfs(int desiredWeight) {
        int[] maxWeights = new int[n+1];
        maxWeights[src] = 1000000001;
        Queue<int[]> q = new LinkedList<>();
        q.add(new int[]{ src, maxWeights[src] });
        int[] current;
        int weightLimit;
        int opposite;

        while (!q.isEmpty()) {
            current = q.poll();
            if(current[1] < maxWeights[dest] || current[1] < maxWeights[current[0]])
                continue;

            for(Edge edge : bridges[current[0]]) {
                opposite = edge.opposite(current[0]);
                weightLimit = Math.min(current[1], edge.c);
                if(maxWeights[opposite] >= weightLimit) continue;

                maxWeights[opposite] = weightLimit;
                if(opposite != dest) {
                    q.add(new int[]{ opposite, weightLimit});
                } else if(desiredWeight <= maxWeights[dest]){
                    return true;
                }
            }
        }
        return false;
    }
}

class Edge {
    int u;
    int v;
    int c;
    public Edge(int u, int v, int c) {
        this.u = u;
        this.v = v;
        this.c = c;
    }

    public int opposite(int num) {
        return num == u ? v : u;
    }
}

// 첫 풀이, 71% 에서 시간 초과
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.Queue;
import java.util.StringTokenizer;

class Main {
    static int n, m, src, dest;
    static ArrayList<Edge>[] bridges;

    public static void main(String[] args) throws IOException {
        int answer = 0;
        int a, b, c;
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        StringTokenizer st = new StringTokenizer(br.readLine());
        n = Integer.parseInt(st.nextToken());
        m = Integer.parseInt(st.nextToken());
        bridges = new ArrayList[n+1];
        for(int idx = 1; idx <= n; idx++) {
            bridges[idx] = new ArrayList<>();
        }
        for (int idx = 0; idx < m; idx++) {
            st = new StringTokenizer(br.readLine());
            a = Integer.parseInt(st.nextToken());
            b = Integer.parseInt(st.nextToken());
            c = Integer.parseInt(st.nextToken());
            Edge edge = new Edge(a, b, c);
            bridges[a].add(edge);
            bridges[b].add(edge);
        }
        st = new StringTokenizer(br.readLine());
        src = Integer.parseInt(st.nextToken());
        dest  = Integer.parseInt(st.nextToken());

        answer = bfs();
        System.out.println(answer);
    }

    // src -> dest 모든 경로에서 경로 마다 중량제한 최소치가 맥스인 값 찾기
    public static int bfs() {
        int[] maxWeights = new int[n+1];
        maxWeights[src] = 1000000001;
        Queue<int[]> q = new LinkedList<>();
        q.add(new int[]{ src, maxWeights[src] });
        int[] current;
        int weightLimit;
        int opposite;
        while (!q.isEmpty()) {
            current = q.poll();
            if(current[1] < maxWeights[dest]) continue;

            for(Edge edge : bridges[current[0]]) {
                opposite = edge.opposite(current[0]);
                weightLimit = Math.min(current[1], edge.c);
                if(weightLimit < maxWeights[opposite]) continue;
                if(maxWeights[opposite] < weightLimit) {
                    maxWeights[opposite] = weightLimit;
                    if(opposite != dest) {
                        q.add(new int[]{ opposite, weightLimit});
                    }
                }
            }
        }
        return maxWeights[dest];
    }
}

class Edge {
    int u;
    int v;
    int c;
    public Edge(int u, int v, int c) {
        this.u = u;
        this.v = v;
        this.c = c;
    }

    public int opposite(int num) {
        return num == u ? v : u;
    }
}