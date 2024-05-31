/*
 * 2021-02-18
 * https://www.acmicpc.net/problem/1208
 * 백준 이분 탐색, 중간에서 만나기 골드 2
 * 
브루트포스 + 백트래킹 + 정렬 + 투 포인터(중간에서 만나기)로 풀었다.
정렬 때문에 이분 탐색 영역인건지? 모르겠지만 알고리즘 분류에 중간에서 만나기로
되어 있어서 합이 0인 네 정수 풀이에서 힌트 얻고 풀음.

부분수열의 합1 과 다르게 n범위가 크고 (1 <= n <= 40) 시간 제한이 1초인데
브루트포스로 풀면 2^40 = (2^20)^2 는 백만의 제곱 정도이기에 시간초과가 남.

선형 시간을 로그 시간으로 바꿀게 필요하고 이분 탐색 카테고리만 봐서는 어떻게
풀어야 될지 모르겠다. 알고리즘 분류에 중간에서 만나기가 있어서 주어진 n개의 정수를
반으로 나누어서 각각 부분수열의 합의 집합을 만들고 좌측과 우측에서 각각 만들어진 합이 s인
경우를 더하고 좌측 합의 집합과 우측 합의 집합을 정렬하여 좌측 최솟값에서 큰 값으로
우측은 최댓값에서 작은 값으로 투 포인터를 써서 합이 s가 나는 경우를 더하였다.

풀고 나니 n개의 정수를 반으로 나누어서 처리한 것에서 이분 탐색 카테고리로 된 것 같다.

첫 시도 때 57% 에서 틀렸는데 answer가 int 범위를 넘어서는 경우가 생겨서
long type으로 바꿨더니 정답 처리됨. 
 */

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Arrays;
import java.util.StringTokenizer;

class Main {
    static int n;
    static int s;
    static long answer;
    static int sumIdx;
    public static void main(String[] args) throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        StringTokenizer st = new StringTokenizer(br.readLine());
        n = Integer.parseInt(st.nextToken());
        s = Integer.parseInt(st.nextToken());
        int[] leftNumbers = new int[n/2];
        int[] rightNumbers = new int[n-n/2];
        int[] sumOfLeftSubsequence = new int[(1 << (leftNumbers.length)) - 1];
        int[] sumOfRightSubsequence = new int[(1 << (rightNumbers.length)) - 1];
        st = new StringTokenizer(br.readLine());
        for (int idx = 0; idx < leftNumbers.length; idx++) {
            leftNumbers[idx] = Integer.parseInt(st.nextToken());
        }
        for (int idx = 0; idx < rightNumbers.length; idx++) {
            rightNumbers[idx] = Integer.parseInt(st.nextToken());
        }

        sumIdx = 0;
        dfs(0, 0, leftNumbers, sumOfLeftSubsequence);
        sumIdx = 0;
        dfs(0, 0, rightNumbers, sumOfRightSubsequence);
        Arrays.sort(sumOfLeftSubsequence);
        Arrays.sort(sumOfRightSubsequence);

        int leftIdx = 0;
        int rightIdx = sumOfRightSubsequence.length - 1;
        int sum;
        long sameLeftSumCount;
        long sameRightSumCount;
        int leftSum;
        int rightSum;
        while (leftIdx < sumOfLeftSubsequence.length && rightIdx >= 0) {
            sum = sumOfLeftSubsequence[leftIdx] + sumOfRightSubsequence[rightIdx];
            if(sum == s) {
                sameLeftSumCount = 0;
                sameRightSumCount = 0;
                leftSum = sumOfLeftSubsequence[leftIdx];
                rightSum = sumOfRightSubsequence[rightIdx];
                while (leftIdx < sumOfLeftSubsequence.length &&
                        sumOfLeftSubsequence[leftIdx] == leftSum) {
                    sameLeftSumCount++;
                    leftIdx++;
                }
                while (rightIdx >= 0 && sumOfRightSubsequence[rightIdx] == rightSum) {
                    sameRightSumCount++;
                    rightIdx--;
                }
                answer += sameLeftSumCount * sameRightSumCount;
            } else if(sum > s) {
                rightIdx--;
            } else {
                leftIdx++;
            }
        }

        System.out.println(answer);
    }

    public static void dfs(int sum, int idx, int[] numbers, int[] sumOfSubsequence) {
        while(idx < numbers.length) {
            sum += numbers[idx];
            if(sum == s) {
                answer++;
            }
            sumOfSubsequence[sumIdx++] = sum;
            dfs(sum, idx + 1, numbers, sumOfSubsequence);
            sum -= numbers[idx++];
        }
    }
}