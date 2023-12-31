package happyaging.server.domain.user;

import happyaging.server.dto.auth.JoinRequestDTO;
import happyaging.server.dto.auth.SocialJoinRequestDTO;
import happyaging.server.dto.user.UserInfoUpdateDTO;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import java.time.LocalDate;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

@NoArgsConstructor
@AllArgsConstructor
@Entity
@Builder
@Getter
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "user_id")
    private Long id;

    @Column(nullable = false)
    private String name;

    @Column(nullable = false)
    private String email;

    private String password;

    @Column(nullable = false)
    private String phoneNumber;

    @Column(nullable = false)
    @Enumerated(EnumType.STRING)
    private UserType userType;

    @Column(nullable = false)
    @Enumerated(EnumType.STRING)
    private Vendor vendor;

    @Column(nullable = false)
    private LocalDate createdAt;

    public static User createFromJoin(JoinRequestDTO userJoinRequestDTO, BCryptPasswordEncoder encoder) {
        return User.builder()
                .name(userJoinRequestDTO.getName())
                .email(userJoinRequestDTO.getEmail())
                .password(encoder.encode(userJoinRequestDTO.getPassword()))
                .phoneNumber(userJoinRequestDTO.getPhoneNumber())
                .userType(userJoinRequestDTO.getUserType())
                .vendor(userJoinRequestDTO.getVendor())
                .createdAt(LocalDate.now())
                .build();
    }

    public static User createFromSocialJoin(SocialJoinRequestDTO socialJoinRequestDTO) {
        return User.builder()
                .name(socialJoinRequestDTO.getName())
                .email(socialJoinRequestDTO.getEmail())
                .password(null)
                .phoneNumber(socialJoinRequestDTO.getPhoneNumber())
                .userType(socialJoinRequestDTO.getUserType())
                .vendor(socialJoinRequestDTO.getVendor())
                .createdAt(LocalDate.now())
                .build();
    }

    public void update(UserInfoUpdateDTO userInfoUpdateDTO, BCryptPasswordEncoder encoder) {
        this.name = userInfoUpdateDTO.getName();
        this.phoneNumber = userInfoUpdateDTO.getPhoneNumber();
        this.userType = userInfoUpdateDTO.getUserType();

        String password = userInfoUpdateDTO.getPassword();
        this.password = password == null ? null : encoder.encode(password);
    }
}
