/*
 * 2021-01-05
 * https://www.acmicpc.net/problem/4779
 * 백준 분할 정복 실버 3 칸토어 집합
 *
 */

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;

class Main {
    static final String PRINT = "- -   - -";
    static final String BLANK = "         ";
    static int n;
    static boolean[] prints;
    static String[] answer;
    static int[] size;
    public static void main(String[] args) throws IOException {
        initialize();
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        BufferedWriter bw = new BufferedWriter(new OutputStreamWriter(System.out));
        String input = br.readLine();
        while(input != null) {
            n = Integer.parseInt(input);
            bw.write(answer[n]);
            bw.newLine();
            input = br.readLine();
        }
        bw.flush();
        bw.close();
    }
    public static void initialize() {
        size = new int[15];
        size[2] = 1;
        for(int idx = 3; idx < 15; idx++) {
            size[idx] = 3 * size[idx - 1];
        }
        prints = new boolean[size[12]];
        answer = new String[13];
        d(12, 0, size[12]);
        StringBuilder sb = new StringBuilder();
        for(int idx = 0; idx < size[12]; idx++) {
            sb.append(prints[idx] ? PRINT : BLANK);
        }
        answer[12] = sb.toString();
        for(int idx = 0; idx < 13; idx++) {
            answer[idx] = answer[12].substring(0, size[idx + 2]);
        }
    }

    public static void d(int depth, int start, int end) {
        if(depth == 2) {
            prints[start] = true;
            return;
        }

        int diff = (end - start) / 3;
        int mid1 = start + diff;
        int mid2 = mid1 + diff;
        d(depth - 1, start, mid1);
        for(int idx = mid1; idx < mid2; idx++) {
            prints[idx] = false;
        }
        d(depth - 1, mid2, end);
    }
}