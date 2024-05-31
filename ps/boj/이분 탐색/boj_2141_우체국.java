/*
 * 2021-02-16
 * https://www.acmicpc.net/problem/2141
 * 백준 이분 탐색 골드5
 *
푸는 중에는 골드 5 아닌 것 같은데? 생각이 들었는데 풀고나니 골드 5 얼추 맞는 것 같다...
풀이 시간 생각보다 오래걸림
7%에서 두 번 틀리고 세 번째 시도 때 해결됨.

놓쳤던 것
--- 1. 입력 순서가 곧 마을 위치인 줄 알았다.
3
1 3
2 5
3 3
처럼 idx 가 곧 마을 위치인 줄 알아서 1차원 배열로 int[] towns 놓고 사람 수만 저장하고
index 위치를 마을 위치로 사용했다. 그런데
2
1 1
3 1
이런식으로 입력이 들어 올 수도 있다.

--- 2. 1번과 비슷한 이유로 x[i] 범위가 1 <= x[i] <= 1000000000 인줄 알았는데 
|x[i]| <= 1000000000 으로 음수 값이 들어 올 수 있었다. 그래서 다시 int[][] towns
2차원 배열로 수정하고 towns[idx][0] 에 마을 위치를 놓고 오름차순으로 정렬해서 사용했다.

--- 사용 예제 입력
5
-5 23
-22 13
0 23
4 55
2 7
답 : 2

10
1 55577
2 111
3 71
4 2
5 210
6 22
7 55
8 226
9 2
10 55225
답 : 3

10
1 77
2 214
3 71
4 2
5 210
6 22
7 55
8 226
9 2
10 225
답 : 5

3
1 1
2 0
3 1
답 : 1
 */

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Arrays;
import java.util.Comparator;
import java.util.StringTokenizer;

class Main {
    static int n;
    static int answer;
    static int[][] towns;
    public static void main(String[] args) throws IOException {
        StringTokenizer st;
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        n = Integer.parseInt(br.readLine());
        towns = new int[n][2];
        for (int idx = 0; idx < n; idx++) {
            st = new StringTokenizer(br.readLine());
            towns[idx][0] = Integer.parseInt(st.nextToken());
            towns[idx][1] = Integer.parseInt(st.nextToken());
        }
        Arrays.sort(towns, Comparator.comparingInt(o -> o[0]));

        answer = binarySearch();
        System.out.println(answer);
    }

    public static int binarySearch() {
        int start = towns[0][0];
        int end = towns[n-1][0];
        int mid;
        long centerSumDistance = 0;
        long lowRangeSumDistance = 0;
        while (start <= end) {
            mid = (start + end) / 2;
            centerSumDistance = calculateDistance(mid);
            lowRangeSumDistance = calculateDistance(mid - 1);
            if(lowRangeSumDistance <= centerSumDistance) {
                end = mid - 1;
            } else {
                start = mid + 1;
            }
        }
        return end;
    }

    public static long calculateDistance(int position) {
        if(position < towns[0][0] || position > towns[n-1][0]) return Long.MAX_VALUE;
        long sumDistance = 0;
        for(int idx = 0; idx < n; idx++) {
            sumDistance += Math.abs(position - towns[idx][0]) * (long)towns[idx][1];
        }
        return sumDistance;
    }
}