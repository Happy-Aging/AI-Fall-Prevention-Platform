package happyaging.server.dto.result;


import happyaging.server.dto.survey.SurveyResponseDTO;
import java.util.Map;
import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class ResultRequestDTO {
    private String name;
    private Integer rank;
    private Double totalScore;
    private Map<String, SurveyResponseDTO> data;
}
