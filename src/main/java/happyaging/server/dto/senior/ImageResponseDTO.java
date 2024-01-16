package happyaging.server.dto.senior;

import happyaging.server.domain.image.ExampleImage;
import java.util.Arrays;
import java.util.List;
import lombok.Builder;
import lombok.Getter;

@Builder
@Getter
public class ImageResponseDTO {
    private String image;
    private String location;
    private List<String> description;

    public static ImageResponseDTO create(ExampleImage exampleImage) {
        return ImageResponseDTO.builder()
                .image(exampleImage.getImage())
                .location(exampleImage.getLocation().getName())
                .description(Arrays.stream(exampleImage.getDescription().split(" \\| ")).toList())
                .build();
    }
}
