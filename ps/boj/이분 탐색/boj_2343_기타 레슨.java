/*
 * 2021-02-21
 * https://www.acmicpc.net/problem/2343
 * 백준 이분 탐색 실버 1
 */

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.StringTokenizer;

class Main {
    static int n;
    static int m;
    static int[] lessons;
    static long answer;
    public static void main(String[] args) throws IOException {
        answer = 0;
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        StringTokenizer st = new StringTokenizer(br.readLine());
        n = Integer.parseInt(st.nextToken());
        m = Integer.parseInt(st.nextToken());
        lessons = new int[n];
        st = new StringTokenizer(br.readLine());
        int sum = 0;
        for (int idx = 0; idx < n; idx++) {
            lessons[idx] = Integer.parseInt(st.nextToken());
            sum += lessons[idx];
        }

        answer = binarySearch(sum);
        System.out.println(answer);
    }

    public static int binarySearch(int end) {
        int start = 1;
        int mid;
        while (start <= end) {
            mid = (start + end) / 2;
            if (recodesBluray(mid)) {
                end = mid - 1;
            } else {
                start = mid + 1;
            }
        }
        return start;
    }

    public static boolean recodesBluray(int possibleSize) {
        int count = 0;
        int sum = 0;
        int idx = 0;
        while(idx < lessons.length) {
            if(sum + lessons[idx] <= possibleSize) {
                sum += lessons[idx++];
                continue;
            }
            if(++count > m) return false;
            sum = 0;
        }
        if(sum > 0 && ++count > m) return false;
        return true;
    }
}
