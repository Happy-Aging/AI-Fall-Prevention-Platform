package happyaging.server.dto.response;

import jakarta.validation.constraints.NotNull;
import lombok.Getter;

@Getter
public class ResponseDTO {
    @NotNull(message = "questionId는 필수입니다.")
    private Long questionId;

    @NotNull(message = "response는 필수입니다.")
    private String response;
}
