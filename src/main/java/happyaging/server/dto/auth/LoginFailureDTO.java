package happyaging.server.dto.auth;

import happyaging.server.domain.user.Vendor;
import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public class LoginFailureDTO {
    private String email;
    private Vendor vendor;
}
