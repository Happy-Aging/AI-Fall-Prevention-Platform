package happyaging.server.dto.admin.user;

import happyaging.server.domain.user.User;
import lombok.Builder;
import lombok.Getter;

@Builder
@Getter
public class ReadManagerDTO {
    private Long id;
    private String email;
    private String phoneNumber;
    private String name;

    public static ReadManagerDTO create(User user) {
        return ReadManagerDTO.builder()
                .id(user.getId())
                .email(user.getEmail())
                .phoneNumber(user.getPhoneNumber())
                .name(user.getName())
                .build();
    }
}
