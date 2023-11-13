package happyaging.server.service;

import happyaging.server.domain.Result;
import happyaging.server.domain.Senior;
import happyaging.server.domain.Survey;
import happyaging.server.dto.survey.SurveyResponseDTO;
import happyaging.server.repository.SurveyRepository;
import java.time.LocalDate;
import java.util.Collections;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class SurveyService {
    private final SurveyRepository surveyRepository;

//    @Transactional
//    public Survey createSurvey(Senior senior) {
//        LocalDate today = LocalDate.now();
//        Survey survey = Survey.builder()
//                .date(today)
//                .senior(senior)
//                .build();
//        return surveyRepository.save(survey);
//    }

    @Transactional
    public Survey createSurvey() {
        LocalDate today = LocalDate.now();
        Survey survey = Survey.builder()
                .date(today)
                .senior(null)
                .build();
        return surveyRepository.save(survey);
    }

    @Transactional(readOnly = true)
    public List<SurveyResponseDTO> getSeniorServeyList(Senior existingSenior) {
        List<Survey> surveyList = existingSenior.getSurveyList();
        if (surveyList.isEmpty()) {
            return Collections.emptyList();
        }
        return surveyList.stream().map(this::getSeniorSurvey).toList();
    }

    private SurveyResponseDTO getSeniorSurvey(Survey survey) {
        Result result = survey.getResult();
        return SurveyResponseDTO.builder()
                .resultId(result.getId())
                .date(survey.getDate())
                .rank(result.getRank())
                .score(result.getTotalScore())
                .summary(result.getSummary())
                .build();
    }
}
