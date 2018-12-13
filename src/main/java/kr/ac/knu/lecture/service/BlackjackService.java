package kr.ac.knu.lecture.service;

import kr.ac.knu.lecture.domain.User;
import kr.ac.knu.lecture.game.blackjack.Deck;
import kr.ac.knu.lecture.game.blackjack.Evaluator;
import kr.ac.knu.lecture.game.blackjack.GameRoom;
import kr.ac.knu.lecture.repository.UserRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

/**
 * Created by rokim on 2018. 11. 30..
 */
@Service
@AllArgsConstructor
public class BlackjackService {
    private final UserRepository userRepository;
    private final int DECK_NUMBER = 1;
    private final Map<String, GameRoom> gameRoomMap = new HashMap<>();

    public GameRoom createGameRoom(User user) { // 유저라는 객체를 받아
        Deck deck = new Deck(DECK_NUMBER);

        GameRoom gameRoom = new GameRoom(deck);
        gameRoom.addPlayer(user.getName(), user.getAccount()); // 이 유저의 아이디와 자금 데이터를 얻어 플레이어로써 방에 넣고

        gameRoomMap.put(gameRoom.getRoomId(), gameRoom); // 새로 만들 방을 방 목록에 넣는다.

        return gameRoom;
    }

    public GameRoom joinGameRoom(String roomId, User user) {
        // multi player Game 이 아니므로 필요가 없다.
        return null;
    } // 게임방에 들어가도록 만드는것. Multi였다면 구현 필요.

    public void leaveGameRoom(String roomId, User user) {
        gameRoomMap.get(roomId).removePlayer(user.getName());
    } // 게임 나감

    public GameRoom getGameRoom(String roomId) {
        return gameRoomMap.get(roomId);
    }
    // ~번째 게임 룸을 가져옴

    public GameRoom bet(String roomId, User user, long bet) {
        GameRoom gameRoom = gameRoomMap.get(roomId);

        gameRoom.reset();
        gameRoom.bet(user.getName(), bet);
        gameRoom.deal();

        gameRoom.checkBlackjack(user.getName());

        return gameRoom;
    } // bet을 하고, 카드를 나눠주고, 블랙잭 검사가 이뤄지며, 게임이 시작된다.

    public GameRoom doubleDown(String roomId, User user) {
        GameRoom gameRoom = gameRoomMap.get(roomId);
        gameRoom.doubledown(user.getName());
        gameRoom.playDealer();

        updateGameResult(gameRoom);

        return gameRoom;
    } // 2배로 딜하게 되는 doubleDown을 실행.

    public GameRoom hit(String roomId, User user) {
        GameRoom gameRoom = gameRoomMap.get(roomId);

        gameRoom.hit(user.getName());

        updateGameResult(gameRoom);
        return gameRoom;
    } // hit가 이뤄지고 각 플레이어 정보가 업데이트된다.



    public GameRoom stand(String roomId, User user) {
        GameRoom gameRoom = gameRoomMap.get(roomId);

        gameRoom.stand(user.getName());
        gameRoom.playDealer();

        updateGameResult(gameRoom);
        return gameRoom;
    } // stand시 결과 계산 후 플레이어 업데이트, 게임이 종료된다.

    private void updateGameResult(GameRoom gameRoom) {
        if (gameRoom.isFinished()) {
            gameRoom.getPlayerList().forEach((loginId, player) -> {
                User playUser = userRepository.findById(loginId).orElseThrow(() -> new RuntimeException());
                playUser.setAccount(player.getBalance());

                userRepository.save(playUser);
            }); // 플레이어들의 정보를 업데이트
        }
    }

    public GameRoom addNextCard(String roomId, int rank) {

        GameRoom gameRoom = gameRoomMap.get(roomId);
        Deck deck = gameRoom.getDeck();

        deck.addNextCard(rank);

        return gameRoom;
    } // 해당 게임룸의 덱에 다음 카드를 추가한다.
}
