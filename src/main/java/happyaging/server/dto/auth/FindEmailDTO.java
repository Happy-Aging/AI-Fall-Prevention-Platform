package happyaging.server.dto.auth;

import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class FindEmailDTO {
    @NotNull(message = "이름은 필수입니다.")
    private String name;

    @NotNull(message = "전화번호는 필수입니다.")
    private String phoneNumber;
}
