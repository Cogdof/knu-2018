package kr.ac.knu.lecture.controller.api;

import kr.ac.knu.lecture.domain.User;
import kr.ac.knu.lecture.game.blackjack.GameRoom;
import kr.ac.knu.lecture.repository.UserRepository;
import kr.ac.knu.lecture.service.BlackjackService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.sql.SQLException;
import java.util.ArrayList;

/**
 * Created by rokim on 2018. 5. 21..
 */
@RestController
@RequestMapping("/api/black-jack")
@CrossOrigin
public class BlackjackApiController {
    @Autowired
    private BlackjackService blackjackService;

    @Autowired
    private UserRepository userRepository;

    @GetMapping("/ranking")
    public ArrayList<User> getRankingData() throws SQLException {
        return blackjackService.getAllUsers();
    }

    @PostMapping("/rooms")
    public GameRoom createRoom(@AuthenticationPrincipal User user) throws SQLException {
        User currentUser = userRepository.getOne(user.getName());
        return blackjackService.createGameRoom(currentUser);
    }

    @PostMapping(value = "/rooms/{roomId}/bet", consumes = MediaType.APPLICATION_JSON_VALUE)
    public GameRoom bet(@AuthenticationPrincipal User user, @PathVariable String roomId, @RequestBody long betMoney) {

        if(betMoney > 10000){
            return null;
        }

        User currentUser = userRepository.getOne(user.getName());
        return blackjackService.bet(roomId, currentUser, betMoney);
    }

    @PostMapping("/rooms/{roomId}/hit")
    public GameRoom hit(@AuthenticationPrincipal User user, @PathVariable String roomId) {
        User currentUser = userRepository.getOne(user.getName());

        return blackjackService.hit(roomId, currentUser);
    }

    @PostMapping("/rooms/{roomId}/stand")
    public GameRoom stand(@AuthenticationPrincipal User user, @PathVariable String roomId) {
        User currentUser = userRepository.getOne(user.getName());
        return blackjackService.stand(roomId, currentUser);
    }

    @PostMapping("/rooms/{roomId}/doubledown")
    public GameRoom doubledown(@AuthenticationPrincipal User user, @PathVariable String roomId) {

        User currentUser = userRepository.getOne(user.getName());
        return blackjackService.doubledown(roomId, currentUser);
    }

    @PostMapping("/rooms/{roomId}/surrender")
    public GameRoom surrender(@AuthenticationPrincipal User user, @PathVariable String roomId) {

        User currentUser = userRepository.getOne(user.getName());
        return blackjackService.surrender(roomId, currentUser);
    }

    @PutMapping("/rooms/{roomId}/deck/cards")
    public GameRoom addNextCard(@PathVariable String roomId, @RequestBody int rank){
        return blackjackService.addNextCard(roomId, rank);
    }

    @GetMapping("/rooms/{roomId}")
    public GameRoom getGameRoomData(@PathVariable String roomId) {
        return blackjackService.getGameRoom(roomId);
    }
}
