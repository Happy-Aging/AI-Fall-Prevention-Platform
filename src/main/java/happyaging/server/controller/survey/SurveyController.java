package happyaging.server.controller.survey;

import happyaging.server.service.result.ResultService;
import happyaging.server.service.senior.SeniorService;
import happyaging.server.service.survey.SurveyService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("survey")
public class SurveyController {
    private final SeniorService seniorService;
    private final SurveyService surveyService;
    private final ResultService resultService;
//
//    @GetMapping("/list/{seniorId}")
//    public List<ResultResponseDTO> getSeniorSurveyList(Authentication authentication,
//                                                       @PathVariable Long seniorId) {
//        if (authentication == null) {
//            return null;
//        }
//        Senior existingSenior = seniorService.findSenior(seniorId);
//        return surveyService.getSeniorServeyList(existingSenior);
//    }
//
//    @GetMapping("/{resultId}/download")
//    public ResponseEntity<Resource> downloadReport(@PathVariable Long resultId) {
//        Result result = resultService.findResult(resultId);
//        return resultService.getReport(result);
//    }
}
