package happyaging.server.service.survey;

import happyaging.server.repository.survey.SurveyRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

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
//
//    @Transactional
//    public Survey createSurvey(Senior senior) {
//        LocalDate today = LocalDate.now();
//        Survey survey = Survey.builder()
//                .date(today)
//                .senior(senior)
//                .build();
//        return surveyRepository.save(survey);
//    }
//
//    @Transactional(readOnly = true)
//    public List<ResultResponseDTO> getSeniorServeyList(Senior existingSenior) {
//        List<Survey> surveyList = existingSenior.getSurveyList();
//        if (surveyList.isEmpty()) {
//            return Collections.emptyList();
//        }
//        return surveyList.stream().map(this::getSeniorSurvey).toList();
//    }
//
//    private ResultResponseDTO getSeniorSurvey(Survey survey) {
//        Result result = survey.getResult();
//        return ResultResponseDTO.builder()
//                .resultId(result.getId())
//                .date(survey.getDate())
//                .rank(result.getRank())
//                .summary(result.getSummary())
//                .build();
//    }
}
