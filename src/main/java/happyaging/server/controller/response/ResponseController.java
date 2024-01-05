package happyaging.server.controller.response;

import happyaging.server.domain.senior.Senior;
import happyaging.server.dto.response.ResponseListDTO;
import happyaging.server.dto.result.ResultResponseDTO;
import happyaging.server.service.response.ResponseService;
import happyaging.server.service.senior.SeniorService;
import happyaging.server.service.survey.SurveyService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
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

//    @PostMapping("/{seniorId}")
//    public ResponseEntity<Object> saveSurveyResponse(Authentication authentication,
//                                                     @PathVariable Long seniorId,
//                                                     @RequestBody @Valid ResponseListDTO responseListDTO) {
//        if (authentication == null) {
//            return null;
//        }
//        Senior senior = seniorService.findSenior(seniorId);
//        responseService.saveSurveyResponse(senior, responseListDTO);
//        return new ResponseEntity<>("save response success!", HttpStatus.CREATED);
//    }


    @PostMapping("/{seniorId}")
    public ResultResponseDTO saveSurveyResponse(@PathVariable Long seniorId,
                                                @RequestBody @Valid ResponseListDTO responseListDTO) {
        Senior senior = seniorService.findSenior(seniorId);
        return responseService.saveSurveyResponse(senior, responseListDTO);
    }
}
