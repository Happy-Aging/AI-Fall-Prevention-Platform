package happyaging.server.dto.ai;


import java.util.List;
import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class AiServerRequestDTO {
    private String name;
    private List<ResponseInfoDTO> data;
}
