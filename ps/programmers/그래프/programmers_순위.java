/*
 * 2020-12-18
 * https://programmers.co.kr/learn/courses/30/lessons/49191
 * 프로그래머스 코딩테스트 고득점 Kit 그래프
 *
 * 이기는 방향 그래프
 * 지는 방향 그래프
 * 각각 플로이드와샬을 통해 경로가 있는 개수의 합이 n-1 이면 순위를 판단할 수 있는 선수라 answer++
 */

class Solution {
    private static final int INF = 100000;
        
    public int solution(int n, int[][] results) {
        int answer = 0;
        int winner;
        int loser;
        int judgeableCnt = 0;
        int[][] winEdges = new int[n+1][n+1];
        int[][] loseEdges = new int[n+1][n+1];
        
        for(int row = 1; row <= n; row++) {
            for(int col = 1; col <= n; col++) {
                winEdges[row][col] = INF;
                loseEdges[row][col] = INF;
            }
        }
        
        for(int[] result : results) {
            winner = result[0];
            loser = result[1];
            winEdges[winner][loser] = 1;
            loseEdges[loser][winner] = 1;
        }
        
        for(int mid = 1; mid <= n; mid++) {
            for(int start = 1; start <= n; start++) {
                for(int end = 1; end <= n; end++) {
                    winEdges[start][end] = Math.min(winEdges[start][end], winEdges[start][mid] + winEdges[mid][end]);
                    loseEdges[start][end] = Math.min(loseEdges[start][end], loseEdges[start][mid] + loseEdges[mid][end]);
                }
            }    
        }
        
        for(int row = 1; row <= n; row++) {
            judgeableCnt = 0;
            for(int col = 1; col <= n; col++) {
                judgeableCnt += winEdges[row][col] != INF || loseEdges[row][col] != INF ? 1 : 0;
            }
            answer += judgeableCnt == n - 1 ? 1 : 0;
        }
        
        /*
        for(int row = 1; row <= n; row++) {
            for(int col = 1; col <= n; col++) {
                System.out.printf("%d ", winEdges[row][col]);
            }
            System.out.println();
        }
        
        System.out.println();
        
        for(int row = 1; row <= n; row++) {
            for(int col = 1; col <= n; col++) {
                System.out.printf("%d ", loseEdges[row][col]);
            }
            System.out.println();
        }
        */
        
        return answer;
    }
}