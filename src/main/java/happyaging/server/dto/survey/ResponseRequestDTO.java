package happyaging.server.dto.survey;

import jakarta.validation.constraints.NotNull;
import java.util.List;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class ResponseRequestDTO {
    @NotNull(message = "questionId는 필수입니다.")
    private Long questionId;

    private Long choiceId;
    private List<Long> multiId;
    private String subjectiveResponse;
}