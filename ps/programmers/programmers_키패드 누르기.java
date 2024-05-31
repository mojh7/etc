/*
 * 2020-07-11
 * https://programmers.co.kr/learn/courses/30/lessons/67256
 * 프로그래머스 2020 카카오 인턴십
 */

import java.util.*;

class Solution {
    private static final int[][] keypad = {{1, 3}, {0, 0}, {1, 0}, {2, 0},
                                               {0, 1}, {1, 1}, {2, 1},
                                               {0, 2}, {1, 2}, {2, 2}};
    public String solution(int[] numbers, String hand) {
        String answer = "";
        
        int[] leftCurrentPos = new int[]{0, 3};
        int[] rightCurrentPos = new int[]{2, 3};
        int[] leftDiff = new int[2];
        int[] rightDiff = new int[2];
        int[] target = new int[2];
        StringBuilder sb = new StringBuilder();
        for(int num : numbers){
            switch(num){
                case 1:
                case 4:
                case 7:
                    leftCurrentPos = keypad[num];
                    sb.append("L");
                    break;
                case 3:
                case 6:
                case 9:
                    rightCurrentPos = keypad[num];
                    sb.append("R");
                    break;
                default:
                    target = keypad[num];
                    leftDiff[0] = Math.abs(target[0] - leftCurrentPos[0]);
                    leftDiff[1] = Math.abs(target[1] - leftCurrentPos[1]);
                    rightDiff[0] = Math.abs(target[0] - rightCurrentPos[0]);
                    rightDiff[1] = Math.abs(target[1] - rightCurrentPos[1]);
                    
                    if(leftDiff[0] + leftDiff[1] < rightDiff[0] + rightDiff[1]){
                        leftCurrentPos = keypad[num];
                        sb.append("L");
                    }else if(leftDiff[0] + leftDiff[1] > rightDiff[0] + rightDiff[1]){
                        rightCurrentPos = keypad[num];
                        sb.append("R");
                    }else{
                        if(hand.equals("left")){
                            leftCurrentPos = keypad[num];
                            sb.append("L");
                        }else{
                            rightCurrentPos = keypad[num];
                            sb.append("R");
                        }
                    }
                    break;
            }
        }
        answer = sb.toString();
        return answer;
    }
}