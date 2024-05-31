/*
 * 2021-04-21
 * https://www.acmicpc.net/problem/15686
 * 백준 브루트포스 골드 5
 * 삼성 기출인듯?
 * 
완탐 + 조합문제
조합 구성할 때 필요없는 연산 없애야 시간 초과 안 걸림.
앞으로 고를 수 있는 치킨 갯수 보다 뽑아야 되는 치킨 갯수가 크면 return해서
필요없는 조합을 구하는 경우를 배제해야 정답
 */

import java.io.IOException;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.util.StringTokenizer;

class Main {
    private static final int HOME = 1;
    private static final int CHICKEN = 2;
    private static int answer;
    private static int n;
    private static int m;
    private static int[][] homes;
    private static int[][] chickensDistance;
    private static int[][] chickenHomes;
    private static int[] selectedChicken;
    private static int homesCount;
    private static int chickenHomesCount;

    public static void main(String[] args) throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        BufferedWriter bw = new BufferedWriter(new OutputStreamWriter(System.out));
        StringTokenizer st = new StringTokenizer(br.readLine());
        n = Integer.parseInt(st.nextToken());
        m = Integer.parseInt(st.nextToken());
        homes = new int[2*n][2];
        chickensDistance = new int[2*n][13];
        chickenHomes = new int[13][2];
        selectedChicken = new int[m];
        homesCount = 0;
        chickenHomesCount = 0;
        answer = Integer.MAX_VALUE;
        int type;

        for (int row = 1; row <= n; row++) {
            st = new StringTokenizer(br.readLine());
            for (int col = 1; col <= n; col++) {
                type = Integer.parseInt(st.nextToken());
                if (type == HOME) {
                    homes[homesCount][0] = row;
                    homes[homesCount++][1] = col;
                }
                if (type == CHICKEN) {
                    chickenHomes[chickenHomesCount][0] = row;
                    chickenHomes[chickenHomesCount++][1] = col;
                }
            }
        }

        calcAllChickenDistance();
        combination(0, 0);

        bw.write(Integer.toString(answer));
        bw.flush();
        bw.close();
    }

    public static void combination(int start, int depth) {
        if (depth == m) {
            int sum = 0;
            int minDistance;
            int distance;

            for (int homesIdx = 0; homesIdx < homesCount; homesIdx++) {
                minDistance = 100000;
                for (int selectedIdx = 0; selectedIdx < m; selectedIdx++) {
                    distance = chickensDistance[homesIdx][selectedChicken[selectedIdx]];
                    if (minDistance > distance) {
                        minDistance = distance;
                    }
                }
                sum += minDistance;
            }

            if (answer > sum) {
                answer = sum;
            }
            return;
        }

        for (int idx = start; idx < chickenHomesCount; idx++) {
            if (chickenHomesCount - idx < m - depth) {
                return;
            }
            selectedChicken[depth] = idx;
            combination(start + 1, depth + 1);
        }
    }

    public static void calcAllChickenDistance() {
        for (int homesIdx = 0; homesIdx < homesCount; homesIdx++) {
            for (int chickenIdx = 0; chickenIdx < chickenHomesCount; chickenIdx++) {
                chickensDistance[homesIdx][chickenIdx] =
                        Math.abs(chickenHomes[chickenIdx][0] - homes[homesIdx][0])
                        + Math.abs(chickenHomes[chickenIdx][1] - homes[homesIdx][1]);
            }
        }
    }
}