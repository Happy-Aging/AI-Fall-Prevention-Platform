package happyaging.server.dto.auth;

import happyaging.server.domain.user.User;
import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class ReadEmailDTO {
    private String email;

    public static ReadEmailDTO create(User user) {
        return ReadEmailDTO.builder()
                .email(user.getEmail())
                .build();
    }
}
