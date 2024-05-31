/*
 * 2021-01-28
 * https://www.acmicpc.net/problem/1799
 * 백준 백트래킹 골드 2
 *
어렵다.
틀려서 해설 참고하여서 품.
기존에 틀리게 푼 방법은 n * n을 순회하면서 비숍을 세울 수 있는지 확인하고 세웠는데
모든 경우를 탐색하지 않게되서 바로 아래 반례에서 3이 아닌 2가 나오게 되었다.

해설에서는 비숍의 이동 특성상 검은칸과 흰칸을 나눠서 풀길래
빈칸 들 중 검은칸과 흰칸을 나누고 각각 백트래킹으로 탐색해서 각 색깔 칸에서
최대로 세울 수 있는 값을 합쳐서 리턴해서 정답 처리 되었다.

반례
4
1 1 0 0
1 0 1 0
0 0 0 0
0 0 0 0
답 : 3
 */

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.StringTokenizer;

class Main {
    static int n;
    static int[][] board = new int[10][10];
    static int[][] bishops = new int[2][50];
    static int[] bishopsCnt = new int[2];
    static int[][][] blanks = new int[2][50][2];
    static int[] blanksCnt = new int[2];
    static int[] answer = new int[2];

    public static void main(String[] args) throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        for (int idx = 0; idx < 2; idx++) {
            answer[idx] = 0;
            blanksCnt[idx] = 0;
            bishopsCnt[idx] = 0;
        }
        int blankType = 1;  // 0 : black, 1 : white
        StringTokenizer st;
        n = Integer.parseInt(br.readLine());
        int num;
        for (int row = 0; row < n; row++) {
            st = new StringTokenizer(br.readLine());
            for (int col = 0; col < n; col++) {
                num = Integer.parseInt(st.nextToken());
                board[row][col] = num;
                if (num == 0) continue;
                blankType = (row + col) % 2;
                blanks[blankType][blanksCnt[blankType]][0] = row;
                blanks[blankType][blanksCnt[blankType]++][1] = col;
            }
        }

        backtracking(0, 0);
        backtracking(0,1);
        System.out.println(answer[0] + answer[1]);
    }

    public static void backtracking(int startIdx, int blankType) {
        if(answer[blankType] < bishopsCnt[blankType]) {
            answer[blankType] = bishopsCnt[blankType];
        }
        int[] cur;
        for (int idx = startIdx; idx < blanksCnt[blankType]; idx++) {
            cur = blanks[blankType][idx];
            if (!promising(cur[0], cur[1], blankType)) continue;
            bishops[blankType][bishopsCnt[blankType]++] = idx;
            backtracking(idx + 1, blankType);
            bishopsCnt[blankType]--;
        }
    }

    public static boolean promising(int row, int col, int blankType) {
        if(board[row][col] == 0) return false;
        int[] cur;
        for(int idx = 0; idx < bishopsCnt[blankType]; idx++) {
            cur = blanks[blankType][bishops[blankType][idx]];
            if(row - cur[0] == Math.abs(col - cur[1])) {
                return false;
            }
        }
        return true;
    }
}