package happyaging.server.dto.survey;


import java.util.Map;
import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class SurveyResponseDTO {
    private Map<String, QuestionAndAnswerDTO> data;
}
