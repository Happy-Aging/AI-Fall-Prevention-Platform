package happyaging.server.dto.admin.user;

import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class CreateManagerDTO {
    @NotNull(message = "email은 필수입니다.")
    private String email;
    private String password;

    @NotNull(message = "휴대전화 번호는 필수입니다.")
    private String phoneNumber;

    @NotNull(message = "이름은 필수입니다.")
    private String name;
}
