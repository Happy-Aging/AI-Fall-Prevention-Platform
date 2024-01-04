package happyaging.server.dto.auth;

import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class LoginRequestDTO {
    @NotNull(message = "email은 필수입니다.")
    private String email;

    @NotNull(message = "password는 필수입니다.")
    private String password;
}
