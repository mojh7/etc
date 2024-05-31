/*
 * 2021-02-04
 * https://www.acmicpc.net/problem/10836
 * 백준 구현, 시뮬레이션 골드4
 *
 * 시간 초과 실패 -> 해설 참조 후 해결

문제 곧이곧대로 제일 왼쪽 열과, 제일 위쪽 행에 대한 성장 처리 이후
[1, 1] ~ [m-1, m-1] 위치까지 일일이 왼쪽, 왼쪽 위, 위쪽의 애벌레들 중 가장 많이 자란 애벌레가 자란 만큼 자라게 처리함.
m과 n의 범위가 2 <= m <= 700, 1 <= n <= 1000000 여서
위와 같이 짰을 경우 시간복잡도가 o(m^2 * n)여서 2초를 초과함.

m*m 다 돌 필요있고 성장 패턴이 있는데 두번 째 열 부터는 상단의 값을 따라감
성장 정도가 0 1 2 의 수열 값으로 들어오다 보니 어떤 좌표에서 왼쪽, 왼쪽 위, 위쪽 값이 있으면 관계가
왼쪽 <= 왼쪽 위 <= 위쪽 값이라서 결국 위쪽 값을 따르면 가장 많이 자란 값을 얻을 수 있다.

0 스킵과 StringBuilder로 출력 하도록 바꾸니 시간초과 해결

 */

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.StringTokenizer;

class Main {
    public static void main(String[] args) throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        StringTokenizer st = new StringTokenizer(br.readLine());
        int m = Integer.parseInt(st.nextToken());
        int n = Integer.parseInt(st.nextToken());
        int[] gridZeroRow = new int[m];
        int[] gridZeroCol = new int[m];
        int[] day = new int[3];
        for(int row = 0; row < n; row++) {
            st = new StringTokenizer(br.readLine());
            for(int col = 0; col < 3; col++) {
                day[col] = Integer.parseInt(st.nextToken());
            }
            int order = day[0];
            for(int idx = 0; idx < day[1]; idx++) {
                if(order < m) {
                    gridZeroRow[m - 1 - order++] += 1;
                    continue;
                }
                gridZeroCol[order++ - m + 1] += 1;
            }
            for(int idx = 0; idx < day[2]; idx++) {
                if(order < m) {
                    gridZeroRow[m - 1 - order++] += 2;
                    continue;
                }
                gridZeroCol[order++ - m + 1] += 2;
            }
        }

        StringBuilder answer = new StringBuilder();
        StringBuilder rowSb = new StringBuilder();
        for(int col = 1; col < m; col++) {
            rowSb.append(" ").append(gridZeroCol[col] + 1);
        }
        rowSb.append("\n");

        for(int row = 0; row < m; row++) {
            answer.append(gridZeroRow[row] + 1).append(rowSb);
        }

        System.out.printf("%s", answer.toString());
    }
}
