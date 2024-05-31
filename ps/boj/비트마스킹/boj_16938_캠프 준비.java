/*
 * https://www.acmicpc.net/problem/16938
 * 완탐, 비트마스킹, 백트래킹 골드5
 *

 */

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.StringTokenizer;

class Main {
    private static int answer = 0;
    public static void main(String[] args) throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        //BufferedWriter bw = new BufferedWriter(new OutputStreamWriter(System.out));
        answer = 0;
        StringTokenizer st;
        st = new StringTokenizer(br.readLine());
        int n = Integer.parseInt(st.nextToken());
        int l = Integer.parseInt(st.nextToken());
        int r = Integer.parseInt(st.nextToken());
        int x = Integer.parseInt(st.nextToken());
        int[] problems = new int[n];

        st = new StringTokenizer(br.readLine());
        for(int idx = 0; idx < n; idx++) {
            problems[idx] = Integer.parseInt(st.nextToken());
        }

        combination(0, Integer.MAX_VALUE, 0, 0, 0, problems, n, l, r, x);

        System.out.println(answer);
        /*bw.write(String.valueOf(answer));
        bw.newLine();
        bw.flush();
        bw.close();*/
    }

    static void combination(int sum, int min, int max, int cnt, int start,
                            int[] problems, int n, int l, int r, int x) {
        /*
        조건 중 부합하지 않을 때 새로 문제를
        더 골라도 될 때 cnt < 2, max - min < x 일 때
        더 고르면 안될 때 sum > r
         */
        if(sum > r) return;
        if(cnt >= 2 && max - min >= x && l <= sum) {
            answer++;
        }

        for(int idx = start; idx < n; idx++) {
            int currLevel = problems[idx];
            combination(sum + currLevel, Math.min(currLevel, min),
                    Math.max(currLevel, max), cnt + 1, idx + 1,
                    problems, n, l, r, x);
        }
    }
}