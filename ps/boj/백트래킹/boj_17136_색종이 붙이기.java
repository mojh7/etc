/*
 * 2021-02-26
 * https://www.acmicpc.net/problem/17136
 * 백준 브루트포스, 백트래킹 골드 2
 * 풀이 중
 */

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Arrays;
import java.util.StringTokenizer;

class Main {
    static int n = 10;
    static boolean[][] grid;
    static int[] coloredPapersCount;
    public static void main(String[] args) throws IOException {
        int answer = 0;
        int coloredPaperLength;
        grid = new boolean[n][n];
        coloredPapersCount = new int[6];
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        StringTokenizer st;
        for (int row = 0; row < n; row++) {
            st = new StringTokenizer(br.readLine());
            for (int col = 0; col < n; col++) {
                grid[row][col] = st.nextToken().equals("1") ? true : false;
            }
        }
        Arrays.fill(coloredPapersCount, 5);

        for (int row = 0; row < n; row++) {
            for (int col = 0; col < n; col++) {
                if(!grid[row][col]) continue;
                coloredPaperLength = getRequiredColoredPaperLength(row, col);
                while (coloredPaperLength >= 1) {
                    if(coloredPapersCount[coloredPaperLength] >= 1) {
                        coverPapers(row, col, coloredPaperLength);
                        answer++;
                        break;
                    }
                    coloredPaperLength--;
                }
                if (coloredPaperLength == 0) {
                    System.out.println(-1);
                    return;
                }
            }
        }

        System.out.println(answer);
    }

    public static void coverPapers(int row, int col, int size) {
        coloredPapersCount[size]--;
        for(int y = row; y < row + size; y++) {
            for(int x = col; x < col + size; x++) {
                grid[y][x] = false;
            }
        }
    }

    public static int getRequiredColoredPaperLength(int row, int col) {
        int coloredPaperLength = 1;
        boolean impossible = false;
        for (int length = 0; length < 5; length++) {
            if (!grid[row + length][col + length]) {
                impossible = true;
            }
            for(int idx = 0; idx < length; idx++) {
                if (!grid[row + idx][col + length]) impossible = true;
                if (!grid[row + length][col + idx]) impossible = true;
            }

            if(impossible) {
                coloredPaperLength = length;
                break;
            }
        }
        return coloredPaperLength;
    }
}