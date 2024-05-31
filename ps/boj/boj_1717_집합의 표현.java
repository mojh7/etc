/*
 * 2020-06-06
 * https://www.acmicpc.net/problem/1717
 * 백준 Disjoint-set, Union-Find
 *
 */


import java.io.*;
import java.util.*;
class Main {
    public static void main(String[] args) throws Exception {
        final String YES = "YES";
        final String NO = "NO";
        final int UNION_OPERATION = 0;

        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        BufferedWriter bw = new BufferedWriter(new OutputStreamWriter(System.out));
        StringTokenizer st = new StringTokenizer(br.readLine());
        int n = Integer.parseInt(st.nextToken());
        int m = Integer.parseInt(st.nextToken());
        int op;
        int[] elements = new int[2];
        int[] parents = new int[1000017];
        for(int i = 1; i <= n; i++){
            parents[i] = i;
        }
        for(int i = 0; i < m; i++){
            st = new StringTokenizer(br.readLine());
            op = Integer.parseInt(st.nextToken());
            elements[0] = Integer.parseInt(st.nextToken());
            elements[1] = Integer.parseInt(st.nextToken());
            if(UNION_OPERATION == op){
                union(parents, elements[0], elements[1]);
            }else{
                if(find(parents, elements[0]) == find(parents, elements[1])){
                    bw.write(YES);
                }else{
                    bw.write(NO);
                }
                bw.newLine();
            }
        }
        bw.flush();
        bw.close();
    }

    public static void union(int[] parents, int x, int y){
        x = find(parents, x);
        y = find(parents, y);
        if(x != y){
            parents[y] = x;
        }
    }

    public static int find(int[] parents, int x){
        if(x == parents[x]){
            return x;
        }
        else{
            int p = find(parents, parents[x]);
            parents[x] = p;
            return p;
        }
    }
}