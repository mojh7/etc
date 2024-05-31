/*
 * 2021-02-28
 * https://www.acmicpc.net/problem/11660
 * 백준 dp, 누적 합 실버 1
 *
문제에서 입력이 x1 y1 x2 y2 순서로 들어오는데 y가 행이고 x가 열인줄 알았으나
문제에서는 (x, y)에서 x행 y열로 인식해서
2 4
1 2
3 4
1 1 1 1
1 2 1 2
2 1 2 1
2 2 2 2
출력이
1
3
2
4
로 나왔었다.

짜고있던 소스에서는 y행 x열 기준으로 짜고 있어서 1, 2, 3, 4가 아닌 1, 3, 2, 4 출력으로 나왔었고
dp식은 그대로 두고 합을 구해야 하는 횟수 M 만큼 x1 y1 x2 y2 을 입력받는 코드에서
x와 y만 바꿔서 y1 x1 y2 x2 로 입력받게 하고 풀었다.
 */

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.util.StringTokenizer;

class Main {
    static int n;
    static int m;
    static int[][] dp;
    public static void main(String[] args) throws IOException {
        int x1, x2, y1, y2, sum;
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        BufferedWriter bw = new BufferedWriter(new OutputStreamWriter(System.out));
        StringTokenizer st = new StringTokenizer(br.readLine());
        n = Integer.parseInt(st.nextToken());
        m = Integer.parseInt(st.nextToken());
        dp = new int[n+1][n+1];
        for (int row = 1; row <= n; row++) {
            st = new StringTokenizer(br.readLine());
            for (int col = 1; col <= n; col++) {
                dp[row][col] = Integer.parseInt(st.nextToken());
            }
        }

        for (int row = 1; row <= n; row++) {
            for (int col = 1; col <= n; col++) {
                dp[row][col] += dp[row-1][col] + dp[row][col-1] - dp[row-1][col-1];
            }
        }

        for (int idx = 0; idx < m; idx++) {
            st = new StringTokenizer(br.readLine());
            y1 = Integer.parseInt(st.nextToken());
            x1 = Integer.parseInt(st.nextToken());
            y2 = Integer.parseInt(st.nextToken());
            x2 = Integer.parseInt(st.nextToken());
            sum = dp[y2][x2] - dp[y1-1][x2] - dp[y2][x1-1] + dp[y1-1][x1-1];
            bw.write(Integer.toString(sum));
            bw.newLine();
        }

        bw.flush();
        bw.close();
    }
}