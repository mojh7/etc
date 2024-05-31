/*
 * 2020-05-14
 * https://programmers.co.kr/learn/courses/30/lessons/42578
 * 프로그래머스 코딩테스트 고득점 Kit 해시
 * 주어진 옷의 종류별 갯수를 구하고 해당 종류의 옷을 안 입는 경우를 포함해서 모두 곱한다.
 * 모자 2개, 얼굴 1개인 경우 (2+1) * (1+1). 그러면 모든 종류의 옷을 안 입는 경우도
 * 포함되는데 "스파이는 하루에 최소 한개 의상은 입습니다." 조건이 있으므로 1개 값을 뺀다.
 * (2+1) * (1+1) - 1 =  5
 */

import java.util.Map;
import java.util.HashMap;
import java.util.List;
import java.util.ArrayList;

class Solution {
    public int solution(String[][] clothes) {
        int answer = 1;
        Map<String, List<String>> m = new HashMap<String, List<String>>();
        String key;
        for(int i = 0 ; i < clothes.length; i++){
            
            key = clothes[i][1];
            if(!m.containsKey(key)){
                m.put(key, new ArrayList<String>());
            }
            m.get(key).add(clothes[i][0]);
        }
        for (List<String> value : m.values()) {
            answer *= (value.size() + 1);
        }    
        
        answer -= 1;
        return answer;
    }
}