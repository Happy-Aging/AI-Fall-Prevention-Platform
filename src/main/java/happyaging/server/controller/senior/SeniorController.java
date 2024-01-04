package happyaging.server.controller.senior;

import happyaging.server.domain.user.User;
import happyaging.server.dto.senior.SeniorRequestDTO;
import happyaging.server.dto.senior.SeniorResponseDTO;
import happyaging.server.service.senior.SeniorService;
import happyaging.server.service.user.UserService;
import jakarta.validation.Valid;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("senior")
public class SeniorController {
    private final UserService userService;
    private final SeniorService seniorService;

//    @PostMapping
//    public ResponseEntity<Object> createSenior(Authentication authentication,
//                                               @RequestBody @Valid SeniorRequestDTO seniorRequestDTO) {
//        if (authentication == null) {
//            return null;
//        }
//        String email = authentication.getName();
//
//        System.out.println(email + "님 반가워요");
//
//        User user = userService.getUserByEmail(email);
//        seniorService.createSenior(user, seniorRequestDTO);
//        return new ResponseEntity<>("create senior success!", HttpStatus.CREATED);
//    }

    @PostMapping
    public SeniorResponseDTO createSenior(@RequestBody @Valid SeniorRequestDTO seniorRequestDTO) {
        return seniorService.createSenior(seniorRequestDTO);
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

    @PutMapping("/{seniorId}")
    public ResponseEntity<Object> updateSenior(Authentication authentication,
                                               @PathVariable Long seniorId,
                                               @RequestBody @Valid SeniorRequestDTO seniorRequestDTO) {
        if (authentication == null) {
            return null;
        }
        seniorService.updateSenior(seniorId, seniorRequestDTO);
        return new ResponseEntity<>("update senior success!", HttpStatus.OK);
    }

    @DeleteMapping("/{seniorId}")
    public ResponseEntity<Object> deleteSenior(Authentication authentication, @PathVariable Long seniorId) {
        if (authentication == null) {
            return null;
        }
        seniorService.deleteSenior(seniorId);
        return new ResponseEntity<>("delete senior success!", HttpStatus.OK);
    }
}
