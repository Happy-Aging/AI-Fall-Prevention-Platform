package happyaging.server.dto.auth;

import happyaging.server.domain.user.Vendor;
import lombok.AllArgsConstructor;

@AllArgsConstructor
public class LoginFailureDTO {
    private String email;
    private Vendor vendor;
}
