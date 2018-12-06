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

    public int getCardSum() {
        return cardList.stream().mapToInt(card -> {
            int rank = card.getRank();
            if (rank > 10) {
                return 10;
            }
            return rank;
        }).sum();
    }

    public void reset() {
        cardList.clear();
    }
}
