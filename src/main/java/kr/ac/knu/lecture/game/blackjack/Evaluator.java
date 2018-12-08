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
    }

    public boolean evaluate() {
        if (playerMap.values().stream().anyMatch(player -> player.isPlaying())) {
            return false;
        }

        int dealerResult = dealer.getHand().getCardSum();

        if (dealerResult > 21) {
            playerMap.forEach((s, player) -> player.win());

            return true;
        }

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
            }
        });

        return true;
    }
    public boolean evaluate_blackjack(Player player) {


        int playerResult = player.getHand().getCardSum();
        int playerHand = player.getHand().handSize();

        if (playerResult == 21 && playerHand==2) {
            player.blackjack_win();
            return true;
        }
        else{
            return false;
        }
    }




}
