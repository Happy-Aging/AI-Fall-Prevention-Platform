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
    private final ResponseRepository responseRepository;

    @Transactional(readOnly = true)
    public void saveSurveyResponse(Survey survey, ResponseListDTO responseListDTO) {
        List<Response> responses = responseListDTO.getResponseDTOS().stream()
                .map(responseDTO -> createResponse(survey, responseDTO))
                .toList();
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
