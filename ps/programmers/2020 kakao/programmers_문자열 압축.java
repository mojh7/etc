/*
 * 2020-09-09
 * https://programmers.co.kr/learn/courses/30/lessons/60057
 * 프로그래머스 2020 KAKAO BLIND RECRUITMENT 문자열 압축
 *
 * 주어진 문자열 s를 1개 단위 부터 s의 길이 n까지(i = 1; i <= s.length(); i++)
 * 압축하여 표현한 문자열 중 가장 짧은 것의 길이를 구하면 된다.
 *
 * 하지만 i > s.length()/2 단위로 압축하는 경우에는 2i > s.length() 이기 때문에
 * 압축이 일어나지 않아서 압축하여 표현한 문자열 중 가장 짧게 나올 수가 없다.
 * 문자의 반복되는 부분을 최대한 많이 압축 해야 정답에 가까운 값을 찾을 수 있다.
 *
 * 그래서 for의 범위를 보면 for(int i = 1; i <= s.length()/2+1; i++)  로 하였는데
 * 사실  i <= s.length()/2 까지만 봐도 되나 입력한 문자열 s의 길이가 1일 경우
 * 에러가 생기기 때문에 i <= s.length()/2 + 1 로 범위를 잡았고 문자열 s의 길이가
 * 1일 때의 처리를 아래 처럼 따로 한다면
 * if(1 == s.length()) {
       return 1;
   }  
 * for(int i = 1; i <= s.length()/2; i++) { 로 작성하여도 정답이다.
 */

class Solution {
    public String compression(String s, int chunkSize) {
        String result = "";
        boolean isRepeating = false;
        int index = 0;
        String prevSubStr = "";
        String curSubStr = "";
        int repeatedCnt = 1;
        while(index + chunkSize <= s.length()) {
            curSubStr = s.substring(index, index + chunkSize);
            if(prevSubStr.equals(curSubStr)) {
                repeatedCnt++;
            }else {
                if(2 <= repeatedCnt) {
                    result += repeatedCnt;
                }
                result += prevSubStr;
                prevSubStr = curSubStr;
                repeatedCnt = 1;
            }
            index += chunkSize;
        }
        if(2 <= repeatedCnt) {
            result += repeatedCnt;
        }
        result += curSubStr;
        result += s.substring(index, s.length());
        
        return result;
    }
    
    
    public int solution(String s) {
        int answer = 100000;
        int compressedStrLen = 0;
        for(int i = 1; i <= s.length()/2+1; i++) {
            compressedStrLen = compression(s, i).length();
            if(answer > compressedStrLen) {
                answer = compressedStrLen;
            }
        }
        return answer;
    }
}