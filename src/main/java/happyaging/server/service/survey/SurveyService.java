package happyaging.server.service.survey;

import happyaging.server.domain.question.Question;
import happyaging.server.domain.senior.Senior;
import happyaging.server.domain.survey.Survey;
import happyaging.server.dto.response.ResponseRequestDTO;
import happyaging.server.dto.survey.OptionDTO;
import happyaging.server.dto.survey.SurveyResponseDTO;
import happyaging.server.exception.AppException;
import happyaging.server.exception.errorcode.AppErrorCode;
import happyaging.server.repository.senior.SeniorRepository;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class SurveyService {

    private final SeniorRepository seniorRepository;

    @Transactional(readOnly = true)
    public List<SurveyResponseDTO> readSurvey(List<Question> questions) {
        return questions.stream()
                .map(this::createSurveyResponseDTOS)
                .toList();
    }

    private SurveyResponseDTO createSurveyResponseDTOS(Question question) {
        List<OptionDTO> optionDTOS = createOptionDTOs(question);
        return SurveyResponseDTO.create(question, optionDTOS);
    }

    private static List<OptionDTO> createOptionDTOs(Question question) {
        return question.getOptions().stream()
                .map(OptionDTO::create)
                .toList();
    }

    @Transactional
    public Long submit(Long seniorId, List<ResponseRequestDTO> responseRequestDTOS) {
        Senior senior = findSenior(seniorId);
        Survey survey = Survey.create(senior);
        return null;
    }

    private Senior findSenior(Long seniorId) {
        return seniorRepository.findById(seniorId)
                .orElseThrow(() -> new AppException(AppErrorCode.INVALID_SENIOR));
    }

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
