package happyaging.server.dto.user;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public class UserLoginRequestDTO {
    private String email;
    private String password;
}
