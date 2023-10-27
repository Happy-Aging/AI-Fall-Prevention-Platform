package happyaging.server.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

@Builder
@Data
@AllArgsConstructor
public class LoginResponseToken {
    private String grantType;
    private String accessToken;
    private String refreshToken;
}
