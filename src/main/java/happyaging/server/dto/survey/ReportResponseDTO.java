package happyaging.server.dto.survey;

import lombok.Builder;
import lombok.Getter;

@Builder
@Getter
public class ReportResponseDTO {
    private String url;
    private String summary;
}
