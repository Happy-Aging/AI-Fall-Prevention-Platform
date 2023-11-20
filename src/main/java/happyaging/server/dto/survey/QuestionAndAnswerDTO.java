package happyaging.server.dto.survey;

import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class QuestionAndAnswerDTO {
    private String question;
    private String answer;
}
