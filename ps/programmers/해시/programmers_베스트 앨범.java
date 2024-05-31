/*
 * 2020-05-14
 * https://programmers.co.kr/learn/courses/30/lessons/42579
 * 프로그래머스 코딩테스트 고득점 Kit
 */

import java.util.Map;
import java.util.HashMap;
import java.util.Arrays;
import java.util.ArrayList;
import java.util.List;
import java.util.Collections;

class Pair{
    public Integer first;
    public Integer second;
    public Pair(Integer first, Integer second){
        this.first = first;
        this.second = second;
    }
}

class Solution {
    public static int[] solution(String[] genres, int[] plays) {
        
        List<Integer> answerList = new ArrayList<Integer>();
        Map<String, List<Pair>> m = new HashMap<String, List<Pair>>();
        String key;
        for (int i = 0; i < genres.length; i++){
            key = genres[i];
            if(m.containsKey(key)){
                m.get(key).get(0).first += plays[i];
            }
            else{
                m.put(key, new ArrayList<Pair>());
                m.get(key).add(new Pair(plays[i], i));
            }
            m.get(key).add(new Pair(plays[i], i));
        }

        List<String> keySetList = new ArrayList<String>(m.keySet());
        Collections.sort(keySetList, (o1, o2) -> (m.get(o2).get(0).first.compareTo(m.get(o1).get(0).first)));

        for(String k : keySetList) {
            if(2 == m.get(k).size()){
                answerList.add(m.get(k).get(1).second);
            }
            else{
                Collections.sort(m.get(k), (o1, o2) -> (o2.first.compareTo(o1.first)));
                answerList.add(m.get(k).get(1).second);
                answerList.add(m.get(k).get(2).second);
            }
        }

        int[] answer = new int[answerList.size()];
        for(int i = 0; i < answerList.size(); i++){
            answer[i] = answerList.get(i);;
        }
        
        return answer;
    }
}