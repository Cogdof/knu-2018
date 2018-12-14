package kr.ac.knu.lecture.game.blackjack;

import kr.ac.knu.lecture.exception.NoSuchRankException;
import lombok.Data;
import lombok.Getter;
import org.springframework.web.bind.annotation.GetMapping;

/**
 * Created by rokim on 2018. 5. 26..
 */
@Data
public class Card {
    private final int rank;
    private final Suit suit;

    public Card(int rank, Suit suit) { // 생성시
        if (rank > 13) {
            throw new NoSuchRankException();
        } // 카드 Rank가 13이 넘지 않도록
        this.rank = rank;
        this.suit = suit;
    }
}
