/*
 * 2021-02-22
 * https://www.acmicpc.net/problem/11659
 * 백준 누적 합 실버 3
 */

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.util.StringTokenizer;

class Main {
    public static void main(String[] args) throws IOException {
        int start = 0;
        int end = 0;
        int number;
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        BufferedWriter bw = new BufferedWriter(new OutputStreamWriter(System.out));
        StringTokenizer st = new StringTokenizer(br.readLine());
        int n = Integer.parseInt(st.nextToken());
        int m = Integer.parseInt(st.nextToken());
        int[] sum = new int[n+1];
        st = new StringTokenizer(br.readLine());
        for (int idx = 1; idx <= n; idx++) {
            number = Integer.parseInt(st.nextToken());
            sum[idx] = sum[idx-1] + number;
        }

        for (int idx = 0; idx < m; idx++) {
            st = new StringTokenizer(br.readLine());
            start = Integer.parseInt(st.nextToken());
            end = Integer.parseInt(st.nextToken());
            bw.write(Integer.toString(sum[end] - sum[start-1]));
            bw.newLine();
        }

        bw.flush();
        bw.close();
    }
}
