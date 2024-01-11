package happyaging.server.controller.survey;

import happyaging.server.domain.question.Question;
import happyaging.server.dto.response.ResponseRequestDTO;
import happyaging.server.dto.survey.SurveyResponseDTO;
import happyaging.server.service.question.QuestionService;
import happyaging.server.service.survey.SurveyService;
import jakarta.validation.Valid;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("survey")
public class SurveyController {

    private final QuestionService questionService;
    private final SurveyService surveyService;

    @GetMapping
    public List<SurveyResponseDTO> readSurvey() {
        List<Question> questions = questionService.readAllQuestions();
        return surveyService.readSurvey(questions);
    }

    @PostMapping("/{seniorId}")
    public ResponseEntity<Long> submitSurvey(@PathVariable Long seniorId,
                                             @RequestBody @Valid List<ResponseRequestDTO> responseRequestDTOS) {
        Long surveyId = surveyService.submit(seniorId, responseRequestDTOS);
        return ResponseEntity.ok().body(surveyId);
    }

//    @GetMapping("/{resultId}/download")
//    public ResponseEntity<Resource> downloadReport(@PathVariable Long resultId) {
//        Result result = resultService.findResult(resultId);
//        return resultService.getReport(result);
//    }
}
