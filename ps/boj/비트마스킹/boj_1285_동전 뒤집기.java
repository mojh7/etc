/*
 * https://www.acmicpc.net/problem/1285
 * 그리디, 완탐, 비트마스킹 골2
 *
행*열 n*n 동전 행 혹은 열에 놓인 동전 뒤집기
뒷면(T) 최소화되게 뒤집고 최소값 출력

n이 20이하의 자연수라 경우의수 2^(20+20) 이고 그대로 하면 시간초과

그래서 행 혹은 열로만 완탐 진행하면서
행으로 했으면 열은 한 번에 체크해서 열마다 뒷면 최소화되게 계산하면
2^20으로 해결됨

열 먼저 뒤집고
행 모두 뒤집어도 되는지 계산했는데

행 계산할 때 일일이 뒤집고 list에 담아두고
원상복구 했었는데
뒤집는 처리 안하고 뒷면 갯수만 체크해서
Math.min(n - cnt, cnt)로 뒷면 갯수 파악하는게 더빠름 (984 -> 236ms)

 */

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

class Main {
    private static int answer;
    private static int n;
    private static int rowMask;

    public static void main(String[] args) throws IOException {
        answer = Integer.MAX_VALUE;
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        //StringTokenizer st = new StringTokenizer(br.readLine());
        n = Integer.parseInt(br.readLine());
        int[] board = new int[n];

        for (int y = 0; y < n; y++) {
            String input = br.readLine();
            // 앞면 H:0, 뒷면 T:1
            for (int x = 0; x < n; x++) {
                if(input.charAt(x) == 'T') {
                    board[y] = board[y] | (1 << x);
                }
            }
        }

        rowMask = (1 << n) - 1;
        flip(board, -1);

        System.out.println(answer);
    }

    /**
     * n <= 20 이면 행, 열 뒤집는 경우의수 20+20 = 40으로
     * 2^40 가지, 2^20 = 약 100만이라 2^40으로하면 시간 초과될 듯
     */
    private static void flip(int[] board, int curr) {
        if (curr > 0) {
            // 열 먼저 뒤집기
            int colMask = 1 << curr;
            for (int y = 0; y < n; y++) {
                board[y] = board[y] ^ colMask;
            }
        }

        int tailCnt = 0;
        // 행 뒤집기, 뒷면 많은 경우에만
        for(int y = 0; y < n; y++) {
            int cnt = Integer.bitCount(board[y]);
            tailCnt += Math.min(n - cnt, cnt);
        }
        answer = Math.min(answer, tailCnt);

        for(int idx = curr+1; idx < n; idx++) {
            flip(board, idx);
        }

        if (curr > 0) {
            int colMask = 1 << curr;
            for (int y = 0; y < n; y++) {
                board[y] = board[y] ^ colMask;
            }
        }
    }
}