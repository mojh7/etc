/*
 * 2021-02-17
 * https://www.acmicpc.net/problem/2352
 * 백준 이분 탐색, 가장 긴 증가하는 부분 수열 골드 3
 *
가장 긴 증가하는 부분 수열(LIS) 몰라서 풀이법 보고 문제 풀음
https://seungkwan.tistory.com/8 여기 글이 이해 잘 됨.
 */

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.StringTokenizer;

class Main {
    static int n;
    static int answer;
    static int[][] ports;
    static int subsequenceLength;
    public static void main(String[] args) throws IOException {
        subsequenceLength = 0;
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        n = Integer.parseInt(br.readLine());
        ports = new int[n][3];
        StringTokenizer st = new StringTokenizer(br.readLine());
        for (int idx = 0; idx < n; idx++) {
            ports[idx][0] = Integer.parseInt(st.nextToken());
        }

        answer = getMaxConnectedCount();
        System.out.println(answer);
    }

    public static int getMaxConnectedCount() {
        subsequenceLength = 0;
        for(int idx = 0; idx < n; idx++) {
            if(subsequenceLength == 0 || ports[idx][0] > ports[subsequenceLength - 1][1]) {
                ports[subsequenceLength++][1] = ports[idx][0];
            } else {
                ports[binarySearch(ports[idx][0], subsequenceLength)][1] = ports[idx][0];
            }
        }
        return subsequenceLength;
    }

    public static int binarySearch(int portNumber, int maxLength) {
        int start = 0;
        int end = maxLength;
        int  mid;
        while (start <= end) {
            mid = (start + end) / 2;
            if(portNumber <= ports[mid][1]) {
                end = mid - 1;
            } else {
                start = mid + 1;
            }
        }
        return start;
    }
}