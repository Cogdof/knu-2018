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

    public boolean cardSumExceedStandard(Player player){

        int playerResult = player.getHand().getCardSum();
        if (playerResult > 21) {
            player.lost();
            return true;
        }
        return false;
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
            if (evaluateBlackjackDealer()) {
                player.lost();
            }
            else {
                if (playerResult > 21) {
                    player.lost();
                } else if (playerResult > dealerResult) {
                    player.win();
                } else if (playerResult == dealerResult) {
                    player.tie();
                } else {
                    player.lost();
                }
            }
        });

        return true;
    }

    private boolean evaluateBlackjackDealer(){
        if (dealer.getHand().getCardSum() == 21 && dealer.getHand().getCardList().size() == 2) {
            return true;
        }
        return false;
    }

    public boolean evaluate_blackjack(Player player) {

        int playerResult = player.getHand().getCardSum();
        int playerHand = player.getHand().handSize();

        if (playerResult == 21 && playerHand==2) {
            dealer.deal();
            if (evaluateBlackjackDealer()) {
                player.tie();
                return true;
            } else {
                player.blackjack_win();
                return true;
            }
        }

        else{
            return false;
        }
    }

    public boolean evaluate_surrender(Player player) {
        dealer.deal();
        if(evaluateBlackjackDealer()){
            player.lost();
        }
        else{
            player.surrender();
        }
        return true;
    }
}
