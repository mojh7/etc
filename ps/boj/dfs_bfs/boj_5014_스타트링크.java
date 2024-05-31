/*
 * 2021-02-01
 * https://www.acmicpc.net/problem/5014
 * 백준 bfs 골드 5
 *

37% 틀림 -> 해결

------------
최단 거리 찾는 문제이므로 bfs를 사용하는데
입력이 10 1 10 3 2 일 경우 일일이 1에서 +3 +3 +3 찾지 않고 배수 만큼 스킵할 수 있다.
그래서 1 + 3 * 3 = 10으로 답이 3이 나와야 되는데 

10 7 10 1 15 같이 7 + 1 * 3 = 10, 목적지에 이미 도착했는데도 바로 cnt를 출력 안하고
10 + up 혹은 10 + down 층을 검사하려고 하고 둘 다 조건에 맞지않아서 while문을 빠져나오고
-1이 리턴되어 use the stairs 출력되게 해서 틀렸었다.

if(s < g) {
    if(u == 0) return -1;
    cnt = (g - s) / u;
    start += u * cnt;
    if(cnt >= 1) {
        visited[start - u] = cnt - 1;
    }
} else if(s > g){
    if(d == 0) return -1;
    cnt = (s - g) / d;
    start -= d * cnt;
    if(cnt >= 1) {
        visited[start + d] = cnt - 1;
    }
}

위에 조건을 아예 빼고 s부터 bfs로 탐색하던가 위의 조건문을 추가하면
아래 조건 문을 추가 해줘야 틀리지 않고 정답.
if(start == g) return cnt;

s 부터 바로 bfs하는 것 보다 s -> g 갈 때 차이나는 만큼
up 혹은 down 만큼의 배수를 먼저 카운팅 처리해주고 bfs 탐색하니 
156ms -> 76ms 약간 더 빠르게 돌아간다.

---

사용 반례
10 1 10 100 100
답 : use the stairs

10 5 5 234 543
답 : 0

1000 5 6 222 221
답 2

1000 5 50 222 221
답 90

 */

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.LinkedList;
import java.util.Queue;
import java.util.StringTokenizer;

class Main {
    static int answer;
    static int f; // 총 층수
    static int s; // 시작
    static int g; // 목적지
    static int u;
    static int d;
    static int[] visited;

    public static void main(String[] args) throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        StringTokenizer st = new StringTokenizer(br.readLine());
        f = Integer.parseInt(st.nextToken());
        s = Integer.parseInt(st.nextToken());
        g = Integer.parseInt(st.nextToken());
        u = Integer.parseInt(st.nextToken());
        d = Integer.parseInt(st.nextToken());
        visited = new int[f+1];

        answer = bfs();
        if(answer == -1) {
            System.out.println("use the stairs");
        } else {
            System.out.println(answer);
        }
    }

    public static int bfs() {
        int start = s;
        int floor;
        int nextUpFloor = 0;
        int nextDownFloor = 0;
        int cnt = 0;

        if(s < g) {
            if(u == 0) return -1;
            cnt = (g - s) / u;
            start += u * cnt;
            if(cnt >= 1) {
                visited[start - u] = cnt - 1;
            }
        } else if(s > g){
            if(d == 0) return -1;
            cnt = (s - g) / d;
            start -= d * cnt;
            if(cnt >= 1) {
                visited[start + d] = cnt - 1;
            }
        }
        if(start == g) return cnt;

        Queue<Integer> q = new LinkedList<>();
        q.add(start);
        visited[start] = cnt;
        
        while (!q.isEmpty()) {
            floor = q.poll();
            cnt = visited[floor];
            nextUpFloor = floor + u;
            nextDownFloor = floor - d;
            if(u > 0 && nextUpFloor <= f && (visited[nextUpFloor] <= 0 ||
                (cnt + 1 < visited[nextUpFloor]))) {
                visited[nextUpFloor] = cnt + 1;
                q.add(nextUpFloor);
            }
            if(d > 0 && nextDownFloor >= 1 && (visited[nextDownFloor] <= 0 ||
                (cnt + 1 < visited[nextDownFloor]))) {
                visited[nextDownFloor] = cnt + 1;
                q.add(nextDownFloor);
            }

            if(nextUpFloor == g || nextDownFloor == g) return visited[g];
        }

        return -1;
    }
}