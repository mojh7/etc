/*
 * 2024-06-03
 * https://www.acmicpc.net/problem/10800
 * 백준 누적 합 골드 2

보통 1차원 배열로 누적합 혹은 2차원 배열(카카오 문제)로 누적합 처리해서
푸는 문제는 봤는데 문제풀이에 연관된 조건이 1개가 아닌 2개(색, 크기)를
고려해서 누적합으로 풀어야되는 것은 처음 풀어봄

현재 공의 크기보다 작고, 색이 다른 공을 사로잡을 수 있고
사로잡은 모든 공들의 크기 합을 각 공마다 출력해야되는데

조건이
공의 개수 n은 1이상 20만이하
공의 색 c는 1이상, n이하
공의 크기는 1이상 2000이하

공의 색깔을 기준으로 크기별로 누적합이나
색깔 구분 없이 크기별로 누적합이 필요해보이나
각 색깔별로 크기마다 누적합을 만들려면 20만 * 2000개가 필요하고
약 4억개의 공간이 필요함, 시간복잡도도 이미 1초를 초과

1초내에 처리하려면 공의 색깔 개수 * 공의 크기 연산을 피해야하고
특히 공의 개수(공의 색깔) 20만을 피하고 공의 크기정도만 활용해서
배열 2천개정도 만들어서 푸는 방법이 제일 적합하다고 생각

그래서 색깔 상관없이 일단은 크기 기준으로 누적합을 만들기 위해
1차원 배열을 만들고(최대 크기 2천)
sum[현재 선택한 공의 크기 - 1] 으로 현재 선택한 공보다 크기가 작은
경우들을 찾을 수 있음
하지만 위에 누적합 정보에는 선택한 공과 같은 색깔의 공들의 무게도
포함되어 있기에 결국

sum[현재 선택한 공의 크기 - 1] 에다가 선택한 공의 색깔과 같고 현재
크기보다 1작은 크기까지의 누적합을 빼야 정답을 알 수 있다.

하지만 돌고돌아서 현재 선택한 공의 색깔에 대해 각 크기에 대한 누적합을
만들려면 배열의 크기가 20만 * 2천이 필요한데 이 것에 대한
문제풀이가 떠오르지 않아서 해설 보고 풀이

참고 해설, 그림 포함해서 이해 쉬움
https://developerbee.tistory.com/126

올바른 문제풀이는 입력으로 들어온 공의 정보(공번호, 색, 크기)에 대해

크기 기준으로 오름차순으로 정렬 후 각 공마다 그 때마다

현재 까지의 공 전체 무게 - 해당 공 색깔의 누적합을 빼서 출력

크기 기준 오름차순으로 정렬하게 되면 현재 선택한 공은

이전에 선택한 공보다 무게가 같거나 크고

이후에 선택한 공보다 무게가 같거나 작기에

현재 선택한 공까지의 누적합 정보를 활용하면

공 색깔에 대한 무게 누적합에 대한 배열의 크기를 최대 20만개 까지만
사용하면 된다.

주의할점은 공을 크기 기준으로 오름차순하면 입력에 따라서

공의 크기가 같은 공들이(색깔 상관 없이) 여러 개 있을 수 있어서

매 번 누적합 처리를 하기보다는 누적합 계산할 때 조건을 추가해줘서

마지막으로 누적합 계산했던 공의 크기보다 현재 선택한 공의 크기가

클 때만 누적합 계산해줘야 틀리지 않음
 */

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.util.Arrays;
import java.util.Comparator;
import java.util.StringTokenizer;


class Main {
    public static void main(String[] args) throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        BufferedWriter bw = new BufferedWriter(new OutputStreamWriter(System.out));
        StringTokenizer st;
        int n = Integer.parseInt(br.readLine());
        int c;
        int s;
        int[] answer = new int[n];
        int[] colorSum = new int[n+1];
        int[][] balls = new int[n][3];

        int sum = 0;

        for(int idx = 0; idx < n; idx++) {
            st = new StringTokenizer(br.readLine());
            c = Integer.parseInt(st.nextToken());
            s = Integer.parseInt(st.nextToken());
            balls[idx][0] = c;
            balls[idx][1] = s;
            balls[idx][2] = idx;
        }

        Arrays.sort(balls, Comparator.comparingInt(o -> o[1]));

        int currIdx = 0;
        int prevIdx = 0;
        int[] ball;
        int[] prevBall;

        while(currIdx < n) {
            ball = balls[currIdx++];
            prevBall = balls[prevIdx];

            while(ball[1] > prevBall[1]) {
                colorSum[prevBall[0]] += prevBall[1];
                sum += prevBall[1];
                prevBall = balls[++prevIdx];
            }

            answer[ball[2]] = sum - colorSum[ball[0]];
        }

        for(int size : answer) {
            bw.write(Integer.toString(size));
            bw.newLine();
        }

        bw.flush();
        bw.close();
    }
}
