package happyaging.server.dto.admin;

import happyaging.server.domain.user.User;
import lombok.Builder;

@Builder
public class ManagerReadResponseDTO {
    private Long id;
    private String email;
    private String phoneNumber;
    private String name;

    public static ManagerReadResponseDTO create(User user) {
        return ManagerReadResponseDTO.builder()
                .id(user.getId())
                .email(user.getEmail())
                .phoneNumber(user.getPhoneNumber())
                .name(user.getName())
                .build();
    }
}
