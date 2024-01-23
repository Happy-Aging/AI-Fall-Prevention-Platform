package happyaging.server.domain.survey;

import happyaging.server.dto.ai.AiServerResponseDTO;
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

    public static Result create(Survey survey, AiServerResponseDTO aiServerResponseDTO) {
        return Result.builder()
                .rank(aiServerResponseDTO.getRank())
                .summary(aiServerResponseDTO.getSummary())
                .report(aiServerResponseDTO.getReport())
                .survey(survey)
                .build();
    }
}
