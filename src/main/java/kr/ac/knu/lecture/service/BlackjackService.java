package kr.ac.knu.lecture.service;

import kr.ac.knu.lecture.domain.User;
import kr.ac.knu.lecture.game.blackjack.Deck;
import kr.ac.knu.lecture.game.blackjack.Evaluator;
import kr.ac.knu.lecture.game.blackjack.GameRoom;
import kr.ac.knu.lecture.repository.JdbcTemplateRepository2;
import kr.ac.knu.lecture.repository.UserRepository;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.sql.SQLException;
import java.util.*;

/**
 * Created by rokim on 2018. 11. 30..
 */
@Service
@AllArgsConstructor
public class BlackjackService {
    private final UserRepository userRepository;
    private final int DECK_NUMBER = 1;
    private final Map<String, GameRoom> gameRoomMap = new HashMap<>();
    @Autowired
    private final JdbcTemplateRepository2 jdbcTemplateRepository2;


    public GameRoom createGameRoom(User user){
        Deck deck = new Deck(DECK_NUMBER);

        GameRoom gameRoom = new GameRoom(deck);
        gameRoom.addPlayer(user.getName(), user.getAccount());

        gameRoomMap.put(gameRoom.getRoomId(), gameRoom);

        return gameRoom;
    }

    public GameRoom joinGameRoom(String roomId, User user) {
        // multi player Game 이 아니므로 필요가 없다.
        return null;
    }

    public void leaveGameRoom(String roomId, User user) {
        gameRoomMap.get(roomId).removePlayer(user.getName());
    }

    public GameRoom getGameRoom(String roomId) {
        return gameRoomMap.get(roomId);
    }

    public GameRoom bet(String roomId, User user, long bet) {
        GameRoom gameRoom = gameRoomMap.get(roomId);

        gameRoom.reset();
        gameRoom.bet(user.getName(), bet);
        gameRoom.deal();
        //blackjack
        gameRoom.checkBlackjack(user.getName());

        updateGameResult(gameRoom);

        return gameRoom;
    }

    public GameRoom doubledown(String roomId, User user) {
        GameRoom gameRoom = gameRoomMap.get(roomId);

        if(gameRoom.doubledown(user.getName())){
            // 끝낸다
            gameRoom.endGameWithoutDealerPlaying();
            updateGameResult(gameRoom);
            return gameRoom;
        }


        gameRoom.playDealer();
        updateGameResult(gameRoom);
        return gameRoom;
    }

    public GameRoom hit(String roomId, User user) {
        GameRoom gameRoom = gameRoomMap.get(roomId);

        gameRoom.hit(user.getName());

        updateGameResult(gameRoom);
        return gameRoom;
    }



    public GameRoom stand(String roomId, User user) {
        GameRoom gameRoom = gameRoomMap.get(roomId);

        gameRoom.stand(user.getName());
        gameRoom.playDealer();

        updateGameResult(gameRoom);
        return gameRoom;
    }

    public GameRoom surrender(String roomId, User user) {
        GameRoom gameRoom = gameRoomMap.get(roomId);

        gameRoom.surrender(user.getName());


        updateGameResult(gameRoom);
        return gameRoom;
    }



    private void updateGameResult(GameRoom gameRoom) {
        if (gameRoom.isFinished()) {
            gameRoom.getPlayerList().forEach((loginId, player) -> {
                User playUser = userRepository.findById(loginId).orElseThrow(() -> new RuntimeException());
                playUser.setAccount(player.getBalance());

                userRepository.save(playUser);
            });
        }
    }

    public GameRoom addNextCard(String roomId, int rank) {

        GameRoom gameRoom = gameRoomMap.get(roomId);
        Deck deck = gameRoom.getDeck();

        deck.addNextCard(rank);

        return gameRoom;
    }

    public ArrayList<User> getAllUsers() throws SQLException {

        ArrayList<User> users = jdbcTemplateRepository2.getAllUsersOrderByAccount();
        return users;
    }

    public GameRoom revive(String roomId, User user) {
        GameRoom gameRoom = gameRoomMap.get(roomId);

        gameRoom.revive(user.getName());

        updateGameResult(gameRoom);
        return gameRoom;
    }
}
