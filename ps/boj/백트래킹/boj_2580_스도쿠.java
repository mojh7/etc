/*
 * 2021-01-27
 * https://www.acmicpc.net/problem/2580
 * 백준 백트래킹 골드 4
 *
 */

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.StringTokenizer;

class Main {
    static final int n = 9;
    static int[][] board = new int[n][n];
    static ArrayList<int[]> blanks;
    static boolean[][] filledNumCol = new boolean[n][n+1];
    static boolean[][] filledNumRow = new boolean[n][n+1];
    static boolean[][][] filledNumSquare = new boolean[n/3][n/3][n+1];

    public static void main(String[] args) throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        StringTokenizer st;
        int num;
        blanks = new ArrayList<>();
        for(int row = 0; row < n; row++) {
            for(int col = 1; col <= n; col++) {
                filledNumRow[row][col] = false;
                filledNumCol[row][col] = false;
            }
        }
        for(int row = 0; row < n / 3; row++) {
            for(int col = 0; col < n / 3; col++) {
                for(int idx = 1; idx <= n; idx++) {
                    filledNumSquare[row][col][idx] = false;
                }
            }
        }
        for(int row = 0; row < n; row++) {
            st = new StringTokenizer(br.readLine());
            for(int col = 0; col < n; col++) {
                num = Integer.parseInt(st.nextToken());
                board[row][col] = num;
                if(num == 0) {
                    blanks.add(new int[]{ row, col, 0 });
                    continue;
                }
                filledNumRow[row][num] = true;
                filledNumCol[col][num] = true;
                filledNumSquare[row/3][col/3][num] = true;
            }
        }

        backtracking();

        for(int row = 0; row < n; row++) {
            for(int col = 0; col < n; col++) {
                System.out.printf("%d ", board[row][col]);
            }
            System.out.println();
        }
    }

    public static void backtracking() {
        int[] cur;
        int blanksIdx = 0;
        int row;
        int col;
        boolean back = false;
        while (blanksIdx < blanks.size()) {
            cur = blanks.get(blanksIdx);
            row = cur[0];
            col = cur[1];
            if(back) {
                filledNumRow[row][cur[2]] = false;
                filledNumCol[col][cur[2]] = false;
                filledNumSquare[row/3][col/3][cur[2]] = false;
                board[row][col] = 0;
            }
            back = true;
            while (++cur[2] <= n) {
                if(filledNumRow[row][cur[2]] || filledNumCol[col][cur[2]] ||
                    filledNumSquare[row/3][col/3][cur[2]])
                    continue;
                filledNumRow[row][cur[2]] = true;
                filledNumCol[col][cur[2]] = true;
                filledNumSquare[row/3][col/3][cur[2]] = true;
                board[row][col] = cur[2];
                back = false;
                break;
            }
            if(back) {
                blanksIdx--;
                cur[2] = 0;
                continue;
            }
            blanksIdx++;
        }
    }
}