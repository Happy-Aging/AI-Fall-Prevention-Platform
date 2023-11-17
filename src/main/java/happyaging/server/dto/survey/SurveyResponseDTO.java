package happyaging.server.dto.survey;


import java.util.Map;
import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class SurveyResponseDTO {
    private String name;
    private Integer rank;
    private Map<String, QuestionAndAnswerDTO> data;
}
