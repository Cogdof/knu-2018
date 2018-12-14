package kr.ac.knu.lecture.game.blackjack;

import kr.ac.knu.lecture.exception.NoMoreCardException;
import lombok.Getter;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * Created by rokim on 2018. 5. 26..
 */
public class Deck {
    @Getter
    private final int number;
    @Getter
    private final List<Card> cardList;

    public Deck(int number) {
        this.number = number;
        this.cardList = new ArrayList<Card>();
        createCards(number);
        Collections.shuffle(cardList);
    } // 생성시 카드를 만들고 섞는다.

    private void createCards(int number) {
        // create card for single deck
        for (int j = 0; j < number; j++) {
            for (Suit suit : Suit.values()) { // 각 종류별로
                for (int i = 1; i < 14; i++) {
                    Card card = new Card(i, suit); // A부터 K까지의 Rank로 덱을 만든다.
                    cardList.add(card);
                }
            }
        }
    }

    public Card drawCard() {
//        이렇게 만들면 될 것.
        if (cardList.size() <= 10) {
            cardList.removeAll(cardList);
            createCards(number);
            Collections.shuffle(cardList);
        }
//
//        if (cardList.size() == 0) {
//            // TODO 실제 게임에서 이런 일이 절대로 일어나면 안되겠죠?
//            // 그래서 보통 게임에서는 N 장의 카드가 남으면 모든 카드를 합쳐서 다시 셔플 합니다.
//            // 코드에 그런 내용이 들어가야 함.
//            throw new NoMoreCardException();
//        }
        return cardList.remove(0);
    } // 카드의 수가 적으면 카드를 다시 섞고, 카드를 한장 드로우(삭제&반환)한다.


    public void addNextCard(int rank) {
        cardList.add(0, new Card(rank, Suit.SPADES)); // 어째서 SPADES?
    }
}
