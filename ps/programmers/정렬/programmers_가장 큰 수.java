/*
 * 2021-01-22
 * https://programmers.co.kr/learn/courses/30/lessons/42746
 * 코딩테스트 고득점 Kit 정렬 level 2
 * 
 * 푸는 중

----- 첫 시도
테스트 1 〉	실패 (122.21ms, 63.6MB)
테스트 2 〉	실패 (105.17ms, 60.1MB)
테스트 3 〉	실패 (157.99ms, 67.6MB)
테스트 4 〉	실패 (17.94ms, 53.6MB)
테스트 5 〉	실패 (142.96ms, 63.1MB)
테스트 6 〉	실패 (110.19ms, 61.5MB)
테스트 7 〉	통과 (0.93ms, 52.3MB)
테스트 8 〉	통과 (0.87ms, 52.9MB)
테스트 9 〉	통과 (1.00ms, 52.4MB)
테스트 10 〉	통과 (0.94ms, 52.1MB)
테스트 11 〉	실패 (1.01ms, 52MB)

예제 테스트케이스 2개는 맞음

int[] numbers의 각각의 int형 원소를 toString() 하여
PriorityQueue<String> pq 에 넣고 우선 순위 높은 순서대로 출력했는데 일부 틀림.

우선순위는 두 숫자를 String으로 변환한 s1과 s2가 있으면 자릿수가 높은 수 부터 비교하여 큰 숫자가
먼저오고 s1와 s2 길이 차이가 나는데 각각의 index를 비교하다가 작은 길이를 가진 string은 가장 마지막
index의 숫자로 비교함. 9976과 99가 있으면 3번째 포문에서 좌측 숫자에 7과 우측 숫자에서 두번 째 index 9와
비교하여 99가 먼저 오도록 함.


---- 질문하기 글 반례 보고 해결
반례
input : [0, 0, 0, 0, 0]
return : "0"
이전 코드로는 "00000" 나옴
고쳤더니 테스트 11 통과 1~6 실패

반례
input : [40, 403]
return : "40403"
이전 틀렸던 코드로는 "40340"이 나온다
 */


// 해결 코드

import java.util.PriorityQueue;

class Solution {
    public String solution(int[] numbers) {
        String answer;
        StringBuilder sb = new StringBuilder();
        PriorityQueue<String> pq = new PriorityQueue<>((s1, s2) -> {
            for(int idx = 0; idx < 4; idx++) {
                if(s1.charAt(idx % s1.length()) == s2.charAt(idx % s2.length())) continue;
                return s1.charAt(idx % s1.length()) - s2.charAt(idx % s2.length()) > 0 ? -1 : 1;
            }
            return s1.length() - s2.length();
        });
        
        for(int number : numbers) {
            pq.add(Integer.toString(number));
        }
        
        while(!pq.isEmpty()) {
            sb.append(pq.poll());
        }
        answer = sb.toString();
        if(answer.charAt(0) == '0') {
            return "0";
        }
        
        return answer;
    }
}


// 틀린 코드

import java.util.PriorityQueue;

class Solution {
    public String solution(int[] numbers) {
        PriorityQueue<String> pq = new PriorityQueue<>((s1, s2) -> {
            if(s1.length() < s2.length()) {
                for(int idx = 0; idx < s2.length(); idx++) {
                    if(idx < s1.length()) {
                        if(s1.charAt(idx) == s2.charAt(idx)) continue;
                        return s1.charAt(idx) - s2.charAt(idx) > 0 ? -1 : 1;
                    }else {
                        if(s1.charAt(s1.length() - 1) == s2.charAt(idx)) continue;
                        return s1.charAt(s1.length() - 1) - s2.charAt(idx) > 0 ? -1 : 1;
                    }
                }    
            } else {
                for(int idx = 0; idx < s1.length(); idx++) {
                    if(idx < s2.length()) {
                        if(s1.charAt(idx) == s2.charAt(idx)) continue;
                        return s1.charAt(idx) - s2.charAt(idx) > 0 ? -1 : 1;
                    }else {
                        if(s1.charAt(idx) == s2.charAt(s2.length() - 1)) continue;
                        return s1.charAt(idx) - s2.charAt(s2.length() - 1) > 0 ? -1 : 1;
                    }
                }
            }
            return s1.length() - s2.length();
        });
        
        for(int number : numbers) {
            pq.add(Integer.toString(number));
        }
        
        StringBuilder answer = new StringBuilder();
        while(!pq.isEmpty()) {
            answer.append(pq.poll());
        }
        
        return answer.toString();
    }
}