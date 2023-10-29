package happyaging.server.controller;

import happyaging.server.domain.User;
import happyaging.server.dto.senior.SeniorRequestDTO;
import happyaging.server.dto.senior.SeniorResponseDTO;
import happyaging.server.service.SeniorService;
import happyaging.server.service.UserService;
import jakarta.validation.Valid;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.GetMapping;
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
            return null;
        }
        String email = authentication.getName();

        System.out.println(email + "님 반가워요");

        User user = userService.getUserByEmail(email);
        seniorService.createSenior(user, seniorRequestDTO);
        return new ResponseEntity<>("create senior success!", HttpStatus.CREATED);
    }

    @GetMapping("/list")
    public List<SeniorResponseDTO> getSeniorList(Authentication authentication) {
        if (authentication == null) {
            return null;
        }
        String email = authentication.getName();
        User user = userService.getUserByEmail(email);
        return seniorService.getSeniorList(user);
    }

}
