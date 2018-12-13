package kr.ac.knu.lecture.game.blackjack;

import lombok.Getter;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

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
        this.roomId = UUID.randomUUID().toString(); // 랜덤하게 주는듯.
        this.deck = deck;
        this.dealer = new Dealer(new Hand(deck));
        this.playerList = new HashMap<>();
        this.evaluator = new Evaluator(playerList, dealer);
        this.isFinished = true;
    } // GameRoom 초기화

    public void addPlayer(String playerName, long seedMoney) {
        Player player = new Player(seedMoney, new Hand(deck));

        playerList.put(playerName, player);
    } // PlayerList에 seedMoney만큼 돈을 가진 플레이어를 추가한다.

    public void removePlayer(String playerName) {
        playerList.remove(playerName);
    } // 플레이어 삭제

    public void reset() {
        dealer.reset();
        playerList.forEach((s, player) -> player.reset());
    } // dealer와 각 player들을 reset

    public void bet(String name, long bet) {
        Player player = playerList.get(name);

        player.placeBet(bet);
    }  // blackjackService로부터 불러져, bet을 눌렀을때 그 플레이어의 게임이 시작한다.

    public void deal() {
        this.isFinished = false;
        dealer.deal();
        playerList.forEach((s, player) -> player.deal());
    } // deal = draw card를 한다.

    public Card hit(String name) {
        Player player = playerList.get(name);

        Card card = player.hitCard(); // 하나 더 뽑기

        boolean isCardSumExceedStandard = evaluator.cardSumExceedStandard(player); // hit할때마다 하나씩 더 뽑음.
        if(isCardSumExceedStandard){
            player.stand();
            this.isFinished = true; // 점수가 넘으면 이 플레이어는 끝
        }

        return card;
    }

    public Card doubledown(String name) {
        Player player = playerList.get(name);
        player.doubleBet();
        return player.doubledown();
    } // doubledown시 더블로 bet하고 게임의 판결을 내린다.

    public void stand(String name) {
        Player player = playerList.get(name);
        player.stand();
    } // 게임의 끝으로

    public void playDealer() {
        dealer.play();
        evaluator.evaluate();
        this.isFinished = true; // ???
    }

    public void checkBlackjack(String name) {
        Player player = playerList.get(name);
        if(evaluator.evaluateBlackjack(player)){
            this.isFinished = true;
        }
    } // blackJack임을 확인.
}
