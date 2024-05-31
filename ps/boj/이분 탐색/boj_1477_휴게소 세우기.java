/*
 * 2021-01-03
 * https://www.acmicpc.net/problem/1477
 * 백준 휴게소 세우기 이분 탐색
 *
 * https://www.acmicpc.net/problem/2110 공유기 설치 문제랑 동일
 *
 * 각 휴게소 끼리의 거리 차이를 pq에 넣고 거리 차이가 큰 지점에 중간에 휴게소를
 * 세워 나가면 답이 나올까 생각했는데 휴게소가 없는 구간의 최대값의 "최솟값"을
 * 구하는 것이라 다르게 접근 해서 풀어야됨.
 *
 * 해설 참고 해서 풀음. 휴게소가 없는 구간의 최대값인 mid를
 * mid 값 대로 휴게소를 세웠을 때 갯수와 m과 비교하여 이분 탐색하여 해결
 *
 * 이분 탐색 left, right 부등호에 대해서 자주 헷갈리는데 이분 탐색 함수내에서

while(left < right) 로 했으면 

if(m < cnt) {
    left = mid + 1;
} else {
    right = mid;
}
이게 맞고

while (left <= right) 로 할 거면

right = mid - 1로 해야됨.
 */

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.IOException;
import java.util.*;

class Main {
    public static void main(String[] args) throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        BufferedWriter bw = new BufferedWriter(new OutputStreamWriter(System.out));

        StringTokenizer st = new StringTokenizer(br.readLine());
        int n = Integer.parseInt(st.nextToken());
        int m = Integer.parseInt(st.nextToken());
        int l = Integer.parseInt(st.nextToken());
        int[] highway = new int[n+2];
        highway[n+1] = l;

        st = new StringTokenizer(br.readLine());
        for (int idx = 1; idx <= n; idx++) {
            highway[idx] = Integer.parseInt(st.nextToken());
        }
        Arrays.sort(highway);

        int answer = binarySearch(highway, m);
        bw.write(Integer.toString(answer));
        bw.flush();
        bw.close();
    }

    private static int binarySearch(int[] highway, int m) {
        int left = 0;
        int right = highway[highway.length - 1];
        int mid = 0;
        int cnt = 0;
        int pos = 0;
        while (left < right) {
            cnt = 0;
            mid = (left + right) / 2;
            for(int idx = 0; idx < highway.length - 1; idx++) {
                pos = highway[idx];
                while (pos + mid < highway[idx + 1]) {
                    cnt++;
                    pos += mid;
                }
            }

            if(m < cnt) {
                left = mid + 1;
            } else {
                right = mid;
            }
        }

        return left;
    }
}