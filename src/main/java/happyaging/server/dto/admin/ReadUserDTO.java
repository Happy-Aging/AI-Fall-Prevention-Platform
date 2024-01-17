package happyaging.server.dto.admin;

import happyaging.server.domain.user.User;
import java.time.LocalDate;
import lombok.Builder;
import lombok.Getter;

@Builder
@Getter
public class ReadUserDTO {
    private Long id;
    private String name;
    private String email;
    private String phoneNumber;
    private LocalDate createDate;

    public static ReadUserDTO create(User user) {
        return ReadUserDTO.builder()
                .id(user.getId())
                .name(user.getName())
                .email(user.getEmail())
                .phoneNumber(user.getPhoneNumber())
                .createDate(user.getCreatedAt())
                .build();
    }
}
