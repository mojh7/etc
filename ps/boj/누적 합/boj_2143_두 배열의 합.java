/*
 * 2021-10-23
 * https://www.acmicpc.net/problem/2143
 * 백준 누적 합 골드 3
cnt가 int 범위 벗어나는 경우 생김 한 70~80%에서 틀렸음
long으로 고치니 정답

n과 m범위가 1~1000이고
부 배열 갯수가
1부터 n 혹은 m까지의 합과 같음 그래서
n(n+1)/2 인데

a부 배열 + b부 배열 = t 되는 경우 최대치가

n(n+1)/2 * m(m+1)/2 이 될 수 있음
5*5*10^10 될 수 있어서 cnt를 long으로 했어야됨
 */

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.HashMap;
import java.util.Map;
import java.util.StringTokenizer;

public class Main {

    public static void main(String[] args) throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        StringTokenizer st;
        long cnt = 0;
        int t = Integer.parseInt(br.readLine());
        int n = Integer.parseInt(br.readLine());

        // b 합 갯수
        Map<Integer, Integer> map = new HashMap<>();
        int[] a = new int[n+1];

        st = new StringTokenizer(br.readLine());
        for (int idx = 1; idx <= n; idx++) {
            a[idx] = a[idx - 1] + Integer.parseInt(st.nextToken());
        }

        int m = Integer.parseInt(br.readLine());
        int[] b = new int[m+1];

        st = new StringTokenizer(br.readLine());
        for (int idx = 1; idx <= m; idx++) {
            b[idx] = b[idx - 1] + Integer.parseInt(st.nextToken());
        }

        int currSum;
        // b 부 배열의 합 갯수 구하기
        for (int size = 1; size <= m; size++) {
            for (int end = size; end <= m; end++) {
                currSum = b[end] - b[end - size];
                if (map.containsKey(currSum)) {
                    map.put(currSum, map.get(currSum) + 1);
                } else {
                    map.put(currSum, 1);
                }
            }
        }

        int target;

        // t - a인 b 부 배열의 합 찾기
        for (int size = 1; size <= n; size++) {
            for (int end = size; end <= n; end++) {
                currSum = a[end] - a[end - size];
                target = t - currSum;
                cnt += map.getOrDefault(target, 0);
            }
        }

        System.out.println(cnt);
    }
}
