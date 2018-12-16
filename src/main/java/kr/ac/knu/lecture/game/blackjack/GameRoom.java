package kr.ac.knu.lecture.game.blackjack;

import kr.ac.knu.lecture.domain.User;
import kr.ac.knu.lecture.repository.UserRepository;
import lombok.Getter;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.*;

/**
 * Created by rokim on 2018. 5. 26..
 */
public class GameRoom {
    @Getter
    private final String roomId;
    @Getter
    private final Dealer dealer;
    @Getter
    private final Map<String, Player> playerList;

    @Getter
    private final Deck deck;
    @Getter
    private boolean isFinished;
    private final Evaluator evaluator;


    public GameRoom(Deck deck) {
        this.roomId = UUID.randomUUID().toString();
        this.deck = deck;
        this.dealer = new Dealer(new Hand(deck));
        this.playerList = new HashMap<>();
        this.evaluator = new Evaluator(playerList, dealer);
        this.isFinished = true;
    }

    public void addPlayer(String playerName, long seedMoney) {
        Player player = new Player(seedMoney, new Hand(deck));

        playerList.put(playerName, player);
    }

    public void removePlayer(String playerName) {
        playerList.remove(playerName);
    }

    public void reset() {
        dealer.reset();
        playerList.forEach((s, player) -> player.reset());
    }

    public void bet(String name, long bet) {
        Player player = playerList.get(name);

        player.placeBet(bet);
    }

    public void deal() {
        this.isFinished = false;
        dealer.deal();
        playerList.forEach((s, player) -> player.deal());
    }

    public Card hit(String name) {
        Player player = playerList.get(name);

        Card card = player.hitCard();

        boolean isCardSumExceedStandard = evaluator.cardSumExceedStandard(player);
        if(isCardSumExceedStandard){
            player.stand();
            this.isFinished = true;
        }

        return card;

    }

    public Card doubledown(String name) {
        Player player = playerList.get(name);
        player.doubleBet();
        return player.doubledown();
    }

    public void stand(String name) {
        Player player = playerList.get(name);
        player.stand();
    }

    public void playDealer() {
        dealer.play();
        evaluator.evaluate();
        this.isFinished = true;
    }

    public void checkBlackjack(String name) {
        Player player = playerList.get(name);
        if(evaluator.evaluate_blackjack(player)){
            this.isFinished = true;
        }


    }

    public void surrender(String name) {
        Player player = playerList.get(name);
        //딜러 핸드 확인, 블랙잭시 패배
        //아니면 \0.5배 가져오기.
        evaluator.evaluate_surrender(player);
        this.isFinished = true;


    }
}
