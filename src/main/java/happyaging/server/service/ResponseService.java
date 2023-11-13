package happyaging.server.service;

import happyaging.server.domain.Response;
import happyaging.server.domain.Survey;
import happyaging.server.dto.response.ResponseDTO;
import happyaging.server.dto.response.ResponseListDTO;
import happyaging.server.repository.ResponseRepository;
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
//        //TODO 응답을 AI 서버로 보내서 보고서 생성 (필요한 정보가 더 있을 수도 있음)
//        resultService.createResult(responseListDTO);
//
//        responseRepository.saveAll(responses);
//    }

    @Transactional
    public void saveSurveyResponse(ResponseListDTO responseListDTO) {
        Survey survey = surveyService.createSurvey();
        List<Response> responses = responseListDTO.getResponseDTOS().stream()
                .map(responseDTO -> createResponse(survey, responseDTO))
                .toList();

        //TODO 응답을 AI 서버로 보내서 보고서 생성 (필요한 정보가 더 있을 수도 있음)
        resultService.createResult(responseListDTO);

        responseRepository.saveAll(responses);
    }

    private Response createResponse(Survey survey, ResponseDTO responseDTO) {
        return Response.builder()
                .questionId(responseDTO.getQuestionId())
                .response(responseDTO.getResponse())
                .survey(survey)
                .build();
    }
}
