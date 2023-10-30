package happyaging.server.controller;

import happyaging.server.dto.question.QuestionResponseDTO;
import happyaging.server.service.QuestionService;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("question")
public class QuestionController {

    private final QuestionService questionService;

    @GetMapping
    public List<QuestionResponseDTO> getQuestionList(Authentication authentication) {
        if (authentication == null) {
            return null;
        }
        return questionService.getQuestions();
    }
}
