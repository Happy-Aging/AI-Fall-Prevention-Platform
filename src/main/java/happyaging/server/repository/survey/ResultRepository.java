package happyaging.server.repository.survey;

import happyaging.server.domain.survey.Result;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ResultRepository extends JpaRepository<Result, Long> {
    Optional<Result> findBySurveyId(Long id);
}
