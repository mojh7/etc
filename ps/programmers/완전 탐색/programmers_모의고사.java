/*
 * 2020-06-01
 * https://programmers.co.kr/learn/courses/30/lessons/42840
 * 프로그래머스 코딩테스트 고득점 Kit 완전탐색
 *
 * 1. 1~3번 수포자가 찍는 방법대로 answers[i] 비교하여 int[][] 타입에 answerCnt에다가 {n번 학생, 맞은 갯수} 로 저장 
 * 2. answerCnt 정렬 후 answer에 값 대입 후 출력
 *
 * Integer.compare(o1, o2);
 * 좌변이 크면 1 우변이 크면 -1 같으면 0 리턴. 방향 뒤집으면 내림차순 정렬
 *
 * answerPattern2과 배열 정렬 때 조건 잘 못 작성해서 틀렸음.
 * 1, 3, 5, 7 에서 1, 3, 4, 5 수정
 * if(o1[1] == o1[1])에서 o1[1] == o2[1] 수정
 */

import java.util.*;

class Solution {
    public int[] solution(int[] answers) {
        int n = answers.length;
        int[][] answerCnt = {{1, 0}, {2, 0}, {3, 0}};
        int[] answerPattern2 = {1, 3, 4, 5};
        int[] answerPattern3 = {3, 1, 2, 4, 5};

        for(int i = 0 ; i < n; i++){
            if((i % 5)+1 == answers[i]){
                answerCnt[0][1] += 1;
            }
            if(i % 2 == 0){
                if(2 == answers[i]){
                    answerCnt[1][1] += 1;
                }
            }else if(answerPattern2[(i % 8) / 2]== answers[i]){
                answerCnt[1][1] += 1;
            }
            if(answerPattern3[(i % 10)/2] == answers[i]){
                answerCnt[2][1] += 1;
            }
        }

        Arrays.sort(answerCnt, (o1, o2) -> {
            if(o1[1] == o2[1]){
                return Integer.compare(o1[0], o2[0]);
            } else {
                return Integer.compare(o2[1], o1[1]);
            }
        });

        int topScorers = 1;

        if(answerCnt[0][1] == answerCnt[1][1])
        {
            topScorers++;
            if(answerCnt[1][1] == answerCnt[2][1]){
                topScorers++;
            }
        }
        int[] answer = new int[topScorers];
        for(int i = 0 ; i < topScorers; i++){
            answer[i] = answerCnt[i][0];
        }
        return answer;
    }
}