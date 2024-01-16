package happyaging.server.repository.survey;

import happyaging.server.domain.survey.Survey;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface SurveyRepository extends JpaRepository<Survey, Long> {

    List<Survey> findAllBySeniorIdOrderByDateDescIdDesc(Long seniorId);
}
