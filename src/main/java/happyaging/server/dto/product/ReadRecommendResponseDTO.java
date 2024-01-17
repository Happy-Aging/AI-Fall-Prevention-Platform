package happyaging.server.dto.product;

import java.util.List;
import lombok.Builder;
import lombok.Getter;

@Builder
@Getter
public class ReadRecommendResponseDTO {
    private String name;
    private String description;
    private String image;
    private List<String> installedImage;

    public static ReadRecommendResponseDTO create(String name, String description, String image,
                                                  List<String> installedImages) {
        return ReadRecommendResponseDTO.builder()
                .name(name)
                .description(description)
                .image(image)
                .installedImage(installedImages)
                .build();
    }
}
