package happyaging.server.repository.survey;

import happyaging.server.domain.survey.Response;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ResponseRepository extends JpaRepository<Response, Long> {
    List<Response> findAllBySurveyId(Long surveyId);
}
