package happyaging.server.dto;

import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public class UserJoinRequestDTO {
    @NotNull(message = "email은 필수입니다.")
    private String email;

    @NotNull(message = "password는 필수입니다.")
    private String password;

    @NotNull(message = "user명은 필수입니다.")
    private String nickname;
}
