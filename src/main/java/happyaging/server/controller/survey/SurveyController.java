package happyaging.server.controller.survey;

import happyaging.server.domain.question.Question;
import happyaging.server.domain.senior.Senior;
import happyaging.server.dto.response.ResponseRequestDTO;
import happyaging.server.dto.result.ResultResponseDTO;
import happyaging.server.dto.survey.SurveyResponseDTO;
import happyaging.server.service.question.QuestionService;
import happyaging.server.service.senior.SeniorService;
import happyaging.server.service.survey.SurveyService;
import jakarta.validation.Valid;
import java.util.List;
import lombok.RequiredArgsConstructor;
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
    private final SeniorService seniorService;
    private final SurveyService surveyService;

    @GetMapping
    public List<SurveyResponseDTO> readSurvey() {
        List<Question> questions = questionService.readAllQuestions();
        return surveyService.readSurvey(questions);
    }

    @PostMapping("/{seniorId}")
    public ResultResponseDTO submitSurvey(@PathVariable Long seniorId,
                                          @RequestBody @Valid List<ResponseRequestDTO> responseRequestDTOS) {
        Senior senior = seniorService.findSeniorById(seniorId);
        ResultResponseDTO resultResponseDTO = surveyService.submit(senior, responseRequestDTOS);
        seniorService.updateRank(senior, resultResponseDTO.getRank());
        // TODO: senior 추천 물품 update
        return resultResponseDTO;
    }

    @GetMapping("/{seniorId}")
    public List<ResultResponseDTO> readSeniorSurveys(@PathVariable Long seniorId) {
        return surveyService.findSurveys(seniorId);
    }
}
