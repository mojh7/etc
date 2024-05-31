/*
 * 2021-10-29
 * https://www.acmicpc.net/problem/10986
 * 백준 누적 합 골드3

index 1~i 까지 수 i개 누적합 S 라고 하면 S가 M의 배수일 때
나누어 떨어진다. 그리고 S에서 또 다른 index 1 ~ j 까지의
수 j개의 누적합 s을 라고 할 때 이 s가 M의 배수이면 S - s 도
결국 M의 배수이다.

그런데 S = 7, s = 1, M = 3일 때도 S - s = 7 - 1 = 6 = 2 * M
이 성립되는데 이처럼 S와 s가 m의 배수가 아닐 때 S에서
이전 수들의 누적합 s을 어떻게 한 번에 적절한 찾아서
M의 배수를 만들지? 생각에서 더 이상 생각이 안나
해설 참고 하여 풀이

모듈러 연산 특징 이용해서 풀이
(A + B) % C = (A%C + B%C) %C
답 변수 answer 문제 특성상 long으로 해야 안틀림
int로 해서 틀렸음
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
        StringTokenizer st = new StringTokenizer(br.readLine());

        int n = Integer.parseInt(st.nextToken());
        int m = Integer.parseInt(st.nextToken());

        long answer = 0;
        int sum = 0;
        int curr;
        Map<Integer, Integer> map = new HashMap<>();
        st = new StringTokenizer(br.readLine());

        for (int idx = 0; idx < n; idx++) {
            curr = Integer.parseInt(st.nextToken());
            sum = (sum + curr) % m;

            if (sum == 0) {
                answer++;
            }
            answer += map.getOrDefault(sum, 0);

            if (map.containsKey(sum)) {
                map.replace(sum, map.get(sum) + 1);
            } else {
                map.put(sum, 1);
            }
        }

        System.out.println(answer);
    }
}