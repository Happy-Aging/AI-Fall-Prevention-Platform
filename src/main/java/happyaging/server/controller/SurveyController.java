package happyaging.server.controller;

import happyaging.server.domain.Result;
import happyaging.server.domain.Senior;
import happyaging.server.dto.result.ResultResponseDTO;
import happyaging.server.service.ResultService;
import happyaging.server.service.SeniorService;
import happyaging.server.service.SurveyService;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.core.io.Resource;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("survey")
public class SurveyController {
    private final SeniorService seniorService;
    private final SurveyService surveyService;
    private final ResultService resultService;

    @GetMapping("/list/{seniorId}")
    public List<ResultResponseDTO> getSeniorSurveyList(Authentication authentication,
                                                       @PathVariable Long seniorId) {
        if (authentication == null) {
            return null;
        }
        Senior existingSenior = seniorService.findSenior(seniorId);
        return surveyService.getSeniorServeyList(existingSenior);
    }

    @GetMapping("/{resultId}/download")
    public ResponseEntity<Resource> downloadReport(@PathVariable Long resultId) {
        Result result = resultService.findResult(resultId);
        return resultService.getReport(result);
    }
}
