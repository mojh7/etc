/*
 * 2020-06-24
 * https://www.acmicpc.net/problem/2606
 *
 * 플로이드 와샬 문제로 분류 되어 있긴한데 union-find 개념으로 풀거나 연결 요소 개념 이용해서 풀 수도 있다.
 */

// 플로이드 와샬 풀이
import java.io.*;
import java.util.*;

class Main{
    static final int INF = 100000000;

    public static void main(String[] args) throws IOException{
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        BufferedWriter bw = new BufferedWriter(new OutputStreamWriter(System.out));
        StringTokenizer st = null;
        int answer = 0;
        int a;
        int b;
        int v = Integer.parseInt(br.readLine());
        int e = Integer.parseInt(br.readLine());
        int[][] edges = new int[101][101];

        for(int i = 1; i < v+1; i++){
            Arrays.fill(edges[i], 1, v+1, INF);
        }

        for(int i = 0; i < e; i++){
            st = new StringTokenizer(br.readLine());
            a = Integer.parseInt(st.nextToken());
            b = Integer.parseInt(st.nextToken());
            edges[a][b] = 1;
            edges[b][a] = 1;
        }

        floydWarshall(edges, v);
        for(int i = 2; i < v+1; i++){
            if(INF != edges[1][i]){
                answer += 1;
            }
        }
        bw.write(Integer.toString(answer));
        bw.newLine();
        bw.flush();
        bw.close();
    }

    public static void floydWarshall(int[][] edges, int v){
        for(int i = 1; i < v+1; i++){
            for(int j = 1; j < v+1; j++){
                for(int k = 1; k < v+1; k++){
                    edges[j][k] = Math.min(edges[j][k], edges[j][i] + edges[i][k]);
                }
            }
        }
    }
}

// union-find
/*
 * Union 함수에서 x, y가 다를 때 y의 부모를 x로 무조건 지정하고 있는데 이 문제에선 수정해야 한다.
 * 노드 1이 루트 노드가 되도록 하기 위해 x와 y중 크기 비교를 해서 parent[큰 수] = 작은 수 가 되도록 한다.
 * union 과정이 끝나고 1을 제외한 모든 노드에 대해 find 함수를 통해 루트 노드가 1인 노드의 갯수를 세서 리턴한다.
 * find함수 안쓰고 parent[i] == 1 인 i 노드를 찾아서 갯수세니 틀림.
 */

import java.io.*;
import java.util.*;

class Main{
    public static void main(String[] args) throws IOException{
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        BufferedWriter bw = new BufferedWriter(new OutputStreamWriter(System.out));
        StringTokenizer st = null;
        int answer = 0;
        int a;
        int b;
        int v = Integer.parseInt(br.readLine());
        int e = Integer.parseInt(br.readLine());
        int[] parent = new int[v+1];
        boolean[][] edges = new boolean[101][101];
        init(parent);
        for(int i = 0; i < e; i++){
            st = new StringTokenizer(br.readLine());
            a = Integer.parseInt(st.nextToken());
            b = Integer.parseInt(st.nextToken());
            edges[a][b] = true;
            edges[b][a] = true;
            merge(parent, a, b);
        }

        int root = 0;
        for(int i = 2; i < v+1; i++){
            root = find(parent, i);
            if(1 == root){
                answer += 1;
            }
        }

        bw.write(Integer.toString(answer));
        bw.newLine();
        bw.flush();
        bw.close();
    }

    public static void init(int[] parent){
        for(int i = 1; i < parent.length; i++){
            parent[i] = i;
        }
    }

    public static int find(int[] parent, int x){
        if(x == parent[x]){
            return x;
        }
        return parent[x] = find(parent, parent[x]);
    }

    public static void merge(int[] parent, int x, int y){
        x = find(parent, x);
        y = find(parent, y);
        if(x != y){
            if(x < y){
                parent[y] = x;
            }else{
                parent[x] = y;
            }
        }
    }
}

// 1번 노드를 시작으로 bfs로 탐색한 노드 갯수 구해서 풀이

import java.io.*;
import java.util.*;

class Main{
    public static void main(String[] args) throws IOException{
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        BufferedWriter bw = new BufferedWriter(new OutputStreamWriter(System.out));
        StringTokenizer st = null;
        int answer = 0;
        int a;
        int b;
        int v = Integer.parseInt(br.readLine());
        int e = Integer.parseInt(br.readLine());
        boolean[][] edges = new boolean[101][101];
        for(int i = 0; i < e; i++){
            st = new StringTokenizer(br.readLine());
            a = Integer.parseInt(st.nextToken());
            b = Integer.parseInt(st.nextToken());
            edges[a][b] = true;
            edges[b][a] = true;
        }

        answer = bfs(edges, v);

        bw.write(Integer.toString(answer));
        bw.newLine();
        bw.flush();
        bw.close();
    }

    public static int bfs(boolean[][] edges, int v){
        Queue<Integer> q = new LinkedList<>();
        q.add(1);
        int answer = 0;
        int node;
        boolean[] visited = new boolean[v+1];
        while(!q.isEmpty()){
            node = q.poll();
            visited[node] = true;
            for(int i = 2; i < v+1; i++){
                if(!visited[i] && edges[node][i]){
                    q.add(i);
                    visited[i] = true;
                    answer += 1;
                }
            }
        }

        return answer;
    };
}