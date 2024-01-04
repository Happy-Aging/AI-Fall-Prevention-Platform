package happyaging.server.dto.auth;

import lombok.AllArgsConstructor;

@AllArgsConstructor
public class LoginSuccessDTO {
    private String accessToken;
    private String refreshToken;
}
