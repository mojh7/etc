/*
 * 2021-04-22
 * https://www.acmicpc.net/problem/2660
 * 백준 플로이드 와샬 골드 5
 *

친구의 친구도 두 회원이 서로 친구가 됨. 

1점 : 어느 회원이 다른 모든 회원과 친구
2점 : 다른 모든 회원이 친구 혹은 친구의 친구
3점 : 다른 모든 회원이 친구 혹은 친구의 친구 혹은 친구의 친구의 친구

정점간에 연결된 path가 몇 다리 건너야 되는지에 따라서 점수를 메기고
가장 큰 비용이 들어가는게 회장 후보 점수
 */

import java.io.IOException;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.util.StringTokenizer;

class Main {
    private static final int MAX_VALUE = 1000000;

    public static void main(String[] args) throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        //BufferedWriter bw = new BufferedWriter(new OutputStreamWriter(System.out));
        StringTokenizer st;
        int n = Integer.parseInt(br.readLine());
        int[][] friends = new int[n+1][n+1];
        int[] answer = new int[n];
        int candidateScore = MAX_VALUE;
        int answerCount = 0;
        for (int row = 1; row <= n; row++) {
            for (int col = 1; col <= n; col++) {
                friends[row][col] = MAX_VALUE;
            }
            friends[row][row] = 0;
        }
        int u, v;
        while (true) {
            st = new StringTokenizer(br.readLine());
            u = Integer.parseInt(st.nextToken());
            v = Integer.parseInt(st.nextToken());
            if (u == -1 && v == -1) {
                break;
            }
            friends[u][v] = 1;
            friends[v][u] = 1;
        }

        for (int mid = 1; mid <= n; mid++) {
            for (int start = 1; start <= n; start++) {
                for (int end = 1; end <= n; end++) {
                    friends[start][end] = Math.min(friends[start][end],
                            friends[start][mid] + friends[mid][end]);
                }
            }
        }

        int max;
        for (int row = 1; row <= n; row++) {
            max = 0;
            for (int col = 1; col <= n; col++) {
                if (max < friends[row][col] && friends[row][col] != MAX_VALUE) {
                    max = friends[row][col];
                }

            }
            if (candidateScore == max) {
                answer[answerCount++] = row;
            }
            if (candidateScore > max) {
                candidateScore = max;
                answerCount = 0;
                answer[answerCount++] = row;
            }
        }

        System.out.printf("%d %d\n", candidateScore, answerCount);
        for (int idx = 0; idx < answerCount; idx++) {
            System.out.printf("%d ", answer[idx]);
        }
    }
}