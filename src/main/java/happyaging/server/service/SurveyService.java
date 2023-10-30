package happyaging.server.service;

import happyaging.server.domain.Senior;
import happyaging.server.domain.Survey;
import happyaging.server.repository.SurveyRepository;
import java.time.LocalDate;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class SurveyService {
    private final SurveyRepository surveyRepository;

    @Transactional
    public Survey createSurvey(Senior senior) {
        LocalDate today = LocalDate.now();
        Survey survey = Survey.builder()
                .date(today)
                .senior(senior)
                .build();
        return surveyRepository.save(survey);
    }
}
