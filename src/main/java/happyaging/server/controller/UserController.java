package happyaging.server.controller;

import happyaging.server.dto.LoginResponseToken;
import happyaging.server.dto.RefreshRequestDTO;
import happyaging.server.dto.UserJoinRequestDTO;
import happyaging.server.dto.UserLoginRequestDTO;
import happyaging.server.service.UserService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("user")
public class UserController {

    private final UserService userService;

    @PostMapping("/join")
    public ResponseEntity<String> join(@RequestBody @Valid UserJoinRequestDTO userJoinRequestDTO) {
        userService.join(userJoinRequestDTO.getEmail(), userJoinRequestDTO.getPassword(),
                userJoinRequestDTO.getNickname());
        return ResponseEntity.ok().body("회원가입이 성공했습니다");
    }

    @PostMapping("/login")
    public ResponseEntity<LoginResponseToken> login(@RequestBody @Valid UserLoginRequestDTO userLoginRequestDTO) {
        LoginResponseToken token = userService.login(userLoginRequestDTO.getEmail(), userLoginRequestDTO.getPassword());
        return ResponseEntity.ok().body(token);
    }

    @PostMapping("/refreshToken")
    public ResponseEntity<LoginResponseToken> refreshToken(@RequestBody RefreshRequestDTO refreshRequestDTO) {
        LoginResponseToken token = userService.refresh(refreshRequestDTO.getRefreshToken());
        return ResponseEntity.ok().body(token);
    }

}
