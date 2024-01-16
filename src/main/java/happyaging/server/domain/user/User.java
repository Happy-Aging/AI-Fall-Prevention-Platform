package happyaging.server.domain.user;

import happyaging.server.dto.admin.ManagerCreateRequestDTO;
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
import jakarta.persistence.Table;
import jakarta.persistence.UniqueConstraint;
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
@Table(uniqueConstraints = {@UniqueConstraint(name = "EMAIL_UNIQUE", columnNames = {"email"})})
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
                .userType(UserType.USER)
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
                .userType(UserType.USER)
                .vendor(socialJoinRequestDTO.getVendor())
                .createdAt(LocalDate.now())
                .build();
    }

    public static User createManager(String email, String password, String name, String phoneNumber) {
        return User.builder()
                .name(name)
                .email(email)
                .password(password)
                .phoneNumber(phoneNumber)
                .userType(UserType.MANAGER)
                .vendor(Vendor.HAPPY_AGING)
                .build();
    }

    public void update(UserInfoUpdateDTO userInfoUpdateDTO, BCryptPasswordEncoder encoder) {
        this.name = userInfoUpdateDTO.getName();
        this.phoneNumber = userInfoUpdateDTO.getPhoneNumber();

        String password = userInfoUpdateDTO.getPassword();
        if (password != null) {
            this.password = encoder.encode(password);
        }
    }


    public void updateManager(ManagerCreateRequestDTO managerCreateRequestDTO, BCryptPasswordEncoder encoder) {
        this.email = managerCreateRequestDTO.getEmail();

        String password = managerCreateRequestDTO.getPassword();
        this.password = password == null ? null : encoder.encode(password);
        this.name = managerCreateRequestDTO.getName();
        this.phoneNumber = managerCreateRequestDTO.getPhoneNumber();
    }
}
