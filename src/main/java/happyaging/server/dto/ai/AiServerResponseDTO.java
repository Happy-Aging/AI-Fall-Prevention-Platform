package happyaging.server.dto.ai;

import java.util.List;
import lombok.Builder;
import lombok.Getter;

@Builder
@Getter
public class AiServerResponseDTO {
    private Integer rank;
    private String summary;
    private String report;
    private List<Long> solutionId;
}
