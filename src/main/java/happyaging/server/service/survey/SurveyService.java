package happyaging.server.service.survey;

import happyaging.server.domain.question.Question;
import happyaging.server.domain.response.Response;
import happyaging.server.domain.result.Result;
import happyaging.server.domain.senior.Senior;
import happyaging.server.domain.survey.Survey;
import happyaging.server.dto.response.ResponseRequestDTO;
import happyaging.server.dto.result.ResultResponseDTO;
import happyaging.server.dto.survey.OptionDTO;
import happyaging.server.dto.survey.SurveyResponseDTO;
import happyaging.server.exception.AppException;
import happyaging.server.exception.errorcode.AppErrorCode;
import happyaging.server.repository.senior.SeniorRepository;
import happyaging.server.repository.survey.SurveyRepository;
import happyaging.server.service.response.ResponseService;
import happyaging.server.service.result.ResultService;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class SurveyService {

    private final SeniorRepository seniorRepository;
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
    public ResultResponseDTO submit(Long seniorId, List<ResponseRequestDTO> responseRequestDTOS) {
        Senior senior = findSenior(seniorId);
        Survey survey = createSurvey(senior);
        List<Response> responses = responseService.saveResponse(responseRequestDTOS, survey);
        Result result = resultService.create(senior, survey, responses);
        return ResultResponseDTO.create(survey, result);
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
    
    private Senior findSenior(Long seniorId) {
        return seniorRepository.findById(seniorId)
                .orElseThrow(() -> new AppException(AppErrorCode.INVALID_SENIOR));
    }
}
