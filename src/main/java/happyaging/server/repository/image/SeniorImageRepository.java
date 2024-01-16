package happyaging.server.repository.image;

import happyaging.server.domain.image.SeniorImage;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface SeniorImageRepository extends JpaRepository<SeniorImage, Long> {
    void deleteALLBySeniorId(Long seniorId);
}
