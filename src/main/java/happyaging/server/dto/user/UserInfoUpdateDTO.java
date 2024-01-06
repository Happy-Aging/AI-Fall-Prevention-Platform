package happyaging.server.dto.user;

import happyaging.server.domain.user.UserType;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class UserInfoUpdateDTO {
    @NotEmpty(message = "이름은 필수입니다.")
    private String name;

    @NotEmpty(message = "전화번호는 필수입니다.")
    private String phoneNumber;

    @NotNull(message = "가입자 유형은 필수입니다.")
    private UserType userType;

    private String password;
}
