package happyaging.server.dto.admin;

import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class ManagerCreateRequestDTO {
    private String email;
    private String password;
    private String phoneNumber;
    private String name;
}
