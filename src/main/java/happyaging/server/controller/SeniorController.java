package happyaging.server.controller;

import happyaging.server.domain.User;
import happyaging.server.dto.SeniorRequestDTO;
import happyaging.server.service.SeniorService;
import happyaging.server.service.UserService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
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
    public ResponseEntity<Object> createSenior(Authentication authentication,
                                               @RequestBody @Valid SeniorRequestDTO seniorRequestDTO) {
        if (authentication == null) {
            //TODO 허용되지 않은 접근이라는 에러
        }
        String email = authentication.getName();

        System.out.println(email + "님 반가워요");

        User user = userService.getUserByEmail(email);
        seniorService.createSenior(user, seniorRequestDTO);
        return new ResponseEntity<>("create senior success!", HttpStatus.CREATED);
    }
}
