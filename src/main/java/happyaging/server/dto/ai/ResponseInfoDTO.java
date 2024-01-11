package happyaging.server.dto.ai;

import lombok.Builder;
import lombok.Getter;

@Builder
@Getter
public class ResponseInfoDTO {
    private String question;
    private String answer;
    private Double weight;
}
