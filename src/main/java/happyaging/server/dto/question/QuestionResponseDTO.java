package happyaging.server.dto.question;

import lombok.Builder;

@Builder
public class QuestionResponseDTO {
    private Long id;
    private String number;
    private String question;
    private String type;
}
