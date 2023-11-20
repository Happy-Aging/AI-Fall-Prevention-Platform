package happyaging.server.dto.report;

import lombok.Builder;
import lombok.Getter;

@Builder
@Getter
public class ReportResponseDTO {
    private String url;
    private String summary;
}
