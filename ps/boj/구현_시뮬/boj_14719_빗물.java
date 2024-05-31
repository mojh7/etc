/*
 * 2021-02-25
 * https://www.acmicpc.net/problem/14719
 * 백준 구현, 시뮬레이션 골드 5
 *
 */

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.StringTokenizer;

class Main {
    static int[] blocks;

    public static void main(String[] args) throws IOException {
        int answer = 0;
        int maxHeightIdx = 0;
        int maxHeight = 0;
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        StringTokenizer st = new StringTokenizer(br.readLine());
        int h = Integer.parseInt(st.nextToken());
        int w = Integer.parseInt(st.nextToken());
        blocks = new int[w];
        st = new StringTokenizer(br.readLine());
        for (int idx = 0; idx < w; idx++) {
            blocks[idx] = Integer.parseInt(st.nextToken());
            if(maxHeight < blocks[idx]) {
                maxHeight = blocks[idx];
                maxHeightIdx = idx;
            }
        }

        int prevMaxHeight = blocks[0];
        for (int leftIdx = 0; leftIdx <= maxHeightIdx; leftIdx++) {
            if(prevMaxHeight >= blocks[leftIdx]) {
                answer += prevMaxHeight - blocks[leftIdx];
            } else {
                prevMaxHeight = blocks[leftIdx];
            }
        }

        prevMaxHeight = blocks[w-1];
        for (int rightIdx = w-1; rightIdx > maxHeightIdx; rightIdx--) {
            if(prevMaxHeight >= blocks[rightIdx]) {
                answer += prevMaxHeight - blocks[rightIdx];
            } else {
                prevMaxHeight = blocks[rightIdx];
            }
        }
        System.out.println(answer);
    }
}