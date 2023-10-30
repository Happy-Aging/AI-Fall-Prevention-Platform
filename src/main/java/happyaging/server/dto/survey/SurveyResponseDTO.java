package happyaging.server.dto.survey;

import java.time.LocalDate;
import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class SurveyResponseDTO {
    private Long resultId;
    private LocalDate date;
    private Integer rank;
    private Double score;
    private String summary;
}
