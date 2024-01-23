package happyaging.server.dto.admin.image;

import happyaging.server.domain.image.Location;
import jakarta.validation.constraints.NotNull;
import java.util.List;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class UpdateExampleImageDTO {
    @NotNull(message = "location 지정은 필수입니다.")
    private Location location;

    @NotNull(message = "사진 설명은 필수입니다.")
    private List<String> description;
}
