/*
 * 2021-04-23
 * https://www.acmicpc.net/problem/17609
 * 백준 문자열 실버 1

반례
1
cccfccfcc
2

답: 0나와야됨
 */

import java.io.IOException;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;

class Main {
    public static void main(String[] args) throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        BufferedWriter bw = new BufferedWriter(new OutputStreamWriter(System.out));
        int n = Integer.parseInt(br.readLine());
        String input;
        for (int idx = 0; idx < n; idx++) {
            input = br.readLine();
            bw.write(calc(input));
            bw.newLine();
        }

        bw.flush();
        bw.close();
    }

    public static String calc(String input) {
        int leftIdx = 0;
        int rightIdx = input.length() - 1;
        while (leftIdx <= rightIdx) {
            if (input.charAt(leftIdx) != input.charAt(rightIdx)) {
                if(calc2(input.substring(leftIdx, rightIdx)) ||
                        calc2(input.substring(leftIdx + 1, rightIdx + 1))) {
                    return "1";
                }
                return "2";
            }

            leftIdx++;
            rightIdx--;
        }

        return "0";
    }

    public static boolean calc2(String input) {
        int leftIdx = 0;
        int rightIdx = input.length() - 1;
        while (leftIdx <= rightIdx) {
            if (input.charAt(leftIdx) != input.charAt(rightIdx)) {
                return false;
            }

            leftIdx++;
            rightIdx--;
        }

        return true;
    }
}