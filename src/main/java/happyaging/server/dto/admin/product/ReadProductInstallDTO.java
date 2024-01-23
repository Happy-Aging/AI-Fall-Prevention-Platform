package happyaging.server.dto.admin.product;

import happyaging.server.domain.product.InstalledImage;
import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class ReadProductInstallDTO {
    private Long id;
    private String image;

    public static ReadProductInstallDTO create(InstalledImage image) {
        return ReadProductInstallDTO.builder()
                .id(image.getId())
                .image(image.getImage())
                .build();
    }
}
