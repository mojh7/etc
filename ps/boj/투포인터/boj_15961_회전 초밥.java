/*
 * 2021-02-23
 * https://www.acmicpc.net/problem/15961
 * 백준 투포인터 골드 5
 * 

index 0 ~ k-1까지의 초밥 가짓 수 체크 후 투 포인터 이용하여 한 바퀴 다 탐색
leftIdx에서 먹은 초밥 빼고 rightIdx에서 먹은 초밥 새로운 거면 가짓 수 추가 할 때
boolean[] type 을 썼는데 [7 9 7 30] 을 먹고 새로운 2 도달 할 때 7번 초밥이 두 개 여서
leftIdx에 있는 7번 초밥을 빼도 가짓 수에 변동이 없어야 되어서 int[] type 으로 조절 함.
 */

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.StringTokenizer;

class Main {
    static int n, d, k, c, answer;
    static int[] sushis;
    public static void main(String[] args) throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        StringTokenizer st = new StringTokenizer(br.readLine());
        n = Integer.parseInt(st.nextToken());
        d = Integer.parseInt(st.nextToken());
        k = Integer.parseInt(st.nextToken());
        c = Integer.parseInt(st.nextToken());
        sushis = new int[n];
        for (int idx = 0; idx < n; idx++) {
            sushis[idx] = Integer.parseInt(br.readLine());
        }

        answer = twoPointers();
        System.out.println(answer);
    }

    public static int twoPointers() {
        int[] type = new int[d+1];
        int maxCount = 0;
        for(int idx = 0; idx < k; idx++) {
            maxCount += addSushi(type, sushis[idx]);
        }
        int leftIdx = 0;
        int rightIdx = k;
        int count = maxCount;

        while (true) {
            if(maxCount <= count) {
                maxCount = containsCouponSushi(type) ? count : count + 1;
            }

            count += removeSushi(type, sushis[leftIdx]);
            count += addSushi(type, sushis[rightIdx]);
            leftIdx = (leftIdx + 1) % n;
            rightIdx = (rightIdx + 1) % n;
            if(leftIdx == 0) break;
        }

        return maxCount;
    }

    public static int addSushi(int[] type, int current) {
        if (type[current]++ == 0) {
            return 1;
        }
        return 0;
    }

    public static int removeSushi(int[] type, int current) {
        if (--type[current] == 0) {
            return -1;
        }
        return 0;
    }

    public static boolean containsCouponSushi(int[] type) {
        return type[c] >= 1;
    }
}