/*
 * 2020-06-11
 * https://www.acmicpc.net/problem/1654
 * 백준 랜선 자르기 (이분 탐색)
 *
 * 틀렸습니다 -> 시간초과
 * 랜선 자연수로 들어오는데 float[] 에다가 값 넣고 있었음. int[]에 넣으니
 * 시간초과로 바뀜
 *
 * int를 넘어서는 경우도 생겨서 변수 일부 long 타입으로 변경
 * 결국 못 풀어서 해설 참고
 * N개보다 많이 만드는 것도 N개를 만드는 것에 포함되고 이때 최대 랜선 길이 구하는게 어려웠음. 알맞게 이분 탐색 조건 짜는게 
 * 이분 탐색내에서도 마지막에 retrun mid;로 하면 틀리고 end 리턴하니 정답
 */


import java.io.*;
import java.util.*;

class Main {
    public static void main(String[] args) throws Exception {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        BufferedWriter bw = new BufferedWriter(new OutputStreamWriter(System.out));
        StringTokenizer st = null;
        int[] cables = new int[10003];
        st = new StringTokenizer(br.readLine());
        int k = Integer.parseInt(st.nextToken());
        int n = Integer.parseInt(st.nextToken());
        int max = 1;

        for(int i = 0; i < k; i++)
        {
            st = new StringTokenizer(br.readLine());
            cables[i] = Integer.parseInt(st.nextToken());
            if(max < (int)cables[i]){
                max = (int)cables[i];
            }
        }
        bw.write(Long.toString(binarySearch(cables, 1, max, k, n)));
        bw.newLine();
        bw.flush();
        bw.close();
    }

    public static long binarySearch(int[] cables, long start, long end, int k, int n){
        long sum = 0;
        long mid = 0;

        while(start <= end) {
            mid = (start + end) / 2;
            sum = cutCables(cables, k, mid);
            if(sum < n){
                end = mid - 1;
            }else {
                start = mid + 1;
            }
        }
        return end;
    }

    public static long cutCables(int[] cables, int k, long cutLen){
        long sum = 0;
        for(int i = 0; i < k; i++){
            sum += cables[i] / cutLen;
        }
        return sum;
    }
}}