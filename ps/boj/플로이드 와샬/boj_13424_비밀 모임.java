/*
 * 2021-01-07
 * https://www.acmicpc.net/problem/13424
 * 백준 플로이드 와샬
 *
 * 다익스트라 문제로 들어왔다가 n이 작아서 플로이드 워셜로 품.(플로이드와샬로도 분류되어있긴함)
 * 첫 edges INF값으로 초기화 때 i -> i 는 0으로 고치니 정답. 모든 경로가 양방향이여서 i -> i
 * 자기 자신으로 갈 때도 INF로 해놓으면 플로이드 와샬 후에 i -> i 가는 최단 경로가 0이 아닌 0이상
 * 값으로 되어서 틀린 답이 나왔었다.
 */

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.IOException;
import java.util.StringTokenizer;

class Main {
    public static void main(String[] args) throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        BufferedWriter bw = new BufferedWriter(new OutputStreamWriter(System.out));
        StringTokenizer st;

        int t = Integer.parseInt(br.readLine());
        while (t-- > 0) {
            st = new StringTokenizer(br.readLine());
            int n = Integer.parseInt(st.nextToken());
            int m = Integer.parseInt(st.nextToken());
            int[][] edges = new int[n+1][n+1];
            for(int row = 1; row <= n; row++) {
                for(int col = 1; col <= n; col++) {
                    edges[row][col] = 123456;
                }
                edges[row][row] = 0;
            }
            for(int idx = 0; idx < m; idx++) {
                st = new StringTokenizer(br.readLine());
                int u = Integer.parseInt(st.nextToken());
                int v = Integer.parseInt(st.nextToken());
                int c = Integer.parseInt(st.nextToken());
                edges[u][v] = c;
                edges[v][u] = c;
            }
            int k = Integer.parseInt(br.readLine());
            int[] friends = new int[k];
            st = new StringTokenizer(br.readLine());
            for(int idx = 0; idx < k; idx++) {
                friends[idx] = Integer.parseInt(st.nextToken());
            }

            for(int mid = 1; mid <= n; mid++) {
                for(int start = 1; start <= n; start++) {
                    for (int end = 1; end <= n; end++) {
                        edges[start][end] = Math.min(edges[start][end], edges[start][mid] + edges[mid][end]);
                    }
                }
            }

            int min = Integer.MAX_VALUE;
            int answer = 1;
            int distSum = 0;
            for(int dest = 1; dest <= n; dest++) {
                distSum = 0;
                for(int friendsIdx = 0; friendsIdx < k; friendsIdx++) {
                    distSum += edges[friends[friendsIdx]][dest];
                }
                if(distSum < min) {
                    min = distSum;
                    answer = dest;
                }
            }

            bw.write(Integer.toString(answer));
            bw.newLine();
        }

        bw.flush();
        bw.close();
    }
}