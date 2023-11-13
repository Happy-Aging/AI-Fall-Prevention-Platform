package happyaging.server.dto.question;

import lombok.Builder;
import lombok.Getter;

@Builder
@Getter
public class QuestionResponseDTO {
    private String number;
    private String question;
    private String type;
}
