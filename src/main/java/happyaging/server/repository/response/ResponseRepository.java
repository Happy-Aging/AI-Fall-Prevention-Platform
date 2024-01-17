package happyaging.server.repository.response;

import happyaging.server.domain.response.Response;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ResponseRepository extends JpaRepository<Response, Long> {
    List<Response> findAllBySurveyId(Long surveyId);
}
