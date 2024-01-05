package happyaging.server.domain.user;

import happyaging.server.domain.senior.Senior;
import happyaging.server.dto.auth.JoinRequestDTO;
import happyaging.server.dto.auth.SocialJoinRequestDTO;
import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
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

    // TODO: 지울 예정
    @OneToMany(mappedBy = "user", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private List<Senior> seniorList = new ArrayList<>();

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
}
