/*
 * 2021-10-23
 * https://www.acmicpc.net/problem/3020
 * 백준 누적 합 골드 5
 */

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.StringTokenizer;

public class Main {

    public static void main(String[] args) throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        StringTokenizer st = new StringTokenizer(br.readLine());
        boolean isStalagmite = true;
        int n = Integer.parseInt(st.nextToken());
        int h = Integer.parseInt(st.nextToken());
        int min = n + 1;
        int cnt = 0;
        int[] prefixSum = new int[h+1];
        int size;

        for (int idx = 0; idx < n; idx++) {
            size = Integer.parseInt(br.readLine());
            if (isStalagmite) {
                prefixSum[0]++;
                prefixSum[size]--;
            } else {
                prefixSum[h-size]++;
                prefixSum[h]--;
            }

            isStalagmite = !isStalagmite;
        }

        int sum = 0;

        for (int idx = 0; idx < h; idx++) {
            sum += prefixSum[idx];

            if (sum < min) {
                min = sum;
                cnt = 1;
            } else if (sum == min) {
                cnt++;
            }
        }

        System.out.printf("%d %d\n", min, cnt);
    }
}
