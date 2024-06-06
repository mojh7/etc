/*
 * 2024-06-06
 * https://www.acmicpc.net/problem/12865
 * 백준 dp 골드 5, 배낭 문제
 *

최근에 해설보고 풀었다가 1106번 호텔 문제 풀고나서
기존 풀이에서 약간 바꿔도 될 것 같아서 다시풂

처음에는 배낭 문제 종류의 문제를 어떻게 풀어야될지 몰라서
해설보고 dp를 2차원 배열로 만들어서 해결함
int[][] dp = new int[n + 1][k + 1];

다른 dp 문제인 1106번 호텔을 풀고나니 이 문제도
굳이 2차원 배열로 할 필요 없고 1차원 배열로 dp를 만들어도 될 것 같아서
dp를 1차원 배열로 만들고
물건 무게(w)랑 물건의 가치(v) 입력 받자마자 바로 dp 계산함

처음에는 반복문을 1부터 k까지 실행하게 했는데
낮은 무게부터 계산을 해버리면 이전의 선택한 물건에 대한 dp 값이
아닌 이번 물건에서 계산한 dp 값으로 처리하기 때문에 틀린 답이 나오므로

역순으로 순서를 k에서 1로 내려가도록 수정
그리고 1이 아닌 w까지만 반복하도록 수정(백팩 무게가 최소 w여야하니)

 */


import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.util.StringTokenizer;

class Main {
    public static void main(String[] args) throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        BufferedWriter bw = new BufferedWriter(new OutputStreamWriter(System.out));
        StringTokenizer st;
        int answer = 0;
        st = new StringTokenizer(br.readLine());
        int n = Integer.parseInt(st.nextToken()); // 물품 수
        int k = Integer.parseInt(st.nextToken()); // 버틸 수 있는 무게
        int[] dp = new int[k + 1];

        for(int idx = 0; idx < n; idx++) {
            st = new StringTokenizer(br.readLine());
            int w = Integer.parseInt(st.nextToken());
            int v = Integer.parseInt(st.nextToken());

            for(int backpack = k; backpack >= w; backpack--) {
                dp[backpack] = Math.max(dp[backpack], dp[backpack - w] + v);
            }
        }

        answer = dp[k];
        bw.write(Integer.toString(answer));
        bw.newLine();
        bw.flush();
        bw.close();
    }
}