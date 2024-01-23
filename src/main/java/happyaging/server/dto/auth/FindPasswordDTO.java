package happyaging.server.dto.auth;

import jakarta.validation.constraints.NotNull;
import lombok.Getter;

@Getter
public class FindPasswordDTO {
    @NotNull(message = "email은 필수입니다.")
    private String email;
}
