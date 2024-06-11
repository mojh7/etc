/*
 * 2024-06-11
 * https://school.programmers.co.kr/learn/courses/30/lessons/258709
 * 프로그래머스 2024 카카오 겨울 인턴 3번 문제 - 주사위 고르기

해설에서 설명하기를
"이 문제는 제한사항을 파악해, 효율적인 시간복잡도의
완전탐색 풀이를 설계할 수 있어야 합니다." 라고 설명함

-------
n 최대 10

10c5 = 252

1 2 3 4 5 6

123, 124, 125, 126
134, 135, 136,
145, 146,
156
234, 235, 236
245 246
256
345 346
456

꼭 다 순회안해도 되고 반절만 봐도됨

주사위 n/2 고르고 던졌을 때 경우의 수가 같아서 % 계산안하고
승이나 패 제일 높은 것 고르면 됨
승 높으면 -> A가 승리할 확률 높은 경우
패 높으면 -> B가 고른 케이스를 그대로 A가 고르면 A가 승리할 확률 높음


주사위 고른 것에서 각 주사위 굴린 후 나온 수들 모두 합해 나온 점수
하나씩 계산해야되나?

6^5 = 7776
6^10 = 60,466,176

10c5 = 252
252 / 2 = 126
6^10 = 7,618,738,176, 76억

dice[i]의 원소는 1이상 100이하
주사위 한 면의 값이 최대 100이고 n은 최대 10이라
주사위 5개 고르고 다 던져도 합이 1이상 500이하이다.

일일이 계산하게되면
6^5 * 6^5 * (252/2) = 악 76억으로 시간초과이니
A가 고른 경우에서 나올 수 있는 주사위 합
B가 고른 경우에서 나올 수 있는 주사위 합
각각 따로 계산해서 6^5 + 6^5 로 계산하고
누적합 만든다음에
A가 고른 경우에서 나올 수 있는 주사위 합 기준으로 순회하면서
승, 무, 패 한 번에 계산하면됨

n^2
n 20000, n^2 = 4억 -> 시간초과
1<= arr[i] <= 3000, 600만

실제 구현은 B가 고른 주사위를 모두 던져서 나올 수 있는 합들을

int[] sumB = new int[501];
누적합으로 저장 + 처리 하고

A가 고른 주사위들로 모두 던져서 나오는 합들 마다
B가 던진 주사위의 합이 낮은 것은 승리처리
전체에서 A가 던진 주사위 합보다 +1 높은 것 까지의 경우는 패배 처리하고

win += sumB[sum - 1];
lose += sumB[500] - sumB[sum];

승, 패 중 가장 높은 것 계속 찾으면서
A가 승리할 확률이 가장 높아지도록 주사위 조합 찾아서 출력

구현하고 나서 테스트할 때 한 번 틀렸는데

A든 B든 주사위 모두 던져서 합 구하는 과정에서
주사위가 모두 6개면이라서
while(diceB[0][1] < n) {
가 아닌

while(diceB[0][1] < 6) {
여야 돼서 틀렸고 수정하니 정답 처리됨
 */

class Solution {
    int maxCnt;
    int[] answer;
    
    public int[] solution(int[][] dice) {
        int n = dice.length;
        answer = new int[n/2];
        boolean[] select = new boolean[n];
        maxCnt = 0;
        
        simulation(dice, select, n, 0, 0);
        
        for(int idx = 0; idx < answer.length; idx++) {
            answer[idx]++;
        }
        return answer;
    }
    
    void simulation(int[][] dice, boolean[] select, int n,
                    int depth, int start) {
        if(depth == n/2) {
            // 주사위 구분 번호, 선택한 면
            int[][] diceA = new int[n/2][2];
            int[][] diceB = new int[n/2][2];
            int idxA = 0;
            int idxB = 0;
            
            for(int idx = 0; idx < n; idx++) {
                if(select[idx]) {
                    diceA[idxA++][0] = idx;
                } else {
                    diceB[idxB++][0] = idx;
                }
            }
            
            int[] sumB = new int[501];
            
            while(diceB[0][1] < 6) {
                int sum = 0;
                for(int idx = 0; idx < diceB.length; idx++) {
                    sum += dice[diceB[idx][0]][diceB[idx][1]];
                }

                
                sumB[sum]++;
                diceB[diceB.length - 1][1]++;
                
                for(int idx = diceB.length - 1; idx > 0; idx--) {
                    if(diceB[idx][1] != 6) {
                        break;
                    }
                    
                    diceB[idx][1] = 0;
                    diceB[idx - 1][1]++;
                }
            }
            
            for(int idx = 1; idx < sumB.length; idx++) {
                sumB[idx] += sumB[idx - 1];
            }
            
            int win = 0;
            int lose = 0;
            
            while(diceA[0][1] < 6) {
                int sum = 0;
                for(int idx = 0; idx < diceA.length; idx++) {
                    sum += dice[diceA[idx][0]][diceA[idx][1]];
                }  
                
                diceA[diceA.length - 1][1]++;
                
                win += sumB[sum - 1];
                lose += sumB[500] - sumB[sum];
                
                for(int idx = diceA.length - 1; idx > 0; idx--) {
                    if(diceA[idx][1] != 6) {
                        break;
                    }
                    
                    diceA[idx][1] = 0;
                    diceA[idx - 1][1]++;
                }
            }
            
            if(win >= lose) {
                if(win > maxCnt) {
                    maxCnt = win;
                    for(int idx = 0; idx < diceA.length; idx++) {
                        answer[idx] = diceA[idx][0];
                    }
                }
            } else if(lose > maxCnt) {
                maxCnt = lose;
                for(int idx = 0; idx < diceB.length; idx++) {
                    answer[idx] = diceB[idx][0];
                }
            }
            
            return;
        }
        
        for(int idx = start; idx < n; idx++) {
            if(depth == 0 && idx > 0) {
                break;
            }
            
            select[idx] = true;
            simulation(dice, select, n, depth + 1, idx + 1);
            select[idx] = false;
        }
    }
}


