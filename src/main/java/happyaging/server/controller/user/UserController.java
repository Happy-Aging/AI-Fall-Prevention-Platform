package happyaging.server.controller.user;

import happyaging.server.dto.user.UserInfoDTO;
import happyaging.server.service.user.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("user")
public class UserController {

    private final UserService userService;

    @GetMapping
    public UserInfoDTO readUser() {
        Long userId = userService.readCurrentUserId();
        return userService.findUserInfo(userId);
    }
}
