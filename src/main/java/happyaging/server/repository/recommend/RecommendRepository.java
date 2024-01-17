package happyaging.server.repository.recommend;

import happyaging.server.domain.image.Location;
import happyaging.server.domain.product.Recommend;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface RecommendRepository extends JpaRepository<Recommend, Long> {
    void deleteAllBySeniorId(Long seniorId);

    List<Recommend> findAllBySeniorIdAndLocation(Long seniorId, Location location);
}
