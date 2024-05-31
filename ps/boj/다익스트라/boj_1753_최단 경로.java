/*
 * 2020-06-12
 * https://www.acmicpc.net/problem/1753
 * 백준 - 최단경로 (다익스트라)
 *
 * 다익스트라 구현해서 dist[] 출력하면 정답.
 *
 * 우선순위 큐로 해야 시간 초과 안뜬다.
 * 그러나 실제 구현시 우선 순위 큐로 간선에 연결 된 다음 노드의 정보를 담았지만
 * comparator 구현을 원하는 조건에서 반대로 해서 불 필요한 연산이 더 실행되어 시간 초과 걸림
 *
 * comparator 원하는 조건식을 만족하는 리턴 값을 -1로 해야된다.
 * PriorityQueue<Pair<Integer, Long>> pq = new PriorityQueue<>(
                (o1, o2) -> o1.second < o2.second ? -1 : 1);
 *
 * 테스트 케이스 node 방문 순서가 (current = node.first, cost = node.second. dist[node.first])
 * condition ? 1 : -1로 하면 
 * 1, 0, 0 -> 3, 3, 3 -> 4, 9, 9 -> 2, 2, 2 -> 4, 7, 7
 * condition ? -1 : 1 로 하면
 * 1, 0, 0 -> 2, 2, 2 -> 3, 3, 3 -> 4, 7, 7
 * 
 * if(dist[node.first] < node.second){
                continue;
   }
 *
 * 이미 해당 경로를 타고 온 비용이 또 다른 경로로 온 비용보다 크면 해당 경로는 더 이상 볼 필요 없어서
 * continue로 넘어가야되는데 위에 comparator에서 max가 poll되게 해서 더 이상 연산 안해도 되는 경로 에서 nextNode에 대한 계산 for(Pair<Integer, Integer> edge : edges.get(node.first)) 을 더 함.
 * 그래서 시간 초과, min값이 poll 되게 바꾸니 정답.
 */

import java.io.*;
import java.util.*;

class Main{
    public static class Pair<F, S>{
        F first;
        S second;
        public Pair(F first, S second){
            this.first = first;
            this.second = second;
        }
    }

    static final long INF_NUM = 3700000L;
    static final String INF = "INF";

    public static void main(String[] args) throws IOException{
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        BufferedWriter bw = new BufferedWriter(new OutputStreamWriter(System.out));
        StringTokenizer st = null;

        st = new StringTokenizer(br.readLine());
        int vSize = Integer.parseInt(st.nextToken());
        int eSize = Integer.parseInt(st.nextToken());
        int s = Integer.parseInt(br.readLine());
        int u, v, w;
        List<List<Pair<Integer, Integer>>> edges = new ArrayList<>(eSize + 1);
        long[] dist = new long[vSize + 1];
        Arrays.fill(dist, 1, dist.length, INF_NUM);
        dist[s] = 0;
        for(int i = 0; i < vSize + 1; i++){
            edges.add(new LinkedList<Pair<Integer, Integer>>());
        }

        for(int i = 0; i < eSize; i++){
            st = new StringTokenizer(br.readLine());
            u = Integer.parseInt(st.nextToken());
            v = Integer.parseInt(st.nextToken());
            w = Integer.parseInt(st.nextToken());
            edges.get(u).add(new Pair<>(v, w));
        }

        dijkstra(edges, dist, s);

        for(int i = 1; i < vSize + 1; i++){
            String outStr = INF_NUM == dist[i] ? INF : Long.toString(dist[i]);
            bw.write(outStr);
            bw.newLine();
        }
        bw.flush();
        bw.close();
    }

    public static void dijkstra(List<List<Pair<Integer, Integer>>> edges, long[] dist, int s){
        PriorityQueue<Pair<Integer, Long>> pq = new PriorityQueue<>(
                (o1, o2) -> o1.second < o2.second ? -1 : 1);
        pq.add(new Pair(s, 0L));
        Pair<Integer, Long> node = null;
        long cost;
        while(!pq.isEmpty()){
            node = pq.poll();

            if(dist[node.first] < node.second){
                continue;
            }


            for(Pair<Integer, Integer> edge : edges.get(node.first)){
                cost = node.second + edge.second;
                if(cost < dist[edge.first]){
                    dist[edge.first] = cost;
                    pq.add(new Pair(edge.first, cost));
                }
            }
        }
    }
}