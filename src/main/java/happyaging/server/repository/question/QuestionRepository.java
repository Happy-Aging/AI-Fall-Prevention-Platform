package happyaging.server.repository.question;

import happyaging.server.domain.question.Question;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface QuestionRepository extends JpaRepository<Question, Long> {
    List<Question> findAllByValid(Boolean valid);
}
