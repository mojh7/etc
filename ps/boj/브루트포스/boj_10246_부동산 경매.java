/*
 * 2021-01-011
 * https://www.acmicpc.net/problem/10246
 * 백준 브루트포스 골드 5
 * 틀림, 푸는중
 *
 *
 */

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.IOException;

class Main {
    public static void main(String[] args) throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        BufferedWriter bw = new BufferedWriter(new OutputStreamWriter(System.out));
        // StringTokenizer st = new StringTokenizer(br.readLine());
        int n = Integer.parseInt(br.readLine());
        while (n > 0) {
            int answer = 0;
            int start = 2;
            int end = 2;
            int sum = 2;
            while (start <= n) {
                if(sum < n) {
                    sum += ++end;
                    continue;
                }
                if(sum == n) {
                    answer++;
                }
                sum -= start++;
            }
            bw.write(Integer.toString(answer));
            bw.newLine();
            n = Integer.parseInt(br.readLine());
        }
        bw.flush();
        bw.close();
    }
}