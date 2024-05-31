/*
 * 2021-02-06
 * https://www.acmicpc.net/problem/1074
 * 백준 분할 정복 실버 1
 *
4등분 해서 좌측 상단부터 divide 함수 재귀를 통해 일일이 몇 번째로 방문하는지 체크 했다.
결국 o(넓이^2) 걸렸고 n의 크기가 1 <= n <= 15 이므로 o((2^15)^2) 가 걸리게 해서 시간 초과함.

--- 개선

현재 2^n * 2^n 크기의 2차원 배열에서 4등분 한 2^(n-1) * 2^(n-1) 크기의 2차원 배열 내의
r행 c열이 있는가? 를 체크한 후 없으면 해당 칸에 대한 순서를 한 번에 스킵하고
r행 c열이 있으면 divide함수를 다시 호출하여 재귀를 통해 순서를 구하여 해결

 */

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.StringTokenizer;

class Main {
    static int n;
    static int r;
    static int c;
    static int answer;

    public static void main(String[] args) throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        StringTokenizer st = new StringTokenizer(br.readLine());
        n = Integer.parseInt(st.nextToken());
        r = Integer.parseInt(st.nextToken());
        c = Integer.parseInt(st.nextToken());
        divide(0, 0, 1 << n, 0);
        System.out.println(answer);
    }

    public static void divide(int y, int x, int size, int order) {
        if(size == 2) {
            int cnt = 0;
            for(int row = 0; row < 2; row++) {
                for(int col = 0; col < 2; col++) {
                    if(r == y + row && c == x + col) {
                        answer = order + cnt;
                        return;
                    }
                    cnt++;
                }
            }
        }

        int posGap = size / 2;
        int orderGap = posGap * posGap;
        for(int row = y; row < y + size; row += posGap) {
            for(int col = x; col < x + size; col += posGap) {
                if(row <= r && r < row + posGap && col <= c && c < col + posGap) {
                    divide(row, col, posGap, order);
                    return;
                }
                order += orderGap;
            }
        }
    }
}