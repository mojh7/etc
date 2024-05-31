/*
 * 2021-02-05
 * https://www.acmicpc.net/problem/1780
 * 백준 분할 정복 실버 2
 *
 */

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.StringTokenizer;

class Main {
    static int n;
    static int[][] matrix = new int[2187][2187];
    static int[] answer;;

    public static void main(String[] args) throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        StringTokenizer st;
        answer = new int[3];
        n = Integer.parseInt(br.readLine());
        for (int row = 0; row < n; row++) {
            st = new StringTokenizer(br.readLine());
            for (int col = 0; col < n; col++) {
                matrix[row][col] = Integer.parseInt(st.nextToken()) + 1;
            }
        }

        divide(0, 0, n);
        for (int idx = 0; idx < 3; idx++) {
            System.out.println(answer[idx]);
        }
    }

    public static void divide(int y, int x, int size) {
        if (size == 1) {
            answer[matrix[y][x]]++;
            return;
        }

        int num = matrix[y][x];
        for (int row = 0; row < size; row++) {
            for (int col = 0; col < size; col++) {
                if (matrix[y+row][x+col] != num) {
                    int nextSize = size / 3;
                    for (int r = 0; r < size; r += nextSize) {
                        for (int c = 0; c < size; c += nextSize) {
                            divide(y+r, x+c, nextSize);
                        }
                    }
                    return;
                }
            }
        }

        answer[num]++;
    }
}