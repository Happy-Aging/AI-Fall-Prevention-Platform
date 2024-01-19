package happyaging.server.repository.image;

import happyaging.server.domain.image.ExampleImage;
import happyaging.server.domain.image.Location;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ExampleImageRepository extends JpaRepository<ExampleImage, Long> {
    List<ExampleImage> findAllByOrderByIdAsc();

    List<ExampleImage> findAllByLocation(Location location);
}
