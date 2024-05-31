/*
 * 2021-01-22
 * https://programmers.co.kr/learn/courses/30/lessons/43164
 * 코딩테스트 고득점 Kit dfs/bfs level 3
 * 
푸는 중
"ICN" 항공 도시 시작 기준으로 kruskal 알고리즘 이용해서 mst 만들고 dfs해서 경로 리턴할 생각중

--- 1. 문제 자체를 잘 못 이해
주어진 항공권을 가지고 "ICN" 공항에서 시작하여 쓰는 항공권의 갯수는 상관없이 모든 도시를 방문하는
여행 경로를 짜는줄 알고 mst를 구하려고 kruskal를 쓰려고 했는데

주어진 항공권을 모두 사용해서 모든 도시를 도는 문제라 풀이를 다시 생각함.
주어진 항공권을 다 써야되면 a->b 항공을 갔다가 b->a 갈 수도 있고 주어진 예제에서도 ICN -> ATL -> ICN
방식으로 간다. mst를 구하는 문제가 애초에 아님

모든 도시를 방문할 수 없는 경우가 주어지지 않기 때문에 ICN 공항 부터 dfs로 모든 경우를 탐색하는데 답이
여러가지 일 경우 알파벳 순서가 앞서는 경로를 리턴해야되서 주어진 티켓정보 String[][] tickets을
ArrayList<Edge>[] graph에 담고 각각 sort를 해준다. ( Edge class에다가 Comparable 구현해놓았음)

각 노드에서 알파벳 순서가 앞선 간선 부터 탐색을 하는데 다음 탐색할 간선이 없는데 항공권을 모두 사용하지
않았다면 백트래킹을 활용하여 이전 상태로 되돌아가면서 탐색한다.

--- 2. 제출 후 테스트케이스 3, 4번만 맞고 1, 2번 런타임 에러로 실패
질문 글 반례 보고 해결
반례
입력 : [["ICN", "AAA"], ["BBB", "ICN"], ["ICN", "BBB"]]
출력 : ["ICN", "BBB", "ICN", "AAA"]
이 경우에서 AAA 항공에서 다음으로 갈 간선이 없는데 없을 경우에 대한 처리 때문에 에러 발생
dfs내에서 노드내의 간선 탐색을 for(Edge edge : graph[curIdx]) 로 하고 있는데
String[][] tickets 에서 ArrayList인 graph를 구성할 때 간선이 없는 곳은 size가 0인 ArrayList가 할당된게 아니라
null인 상태여서 에러가 떴다. 간선 없는 노드는 new ArrayList()로 size가 0인 ArrayList를 만드니 1, 2번 해결
 */

import java.util.HashMap;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.Deque;
import java.util.Collections;

class Solution {
    private static final String ICN = "ICN";
    private Deque<Edge> deque;
    private ArrayList<Edge>[] graph;
    private int edgesSize;
    public String[] solution(String[][] tickets) {
        edgesSize = tickets.length;
        String[] answer = new String[edgesSize + 1];
        answer[0] = ICN;
        Edge cur;
        HashMap<String, Integer> strToNodeIdx = new HashMap<>();
        int nodeIdx = 0;
        int[] twoAirport = new int[2];
        graph = new ArrayList[edgesSize + 1];
        deque = new LinkedList<>();
        
        for(String[] ticket : tickets) {
            for(int idx = 0; idx < 2; idx++) {
                if(!strToNodeIdx.containsKey(ticket[idx])) {
                    strToNodeIdx.put(ticket[idx], nodeIdx++);
                }
                twoAirport[idx] = strToNodeIdx.get(ticket[idx]);
            }
            
            if(graph[twoAirport[0]] == null) {
                graph[twoAirport[0]] = new ArrayList<>();
            }
            graph[twoAirport[0]].add(new Edge(ticket[1], twoAirport[1]));
        }
        
        
        int n = nodeIdx;
        for(int idx = 0; idx < n; idx++) {
            if(graph[idx] != null) {
                Collections.sort(graph[idx]);    
            } else {
                graph[idx] = new ArrayList();
            }
        }
        
        dfs(strToNodeIdx.get(ICN), 0);
        
        int answerIdx = 1;
        while(!deque.isEmpty()) {
            cur = deque.pollFirst();
            answer[answerIdx++] = cur.dest;
        }
        
        return answer;
    }
    
    public boolean dfs(int curIdx, int cnt) {
        boolean res = false;
        if(cnt == edgesSize) {
            return true;
        }
        for(Edge edge : graph[curIdx]) {
            if(edge.isVisited) continue;
            
            edge.isVisited = true;
            deque.add(edge); // = addLast
            
            res = dfs(edge.destIdx, cnt + 1);
            
            if(res) break;
            deque.pollLast();
            edge.isVisited = false;
        }
        
        return res;
    }
}

class Edge implements Comparable<Edge> {
    String dest;
    int destIdx;
    boolean isVisited;
    
    public Edge(String dest, int destIdx) {
        this.dest = dest;
        this.destIdx = destIdx;
        this.isVisited = false;
    }
    
    public int compareTo(Edge other) {
        return this.dest.compareTo(other.dest); 
    }
}