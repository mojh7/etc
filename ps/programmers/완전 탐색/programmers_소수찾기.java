/*
 * 2020-06-01
 * https://programmers.co.kr/learn/courses/30/lessons/42839
 * 프로그래머스 코딩테스트 고득점 Kit 완전탐색
 *
 * (1). numbers 에서 주어진 숫자들로 만들 수 있는 모든 숫자 조합(Map<Integer, Boolean> numCombination = new HashMap<>(); value의 boolean값은 딱히 상관 없음) 만들기
 * (2). 에라토스테네스의 체를 활용하여 (1) 에서 만든 숫자 조합중에서 가장큰수 numMax까지의 소수 판별 배열(boolean[] isPrime)을 만든다.
 * (3). numCombination에서 keySet을 순회하여 isPrime[index]와 비교하여 소수를 찾고 갯수 출력
 * 
 * 조심 : bfs에서 while(!q.isEmpty() 내에서 boolean[] visit2 = new boolean[visit.length()]; 로 새로운 배열을 만들고 for문이나 visit.clone으로 값을 복사했으나 visit2를 매 while마다 만들어지지 않은 것 같아서 잘못 된 답 출력.
 * 그래서 다음 숫자 조합을 만드는 if(!visit[i]) 안에서 Pair<String, boolean[]> new Node를 초기화 할 때 visit.clone()으로 새로운 boolean[]을 만들어 넘겨주도록 수정하여 해결함.
 */

import java.util.*;

class Solution {
    public class Pair<F, S>{
        F first;
        S second;
        public Pair(F first, S second){
            this.first = first;
            this.second = second;
        }

        public F getFirst(){
            return first;
        }

        public S getSecond(){
            return second;
        }
    }

   
    public int solution(String numbers) {
        int answer = 0;
        int numMax = -1;
        Map<Integer, Boolean> numCombination = new HashMap<>();

        Queue<Pair<String, boolean[]>> q = new LinkedList<>();
        q.add(new Pair<>("", new boolean[numbers.length()]));
        bfs(numCombination, numbers, q);

        for(Integer key : numCombination.keySet()){
            if(numMax < key){
                numMax = key;
            }
        }
        boolean[] isPrime = getPrime(numMax);

        for(Integer key : numCombination.keySet()){
            //System.out.println("key : " + key + " = " + isPrime[key]);
            if(isPrime[key]){
                answer += 1;
            }
        }
        return answer;
    }

    public boolean[] getPrime(int num){
        boolean[] isPrime = new boolean[num+1];
        Arrays.fill(isPrime, true);
        isPrime[0] = false;
        isPrime[1] = false;
        for(int i = 2; i <= Math.sqrt(num); i++){
            if(isPrime[i]){
                for(int j = 2; i * j <= num; j++){
                    isPrime[i*j] = false;
                }
            }
        }
        return  isPrime;
    }

    public void bfs(Map<Integer, Boolean> numCombination, String numbers
                        , Queue<Pair<String, boolean[]>> q){
        Queue<Pair<String, boolean[]>> q2 = new LinkedList<>();

        while(!q.isEmpty()){
            Pair<String, boolean[]> node = q.poll();
            String str = node.getFirst();
            boolean[] visit = node.getSecond();

            for(int i = 0 ; i < numbers.length(); i++){
                if(!visit[i]){
                    String str2 = new String(str + numbers.charAt(i));
                    numCombination.put(Integer.parseInt(str2), true);
                    Pair<String, boolean[]> newNode = new Pair<>(str2, visit.clone());
                    newNode.getSecond()[i] = true;
                    q2.add(newNode);
                }
            }
        }

        if(!q2.isEmpty()){
            bfs(numCombination, numbers, q2);
        }
    }
}