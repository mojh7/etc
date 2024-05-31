/* 
 * 2020-05-13
 * https://programmers.co.kr/learn/courses/30/lessons/42577
 * 프로그래머스 코딩테스트 고득점 Kit 해시
 */

class Solution {
    public boolean solution(String[] phone_book) {
        boolean answer = true;
        String tempStr;
        int n = phone_book.length;
        for(int i = 0; i < n; i++)
        {
            tempStr = phone_book[i];
            for(int j = 0; j < n; j++)
            {
                if(j != i)
                {
                    if(true == phone_book[j].startsWith(tempStr))
                    {
                        return false;
                    }
                }
            }
        }
        answer = true;
        return answer;
    }
}