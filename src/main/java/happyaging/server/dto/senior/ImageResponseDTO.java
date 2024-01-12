package happyaging.server.dto.senior;

import java.util.List;
import lombok.Builder;
import lombok.Getter;

@Builder
@Getter
public class ImageResponseDTO {
    private String url;
    private String location;
    private List<String> description;
}
