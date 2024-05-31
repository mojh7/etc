/*
 * 2020-06-05
 * https://programmers.co.kr/learn/courses/30/lessons/42842
 * 프로그래머스 고득점 Kit - 완전탐색
 *
 * 기존에 생각했던 방식 yellow가 홀수냐 짝수냐 분기로 나누고 짝수에 경우
 * yellow 사각형의 row를 2의 배수씩 늘려가며 갯수 체크 후 brown 갯수 체크
 * 홀 수는 yellow 사각형의 row를 1부터 +2씩 홀수 개 씩 늘려서 yellow갯수와 brown 갯수가 맞는 지점 찾았음.
 * 일부 정답, 일부 시간 초과 나오고 해결을 못해서 다른 사람꺼 아래 해답 보고 풂
 * 
 * brown + yellow은 사각형의 넓이이고 이 넓이는 곧 col * row
 * col >= row인 점을 이용하여 해결
 *
 * col * row == brown인 col과 row를 찾고
 * yellow의 갯수가 (col-2) * (row-2)와 같은지 체크하여 결과 출력
 * 
 */

class Solution {
    public int[] solution(int brown, int yellow) {
        int sum = brown + yellow;
        int row = 0;
        int col = 0;
        for(int i = 3; i <= sum / 3; i++){
            row = i;
            col = sum / i;
            if(col * row == sum && (col-2)*(row-2) == yellow){
                break;
            }
        }
        int[] answer = {col, row};
        return answer;
    }
}


