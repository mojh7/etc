/*
 * 2020-09-25
 * https://www.acmicpc.net/problem/1956
 *
 * 플로이드 와샬
 */

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.util.StringTokenizer;
import java.util.Arrays;

class Main {
    public static void main(String[] args) throws IOException {
        Solution s = new Solution();
        s.solution();
    }
}

class Solution {
    int INF = 987654321;

    public void solution() throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        BufferedWriter bw = new BufferedWriter(new OutputStreamWriter(System.out));

        StringTokenizer st = null;
        st = new StringTokenizer(br.readLine());
        int answer = 0;
        int v = Integer.parseInt(st.nextToken());
        int e = Integer.parseInt(st.nextToken());
        int a, b, c;
        int cycleDistMin = INF;
        int[][] d = new int[v+1][v+1];

        for(int[] row : d) {
            Arrays.fill(row, INF);
        }

        for(int i = 0 ; i < e; i++){
            st = new StringTokenizer(br.readLine());
            a = Integer.parseInt(st.nextToken());
            b = Integer.parseInt(st.nextToken());
            c = Integer.parseInt(st.nextToken());
            d[a][b] = c;
        }

        floydWarshall(d);
        for(int i = 1; i <= v; i++) {
            for (int j = 1; j <= v; j++) {
                if(cycleDistMin > d[i][j] + d[j][i]) {
                    cycleDistMin = d[i][j] + d[j][i];
                }
            }
        }

        answer = cycleDistMin == INF ? -1 : cycleDistMin;
        bw.write(Integer.toString(answer));
        bw.newLine();
        bw.flush();
        bw.close();
    }

    public void floydWarshall(int[][] d) {
        int v = d.length;
        for(int k = 1; k < v; k++) {
            for(int i = 1; i < v; i++) {
                for(int j = 1; j < v; j++) {
                    d[i][j] = Math.min(d[i][j], d[i][k] + d[k][j]);
                }
            }
        }
    }
}
