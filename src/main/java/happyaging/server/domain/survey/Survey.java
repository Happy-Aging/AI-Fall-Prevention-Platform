package happyaging.server.domain.survey;

import happyaging.server.domain.senior.Senior;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import java.time.LocalDateTime;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@AllArgsConstructor
@Entity
@Builder
@Getter
public class Survey {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "survey_id")
    private Long id;

    @Column(nullable = false)
    private LocalDateTime date;

    @ManyToOne
    @JoinColumn(name = "senior_id")
    private Senior senior;

    public static Survey create(Senior senior) {
        return Survey.builder()
                .date(LocalDateTime.now())
                .senior(senior)
                .build();
    }
}
