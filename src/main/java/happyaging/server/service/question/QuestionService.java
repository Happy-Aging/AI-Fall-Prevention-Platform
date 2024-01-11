package happyaging.server.service.question;

import happyaging.server.domain.question.Question;
import happyaging.server.exception.AppException;
import happyaging.server.exception.errorcode.AppErrorCode;
import happyaging.server.repository.question.QuestionRepository;
import java.util.Collections;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class QuestionService {

    private final QuestionRepository questionRepository;

    @Transactional(readOnly = true)
    public List<Question> readAllQuestions() {
        List<Question> questions = questionRepository.findAll();
        return sortQuestions(questions);
    }

    @Transactional(readOnly = true)
    public Question findQuestion(Long questionId) {
        return questionRepository.findById(questionId)
                .orElseThrow(() -> new AppException(AppErrorCode.INVALID_QUESTION));
    }

    private List<Question> sortQuestions(List<Question> questions) {
        Collections.sort(questions, new QuestionComparator());
        return questions;
    }
}
