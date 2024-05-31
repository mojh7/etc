/*
 * https://www.acmicpc.net/problem/1322
 * 수학, 비트마스킹 골드4
 *

주어진 조건 X + Y = X | Y 가 만족하려면
X와 Y를 2진수로 바꿨을 때 비트1의 위치가 두 숫자가 각각 달라야함

5 = 00101
10 = 01010
15 = 5+10 = 01111

이 조건을 만족하는 K번 째 작은 Y를 찾으려면

K를 이진수로 바꾼 값이 X를 2진수로 바꾼 값에서 비트가 0인 위치에
들어가면 된다
 */

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.StringTokenizer;

class Main {
    private static long answer;
    private static long x;
    private static int k;

    public static void main(String[] args) throws IOException {
        answer = 0l;
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        StringTokenizer st = new StringTokenizer(br.readLine());
        x = Long.parseLong(st.nextToken());
        k = Integer.parseInt(st.nextToken());

        int xIdx = 0;
        int kIdx = 0;
        int maxKIdx = 0;
        int tmp = k;
        while (tmp > 0) {
            maxKIdx++;
            tmp = tmp >> 1;
        }

        while(kIdx < maxKIdx) {
            if((x & (1L << xIdx)) == 0) {
                if((k & (1 << kIdx)) > 0) {
                    answer = answer | (1L << xIdx);
                }
                kIdx++;
            }

            xIdx++;
        }

        System.out.println(answer);
    }
}