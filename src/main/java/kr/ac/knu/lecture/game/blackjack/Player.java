package kr.ac.knu.lecture.game.blackjack;

import kr.ac.knu.lecture.exception.NotEnoughBalanceException;
import lombok.Getter;

/**
 * Created by rokim on 2018. 5. 26..
 */
public class Player {

    public static int WIN = 0;
    public static int TIE = 1;
    public static int LOST = 2;

    @Getter
    private long balance;
    @Getter
    private long currentBet;
    @Getter
    private boolean isPlaying;
    @Getter
    private Hand hand;
    @Getter
    private int gameStatus = -1;

    public Player(long seedMoney, Hand hand) {
        this.balance = seedMoney;
        this.hand = hand;

        isPlaying = false;
    } // Player는 자금과 손(카드뭉치)을 가지고 있다.

    public void reset() {
        hand.reset();
        isPlaying = false;
    } // 플레이 종료시 호출되는듯.

    public void placeBet(long bet) {
        if (balance < bet) {
            throw new NotEnoughBalanceException();
        } // bet보다 자금이 적을때의 처리

        balance -= bet;
        currentBet = bet;

        isPlaying = true;
    } // bet을 하면 게임이 시작된다

    public void doubleBet(){
        if (balance < currentBet){
            throw new NotEnoughBalanceException();
        }
        balance -= currentBet;
        currentBet = currentBet*2;

        isPlaying = false; // 더블다운할때의 상황. 따라서 게임은 종료상태로 간다.
    } // 현재의 2배로 bet을 늘린다.

    public void deal() {
        hand.drawCard();
        hand.drawCard();
    } // 2장의 카드를 받음

    public void win() {
        gameStatus = WIN;
        balance += currentBet * 2;
        currentBet = 0;
    } // 이겼을시 Bet의 2배를 받는다.

    public void tie() {
        gameStatus = TIE;
        balance += currentBet;
        currentBet = 0;
    } // 동점일시 Bet만큼 받는다.

    public void lost() {
        gameStatus = LOST;
        currentBet = 0;
    } // 졌을시 돈을 받지 못하낟.

    public Card hitCard() {
        return hand.drawCard();
    } // hit를 누르면 한장을 받는다.

    public void stand() {
        this.isPlaying = false;
    } // stand를 누르면 게임이 끝난다.

    public Card doubledown() {
        return hitCard();
    } // doubleDown하고, hit를 누른것과 동일한 상황이 이뤄진다.

    public void blackjack_win() {
        balance += currentBet + (currentBet*1.5); // currentBet * 1.5만 받아야 하는것이 아닌가?
        currentBet = 0;
        isPlaying = false;
    } // 블랙잭으로 이기면 현 bet의 2.5배의 돈을 받는다.
}
