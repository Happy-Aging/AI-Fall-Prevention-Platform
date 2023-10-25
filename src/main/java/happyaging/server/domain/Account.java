package happyaging.server.domain;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.DynamicUpdate;

@NoArgsConstructor
@AllArgsConstructor
@DynamicUpdate
@Entity
@Getter
@Table(name = "account")
public class Account {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "account_id")
    private Long id;

    @Column
    private String email;

    @Column
    private String password;

    @Column
    private String socialId;

    @Column
    private String socialProvider;

    @Column(nullable = false)
    private String nickname;

    @Column(nullable = false)
    private LocalDate createdAt;

    @Column(name = "is_manager", nullable = false)
    private boolean isManager;

    @Column(name = "profile_image")
    private String profile;
    
    @OneToMany(mappedBy = "account", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private List<Senior> seniorList = new ArrayList<>();
}
