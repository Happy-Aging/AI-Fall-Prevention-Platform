package happyaging.server.dto.ai;

import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import java.util.List;
import lombok.Builder;
import lombok.Getter;

@Builder
@Getter
public class AiServerResponseDTO {
    @NotNull(message = "rank는 필수입니다.")
    private Integer rank;

    @NotEmpty(message = "요약은 필수입니다.")
    private String summary;

    @NotEmpty(message = "보고서는 필수입니다.")
    private String report;

    @NotNull(message = "상품 추천은 필수입니다.")
    private List<SolutionDTO> product;
}
