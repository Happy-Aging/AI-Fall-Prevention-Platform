package happyaging.server.controller.auth;

import happyaging.server.dto.auth.JoinRequestDTO;
import happyaging.server.dto.auth.LoginRequestDTO;
import happyaging.server.dto.auth.LoginSuccessDTO;
import happyaging.server.dto.auth.SocialJoinRequestDTO;
import happyaging.server.dto.auth.SocialLoginRequestDTO;
import happyaging.server.service.auth.AuthService;
import happyaging.server.service.user.UserService;
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
        return authService.socialLogin(socialLoginRequestDTO);
    }

    @PostMapping("/login")
    public LoginSuccessDTO login(@RequestBody @Valid LoginRequestDTO loginRequestDTO) {
        return authService.login(loginRequestDTO.getEmail(), loginRequestDTO.getPassword());
    }

    @PostMapping("/join")
    public LoginSuccessDTO join(@RequestBody @Valid JoinRequestDTO joinRequestDTO) {
        return authService.join(joinRequestDTO);
    }

    @PostMapping("/join/social")
    public LoginSuccessDTO join(@RequestBody @Valid SocialJoinRequestDTO socialJoinRequestDTO) {
        return authService.socialJoin(socialJoinRequestDTO);
    }

//    @PostMapping("/refreshToken")
//    public ResponseEntity<LoginSuccessDTO> refreshToken(@RequestBody RefreshRequestDTO refreshRequestDTO) {
//        LoginSuccessDTO token = userService.refresh(refreshRequestDTO.getRefreshToken());
//        return ResponseEntity.ok().body(token);
//    }
}
