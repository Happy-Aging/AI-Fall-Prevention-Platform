package happyaging.server.dto.user;

import happyaging.server.domain.UserType;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public class UserJoinRequestDTO {
    @NotNull(message = "user명은 필수입니다.")
    private String name;

    @NotNull(message = "email은 필수입니다.")
    private String email;

    @NotNull(message = "password는 필수입니다.")
    private String password;

    private String phoneNumber;

    @NotNull(message = "가입자 유형은 필수입니다.")
    private UserType userType;
}
