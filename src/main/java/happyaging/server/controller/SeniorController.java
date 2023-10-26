package happyaging.server.controller;

import happyaging.server.domain.User;
import happyaging.server.dto.SeniorRequestDTO;
import happyaging.server.service.SeniorService;
import happyaging.server.service.UserService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("senior")
public class SeniorController {
    private final UserService userService;
    private final SeniorService seniorService;

    @PostMapping
    public ResponseEntity<Object> createSenior(@RequestBody @Valid SeniorRequestDTO seniorRequestDTO) {
        // TODO 로그인 기능 구현 후 token 분리하여 accountID 얻기

        User user = userService.getUserById(1L);
        seniorService.createSenior(user, seniorRequestDTO);
        return new ResponseEntity<>("create senior success!", HttpStatus.CREATED);
    }
}
