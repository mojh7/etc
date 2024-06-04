/*
 * 2024-06-04
 * https://www.acmicpc.net/problem/1106
 * 백준 dp 골드 4
 *

정확히 기억은 안나지만 이전에 풀었던

동전 100, 500, 1000원 뭐 이렇게 있고 어떻게 골라야

최소, 최대값이 나오는지 계산하는 dp 문제들이랑 유사한 것 같은데

어떻게 풀었는지 감이 안옴

알고리즘 분류를 보니 dp랑 배낭 문제에 속하길래

처음에는 배낭문제 때 처럼 idx랑 고객 수로 2차원배열로 dp 만들어서 풂

첫 풀이가 164ms 나오고 다른 풀이가 70~80ms 나오는 것을 보니

내 풀이에 불필요한 연산이 있는 것 같은데 무엇인지 알 수 없어서

아래 해설나온 사이트 보고 수정함
https://dingdingmin-back-end-developer.tistory.com/entry/%EB%B0%B1%EC%A4%80-1106-%EC%9E%90%EB%B0%94-java-%ED%98%B8%ED%85%94

그냥 단순하게 다른 비슷한 난이도의 dp문제들 처럼

1차원 배열 dp로도 해결 가능했고

"호텔의 고객을 적어도 C명 늘이기 위해 형택이가 투자해야 하는
돈의 최솟값을 구하는 프로그램을 작성하시오."

와 같이 c 보다 큰 값이지만 돈이 최솟값이 나오는 예시들이 있어서

dp 길이를 좀 더 길게 해줘야되는(비용이랑 고객수가 최대 100이라 +101 이상)

처리는 기존에 했던 풀이랑 같음. 1차원 배열 dp로 수정해서

풀이도 성공하고 기존 풀이 대비 속도도 빠르게 나옴

다만 아래 풀이로 할 때 처음에 dp에 0이 아닌 큰 값을 넣을 때

단순히 int max value 값 넣으면 dp[currCnt - cnt] + cost 여기서 아예

오버플로우가 발생해 마이너스값이 되버리니 적절히 큰 값으로 넣어줘야됨

 */

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.util.Arrays;
import java.util.StringTokenizer;


class Main {
    public static void main(String[] args) throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        BufferedWriter bw = new BufferedWriter(new OutputStreamWriter(System.out));
        StringTokenizer st = new StringTokenizer(br.readLine());
        int c = Integer.parseInt(st.nextToken());
        int n = Integer.parseInt(st.nextToken());
        int answer = 0;

        int[] dp = new int[c + 101];
        Arrays.fill(dp, 12345678);
        dp[0] = 0;

        for(int idx = 0; idx < n; idx++) {
            st = new StringTokenizer(br.readLine());

            int cost = Integer.parseInt(st.nextToken());
            int cnt = Integer.parseInt(st.nextToken());

            for(int currCnt = cnt; currCnt < dp.length; currCnt++) {
                dp[currCnt] = Math.min(dp[currCnt], dp[currCnt - cnt] + cost);
            }
        }

        int min = Integer.MAX_VALUE;

        for(int currCnt = c; currCnt < dp.length; currCnt++) {
            min = Math.min(min, dp[currCnt]);
        }

        answer = min;
        bw.write(Integer.toString(answer));
        bw.newLine();
        bw.flush();
        bw.close();
    }
}