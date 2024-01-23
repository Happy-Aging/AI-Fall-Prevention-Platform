package happyaging.server.repository.product;

import happyaging.server.domain.product.InstalledImage;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;

public interface InstalledImageRepository extends JpaRepository<InstalledImage, Long> {

    List<InstalledImage> findAllByProductId(Long id);
}
