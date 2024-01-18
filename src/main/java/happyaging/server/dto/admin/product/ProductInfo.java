package happyaging.server.dto.admin.product;

import happyaging.server.domain.product.InstalledImage;
import happyaging.server.domain.product.Product;
import java.util.List;
import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class ProductInfo {
    private Long productId;
    private String name;
    private String description;
    private String image;
    private List<Long> installId;

    public static ProductInfo create(Product product, List<InstalledImage> installImages) {
        List<Long> installId = installImages.stream()
                .map(InstalledImage::getId)
                .toList();
        return ProductInfo.builder()
                .productId(product.getId())
                .name(product.getName())
                .description(product.getDescription())
                .image(product.getImage())
                .installId(installId)
                .build();
    }
}
