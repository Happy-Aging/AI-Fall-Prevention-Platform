package happyaging.server.dto.user;

import happyaging.server.domain.user.User;
import happyaging.server.domain.user.UserType;
import happyaging.server.domain.user.Vendor;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
@AllArgsConstructor
public class UserInfoDTO {

    private String name;
    private String phoneNumber;
    private UserType userType;
    private Vendor vendor;

    public static UserInfoDTO create(User user) {
        return UserInfoDTO.builder()
                .name(user.getName())
                .phoneNumber(user.getPhoneNumber())
                .userType(user.getUserType())
                .vendor(user.getVendor())
                .build();
    }
}
