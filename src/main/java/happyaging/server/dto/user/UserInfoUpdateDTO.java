package happyaging.server.dto.user;

import jakarta.validation.constraints.NotEmpty;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class UserInfoUpdateDTO {
    @NotEmpty(message = "이름은 필수입니다.")
    private String name;

    @NotEmpty(message = "전화번호는 필수입니다.")
    private String phoneNumber;

    private String password;
}
