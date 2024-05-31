/*
 * 2020-06-26
 * https://programmers.co.kr/learn/courses/30/lessons/42626
 * 프로그래머스 코딩테스트 고득점 Kit
 */

import java.util.*;

class Solution {
    public int solution(int[] scoville, long k) {
        int answer = 0;
        PriorityQueue<Long> pq = new PriorityQueue<>();
        for(long s : scoville){
            pq.add(s);
        }
        long f, s;
        while(pq.peek() < k){
            if(1 == pq.size()){
                answer = -1;
                break;
            }
            f = pq.poll();
            s = pq.poll();
            pq.add(mixFood(f, s));
            answer++;
            
        }
        return answer;
    }
    
    public long mixFood(long first, long second){
        return first + second * 2;
    }
}