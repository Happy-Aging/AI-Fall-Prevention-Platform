package happyaging.server.dto.auth;

import jakarta.validation.constraints.NotEmpty;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class LoginRequestDTO {
    @NotEmpty(message = "email은 필수입니다.")
    private String email;

    @NotEmpty(message = "password는 필수입니다.")
    private String password;
}
