/*
 * 2021-02-12
 * https://www.acmicpc.net/problem/7453
 * 백준 투포인터 골드 2
 * 예전에 해설 본 적 있는데도 다시 풀어보니 모르겠다. 또 다시 해설 참고해서 풀음

일반적인 방법으로는 A, B, C, D 하나 씩 바꿔가며 일일이 계산해서 푸는데
이러면 O(n^4) 이 걸리는데 1 <= n <= 4000 이라서 시간초과가 걸린다.

4개의 배열을 두 개씩 합하고 투포인터를 통해서 갯수를 세면된다.

n^2 크기의 또 다른 배열 left와 right 배열을 만들고
left 배열에는 A와 B 원소의 모든 합을 right 배열에는 C와 D원소 모든 합을 저장하고
정렬한 다음 한 쪽은 최솟값 부터 크기를 키우고 다른 한 쪽은 최댓값 크기를 작게 탐색하며
0인 경우를 체크하면된다.

그런데 아래와 같은 경우 합이 0이 되지만 좌측 혹은 우측의 숫자가 여러 개 인경우
좌측의 같은 숫자 * 우측의 같은 숫자 갯수만큼 answer에 덧셈 해줘야한다.

2
2 2 -2 -2
2 2 -2 -2

left right
4 -4
4 -4
4 -4
4 -4
 */

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Arrays;
import java.util.StringTokenizer;

class Main {
    public static void main(String[] args) throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        StringTokenizer st;
        long answer = 0;
        int n = Integer.parseInt(br.readLine());
        int squared = n * n;
        int sumIdx = 0;
        int leftSumIdx = 0;
        int rightSumIdx = squared - 1;
        int sum, leftSum, rightSum;
        long leftCount, rightCount;
        int[][] nums = new int[n][4];
        int[] leftSums = new int[squared];
        int[] rightSums = new int[squared];

        for (int line = 0; line < n; line++) {
            st = new StringTokenizer(br.readLine());
            for (int idx = 0; idx < 4; idx++) {
                nums[line][idx] = Integer.parseInt(st.nextToken());
            }
        }

        for (int left = 0; left < n; left++) {
            for (int right = 0; right < n; right++) {
                leftSums[sumIdx] = nums[left][0] + nums[right][1];
                rightSums[sumIdx++] = nums[left][2] + nums[right][3];
            }
        }
        Arrays.sort(leftSums);
        Arrays.sort(rightSums);

        while (leftSumIdx < squared && rightSumIdx >= 0) {
            leftSum = leftSums[leftSumIdx];
            rightSum = rightSums[rightSumIdx];
            sum = leftSum + rightSum;
            if (sum > 0) {
                rightSumIdx--;
            } else if (sum < 0) {
                leftSumIdx++;
            } else {
                leftCount = 0;
                while (leftSumIdx < squared && leftSum == leftSums[leftSumIdx]) {
                    leftSumIdx++;
                    leftCount++;
                }
                rightCount = 0;
                while (rightSumIdx >= 0 && rightSum == rightSums[rightSumIdx]) {
                    rightSumIdx--;
                    rightCount++;
                }
                answer += leftCount * rightCount;
            }
        }

        System.out.println(answer);
    }
}
