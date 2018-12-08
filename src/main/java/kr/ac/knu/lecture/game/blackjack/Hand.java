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
        int AceCount = 0;
        int CardSumResult = 0;

        for(int i=0; i<cardList.size(); i++){
            if(cardList.get(i).getRank()==1){
                AceCount++;
            }
        }

        CardSumResult = cardList.stream().mapToInt(card -> {
            int rank = card.getRank();
            if (rank > 10) {
                return 10;
            }
            if(rank == 1){
                return 11;
            }
            return rank;
        }).sum();

        for(int i=0; i<AceCount;i++){
            if( CardSumResult>21){
                CardSumResult-=10; // -11 +1
            }
            else break;
        }



        return CardSumResult;
    }

    public void reset() {
        cardList.clear();
    }

    public int handSize() {
        return cardList.size();
    }
}
