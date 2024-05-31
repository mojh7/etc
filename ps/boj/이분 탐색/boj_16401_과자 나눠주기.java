/*
 * 2021-02-19
 * https://www.acmicpc.net/problem/16401
 * 백준 이분 탐색 실버 3
 *
 */

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.StringTokenizer;

class Main {
    static int m; // 조카 수
    static int n; // 과자 수
    static int[] snacks;
    static int maxSnackLength;
    static int answer;
    public static void main(String[] args) throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        StringTokenizer st = new StringTokenizer(br.readLine());
        m = Integer.parseInt(st.nextToken());
        n = Integer.parseInt(st.nextToken());
        snacks = new int[n];
        st = new StringTokenizer(br.readLine());
        for(int idx = 0; idx < n; idx++) {
            snacks[idx] = Integer.parseInt(st.nextToken());
            if(maxSnackLength < snacks[idx]) {
                maxSnackLength = snacks[idx];
            }
        }

        answer = binarySearch();
        System.out.println(answer);
    }

    public static int binarySearch() {
        int start = 1;
        int end = maxSnackLength;
        int mid;
        while (start <= end) {
            mid = (start + end) / 2;
            if(dividesSameSnacksLength(mid)) {
                start = mid + 1;
            } else {
                end = mid - 1;
            }
        }
        return end;
    }

    public static boolean dividesSameSnacksLength(int length) {
        int dividedCount = 0;
        for(int idx = 0; idx < n; idx++) {
            dividedCount += snacks[idx] / length;
            if(dividedCount >= m) return true;
        }
        return false;
    }
}