package happyaging.server.service.survey;

import happyaging.server.domain.option.Option;
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
import happyaging.server.repository.option.OptionRepository;
import happyaging.server.repository.question.QuestionRepository;
import happyaging.server.repository.response.ResponseRepository;
import happyaging.server.repository.senior.SeniorRepository;
import happyaging.server.repository.survey.SurveyRepository;
import happyaging.server.service.result.ResultService;
import java.util.ArrayList;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class SurveyService {

    private final SeniorRepository seniorRepository;
    private final SurveyRepository surveyRepository;
    private final ResponseRepository responseRepository;
    private final QuestionRepository questionRepository;
    private final OptionRepository optionRepository;
    private final ResultService resultService;

    @Transactional(readOnly = true)
    public List<SurveyResponseDTO> readSurvey(List<Question> questions) {
        return questions.stream()
                .map(this::createSurveyResponseDTOS)
                .toList();
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

    @Transactional
    public ResultResponseDTO submit(Long seniorId, List<ResponseRequestDTO> responseRequestDTOS) {
        Senior senior = findSenior(seniorId);
        Survey survey = createSurvey(senior);
        List<Response> responses = saveResponse(responseRequestDTOS, survey);
        Result result = resultService.create(senior, survey, responses);
        return ResultResponseDTO.create(survey, result);
    }

    private List<Response> saveResponse(List<ResponseRequestDTO> responseRequestDTOS, Survey survey) {
        List<Response> responses = new ArrayList<>();
        responseRequestDTOS.stream()
                .map(response -> saveResponse(survey, response))
                .forEach(responses::add);
        return responseRepository.saveAll(responses);
    }

    private Survey createSurvey(Senior senior) {
        Survey survey = Survey.create(senior);
        return surveyRepository.save(survey);
    }

    private Response saveResponse(Survey survey, ResponseRequestDTO responseRequestDTO) {
        Question question = findQuestion(responseRequestDTO.getQuestionId());
        Option option = findOptionByChoiceId(responseRequestDTO.getChoiceId(), responseRequestDTO.getMultiId());
        String subjectiveContext = findContext(responseRequestDTO.getSubjectiveResponse(),
                responseRequestDTO.getMultiId());

        return Response.create(survey, question, option, subjectiveContext);
    }

    private Question findQuestion(Long questionId) {
        return questionRepository.findById(questionId)
                .orElseThrow(() -> new AppException(AppErrorCode.INVALID_QUESTION));
    }

    private String findContext(String subjectiveResponse, List<Long> multiId) {
        if (multiId != null) {
            List<String> optionContext = new ArrayList<>();
            for (Long optionId : multiId) {
                Option option = findOption(optionId);
                optionContext.add(option.getContent());
            }
            return String.join(", ", optionContext);
        }
        return subjectiveResponse;
    }

    private Option findOptionByChoiceId(Long choiceId, List<Long> multiId) {
        if (choiceId != null) {
            return findOption(choiceId);
        }
        return null;
    }

    private Option findOption(Long choiceId) {
        return optionRepository.findById(choiceId)
                .orElseThrow(() -> new AppException(AppErrorCode.INVALID_OPTION));
    }

    private Senior findSenior(Long seniorId) {
        return seniorRepository.findById(seniorId)
                .orElseThrow(() -> new AppException(AppErrorCode.INVALID_SENIOR));
    }

//    @Transactional(readOnly = true)
//    public List<ResultResponseDTO> getSeniorServeyList(Senior existingSenior) {
//        List<Survey> surveyList = existingSenior.getSurveyList();
//        if (surveyList.isEmpty()) {
//            return Collections.emptyList();
//        }
//        return surveyList.stream().map(this::getSeniorSurvey).toList();
//    }
//
//    private ResultResponseDTO getSeniorSurvey(Survey survey) {
//        Result result = survey.getResult();
//        return ResultResponseDTO.builder()
//                .resultId(result.getId())
//                .date(survey.getDate())
//                .rank(result.getRank())
//                .summary(result.getSummary())
//                .build();
//    }
}
