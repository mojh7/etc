/*
 * 2021-02-07
 * https://www.acmicpc.net/problem/1493
 * 백준 분할 정복 골드 5
 *
해설 풀이 방법 참조하여 풀음, 50% 쯤에서 틀렸는데 
long size = cubes[depth][0];
long neededCount = (width / size) * (length / size) * (height / size);
여기서 size를 int -> long으로 바꾸니 맞음.

문제를 보고 3차원 형태 이전에 2차원 형태에서 푼다고 생각하면
각 위치에 어느 부분이 비어있는지 확인을 어떻게 하나 생각이 들고 length, width, height 각 좌표마다
배열을 이용해서 체크 해야되나 했는데 그러기에는 숫자가 너무큼. 1 <= length, width, height <= 10^6

부피를 이용해서 남은 부분만큼 채워야 하나 생각이 들었지만

부피를 이용해서 채워야 하나? 라기에는
2 * 2 * 16 = 4 * 4 * 4 = 64 인데
4 * 4 * 4 큐브로는 2 * 2 * 16 상자를 채울 수 없다.

필요한 큐브의 최솟값이 필요하니 크기가 큰 순서에서 작은 순서대로 큐브를 이용하여 채워야 겠다는 생각은
드는데 방법이 떠오르지 않아 해설을 참고함.

큰 크기의 큐브를 채울 수 있는 만큼 넣고 그 갯수를 prevCount 다음 함수에 넘긴다.
4*4*4 큐브 1개는 2*2*2 큐브 8개를 채운 것과 동일하니
prevCount * 8 + 현재 크기의 큐브 갯수 만큼 채울 수 있는데
이렇게 채운 count 갯수의 부피가 상자의 부피와 일치하면 채울 수 있는 것이고
가장 작은 크기의 큐브를 이용했는데도 부피가 맞지 않다면 채울 수 없어서 -1를 리턴하게 했다.
 */

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.StringTokenizer;

class Main {
    static int answer;
    static long length;
    static long width;
    static long height;
    static long volume;
    static int[][] cubes;

    public static void main(String[] args) throws IOException {
        answer = 0;
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        StringTokenizer st = new StringTokenizer(br.readLine());
        length = Long.parseLong(st.nextToken());
        width = Long.parseLong(st.nextToken());
        height = Long.parseLong(st.nextToken());
        volume = length * width * height;
        int n = Integer.parseInt(br.readLine());
        cubes = new int[n][2];
        for (int idx = 0; idx < n; idx++) {
            st = new StringTokenizer(br.readLine());
            cubes[idx][0] = 1 << Integer.parseInt(st.nextToken());
            cubes[idx][1] = Integer.parseInt(st.nextToken());
        }
        divide(n-1, 0);
        System.out.println(answer);
    }

    public static void divide(int depth, long prevCount) {
        if(depth == -1) {
            answer = -1;
            return;
        }
        long size = cubes[depth][0];
        long neededCount = (width / size) * (length / size) * (height / size);
        long count = prevCount * 8 + cubes[depth][1];
        if(count > neededCount) {
            count = neededCount;
        }
        answer += count - prevCount * 8;
        long currentVolume = size * size * size * count;
        if(currentVolume == volume) {
            return;
        }
        divide(depth - 1, count);
    }
}