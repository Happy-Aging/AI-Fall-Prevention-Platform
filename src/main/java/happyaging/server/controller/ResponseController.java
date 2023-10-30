package happyaging.server.controller;

import happyaging.server.domain.Senior;
import happyaging.server.domain.Survey;
import happyaging.server.dto.response.ResponseListDTO;
import happyaging.server.service.ResponseService;
import happyaging.server.service.SeniorService;
import happyaging.server.service.SurveyService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("response")
public class ResponseController {
    private final SeniorService seniorService;
    private final ResponseService responseService;
    private final SurveyService surveyService;

    @PostMapping("/{seniorId}")
    public ResponseEntity<Object> saveSurveyResponse(Authentication authentication,
                                                     @PathVariable Long seniorId,
                                                     @RequestBody @Valid ResponseListDTO responseListDTO) {
        if (authentication == null) {
            return null;
        }
        Senior senior = seniorService.findSenior(seniorId);
        Survey survey = surveyService.createSurvey(senior);

        responseService.saveSurveyResponse(survey, responseListDTO);
        return new ResponseEntity<>("save response success!", HttpStatus.CREATED);
    }
}
