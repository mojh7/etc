/*
 * 2020-07-21
 * https://programmers.co.kr/learn/courses/30/lessons/43104
 * 프로그래머스 코딩테스트 고득점 Kit dp
 *
 * 피보나치 수열문제
 * dp를 i번 째 타일 한 변의 크기를 담는 dp1과
 * 이러한 타일을 구성하는 직사각형의 둘레를 담는 dp2
 * dp 2개를 가지고 연산했더니 효율성 검사에서 틀림.
 * -> dp 배열 한 개로만 값을 담아가길 원하는 문제인가보다. long타입 dp 배열
 * 한 개만 써서 연산했더니 정답.
 * dp type int형으로 했더니 효율성 검사에서 틀림 -> long으로 수정하니 답
 */

class Solution {
    public long solution(int n) {
        if(n == 1){
            return 4;
        }
        long[] dp = new long[n+1];
        dp[1] = 1;
        dp[2] = 1;
        for(int i = 3; i < n+1; i++){
            dp[i] = dp[i-1] + dp[i-2];
        }
        return 4 * dp[n] + 2 * dp[n-1];
    }
}