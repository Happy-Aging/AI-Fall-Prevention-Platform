package happyaging.server.dto.response;

import jakarta.validation.constraints.NotNull;
import java.util.List;
import lombok.Getter;

@Getter
public class ResponseListDTO {
    @NotNull(message = "응답은 필수입니다.")
    private List<ResponseDTO> responseDTOS;
}
