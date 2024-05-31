/*
 * 2021-03-18
 * https://www.acmicpc.net/problem/17071
 * 백준 BFS
 *
일반 bfs로 했을 때 3^time 걸릴 것 같아서 시간초과 뜰 것 같긴 함.
queue에 다가 현재 방문한 위치에서 n-1, n+1, n*2 위치에 visited[nextPosition]이 false일 때 true로
놓고 queue에다가 push 했는데 위치가 0초 일 때 25 -> 1초 26 -> 2초 25 이런 식으로 한 번 갔던
위치에도 시간에 따라 이동이 되므로 visited를 없애고 1부터 n까지의 합 (n+1)(n) / 2 식을 이용해서
time이 1000이상이 되면 break 되게 했는데 메모리 초과 떴다.

해설 참조해서 풀음

한 번 수빈이가 왔던 위치가 n + 1, n - 1 하기 때문에

1초에 5라는 위치에 왔으면 3초에도 5라는 위치에 있게 된다. 짝수에도 같은 방법이 적용되서
time이 홀 수, 짝 수일 때를 나누어서 처리하면 되고 visited도 [2][50만] 으로 처리함. 짝수일 때 0, 홀 수 일 때
1 index 사용

그리고 3^time도 사실은 위치가 0<= x <= 50만 이기 때문에 그렇게 까지 크게 적용 안됨.
 */

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.LinkedList;
import java.util.Queue;
import java.util.StringTokenizer;

class Main {
    static final int MAX_DISTANCE = 500000;
    public static void main(String[] args) throws IOException {
        int answer = -1;
        int increase = 0;
        int count = 0;
        int flag;
        int current;
        int[] nextPositions = new int[3];
        Queue<Integer> q = new LinkedList<>();
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        StringTokenizer st = new StringTokenizer(br.readLine());
        int n = Integer.parseInt(st.nextToken());
        int k = Integer.parseInt(st.nextToken());
        boolean[][] visited = new boolean[2][MAX_DISTANCE + 1];

        q.add(n);
        visited[0][n] = true;
        while (true) {
            k += increase;
            flag = count % 2; // count 짝수 0, 홀수 1
            if(k > MAX_DISTANCE) break;
            if(visited[flag][k]) {
                answer = count;
                break;
            }

            for (int qCnt = 0, size = q.size(); qCnt < size; qCnt++) {
                current = q.poll();
                nextPositions[0] = current - 1;
                nextPositions[1] = current + 1;
                nextPositions[2] = current * 2;
                for(int idx = 0; idx < 3; idx++) {
                    if (0 <= nextPositions[idx] && nextPositions[idx] <= MAX_DISTANCE && !visited[1-flag][nextPositions[idx]]) {
                        visited[1-flag][nextPositions[idx]] = true;
                        q.add(nextPositions[idx]);
                    }
                }
            }
            count++;
            increase++;
        }

        System.out.println(answer);
    }
}