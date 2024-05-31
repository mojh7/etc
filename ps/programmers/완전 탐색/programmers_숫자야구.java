/*
 * 2020-06-04
 * https://programmers.co.kr/learn/courses/30/lessons/42841
 * 프로그래머스 코딩테스트 고득점 Kit 완전탐색
 *
 * (1). 123~987를 순회하여 숫자 야구 실행
 * (2). 숫자 야구 경기 baseball에 대하여 매 라운드마다 스트라이크와 볼 갯수를 판별하여 주어진 스트라이크와 볼 ( baseball[round][1] 과 baseball[round][2] ) 검사하여 틀리면 +0, 마지막 인덱스 까지 결과가 맞으면 answer에 +1
 *
 * 꽤 오래 걸려서 풀었고 중간에 막혀서 다른 사람이 이미 푼 내용(블로그) 참고 함. "최소 : 123 / 최대 : 987 이라는 제한 선을 두고 시작했다." 내용을 보고 힌트를 얻어서 풀이 방법 바꿔서 풂.
 * 
 * 123~987 순회하는 포문 만드는데 시간 많이 소요. 약간 노가다 식으로 디버깅 해보며 해결...
 *
 * 블로그 보기 전 초기에는 각 자리에 숫자가 올 수있는 경우를 int[3][9] 변수로 가지고 한 라운드 마다 모든 조건(0스트라이크 0볼 ~ 3스트라이크 0볼)에 대해서 분기를 나누어 가능한 숫자 배열을 새로 만들어 숫자 야구 다음 게임에 인수로 넘겨 재귀로 호출하여 마지막 라운드가 끝나고 만들 수 있는 숫자 조합의 갯수를 구하여 계산하려 했으나 스트라이크와 볼 갯수에 따른 조건 처리 하기가 어려워서 막힘.
 */

import java.util.*;
class Solution {
    public int solution(int[][] baseball) {
        int answer = 0;
        boolean[][] possibleNumbers = new boolean[3][9];
        for(int i = 0; i < 3; i++){
            Arrays.fill(possibleNumbers[i], true);
        }
        boolean[] numbers = new boolean[10];
        Arrays.fill(numbers, true);
        int[] permutation = new int[3];
        Arrays.fill(permutation, 0);
        for(int i = 1; i <= 9; i++) {
            numbers[permutation[0]] = true;
            numbers[permutation[1]] = true;
            numbers[permutation[2]] = true;
            permutation[1] = 0;
            permutation[2] = 0;
            numbers[i] = false;
            permutation[0] = i;
            for(int j = 1; j <= 9; j++){
                numbers[permutation[1]] = true;
                numbers[permutation[2]] = true;
                permutation[2] = 0;
                if(numbers[j]){
                    numbers[j] = false;
                    permutation[1] = j;
                }
                else{
                    continue;
                }
                for(int k = 1; k <= 9; k++){
                    if(numbers[k]){
                        permutation[2] = k;
                        numbers[k] = false;
                    }
                    else{
                        continue;
                    }
                    numbers[permutation[2]] = true;
                    answer += baseballGame(baseball, permutation);
                }
            }
        }
        return answer;
    }

    public int baseballGame(int[][] baseball, int[] permutation){
        int[] currentNum = new int[3];
        for(int i = 0; i < baseball.length; i++){
            int strike = 0;
            int ball = 0;
            currentNum[0] = baseball[i][0] / 100;
            currentNum[1] = (baseball[i][0] / 10) % 10;
            currentNum[2] = baseball[i][0] % 10;
            for(int j = 0; j < 3; j++){
                if(permutation[j] == currentNum[j]){
                    strike += 1;
                }
                else{
                    for(int k = 1; k <= 2; k++){
                        int index = (j+k) % 3;
                        if(permutation[j] == currentNum[index]){
                            ball += 1;
                            break;
                        }
                    }
                }
            }

            if(baseball[i][1] != strike || baseball[i][2] != ball){
                return 0;
            }
        }
        return 1;
    }
}
