package happyaging.server.service.response;

import happyaging.server.domain.option.Option;
import happyaging.server.domain.question.Question;
import happyaging.server.domain.response.Response;
import happyaging.server.domain.survey.Survey;
import happyaging.server.dto.response.ResponseRequestDTO;
import happyaging.server.repository.response.ResponseRepository;
import happyaging.server.service.option.OptionService;
import happyaging.server.service.question.QuestionService;
import java.util.ArrayList;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class ResponseService {

    private final QuestionService questionService;
    private final OptionService optionService;

    private final ResponseRepository responseRepository;

    @Transactional
    public List<Response> saveResponse(List<ResponseRequestDTO> responseRequestDTOS, Survey survey) {
        List<Response> responses = new ArrayList<>();
        responseRequestDTOS.stream()
                .map(response -> saveResponse(survey, response))
                .forEach(responses::add);
        return responseRepository.saveAll(responses);
    }

    @Transactional
    public Response saveResponse(Survey survey, ResponseRequestDTO responseRequestDTO) {
        Question question = questionService.findQuestion(responseRequestDTO.getQuestionId());
        Option option = findOptionByChoiceId(responseRequestDTO.getChoiceId(), responseRequestDTO.getMultiId());
        String subjectiveContext = findContext(responseRequestDTO.getSubjectiveResponse(),
                responseRequestDTO.getMultiId());

        return Response.create(survey, question, option, subjectiveContext);
    }

    @Transactional(readOnly = true)
    public List<Response> findResponsesBySurvey(Survey survey) {
        return responseRepository.findAllBySurveyId(survey.getId());
    }

    private String findContext(String subjectiveResponse, List<Long> multiId) {
        if (multiId != null) {
            List<String> optionContext = new ArrayList<>();
            for (Long optionId : multiId) {
                Option option = optionService.findOption(optionId);
                optionContext.add(option.getContent());
            }
            return String.join(", ", optionContext);
        }
        return subjectiveResponse;
    }

    private Option findOptionByChoiceId(Long choiceId, List<Long> multiId) {
        if (choiceId != null) {
            return optionService.findOption(choiceId);
        }
        return null;
    }
}
