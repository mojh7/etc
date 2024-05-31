/*
 * https://www.acmicpc.net/problem/2107
 * 정렬 골4
 */

package com.company;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Arrays;
import java.util.Comparator;
import java.util.StringTokenizer;

class Main {
    public static void main(String[] args) throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        //BufferedWriter bw = new BufferedWriter(new OutputStreamWriter(System.out));
        StringTokenizer st;
        int answer = 0;
        int max = 0;
        int n = Integer.parseInt(br.readLine());
        int[][] numbers = new int[n][2];

        for (int idx = 0; idx < n; idx++) {
            st = new StringTokenizer(br.readLine());
            numbers[idx][0] = Integer.parseInt(st.nextToken());
            numbers[idx][1] = Integer.parseInt(st.nextToken());
        }

        Arrays.sort(numbers, Comparator.comparingInt(o -> o[0]));

        for (int idx = 0; idx < n; idx++) {
            int cnt = 0;
            for (int idx2 = idx + 1; idx2 < n; idx2++) {
                if (numbers[idx2][0] >= numbers[idx][1]) {
                    break;
                }

                if (numbers[idx2][1] < numbers[idx][1]) {
                    cnt++;
                }
            }

            if (max < cnt) {
                max = cnt;
            }
        }

        answer = max;
        System.out.println(answer);
    }

}
