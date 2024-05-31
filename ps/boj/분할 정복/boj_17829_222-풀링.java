/*
 * 2021-02-05
 * https://www.acmicpc.net/problem/17829
 * 백준 분할 정복 실버 2
 *
divide 함수에서 다음 divide 함수로 넘어 갈 때 size / 2값을 매개변수로 넘겼어야 됐는데 size를 지수로 착각해서
size - 1로 넘어가서 시간 초과로 틀렸었다.
 */

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.PriorityQueue;
import java.util.StringTokenizer;

class Main {
    static int n;
    static int answer;
    static int[][] matrix = new int[1024][1024];
    static PriorityQueue<Integer> pq = new PriorityQueue<>((n1, n2) -> n2 - n1);

    public static void main(String[] args) throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        StringTokenizer st;
        n = Integer.parseInt(br.readLine());
        for(int row = 0; row < n; row++) {
            st = new StringTokenizer(br.readLine());
            for(int col = 0; col < n; col++) {
                matrix[row][col] = Integer.parseInt(st.nextToken());
            }
        }
        divide(n);
        System.out.println(answer);
    }

    public static void divide(int size) {
        for(int row = 0; row < size; row += 2) {
            for(int col = 0; col < size; col += 2) {
                pq.add(matrix[row][col]);
                pq.add(matrix[row][col+1]);
                pq.add(matrix[row+1][col]);
                pq.add(matrix[row+1][col+1]);
                pq.poll();
                matrix[row/2][col/2] = pq.poll();
                pq.poll();
                pq.poll();
            }
        }

        if(size == 2) {
            answer = matrix[0][0];
            return;
        }
        divide(size / 2);
    }
}