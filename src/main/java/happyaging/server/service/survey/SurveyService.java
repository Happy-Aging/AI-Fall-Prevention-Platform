package happyaging.server.service.survey;

import happyaging.server.domain.question.Question;
import happyaging.server.domain.response.Response;
import happyaging.server.domain.result.Result;
import happyaging.server.domain.senior.Senior;
import happyaging.server.domain.survey.Survey;
import happyaging.server.dto.admin.survey.ExcelDataDTO;
import happyaging.server.dto.response.ResponseRequestDTO;
import happyaging.server.dto.result.ResultResponseDTO;
import happyaging.server.dto.survey.OptionDTO;
import happyaging.server.dto.survey.SurveyResponseDTO;
import happyaging.server.exception.AppException;
import happyaging.server.exception.errorcode.AppErrorCode;
import happyaging.server.repository.survey.SurveyRepository;
import happyaging.server.service.response.ResponseService;
import happyaging.server.service.result.ResultService;
import java.util.ArrayList;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class SurveyService {

    private final SurveyRepository surveyRepository;
    private final ResponseService responseService;
    private final ResultService resultService;

    @Transactional(readOnly = true)
    public List<SurveyResponseDTO> readSurvey(List<Question> questions) {
        return questions.stream()
                .map(this::createSurveyResponseDTOS)
                .toList();
    }

    @Transactional
    public ResultResponseDTO submit(Senior senior, List<ResponseRequestDTO> responseRequestDTOS) {
        Survey survey = createSurvey(senior);
        List<Response> responses = responseService.saveResponse(responseRequestDTOS, survey);
        Result result = resultService.create(senior, survey, responses);
        return ResultResponseDTO.create(survey, result);
    }

    @Transactional(readOnly = true)
    public List<ResultResponseDTO> findSurveys(Long seniorId) {
        List<Survey> surveys = surveyRepository.findAllBySeniorIdOrderByDateDescIdDesc(seniorId);
        return creatResultResponseDTOS(surveys);
    }

    @Transactional(readOnly = true)
    public Survey findSurveyById(Long surveyId) {
        return surveyRepository.findById(surveyId)
                .orElseThrow(() -> new AppException(AppErrorCode.INVALID_SURVEY));
    }

    @Transactional(readOnly = true)
    public List<ExcelDataDTO> readAllData() {
        List<ExcelDataDTO> data = new ArrayList<>();
        List<Survey> surveys = surveyRepository.findAll();
        for (Survey survey : surveys) {
            Result result = resultService.findBySurvey(survey);
            List<Response> responses = responseService.findResponsesBySurvey(survey);
            data.add(ExcelDataDTO.create(survey, result.getRank(), responses));
        }
        return data;
    }

    private List<ResultResponseDTO> creatResultResponseDTOS(List<Survey> surveys) {
        List<ResultResponseDTO> resultResponseDTOS = new ArrayList<>();
        for (Survey survey : surveys) {
            Result result = resultService.findBySurvey(survey);
            resultResponseDTOS.add(ResultResponseDTO.create(survey, result));
        }
        return resultResponseDTOS;
    }

    private SurveyResponseDTO createSurveyResponseDTOS(Question question) {
        List<OptionDTO> optionDTOS = createOptionDTOs(question);
        return SurveyResponseDTO.create(question, optionDTOS);
    }

    private static List<OptionDTO> createOptionDTOs(Question question) {
        return question.getOptions().stream()
                .map(OptionDTO::create)
                .toList();
    }

    private Survey createSurvey(Senior senior) {
        Survey survey = Survey.create(senior);
        return surveyRepository.save(survey);
    }
}
