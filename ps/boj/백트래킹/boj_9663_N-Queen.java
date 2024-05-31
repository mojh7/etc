/*
 * 2021-01-26
 * https://www.acmicpc.net/problem/9663
 * 백준 백트래킹 골드 5
 *
벽 느꼈다. 시간 초과 메모리 초과 다 나다가 해설 참고해서 작성한 코드에서 틀린 부분 고치고 풀음

좌측 상단 대각선 부터 시계방향으로 8방향 정해놓고 {0, 0} 부터 {n-1, n-1} 퀸을 일일이 놓아보면서
놓여진 퀸 n개가 서로 공격할 수 없을 때 cnt++ 하고 돌아와서 다시 다음 탐색 순서로 dfs해서 풀었다.
-> 시간 초과

질문 글, 해설 글 보니 체스랑, 퀸 이동 방향 특성상 배제 할 수 있는 부분이 있었는데
하나의 행과 하나의 열에는 하나의 퀸만 놓을 수 있고 여기에 대각선 체크를 추가하면 된다.
행은 0부터 n-1 까지 실행하고 있어서 열 기준으로 해당 열에 이미 퀸이 놓아져있는지
확인할 수 있게 boolean[] visitedCol을 추가 했다.

대각선 체크는 처음에는 int[][] board를 두고 특정위치에 퀸을 놓으면 갈 수 있는 경로에
count + 1를 하고 다시 되돌아갈 때 count - 1로 처리를 해준 다음에 
board[row][col] 값이 0이면 놓게 했었는데 이런 방법 보다는
각 행 마다 퀸이 몇 번째 열에 놓여졌는지 저장해두고 현재 위치와 이전에 놓은 퀸의 y와 x값 차이가
각각 절대값이 같으면 대각선에 놓아진 것으로 체크했다.

해당 방법으로도 시간 초과가 나길래 해설 참고해서 작성한 코드를 봤더니 dfs내에서
row for문과 col for문 이중으로 쓰여져있어서 초과가 났다.
이중 포문으로 하면 row = 0, 1, 2, ..., n-1 진행을 할텐데 row가 1일 때 부터 놓으면 이미 0 자리에 놓지
않아서 다음 2, 3, 4, n-1를 놓아도 n개가 될 수 없다. 해당 부분을 고치니 해결됨.
 */

import java.util.Scanner;

class Main {
    static int n;
    static int answer;
    static boolean[] visitedCol;
    static int[] queens;

    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        n = sc.nextInt();
        answer = 0;
        visitedCol = new boolean[n];
        queens = new int[n];

        dfs(0, 0);
        System.out.println(answer);
    }

    public static void dfs(int row, int cnt) {
        if(cnt == n) {
            answer++;
            return;
        }

        for(int col = 0; col < n; col++) {
            if(!canPutQueen(row, col, cnt)) continue;
            visitedCol[col] = true;
            queens[cnt] = col;
            dfs(row + 1, cnt + 1);
            visitedCol[col] = false;
        }
    }

    public static boolean canPutQueen(int row, int col, int cnt) {
        if(visitedCol[col]) return false;
        for(int floor = 0; floor < cnt; floor++) {
            if(row - floor == Math.abs(col - queens[floor])) return false;
        }
        return true;
    }
}