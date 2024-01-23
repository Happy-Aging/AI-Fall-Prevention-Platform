package happyaging.server.dto.admin.survey;

import happyaging.server.domain.question.QuestionType;
import happyaging.server.domain.question.ResponseType;
import jakarta.validation.constraints.NotNull;
import java.util.List;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class CreateQuestionDTO {

    @NotNull(message = "문항번호는 필수입니다.")
    private String number;

    @NotNull(message = "질문 내용은 필수입니다.")
    private String content;

    @NotNull(message = "질문 종류는 필수입니다.")
    private QuestionType questionType;

    @NotNull(message = "응답 유형은 필수입니다.")
    private ResponseType responseType;

    private List<CreateOptionDTO> options;
}
