/*
 * 2020-12-18
 * https://programmers.co.kr/learn/courses/30/lessons/49190
 * 프로그래머스 코딩테스트 고득점 Kit 그래프
시도 1 --- 
이미 방문한 적 있는 위치(Point)에 접근할 때 가는 방향의 edge가 처음 방문하게 되면 방이 만들어짐.
그런데 1, 2번만 맞음

테스트 1 〉	통과 (0.59ms, 52.1MB)
테스트 2 〉	통과 (1.48ms, 52.7MB)
테스트 3 〉	실패 (1.61ms, 52.7MB)
테스트 4 〉	실패 (2.89ms, 53MB)
테스트 5 〉	실패 (12.59ms, 54.6MB)
테스트 6 〉	실패 (60.67ms, 62.5MB)
테스트 7 〉	실패 (9.13ms, 54.8MB)
테스트 8 〉	실패 (53.86ms, 64MB)
테스트 9 〉	실패 (61.63ms, 79MB)

시도 2 ---
https://programmers.co.kr/questions/14646 보고 개선 중인데 코드가 틀린지 1, 2번 테스트만 맞는 중
위 글에 나온 방법에서 첫 번째 방법만 고려해서 틀렸음.

첫 번째, 한번 지나왔던 점으로 다시 연결될 때 방이 하나 만들어집니다.
단, 이때 예외가 있습니다. 점으로 연결될 때, 이미 그려졌던 선이 아닌지 확인해야 합니다.

두 번째, 대각선끼리 교차할 때 방이 하나 만들어집니다.
이때도, 이미 그려졌던 선이 아닌지 확인해야 합니다.

시도 3 ---
질문하기에 다른 테케에서 틀린 닶이 나와 로직 수정 후 문제 해결

5 2 7에 경우 방이 1개 만들어지는데 이전에 짰던 로직에서는 0으로 나와서 수정하니 맞았음.

추가 테스트케이스
[6, 5, 2, 7, 1, 4, 2, 4, 6]
[5, 2, 7, 1, 6, 3]
[6, 2, 4, 0, 5, 0, 6, 4, 2, 4, 2, 0]
[6, 0, 3, 0, 5, 2, 6, 0, 3, 0, 5]

4개다 결과 3 나와야 함.
 */

import java.util.HashMap;

class Solution {
    private static final int[][] MOVE_POS = {
        {0, 1}, {1, 1}, {1, 0}, {1, -1}, {0, -1}, {-1, -1}, {-1, 0}, {-1, 1}
    };
    private static final int X = 0;
    private static final int Y = 1;
    private static final Long Y_CORRECTION_VALUE = 1000000L; 
    // y값 +-, 교차 방향 arrow
    private static final int[][] DIAGONAL_CHECKING_PROPS = {
        {-1, 7}, {1, 5}, {1, 3}, {-1, 1}
    };
    
    public int solution(int[] arrows) {
        int answer = 0;
        Long[] curPos = {new Long(0), new Long(0)};
        Point curPoint = null;
        Point nextPoint = null;
        Point crossedDiagonalPoint = null;
        Long compressedPos = 0L;
        Long crossedDiagonalPos = 0L;
        int crossedDiagonalArrow = 0;
        int horizontalArrow = 0;
        int verticalArrow = 0;
        HashMap<Long, Point> coordinates2D = new HashMap<>();

        compressedPos = curPos[Y] * Y_CORRECTION_VALUE + curPos[X];
        coordinates2D.put(compressedPos, new Point());
        
        for(int arrow : arrows) {
            curPoint = coordinates2D.get(compressedPos);
            curPos[X] += MOVE_POS[arrow][X];
            curPos[Y] += MOVE_POS[arrow][Y];
            compressedPos = curPos[Y] * Y_CORRECTION_VALUE + curPos[X];
            nextPoint = coordinates2D.get(compressedPos);
            
            if(!curPoint.visited8Dir[arrow]) {
                if(nextPoint != null) {
                    answer++;    
                }
                if(arrow % 2 == 1) {
                    int oddArrowIdx = arrow / 2;
                    crossedDiagonalPos = compressedPos + Y_CORRECTION_VALUE * DIAGONAL_CHECKING_PROPS[oddArrowIdx][0];
                    crossedDiagonalArrow = DIAGONAL_CHECKING_PROPS[oddArrowIdx][1];
                    crossedDiagonalPoint = coordinates2D.get(crossedDiagonalPos);
                    if(crossedDiagonalPoint != null && crossedDiagonalPoint.visited8Dir[crossedDiagonalArrow]) {
                        answer++;
                    }
                }
            }
            
            if(nextPoint == null) {
                nextPoint = new Point();
                coordinates2D.put(compressedPos, nextPoint);
            }
            curPoint.visited8Dir[arrow] = true;
            nextPoint.visited8Dir[(arrow + 4) % 8] = true;
        }
        return answer;
    }
}

class Point {
    boolean[] visited8Dir;
    public Point() {
        visited8Dir = new boolean[8];
    }
}