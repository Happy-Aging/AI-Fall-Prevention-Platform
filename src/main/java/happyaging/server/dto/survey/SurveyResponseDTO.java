package happyaging.server.dto.survey;

import happyaging.server.domain.question.Question;
import happyaging.server.domain.question.ResponseType;
import java.util.List;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

@AllArgsConstructor
@Getter
@Builder
public class SurveyResponseDTO {
    private Long questionId;
    private String number;
    private String content;
    private String questionType;
    private ResponseType responseType;
    private List<OptionDTO> options;

    public static SurveyResponseDTO create(Question question, List<OptionDTO> optionDTOS) {
        return SurveyResponseDTO.builder()
                .questionId(question.getId())
                .number(question.getNumber())
                .content(question.getContent())
                .questionType(question.getQuestionType().getType())
                .responseType(question.getResponseType())
                .options(optionDTOS)
                .build();
    }
}
