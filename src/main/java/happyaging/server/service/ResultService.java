package happyaging.server.service;

import happyaging.server.domain.Response;
import happyaging.server.domain.Result;
import happyaging.server.domain.Survey;
import happyaging.server.dto.result.ResultResponseDTO;
import happyaging.server.dto.survey.QuestionAndAnswerDTO;
import happyaging.server.dto.survey.SurveyResponseDTO;
import happyaging.server.repository.ResultRepository;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class ResultService {
    private final ResultRepository resultRepository;
    private final QuestionService questionService;

    //TODO total score를 계산하는 기능
    //TODO rank를 계산하는 기능
    @Transactional
    public ResultResponseDTO createResult(Survey survey, List<Response> responses) {
        //TODO 원래는 rank, summary, report경로 3개를 받아야함.
        String reportSavePath = createReport(createDataForReport(responses));
        Result result = Result.builder()
                .rank(1)
                .summary("test")
                .report(reportSavePath)
                .survey(survey)
                .build();
        resultRepository.save(result);
        return createResultResponseDTO(result, survey);
    }

    private ResultResponseDTO createResultResponseDTO(Result result, Survey survey) {
        return ResultResponseDTO.builder()
                .resultId(result.getId())
                .date(survey.getDate())
                .rank(result.getRank())
                .summary(result.getSummary())
                .build();
    }

    private String createReport(SurveyResponseDTO dataForReport) {
        //TODO dto를 보내고 리턴값을 그대로 리턴
        return "hello";
    }

    private SurveyResponseDTO createDataForReport(List<Response> responses) {
        Map<String, QuestionAndAnswerDTO> surveyResponse = new LinkedHashMap<>();
        responses.forEach(response -> createSurveyResponseDTO(response, surveyResponse));
        return SurveyResponseDTO.builder()
                .data(surveyResponse)
                .build();
    }

    private void createSurveyResponseDTO(Response response, Map<String, QuestionAndAnswerDTO> surveyResponse) {
        surveyResponse.put(response.getQuestionNumber(), createQuestionAndAnswerDTO(response));
    }

    private QuestionAndAnswerDTO createQuestionAndAnswerDTO(Response response) {
        return QuestionAndAnswerDTO.builder()
                .question(questionService.findByNumber(response.getQuestionNumber()))
                .answer(response.getResponse())
                .build();
    }
}
