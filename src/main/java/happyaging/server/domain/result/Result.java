package happyaging.server.domain.result;

import happyaging.server.domain.survey.Survey;
import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.OneToOne;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@AllArgsConstructor
@Builder
@Entity
@Getter
public class Result {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "result_id")
    private Long id;

    @Column(nullable = false)
    private Integer rank;

    @Column(length = 500, nullable = false)
    private String summary;

    @Column(nullable = false)
    private String report;

    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "survey_id")
    private Survey survey;

    public static Result create(Survey survey) {
        return Result.builder()
                .rank(1)
                .summary("500자 정도 하면 될까요? test")
                .report("test.pdf")
                .survey(survey)
                .build();
    }
}
