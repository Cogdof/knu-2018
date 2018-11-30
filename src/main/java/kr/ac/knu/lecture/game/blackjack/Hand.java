package kr.ac.knu.lecture.game.blackjack;

import lombok.Getter;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by rokim on 2018. 5. 26..
 */
public class Hand {
    private Deck deck;
    @Getter
    private List<Card> cardList = new ArrayList<>();

    public Hand(Deck deck) {
        this.deck = deck;
    }

    public Card drawCard() {
        Card card = deck.drawCard();
        cardList.add(card);
        return card;
    }
    // 영어 카드들도 10으로 계산되게.. KQJ 가 10으로!
    public int getCardSum() {
        return cardList.stream().mapToInt(card -> {
            int rank = card.getRank();

            if(rank>10){
                return 10;
            }
            return rank;
        }).sum();

    }


    public void reset() {
        cardList.clear();
    }
}
