package happyaging.server.dto.user;

import happyaging.server.domain.UserType;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@Getter
public class UserJoinRequestDTO {
    @NotNull(message = "이름은 필수입니다.")
    private String name;

    @NotNull(message = "이메일은 필수입니다.")
    private String email;

    @NotNull(message = "비밀번호는 필수입니다.")
    private String password;

    private String phoneNumber;

    @NotNull(message = "가입자 유형은 필수입니다.")
    private UserType userType;
}
