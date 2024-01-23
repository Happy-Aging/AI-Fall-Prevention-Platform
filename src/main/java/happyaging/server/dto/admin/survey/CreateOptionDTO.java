package happyaging.server.dto.admin.survey;

import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class CreateOptionDTO {
    @NotNull(message = "옵션 내용은 필수입니다.")
    private String content;
}
