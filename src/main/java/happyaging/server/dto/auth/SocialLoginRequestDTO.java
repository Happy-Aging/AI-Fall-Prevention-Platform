package happyaging.server.dto.auth;

import happyaging.server.domain.user.Vendor;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class SocialLoginRequestDTO {
    @NotEmpty(message = "accessToken은 필수입니다.")
    private String accessToken;

    @NotNull(message = "vendor는 필수입니다.")
    private Vendor vendor;
}
