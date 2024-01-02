package happyaging.server.controller;

import happyaging.server.dto.user.LoginResponseToken;
import happyaging.server.dto.user.RefreshRequestDTO;
import happyaging.server.dto.user.UserJoinRequestDTO;
import happyaging.server.dto.user.UserLoginRequestDTO;
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
                userJoinRequestDTO.getName());
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
//
//    @GetMapping("/login/kakao")
//    public String handleOAuth2Callback(@RequestParam String code) {
//        // code를 사용하여 카카오로부터 액세스 토큰 요청
//        // 필요한 로직 구현
//        return "인증 코드: " + code;
//    }

}
