/*
 * 2020-06-13
 * https://programmers.co.kr/learn/courses/30/lessons/43237
 * 프로그래머스 코딩테스트 고득점 Kit 이분 탐색
 *
 * 이분 탐색으로 각 지방의 예산을 상한선만큼 더해서 합한 값이
 * m과 같거나 m과 최대한 가까운 상한액 찾도록 추가 조건을 넣었는데 틀림
 * if(sum < target){
       start = mid + 1;
           if(target - sum < diff){
           expectedAnsser = mid;
           diff = target - sum;
       }
   }
 *
 * end 범위 문제에서 주어진 조건에서 가장 큰 수 10만으로 했는데
 * 주어진 숫자 budgets에서 max값을 end로 설정하여 불 필요한 계산 줄임.
 * 그래도 답은 틀림. start와 end를 결정 짓는 조건문 자체가 틀린 듯
 *
 * 질문하기란에서 몇 개 읽어보고 문제를 다시 읽어보니
 * 정해진 총액 이하에서 '가능한 한 최대의' 총 예산을 ~~~ 을 보고
 * 왜 랜선 자르기에서 mid값을 리턴하는 것이 아닌 end을 리턴하는지 깨닫고
 * 가능한 한 최대의 총 예산이 나오도록 아래처럼 수정하니 정답. 
 */

class Solution {
    public int solution(int[] budgets, int m) {
        int answer = 0;
        answer = binarySearch(budgets, m);
        return answer;
    }
    
    public static int binarySearch(int[] budgets, int target){
        long sum = 0;
        int start = 1;
        int end = -1;
        for(int i = 0; i < budgets.length; i++){
            if(end < budgets[i]){
                end = budgets[i];
            }
        }
        int mid = 0;
        while(start <= end){
            sum = 0;
            mid = (start + end) / 2;
            for(int i = 0; i < budgets.length; i++){
                sum += budgets[i] < mid ? budgets[i] : mid;
            }
            if(sum <= target){
                start = mid + 1;
            } else {
                end = mid - 1;
            }
        }
        return end;
    }
}