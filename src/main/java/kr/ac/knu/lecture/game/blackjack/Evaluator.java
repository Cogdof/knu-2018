package kr.ac.knu.lecture.game.blackjack;

import java.util.Map;

/**
 * Created by rokim on 2018. 5. 27..
 */
public class Evaluator {
    private Map<String, Player> playerMap;
    private Dealer dealer;

    public Evaluator(Map<String, Player> playerMap, Dealer dealer) {
        this.playerMap = playerMap;
        this.dealer = dealer;
    } // 점수를 매기는 Class

    public boolean cardSumExceedStandard(Player player){

        int playerResult = player.getHand().getCardSum();
        if (playerResult > 21) {
            player.lost();
            return true;
        }
        return false;
    } // hit를 누를때마다 호출되는 메소드. player의 점수가 21이 넘게되면 즉시 패배한다.

    public boolean evaluate() {
        if (playerMap.values().stream().anyMatch(player -> player.isPlaying())) {
            return false;
        } // 한명이라도 플레이중이라면 평가에 들어가지 않는다.

        int dealerResult = dealer.getHand().getCardSum();

        if (dealerResult > 21) {
            playerMap.forEach((s, player) -> player.win());

            return true;
        } // dealer가 21 이상이면 모든 플레이어가 승리

        playerMap.forEach((s, player) -> {
            int playerResult = player.getHand().getCardSum();
            if (playerResult > 21) {
                player.lost();
            } else if (playerResult > dealerResult) {
                player.win();
            } else if (playerResult == dealerResult) {
                player.tie();
            } else {
                player.lost();
            } // 각각의 결과
        });

        return true;
    }
    public boolean evaluateBlackjack(Player player) {

        int playerResult = player.getHand().getCardSum();
        int playerHand = player.getHand().handSize();

        if (playerResult == 21 && playerHand==2) {
            player.blackjack_win();
            return true;
        } // 플레이어가 시작하자마자 21점을 만들었을때 blackJack 달성.

        else{
            return false;
        }
    }




}
