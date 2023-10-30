package happyaging.server.service;

import happyaging.server.domain.Question;
import happyaging.server.dto.question.QuestionResponseDTO;
import happyaging.server.repository.QuestionRepository;
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
    public List<QuestionResponseDTO> getQuestions() {
        List<Question> questions = questionRepository.findAll();
        if (questions.isEmpty()) {
            return Collections.emptyList();
        }

        return questions.stream()
                .map(this::createQuestionResponseDTO)
                .toList();
    }

    private QuestionResponseDTO createQuestionResponseDTO(Question question) {
        return QuestionResponseDTO.builder()
                .id(question.getId())
                .number(question.getNumber())
                .question(question.getQuestion())
                .type(question.getType())
                .build();
    }
}
