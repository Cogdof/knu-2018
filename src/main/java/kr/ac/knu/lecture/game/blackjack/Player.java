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
    public static int SURRENDER = 3;
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
    }

    public void reset() {
        hand.reset();
        isPlaying = false;
    }

    public void placeBet(long bet) {
        if (balance < bet) {
            throw new NotEnoughBalanceException();
        }
        balance -= bet;
        currentBet = bet;

        isPlaying = true;
    }

    public void doubleBet(){

        if (balance < currentBet){
            currentBet += balance;
            balance = 0;
        } else {
            balance -= currentBet;
            currentBet = currentBet*2;
        }

        isPlaying = false;
    }

    public void deal() {
        hand.drawCard();
        hand.drawCard();
    }

    public void win() {
        gameStatus = WIN;
        balance += currentBet * 2;
        currentBet = 0;
    }

    public void tie() {
        gameStatus = TIE;
        balance += currentBet;
        currentBet = 0;
    }

    public void lost() {
        gameStatus = LOST;
        currentBet = 0;
    }

    public Card hitCard() {
        return hand.drawCard();
    }

    public void stand() {
        this.isPlaying = false;
    }

    public Card doubledown() {
        return hitCard();
    }

    public void blackjack_win() {
        balance += (currentBet * 1.5);
        currentBet = 0;
        isPlaying = false;

    }

    public void surrender() {
        gameStatus =SURRENDER;
        balance += (currentBet * 0.5);
        currentBet = 0;
        this.isPlaying = false;
    }

    public void revive() {
        gameStatus = -1;
        if(balance==0&&currentBet==0) {
            balance = 15000;
        }
    }
}
