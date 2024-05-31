/*
 * https://www.acmicpc.net/problem/1062
 * 완탐, 비트마스킹, 백트래킹 골드4
 *

단어가 anta...tica로 구성됨 그래서 acint는 꼭 필요
그래서 k가 5개 미만이면 0 출력
k가 26개면 모든 단어 읽을 수 있어서 n 출력

가르칠 글자(flag) 미리 acint 체크해놓고
완탐 돌면서 백트래킹하고 visited 체크와
가르칠 글자로 단어 읽을 수 있는지 비교를 비트마스킹으로 처리
 */

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.StringTokenizer;

class Main {
    private static int answer = 0;
    private static int n = 0;
    private static int k = 0;

    public static void main(String[] args) throws IOException {
        answer = 0;
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        StringTokenizer st = new StringTokenizer(br.readLine());
        n = Integer.parseInt(st.nextToken());
        k = Integer.parseInt(st.nextToken());

        if(k < 5) {
            System.out.println(0);
            return;
        } else if(k == 26) {
            System.out.println(n);
            return;
        }

        int[] wordsBin = new int[n];
        for (int idx = 0; idx < n; idx++) {
            wordsBin[idx] = wordTobin(br.readLine());
        }

        // 단어들 기본으로 포함된 글자 a, c, i, n, t 미리 체크
        int flag = 1 << ('a'-'a') | 1 << ('c'-'a') | 1 << ('i'-'a') |
                1 << ('n'-'a') | 1 << ('t'-'a');
        combination(wordsBin, flag, 0, 0);

        System.out.println(answer);
    }

    private static void combination(int[] wordsBin, int flag, int depth, int start) {
        if(depth >= k - 5) {
            int cnt = 0;
            for(int bin : wordsBin) {
                // 가르칠 글자만으로 단어를 읽을 수 있는지 체크
                if((bin & flag) == bin) {
                    cnt++;
                }
            }

            answer = Math.max(cnt, answer);
            return;
        }

        for(int idx = start; idx < 26; idx++) {
            if((flag & (1 << idx)) == 0) {
                combination(wordsBin, flag | (1 << idx), depth + 1, idx + 1);
            }
        }
    }

    private static int wordTobin(String word) {
        word = word.substring(4, word.length() - 4);
        int bin = 0;
        for(char c : word.toCharArray()) {
            bin |= 1 << (c - 'a');
        }

        return bin;
    }
}