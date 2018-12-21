package kr.ac.knu.lecture.game.blackjack;

import lombok.Getter;

/**
 * Created by rokim on 2018. 5. 26..
 */
public class Dealer {
    @Getter
    private Hand hand;

    public Dealer(Hand hand) {
        this.hand = hand;
    }

    public void reset() {
        hand.reset();
    }

    public void deal() {
        hand.drawCard();
    }

    public void play() {

        // 여기서 플레이어의 getCardSum()이 22보다 크면 그냥 return을 해준다

        while (hand.getCardSum() < 17) {
            hand.drawCard();
        }
    }
}
