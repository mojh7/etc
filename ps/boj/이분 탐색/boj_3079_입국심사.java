/*
 * 2021-02-15
 * https://www.acmicpc.net/problem/3079
 * 백준 이분 탐색 실버 1
 *
풀고나니 약 반년 전에 프로그래머스에서 똑같은 문제 풀었었음...
그런데 처음 보고 바로 딱 풀이가 생각나지 않았다...

이분 탐색 문제인 만큼 답으로 예상되는 시간을 두고 그 시간 내에 m명의 친구 모두가 심사를
마칠 수 있냐 없냐에 따라서 범위 조절을 해야겠다 생각을 함.
그런데 해당 시간내에 어떻게 m명의 친구 모두 심사가 완료 되었는지 판단할 수 있는가?
에서 고민을 좀 길게 했음.

답이라 예상되는 시간을 t라고 하면 한 입국 심사대에서는 t / 입국심사대에서 걸리는 시간 명수
만큼 사람을 심사할 수 있으니 t / immigrations[idx] 의 합이 m명보다 크거나 같으면 심사가
완료되었다고 판단하였음.

 */

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.StringTokenizer;

class Main {
    static int n, m;
    static long answer, min;
    static long[] immigrations;
    public static void main(String[] args) throws IOException {
        answer = 0;
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        StringTokenizer st = new StringTokenizer(br.readLine());
        n = Integer.parseInt(st.nextToken());
        m = Integer.parseInt(st.nextToken());
        immigrations = new long[n];
        min = Long.MAX_VALUE;
        for (int idx = 0; idx < n; idx++) {
            immigrations[idx] = Integer.parseInt(br.readLine());
            if(min > immigrations[idx]) {
                min = immigrations[idx];
            }
        }
        answer = binarySearch();
        System.out.println(answer);
    }

    public static long binarySearch() {
        long start = 1;
        long end = min * m;
        long mid;
        boolean possible;
        while (start <= end) {
            mid = (start + end) / 2;
            possible = possible(mid);
            if(possible) {
                end = mid - 1;
            } else {
                start = mid + 1;
            }
        }
        return start;
    }

    public static boolean possible(long time) {
        long passedCount = 0;
        for(int idx = 0; idx < n; idx++) {
            passedCount += time / immigrations[idx];
            if(passedCount >= m) return true;
        }
        return false;
    }
}