package happyaging.server.repository.image;

import happyaging.server.domain.image.SeniorImage;
import happyaging.server.domain.senior.Senior;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface SeniorImageRepository extends JpaRepository<SeniorImage, Long> {
    void deleteALLBySeniorId(Long seniorId);

    @Query("SELECT DISTINCT si.senior FROM SeniorImage si")
    List<Senior> findAllSeniorsWithImages();

    List<SeniorImage> findAllBySenior(Senior senior);
}
