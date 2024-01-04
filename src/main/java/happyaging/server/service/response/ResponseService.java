package happyaging.server.service.response;

import happyaging.server.domain.response.Response;
import happyaging.server.domain.senior.Senior;
import happyaging.server.domain.survey.Survey;
import happyaging.server.dto.response.ResponseDTO;
import happyaging.server.dto.response.ResponseListDTO;
import happyaging.server.dto.result.ResultResponseDTO;
import happyaging.server.repository.response.ResponseRepository;
import happyaging.server.service.result.ResultService;
import happyaging.server.service.survey.SurveyService;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class ResponseService {
    private final SurveyService surveyService;
    private final ResultService resultService;
    private final ResponseRepository responseRepository;

//    @Transactional
//    public void saveSurveyResponse(Senior senior, ResponseListDTO responseListDTO) {
//        Survey survey = surveyService.createSurvey(senior);
//        List<Response> responses = responseListDTO.getResponseDTOS().stream()
//                .map(responseDTO -> createResponse(survey, responseDTO))
//                .toList();
//
//        resultService.createResult(responseListDTO);
//
//        responseRepository.saveAll(responses);
//    }

    @Transactional
    public ResultResponseDTO saveSurveyResponse(Senior senior, ResponseListDTO responseListDTO) {
        Survey survey = surveyService.createSurvey(senior);
        List<Response> responses = responseListDTO.getResponseDTOS().stream()
                .map(responseDTO -> createResponse(survey, responseDTO))
                .toList();
        responseRepository.saveAll(responses);
        return resultService.createResult(survey, responses);
    }

    private Response createResponse(Survey survey, ResponseDTO responseDTO) {
        return Response.builder()
                .questionNumber(responseDTO.getQuestionNumber())
                .response(responseDTO.getResponse())
                .survey(survey)
                .build();
    }
}
