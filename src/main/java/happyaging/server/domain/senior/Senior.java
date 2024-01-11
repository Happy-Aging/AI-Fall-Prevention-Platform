package happyaging.server.domain.senior;

import happyaging.server.domain.user.User;
import happyaging.server.dto.senior.SeniorRequestDTO;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import java.time.LocalDate;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@AllArgsConstructor
@Builder
@Entity
@Getter
public class Senior {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "senior_id")
    private Long id;

    @Column
    private String name;

    @Column(nullable = false)
    private String address;

    @Column(nullable = false)
    private LocalDate birth;

    @Column(nullable = false)
    @Enumerated(EnumType.STRING)
    private Sex sex;

    private String phoneNumber;

    @Column(nullable = false)
    @Enumerated(EnumType.STRING)
    private Relation relation;

    private Integer latestSurveyRank;

    @ManyToOne
    @JoinColumn(name = "user_id")
    private User user;

    public static Senior create(User user, SeniorRequestDTO seniorRequestDTO) {
        return Senior.builder()
                .name(seniorRequestDTO.getName())
                .address(seniorRequestDTO.getAddress())
                .birth(seniorRequestDTO.getBirth())
                .phoneNumber(seniorRequestDTO.getPhoneNumber())
                .relation(seniorRequestDTO.getRelation())
                .sex(seniorRequestDTO.getSex())
                .latestSurveyRank(null)
                .user(user)
                .build();
    }

    public void update(SeniorRequestDTO seniorRequestDTO) {
        this.name = seniorRequestDTO.getName();
        this.address = seniorRequestDTO.getAddress();
        this.birth = seniorRequestDTO.getBirth();
        this.phoneNumber = seniorRequestDTO.getPhoneNumber();
        this.relation = seniorRequestDTO.getRelation();
    }

    public void delete() {
        this.name = null;
        this.phoneNumber = null;
        this.relation = Relation.NOTHING;
        this.user = null;
    }

    public void updateRank(Integer rank) {
        this.latestSurveyRank = rank;
    }
}
