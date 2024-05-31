/*
 * 2021-02-18
 * https://www.acmicpc.net/problem/1182
 * 백준 백트래킹, 브루트포스 실버 2
 *
부분수열의 합 2 풀기 전 에 합 1 부터 풀어보기로 해서 풀어봄.

주어진 n개의 정수에서 모든 부분 수열을 만들어 탐색해서 합이 s인지 확인 후 갯수 출력
브루트포스 + 백트래킹
부분수열 갯수(2^n - 1개) 만큼의 시간복잡도 : o(2^n)
n의 범위가 1 <= n <= 20 이라 2^20 = 1024 ^ 2, 약 백만 이라 시간 제한 2초내에 풀이 가능
 */

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.StringTokenizer;

class Main {
    static int n;
    static int s;
    static int target;
    static int answer;
    static int[] numbers;
    public static void main(String[] args) throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        StringTokenizer st = new StringTokenizer(br.readLine());
        n = Integer.parseInt(st.nextToken());
        s = Integer.parseInt(st.nextToken());
        numbers = new int[n];
        st = new StringTokenizer(br.readLine());
        for (int idx = 0; idx < n; idx++) {
            numbers[idx] = Integer.parseInt(st.nextToken());
        }

        dfs(0, 0);
        System.out.println(answer);
    }

    public static void dfs(int idx, int sum) {
        while(idx < n) {
            sum += numbers[idx];
            if(sum == s) {
                answer++;
            }
            dfs(idx + 1, sum);
            sum -= numbers[idx++];
        }
    }
}