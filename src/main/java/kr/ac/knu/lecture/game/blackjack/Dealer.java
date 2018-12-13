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
    } // 딜러는 손을 가지고 있다.

    public void reset() {
        hand.reset();
    } // 딜러의 손을 초기화

    public void deal() {
        hand.drawCard();
    } // 딜러가 한 장 받음

    public void play() {
        while (hand.getCardSum() < 17) {
            hand.drawCard();
        } // 딜러의 알고리즘. 17보다 카드 합이 적으면 한 장 더 받는다.
    }
}
