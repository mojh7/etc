/*
 * 2021-02-08
 * https://www.acmicpc.net/problem/12846
 * 백준 분할 정복 골드 1 -> 플5로 바뀜
 * 1725, 14727 문제랑 유사

풀이 방법만 보고 따로 짜보려고 했는데 계속 틀림
idx 시작 위치만 가지고 하려고 했는데 잘 안 된다.
직사각형의 좌측과 우측 정보 둘다 가지고 코드를
짜다보니 결국 해설에 나온 코드랑 같아짐

반례
10
20 40 0 20 30 10 15 15 10 10
답 70
50나왔다

3
30 20 40
답 60

3
0 2 1
답 2
처음에 0 나올 때 prevSalary 초기값이 0 이여서
if (prevSalary == currentSalary) 조건문에 걸려서
스택에 추가안되고 넘어가서
2 * 2 = 4 나왔다.

7
0 3 2 0 2 1 4
답 4
6나왔음

8
8 2 5 4 3 1 9 7
답 : 14
 */

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Stack;
import java.util.StringTokenizer;

class Main {
    public static void main(String[] args) throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        int n = Integer.parseInt(br.readLine());
        long salary;
        long max = 0;
        long total;
        Info info;
        Stack<Info> s = new Stack<>();
        StringTokenizer st = new StringTokenizer(br.readLine());
        for (int idx = 0; idx < n; idx++) {
            salary = Long.parseLong(st.nextToken());
            while(!s.isEmpty() && s.peek().salary > salary) {
                info = s.pop();
                total = info.salary * (long)(idx - info.left);
                if (max < total) {
                    max = total;
                }
            }
            s.push(new Info(s.isEmpty() ? 0 : s.peek().right + 1, idx, salary));
        }
        while(!s.isEmpty()) {
            info = s.pop();
            total = info.salary * (long)(n - info.left);
            if (max < total) {
                max = total;
            }
        }
        System.out.println(max);
    }
}

class Info {
    int left;
    int right;
    long salary;
    public Info(int left, int right, long salary) {
        this.left = left;
        this.right = right;
        this.salary = salary;
    }
}