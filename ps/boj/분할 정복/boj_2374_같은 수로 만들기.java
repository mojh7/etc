/*
 * 2021-01-05
 * https://www.acmicpc.net/problem/2374
 * 백준 분할 정복
 *
 * 분할 정복 + 그리디 하게 풀어서 6번 시도 때에 맞춤.
 * 경우의 수에 따라서 분기 나눠서 divide 함수 재귀 돌게 했는데
 * 분할 정복 아닌 방법으로 푼 것 같은 ?? 하드 코딩해서 푼 것 같다...
 *
 *
반례
10
5
2
7
3
5
2
7
3
9
9
출력 : 18

4
2
5
2
4
출력 : 6

3
4
2
4
출력 : 2
*/


import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.IOException;

class Main {
    public static void main(String[] args) throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        BufferedWriter bw = new BufferedWriter(new OutputStreamWriter(System.out));
        int n = Integer.parseInt(br.readLine());
        int[] nums = new int[n];
        long answer = 0;
        for(int idx = 0; idx < n; idx++) {
            nums[idx] = Integer.parseInt(br.readLine());
        }

        answer = divide(nums, 0, nums[0], nums[0], 0);
        bw.write(Long.toString(answer));
        bw.flush();
        bw.close();
    }

    public static long divide(int[] nums, int startPos, int prevMin, int prevMax, long cnt) {
        if(startPos == nums.length) {
            return cnt;
        }

        int curBaseNum = nums[startPos];
        int idx = startPos + 1;
        // 같은 숫자 범위 찾기
        while (idx < nums.length && nums[idx] == curBaseNum) {
            idx++;
        }
        prevMin = curBaseNum < prevMin ? curBaseNum : prevMin;

        if(prevMax <= curBaseNum) {
            return divide(nums, idx, curBaseNum, curBaseNum, cnt + curBaseNum - prevMin);
        } else {
            if(idx == nums.length) {
                return cnt + prevMax - prevMin;
            }
            if(prevMin < curBaseNum) {
                return divide(nums, idx, curBaseNum, prevMax, cnt + curBaseNum - prevMin);
            } else {
                return divide(nums, idx, prevMin, prevMax, cnt);
            }
        }
    }
}