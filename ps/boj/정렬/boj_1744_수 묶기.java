/*
 * https://www.acmicpc.net/problem/1744
 * 그리디, 정렬 골4
 * 
음수와 0
그리고 1 1나 1 3 같은 경우들 고려해서 짜야됨
 */

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

class Main {
    public static void main(String[] args) throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        //BufferedWriter bw = new BufferedWriter(new OutputStreamWriter(System.out));
        //StringTokenizer st;
        int answer = 0;
        int num1, num2;
        int n = Integer.parseInt(br.readLine());
        List<Integer> positive = new ArrayList<>();
        List<Integer> negative = new ArrayList<>();
        boolean containsZero = false;

        for (int idx = 0; idx < n; idx++) {
            int num = Integer.parseInt(br.readLine());
            if (num > 0) {
                positive.add(num);
            } else if (num < 0) {
                negative.add(num);
            } else {
                containsZero = true;
            }
        }

        Collections.sort(positive);
        Collections.sort(negative);

        // {1 1} 이나 {1 3} 같은 경우 곱하지 말고 더하는게 낫다
        for (int idx = positive.size() - 1; idx >= 0; idx -= 2) {
            num1 = positive.get(idx);

            if (idx > 0) {
                num2 = positive.get(idx - 1);

                // 1 1 혹은 1 3 같은 경우 일 때
                if (num1 == 1 || num2 == 1) {
                    answer += num1 + num2;
                    continue;
                }
            } else {
                answer += num1;
                continue;
            }

            answer += num1 * num2;
        }

        // 음수 갯수가 홀수 일 때 0 이 있으면 0이랑 가장 큰 음수랑 수 묶기
        if (negative.size() % 2 == 1) {
            if (!containsZero) {
                answer += negative.get(negative.size() - 1);
            }
        }

        // 음수는 가장 작은 음수 부터 수묶기 해야 곱했을 때 최대값 나옴
        for (int idx = 0; idx < negative.size(); idx += 2) {
            if (idx == negative.size() - 1) {
                continue;
            }
            answer += negative.get(idx) * negative.get(idx + 1);
        }

        System.out.println(answer);
    }
}