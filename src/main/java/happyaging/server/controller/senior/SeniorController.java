package happyaging.server.controller.senior;

import happyaging.server.dto.senior.SeniorRequestDTO;
import happyaging.server.service.senior.SeniorService;
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
@RequestMapping("senior")
public class SeniorController {
    private final UserService userService;
    private final SeniorService seniorService;

    @PostMapping
    public ResponseEntity<Object> createSenior(@RequestBody @Valid SeniorRequestDTO seniorRequestDTO) {
        Long userId = userService.readCurrentUserId();
        seniorService.createSenior(userId, seniorRequestDTO);
        return ResponseEntity.ok().build();
    }

//    @GetMapping("/list")
//    public List<SeniorResponseDTO> getSeniorList(Authentication authentication) {
//        if (authentication == null) {
//            return null;
//        }
//        String email = authentication.getName();
//        User user = userService.getUserByEmail(email);
//        return seniorService.getSeniorList(user);
//    }
//
//    @PutMapping("/{seniorId}")
//    public ResponseEntity<Object> updateSenior(Authentication authentication,
//                                               @PathVariable Long seniorId,
//                                               @RequestBody @Valid SeniorRequestDTO seniorRequestDTO) {
//        if (authentication == null) {
//            return null;
//        }
//        seniorService.updateSenior(seniorId, seniorRequestDTO);
//        return new ResponseEntity<>("update senior success!", HttpStatus.OK);
//    }
//
//    @DeleteMapping("/{seniorId}")
//    public ResponseEntity<Object> deleteSenior(Authentication authentication, @PathVariable Long seniorId) {
//        if (authentication == null) {
//            return null;
//        }
//        seniorService.deleteSenior(seniorId);
//        return new ResponseEntity<>("delete senior success!", HttpStatus.OK);
//    }
}
