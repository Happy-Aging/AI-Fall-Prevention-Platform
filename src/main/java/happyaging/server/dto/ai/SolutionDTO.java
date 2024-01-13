package happyaging.server.dto.ai;

import happyaging.server.domain.image.Location;
import jakarta.validation.constraints.NotNull;
import lombok.Builder;
import lombok.Getter;

@Builder
@Getter
public class SolutionDTO {
    @NotNull(message = "product id는 필수입니다.")
    private Long id;

    @NotNull(message = "올바른 장소를 입력해주세요.")
    private Location location;
}
