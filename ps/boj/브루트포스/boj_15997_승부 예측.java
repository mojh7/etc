/*
 * 2021-01-09
 * https://www.acmicpc.net/problem/15997
 * 백준 브루트포스 골드 3
 * 카카오 코드 페스티벌 2018 A번
 *
 * 예제는 값 나오는데 1%에서 바로 틀림
 * 그런데 아래 예제에서 나온 결과 값이 합이 2가 안되는 것 보면 중간에 로직 틀린 듯
KOREA CCC BBB AAA
KOREA CCC 0.3 0.5 0.2
AAA BBB 0.2 0.8 0.0
AAA KOREA 0.1 0.4 0.5
CCC BBB 0.3 0.6 0.1
KOREA BBB 0.55 0.25 0.2
CCC AAA 0.0 1.0 0.0

------------------------------------
수정 해서 내다가 3번 틀리고 4번 째 때 정답
6번의 경기가 끝난 후 처리를 할 때 4개의 팀 중 1팀은 1등이 확정이고
나머지 3개의 팀이 동점이라서 3개의 팀 중 하나만 다음 라운드로 갈 수 있을 때
그 경기가 일어날 확률에서 1/3f 를 곱해서 더 해줘야되는데 2/3f로 처리해서 더 오바되게 더해져서 틀렸었음
 */

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.IOException;
import java.util.*;

class Main {
    static final int WIN = 0;
    static final int DRAW = 1;
    static final int LOSE = 2;
    static Team[] leagueResult;
    public static void main(String[] args) throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        BufferedWriter bw = new BufferedWriter(new OutputStreamWriter(System.out));
        StringTokenizer st = new StringTokenizer(br.readLine());
        leagueResult = new Team[4];
        LinkedHashMap<String, Team> teams = new LinkedHashMap<>();
        Game[] games = new Game[6];
        for(int idx = 0; idx < 4; idx++) {
            leagueResult[idx] = new Team();
            teams.put(st.nextToken(), leagueResult[idx]);
        }
        for(int idx = 0; idx < 6; idx++) {
            st = new StringTokenizer(br.readLine());
            games[idx] = new Game(teams.get(st.nextToken()), teams.get(st.nextToken()),
                    Float.parseFloat(st.nextToken()), Float.parseFloat(st.nextToken()), Float.parseFloat(st.nextToken()));
        }

        dfs(games, 1, 0);
        for(Map.Entry<String, Team> entry : teams.entrySet()) {
            bw.write(String.format("%.10f\n", entry.getValue().percentage));
        }
        bw.flush();
        bw.close();
    }

    public static void dfs(Game[] games, float odds, int round) {
        if(round == 6) {
            finishLeague(odds);
            return;
        }
        float nextOdds;
        for(int matchResult = 0; matchResult < 3; matchResult++) {
            if(games[round].odds[matchResult] == 0f)
                continue;

            nextOdds = odds * games[round].odds[matchResult];
            runGame(games[round], matchResult,false);
            dfs(games, nextOdds, round + 1);
            runGame(games[round], matchResult, true);
        }
    }

    public static void finishLeague(float odds) {
        Arrays.sort(leagueResult, (t1, t2) -> t2.score - t1.score);
        if(leagueResult[0].score > leagueResult[1].score) {
            leagueResult[0].percentage += odds;
            if(leagueResult[1].score > leagueResult[2].score) {
                leagueResult[1].percentage += odds;
            } else if(leagueResult[2].score > leagueResult[3].score){
                leagueResult[1].percentage += odds * 0.5f;
                leagueResult[2].percentage += odds * 0.5f;
            } else {
                leagueResult[1].percentage += odds * (1f / 3f);
                leagueResult[2].percentage += odds * (1f / 3f);
                leagueResult[3].percentage += odds * (1f / 3f);
            }
        } else if(leagueResult[1].score > leagueResult[2].score) {
            leagueResult[0].percentage += odds;
            leagueResult[1].percentage += odds;
        } else if(leagueResult[2].score > leagueResult[3].score) {
            leagueResult[0].percentage += odds * (2f / 3f);
            leagueResult[1].percentage += odds * (2f / 3f);
            leagueResult[2].percentage += odds * (2f / 3f);
        } else {
            leagueResult[0].percentage += odds * 0.5f;
            leagueResult[1].percentage += odds * 0.5f;
            leagueResult[2].percentage += odds * 0.5f;
            leagueResult[3].percentage += odds * 0.5f;
        }
    }

    public static void runGame(Game game, int matchResult, boolean isReversed) {
        int correction = isReversed ? -1 : 1;
        switch (matchResult) {
            case WIN:
                game.leftTeam.score += 3 * correction;
                break;
            case DRAW:
                game.leftTeam.score += 1 * correction;
                game.rightTeam.score += 1 * correction;
                break;
            case LOSE:
                game.rightTeam.score += 3 * correction;
                break;
            default:
                break;
        }
    }
}

class Team {
    int score;
    float percentage;
}

class Game {
    Team leftTeam;
    Team rightTeam;
    // win, draw, lose
    float[] odds;

    public Game(Team leftTeam, Team rightTeam, float win, float draw, float lose) {
        this.leftTeam = leftTeam;
        this.rightTeam = rightTeam;
        odds = new float[]{ win, draw, lose };
    }
}