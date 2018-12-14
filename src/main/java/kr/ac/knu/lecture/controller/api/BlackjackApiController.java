package kr.ac.knu.lecture.controller.api;

import kr.ac.knu.lecture.domain.User;
import kr.ac.knu.lecture.game.blackjack.GameRoom;
import kr.ac.knu.lecture.repository.UserRepository;
import kr.ac.knu.lecture.service.BlackjackService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

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

    @PostMapping("/rooms")
    public GameRoom createRoom(@AuthenticationPrincipal User user) {
        User currentUser = userRepository.getOne(user.getName());
        return blackjackService.createGameRoom(currentUser);
    } // room에 들어서면, 받은 유저 정보를 바탕으로 게임룸을 생성한다.

    @PostMapping(value = "/rooms/{roomId}/bet", consumes = MediaType.APPLICATION_JSON_VALUE)
    public GameRoom bet(@AuthenticationPrincipal User user, @PathVariable String roomId, @RequestBody long betMoney) {
        User currentUser = userRepository.getOne(user.getName());
        return blackjackService.bet(roomId, currentUser, betMoney);
    } // bet에 들어서면, bet 서비스를 실행한다.

    @PostMapping("/rooms/{roomId}/hit")
    public GameRoom hit(@AuthenticationPrincipal User user, @PathVariable String roomId) {
        User currentUser = userRepository.getOne(user.getName());

        return blackjackService.hit(roomId, currentUser);
    } // hit에 들어서면 hit 서비스를 실행한다.

    @PostMapping("/rooms/{roomId}/stand")
    public GameRoom stand(@AuthenticationPrincipal User user, @PathVariable String roomId) {
        User currentUser = userRepository.getOne(user.getName());
        return blackjackService.stand(roomId, currentUser);
    } // stand에 들어서면 stand 서비스를 실행한다.

    @PostMapping("/rooms/{roomId}/doubleDown")
    public GameRoom doubledown(@AuthenticationPrincipal User user, @PathVariable String roomId) {

        User currentUser = userRepository.getOne(user.getName());
        return blackjackService.doubleDown(roomId, currentUser);
    } // doubleDown에 들어서면 doubleDown 서비스를 실행한다.

    @PutMapping("/rooms/{roomId}/deck/cards")
    public GameRoom addNextCard(@PathVariable String roomId, @RequestBody int rank){
        return blackjackService.addNextCard(roomId, rank);
    } // deck/card에 들어오면 덱에 다음 카드를 추가한다.

    @GetMapping("/rooms/{roomId}")
    public GameRoom getGameRoomData(@PathVariable String roomId) {
        return blackjackService.getGameRoom(roomId);
    }
}
