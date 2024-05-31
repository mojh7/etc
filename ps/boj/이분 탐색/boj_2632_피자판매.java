/*
 * 2021-02-20
 * https://www.acmicpc.net/problem/2632
 * 백준 이분 탐색, 누적합 골드 1
 *
오랜만에 자력으로 첫 시도에 풀어서 뿌듯, 중간에서 만나기 문제 활용해서 투 포인터로 품.

1. 피자 A, B 각각 모든 연속된 조각 크기의 합을 구하고 구하는 도중에 고객이 원하는 피자 크기가
만족되면 answer++ 카운팅함.
2. 1에서 구한 배열을 오름차순으로 정렬하고 투 포인터 + 중간에서 만나기를 활용하여
피자 A는 index 0 부터 n-1 까지 피자 B는 n-1부터 0까지 순회하며 조건에 만족하는 경우 카운팅 

---

ide에서 풀 때 답이 5가 아닌 3이 나와서 다시 보니깐
피자 조각 갯수가 n개 일 때 n, n - 1, n - 2, ... , n - n + 1 이런식으로
n = 5이면 연속된 조각 크기가 1에서 5로 갈수록 5, 4, 3, 2, 1개 인줄 알아서
피자 A와 B 각각 연속된 피자 합을 담은 배열의 크기를 (n * (n + 1)) / 2로 두었는데
이 부분이 틀렸다.

피자가 원형이라 원형 배열임을 감안하여 피자 조각 크기가 n개가 아닌 경우에는
연속된 조각의 합의 경우의 수가 n개가 나온다. 그래서 (n * (n - 1)) + 1 만큼 경우의 수가 나온다.
 */

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Arrays;
import java.util.StringTokenizer;

class Main {
    static int desiredSize;
    static long answer;
    public static void main(String[] args) throws IOException {
        answer = 0;
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        desiredSize = Integer.parseInt(br.readLine());
        StringTokenizer st = new StringTokenizer(br.readLine());
        int m = Integer.parseInt(st.nextToken());
        int n = Integer.parseInt(st.nextToken());
        int[] pizzaA = new int[m];
        int[] pizzaB = new int[n];
        int[] sumOfPizzaSlicesA = new int[m * (m - 1) + 1];
        int[] sumOfPizzaSlicesB = new int[n * (n - 1) + 1];
        for (int idx = 0; idx < m; idx++) {
            pizzaA[idx] = Integer.parseInt(br.readLine());
        }
        for (int idx = 0; idx < n; idx++) {
            pizzaB[idx] = Integer.parseInt(br.readLine());
        }

        prefixSum(pizzaA, sumOfPizzaSlicesA);
        prefixSum(pizzaB, sumOfPizzaSlicesB);
        twoPointer(sumOfPizzaSlicesA, sumOfPizzaSlicesB);
        System.out.println(answer);
    }

    public static void prefixSum(int[] pizza, int[] sumOfPizzaSlices) {
        int sumIdx = 0;
        int maxCountPiece = pizza.length;
        for(int base = 0; base < maxCountPiece - 1; base++) {
            for (int idx = 0; idx < maxCountPiece; idx++) {
                if (base == 0) {
                    sumOfPizzaSlices[idx] = pizza[idx];
                } else {
                    sumOfPizzaSlices[sumIdx] = sumOfPizzaSlices[sumIdx - maxCountPiece] + pizza[(base + idx) % maxCountPiece];
                }
                if (sumOfPizzaSlices[sumIdx] == desiredSize) {
                    answer++;
                }
                sumIdx++;
            }
        }
        sumOfPizzaSlices[sumIdx] = sumOfPizzaSlices[sumIdx - maxCountPiece] + pizza[maxCountPiece - 1];
        if (sumOfPizzaSlices[sumIdx] == desiredSize) {
            answer++;
        }
    }

    public static void twoPointer(int[] sumOfPizzaSlicesA, int[] sumOfPizzaSlicesB) {
        Arrays.sort(sumOfPizzaSlicesA);
        Arrays.sort(sumOfPizzaSlicesB);
        int lengthA = sumOfPizzaSlicesA.length;
        int lengthB = sumOfPizzaSlicesB.length;
        int leftIdx = 0;
        int rightIdx = lengthB - 1;
        int sum;

        while (leftIdx < lengthA && rightIdx >= 0) {
            sum = sumOfPizzaSlicesA[leftIdx] + sumOfPizzaSlicesB[rightIdx];
            if (sum == desiredSize) {
                long countLeftSameSize = 0;
                long countRightSameSize = 0;
                int originalSize = sumOfPizzaSlicesA[leftIdx];
                while (leftIdx < lengthA && originalSize == sumOfPizzaSlicesA[leftIdx]) {
                    leftIdx++;
                    countLeftSameSize++;
                }
                originalSize = sumOfPizzaSlicesB[rightIdx];
                while (rightIdx >= 0 && originalSize == sumOfPizzaSlicesB[rightIdx]) {
                    rightIdx--;
                    countRightSameSize++;
                }

                answer += countLeftSameSize * countRightSameSize;
            } else if (sum > desiredSize) {
                rightIdx--;
            } else {
                leftIdx++;
            }
        }
    }
}
