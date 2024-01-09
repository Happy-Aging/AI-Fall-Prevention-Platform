package happyaging.server.dto.survey;


import java.util.Map;
import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class ResultDTO {
    private String name;
    private Integer rank;
    private Double totalScore;
    private Map<String, SurveyResponseDTO> data;
}
