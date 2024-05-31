/*
 * 2020-06-13
 * https://programmers.co.kr/learn/courses/30/lessons/43238
 * 프로그래머스 코딩테스트 고득점 Kit 이분 탐색
 *
 * 이분 탐색 start, end 결정 짓는 조건문이랑 return 뭘 해야되는지 감좀 잡았다고 생각했는데
 * 막상 다른 문제 푸니 조건문이랑 start, mid, end 중 뭘 반환 해야되는지 적용 잘 못하여 틀림
 *
 * long타입으로 값을 다루려고 했으나 int값을 다루게 되서 틀림.
 * long타입 값으로 제대로 적용하니 정답
 *
 */

class Solution {
    public long solution(int n, int[] times) {
        long answer = 0;
        answer = binarySearch(n, times);
        return answer;
    }
    
    public static long binarySearch(int target, int[] times){
        long sum = 0;
        long start = 1L;
        long mid = 0L;
        long min = 1200000000L;
        for(int i = 0; i < times.length; i++){
            if(min > times[i]){
                min = times[i];
            }
        }
        long end = min * target;
        while(start <= end){
            sum = 0;
            mid = (start + end) / 2;
            for(int t : times){
                sum += mid / t;
            }
            if(target <= sum){
                end = mid - 1;
            } else {
                start = mid + 1;
            }
        }
        return start;
    }
}