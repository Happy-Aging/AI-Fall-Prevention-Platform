package happyaging.server.dto;

import happyaging.server.domain.Vendor;
import lombok.AllArgsConstructor;

@AllArgsConstructor
public class LoginFailureDTO {
    private String email;
    private Vendor vendor;
}
