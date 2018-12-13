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
    private List<Card> cardList = new ArrayList<>(); // 플레이어 손 안의 카드리스트

    public Hand(Deck deck) {
        this.deck = deck;
    }

    public Card drawCard() {
        Card card = deck.drawCard();
        cardList.add(card);
        return card;
    } // 자신의 손에 카드를 추가시킨다.

    public int getCardSum() {
        int aceCount = 0; // 에이스카드의 수
        int cardSumResult = 0; // 카드 총 합

        for(int i=0; i<cardList.size(); i++){ //  손 안의 카드를 쭉 돌며
            if(cardList.get(i).getRank()==1){
                aceCount++;
            } // Ace카드일때의 처리
        }

        cardSumResult = cardList.stream().mapToInt(card -> {
            int rank = card.getRank();
            if (rank > 10) {
                return 10;
            } // J, Q, K는 10으로 친다.

            if(rank == 1){
                return 11;
            } // A는 11로 친다.

            return rank;
        }).sum(); // 이에 대한 합이 CardSumResult가 된다.

        for(int i=0; i<aceCount;i++){
            if( cardSumResult>21){
                cardSumResult-=10; // A가 있으며, CardSumResult가 21을 넘었을때의 최댓값을 가기 위한 처리.
            }
            else break;
        }

        return cardSumResult;
    }

    public void reset() {
        cardList.clear();
    } // 손 안의 카드가 초기화된다.

    public int handSize() {
        return cardList.size();
    } // 손 안의 카드 수를 반환한다.
}
