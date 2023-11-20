package happyaging.server.service;

import happyaging.server.domain.Question;
import happyaging.server.domain.Questions;
import happyaging.server.dto.question.QuestionResponseDTO;
import happyaging.server.repository.QuestionRepository;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Optional;
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
                .number(question.getNumber())
                .question(question.getQuestion())
                .type(question.getType())
                .build();
    }

    public String findByNumber(String questionNumber) {
        Optional<Question> questionOptional = questionRepository.findById(questionNumber);
        if (questionOptional.isPresent()) {
            return questionOptional.get().getQuestion();
        }
        return "";
    }

    public void saveQuestions() {
        List<Question> questions = new ArrayList<>();
        for (Questions question : Questions.values()) {
            questions.add(createQuestion(question));
        }
        questionRepository.saveAll(questions);
    }

    private Question createQuestion(Questions question) {
        return Question.builder()
                .number(question.getNumber())
                .question(question.getQuestion())
                .type(question.getType())
                .build();
    }
}
