package happyaging.server.domain;

import happyaging.server.dto.senior.SeniorRequestDTO;
import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.DynamicUpdate;

@NoArgsConstructor
@AllArgsConstructor
@DynamicUpdate
@Builder
@Entity
@Getter
@Table(name = "senior")
public class Senior {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "senior_id")
    private Long id;

    @Column(nullable = false)
    private String name;

    @Column(nullable = false)
    private String sex;

    @Column(nullable = false)
    private LocalDate birth;

    @Column(nullable = false)
    private String residence;

    @Column(nullable = false)
    private String address;

    @Column(name = "profile_image")
    private String profile;

    @ManyToOne
    @JoinColumn(name = "user_id")
    private User user;

    @OneToMany(mappedBy = "senior", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private List<Survey> surveyList = new ArrayList<>();

    public void update(SeniorRequestDTO seniorRequestDTO) {
        name = seniorRequestDTO.getName();
        sex = seniorRequestDTO.getSex();
        birth = seniorRequestDTO.getBirth();
        residence = seniorRequestDTO.getResidence();
        address = seniorRequestDTO.getAddress();
        profile = seniorRequestDTO.getProfile();
    }
}
