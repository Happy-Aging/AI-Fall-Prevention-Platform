package happyaging.server.dto.result;

import java.time.LocalDate;
import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class ResultResponseDTO {
    private Long resultId;
    private LocalDate date;
    private Integer rank;
    private Double score;
    private String summary;
}
