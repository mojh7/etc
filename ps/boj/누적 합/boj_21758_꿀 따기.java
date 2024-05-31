/*
 * 2023-08-12
 * https://www.acmicpc.net/problem/21758
 * 백준 누적 합 골5
맨 밑에 o(n^2) 풀이 55점

3n으로 o(n) 으로 풀면 100점

처음에는 꿀통 위치 옮기면서
1) 좌측에 벌 한 마리 고정
2) 우측에 벌 한 마리 고정
3) 가장 양 끝에 벌 두 마리

인 경우를 다 체크했는데 이게 시간복잡도가 o(n^2)이 나오고
이 문제가 누적합도 있지만 그리디도 있어서 자세히 보면
위에 1과 2번 경우에는 사실 꿀통이 반대끝 쪽에 있어야 최대치로 꿀의 양을 모음
(ex : 좌측에 벌 한 마리 고정일 때 반대쪽인 가장 우측에 꿀통 위치)
그래서 1번과 2번을 각각 o(n)으로 체크해서
총 3n으로 최대로 모을 수 있는 꿀의 양을 알 수 있음
 */

// 100점

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.StringTokenizer;

class Main {

    public static void main(String[] args) throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        int answer = 0;
        int n = Integer.parseInt(br.readLine());
        StringTokenizer st = new StringTokenizer(br.readLine());

        int[] prefixSum = new int[n+1];

        for (int idx = 1; idx <= n; idx++) {
            prefixSum[idx] = prefixSum[idx - 1] + Integer.parseInt(st.nextToken());
        }

        int honeySum;
        // 가장 좌측에 벌 한 마리 고정
        int fixedBee = prefixSum[n] - prefixSum[1];
        for (int bee = 2; bee < n; bee++) {
            honeySum = fixedBee + (prefixSum[n] - prefixSum[bee])
                    - (prefixSum[bee] - prefixSum[bee - 1]);
            answer = Math.max(answer, honeySum);
        }

        // 가장 우측에 벌 한 마리 고정
        fixedBee = prefixSum[n-1];
        for (int bee = n-1; bee >= 2; bee--) {
            honeySum = fixedBee + prefixSum[bee - 1]
                    - (prefixSum[bee] - prefixSum[bee - 1]);
            answer = Math.max(answer, honeySum);
        }

        // 벌이 각각 좌측 우측 끝에 위치하고 꿀통 위치 변경
        for (int idx = 1; idx <= n; idx++) {
            honeySum = prefixSum[idx] - prefixSum[1] + prefixSum[n-1] - prefixSum[idx - 1];
            answer = Math.max(answer, honeySum);
        }

        System.out.println(answer);
    }
}

//55점 짜리
// (이거 말고도 똑같은 o(n^2) 이긴하지만 꿀통 위치 기준으로 좌측 이나 우측 큰 값
// 기준으로 벌 위치 바꿔서 해도 55점

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.StringTokenizer;

class Main {

    public static void main(String[] args) throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        int answer = 0;
        int n = Integer.parseInt(br.readLine());
        StringTokenizer st = new StringTokenizer(br.readLine());

        int[] prefixSum = new int[n+1];

        for (int idx = 1; idx <= n; idx++) {
            prefixSum[idx] = prefixSum[idx - 1] + Integer.parseInt(st.nextToken());
        }

        // 벌통 위치 1부터 n까지 (idx 1 부터 시작) 바꿔가면서 꿀벌 위치에 따라 꿀의 양 max 구하기
        // 꿀벌 위치가 둘 중 하나는 좌측이나 우측 끝에 있어야 할 듯

        int fixedBee;
        int honeySum = 0;

        for (int idx = 1; idx <= n; idx++) {

            // 가장 좌측에 벌 한마리 고정
            fixedBee = prefixSum[idx] - prefixSum[1];

            for (int bee = 2; bee < idx; bee++) {
                honeySum = fixedBee + (prefixSum[idx] - prefixSum[bee])
                        - (prefixSum[bee] - prefixSum[bee - 1]);
                answer = Math.max(answer, honeySum);
            }

            // 가장 우측에 벌 한마리 고정
            fixedBee = prefixSum[n-1] - prefixSum[idx - 1];

            for (int bee = idx + 1; bee < n; bee++) {
                honeySum = fixedBee + (prefixSum[bee - 1] - prefixSum[idx - 1])
                        - (prefixSum[bee] - prefixSum[bee - 1]);
                answer = Math.max(answer, honeySum);
            }

            // 벌 두마리가 양 끝에 있는 경우
            honeySum = (prefixSum[idx] - prefixSum[1]) + (prefixSum[n - 1] - prefixSum[idx - 1]);
            answer = Math.max(answer, honeySum);
        }

        System.out.println(answer);
    }
}