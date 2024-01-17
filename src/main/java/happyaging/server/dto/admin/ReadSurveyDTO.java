package happyaging.server.dto.admin;

import happyaging.server.domain.result.Result;
import happyaging.server.domain.senior.Senior;
import happyaging.server.domain.survey.Survey;
import java.time.LocalDate;
import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class ReadSurveyDTO {
    private Long resultId;
    private Long surveyId;
    private String seniorName;
    private String seniorAddress;
    private LocalDate createDate;
    private Integer rank;

    public static ReadSurveyDTO create(Survey survey, Result result, Senior senior) {
        return ReadSurveyDTO.builder()
                .resultId(result.getId())
                .surveyId(survey.getId())
                .seniorName(senior.getName())
                .seniorAddress(senior.getAddress())
                .createDate(survey.getDate())
                .rank(result.getRank())
                .build();
    }
}
