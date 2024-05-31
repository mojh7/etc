/*
 * 2021-02-24
 * https://www.acmicpc.net/problem/16472
 * 백준 투포인터 골드 2
 * 
골드 5 문제인 회전 초밥 풀 줄 알면 이 것도 쉽게 풀 것 같다.

 */

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

class Main {
    static int[] alphabet;

    public static void main(String[] args) throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        int n = Integer.parseInt(br.readLine());
        String input = br.readLine();
        int answer = twoPointers(n, input);
        System.out.println(answer);
    }

    public static int twoPointers(int n, String input) {
        alphabet = new int[26];
        int maxStringLength = 0;
        int stringLength = 0;
        int leftIdx = 0;
        int rightIdx = 0;
        int countAlphabetKind = 0;
        while (rightIdx < input.length()) {
            countAlphabetKind += countAlphabetKind <= n
                    ? addAlphabet(input.charAt(rightIdx++))
                    : removeAlphabet(input.charAt(leftIdx++));
            if(countAlphabetKind <= n) {
                stringLength = rightIdx - leftIdx;
                if(maxStringLength < stringLength) {
                    maxStringLength = stringLength;
                }
            }
        }
        return maxStringLength;
    }

    public static int addAlphabet(char kind) {
        if (alphabet[kind - 'a']++ == 0) {
            return 1;
        }
        return 0;
    }

    public static int removeAlphabet(char kind) {
        if (--alphabet[kind - 'a'] == 0) {
            return -1;
        }
        return 0;
    }
}

