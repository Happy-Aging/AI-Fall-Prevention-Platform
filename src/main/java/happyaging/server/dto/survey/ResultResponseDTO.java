package happyaging.server.dto.survey;

import happyaging.server.domain.survey.Result;
import happyaging.server.domain.survey.Survey;
import java.time.LocalDate;
import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class ResultResponseDTO {
    private Long resultId;
    private LocalDate date;
    private Integer rank;
    private String summary;

    public static ResultResponseDTO create(Survey survey, Result result) {
        return ResultResponseDTO.builder()
                .resultId(result.getId())
                .date(survey.getDate())
                .rank(result.getRank())
                .summary(result.getSummary())
                .build();
    }
}
