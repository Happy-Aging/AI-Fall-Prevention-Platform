package happyaging.server.controller.auth;

import happyaging.server.dto.auth.LoginSuccessDTO;
import happyaging.server.dto.auth.SocialLoginRequestDTO;
import happyaging.server.dto.user.RefreshRequestDTO;
import happyaging.server.dto.user.UserJoinRequestDTO;
import happyaging.server.dto.user.UserLoginRequestDTO;
import happyaging.server.service.auth.AuthService;
import happyaging.server.service.user.UserService;
import happyaging.server.exception.SuccessCode;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("auth")
public class AuthController {

    private final AuthService authService;
    private final UserService userService;

    @PostMapping("/login/social")
    public ResponseEntity<?> socialLogin(
            @RequestBody @Valid SocialLoginRequestDTO socialLoginRequestDTO) {
        String email = authService.requestEmail(socialLoginRequestDTO);
        return authService.login(email, socialLoginRequestDTO.getVendor());
    }

    @PostMapping("/login")
    public ResponseEntity<LoginSuccessDTO> login(@RequestBody @Valid UserLoginRequestDTO userLoginRequestDTO) {
        LoginSuccessDTO token = userService.login(userLoginRequestDTO.getEmail(), userLoginRequestDTO.getPassword());
        return ResponseEntity.ok().body(token);
    }

    @PostMapping("/join")
    public ResponseEntity<String> join(@RequestBody @Valid UserJoinRequestDTO userJoinRequestDTO) {
        userService.checkDuplicateEmail(userJoinRequestDTO.getEmail());
        userService.join(userJoinRequestDTO);
        return ResponseEntity.status(SuccessCode.JOIN.getHttpStatus())
                .body(SuccessCode.JOIN.getMessage());
    }

    @PostMapping("/refreshToken")
    public ResponseEntity<LoginSuccessDTO> refreshToken(@RequestBody RefreshRequestDTO refreshRequestDTO) {
        LoginSuccessDTO token = userService.refresh(refreshRequestDTO.getRefreshToken());
        return ResponseEntity.ok().body(token);
    }
}
