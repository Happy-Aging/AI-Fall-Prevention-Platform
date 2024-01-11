package happyaging.server.service.result;

import com.google.gson.Gson;
import happyaging.server.domain.response.Response;
import happyaging.server.domain.result.Result;
import happyaging.server.domain.senior.Senior;
import happyaging.server.domain.survey.Survey;
import happyaging.server.exception.AppException;
import happyaging.server.exception.errorcode.AppErrorCode;
import happyaging.server.repository.result.ResultRepository;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.core.io.Resource;
import org.springframework.core.io.UrlResource;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class ResultService {
    private static final String DISPOSITION_PREFIX = "attachment; filename*=UTF-8''";
    private static final Gson gson = new Gson();

    private final ResultRepository resultRepository;

    @Transactional
    public Result create(Senior senior, Survey survey, List<Response> responses) {
        //TODO: AI server에 보내줄 AiServerRequestDTO 만들고 보내기
        //TODO: AI 서버에서 응답 받은걸로 AiServerResponseDTO 만들기로 수정
        Result result = Result.create(survey);
        return resultRepository.save(result);
    }

    @Transactional(readOnly = true)
    public Result findBySurvey(Survey survey) {
        return resultRepository.findBySurveyId(survey.getId())
                .orElseThrow(() -> new AppException(AppErrorCode.INVALID_RESULT));
    }

    @Transactional(readOnly = true)
    public Resource findReport(Long resultId) {
        Result result = findResult(resultId);
        return getResource(result);
    }

    private Result findResult(Long resultId) {
        return resultRepository.findById(resultId)
                .orElseThrow(() -> new AppException(AppErrorCode.INVALID_RESULT));
    }

    private Resource getResource(Result result) {
        String filePath = result.getReport();
        System.out.println(filePath);
        try {
            Path file = Paths.get(filePath).normalize();
            return checkResource(new UrlResource(file.toUri()));
        } catch (Exception e) {
            throw new AppException(AppErrorCode.INVALID_FILE);
        }
    }

    private Resource checkResource(Resource resource) {
        if (resource.exists()) {
            return resource;
        }
        throw new AppException(AppErrorCode.INVALID_FILE);
    }
    
    public String createContentDisposition(Resource resource) {
        String filename = checkFileName(resource.getFilename());
        String encodedFilename = URLEncoder.encode(filename, StandardCharsets.UTF_8);
        return DISPOSITION_PREFIX + encodedFilename;
    }

    private static String checkFileName(String filename) {
        if (filename == null) {
            throw new AppException(AppErrorCode.INVALID_FILE);
        }
        return filename;
    }

//
//    @Transactional
//    public ResultResponseDTO createResult(Survey survey, List<Response> responses) {
//        SurveyResponseDTO surveyResponseDTO = createDataForReport(survey.getSenior().getName(), responses);
//        updateSeniorInfo(survey.getSenior(), surveyResponseDTO);
//        ReportResponseDTO reportResponseDTO = createReport(surveyResponseDTO);
//        Result result = Result.builder()
//                .rank(surveyResponseDTO.getRank())
//                .totalScore(surveyResponseDTO.getTotalScore())
//                .summary(reportResponseDTO.getSummary())
//                .report(reportResponseDTO.getUrl())
//                .survey(survey)
//                .build();
//        resultRepository.save(result);
//        return createResultResponseDTO(result, survey);
//    }
//
//
//    private ReportResponseDTO createReport(SurveyResponseDTO dataForReport) {
//        HttpURLConnection con = null;
//        try {
//            URL url = new URL("http://localhost:8000/makeReport");
//            con = (HttpURLConnection) url.openConnection();
//            con.setRequestMethod("POST");
//            con.setRequestProperty("Content-Type", "application/json; utf-8");
//            con.setRequestProperty("Accept", "application/json");
//            con.setDoOutput(true);
//
//            String jsonInputString = gson.toJson(dataForReport);
//            System.out.println(jsonInputString);
//
//            try (OutputStream os = con.getOutputStream()) {
//                byte[] input = jsonInputString.getBytes(StandardCharsets.UTF_8);
//                os.write(input, 0, input.length);
//            }
//
//            try (BufferedReader br = new BufferedReader(
//                    new InputStreamReader(con.getInputStream(), StandardCharsets.UTF_8))) {
//                StringBuilder response = new StringBuilder();
//                String responseLine;
//                while ((responseLine = br.readLine()) != null) {
//                    response.append(responseLine.trim());
//                }
//                return gson.fromJson(response.toString(), ReportResponseDTO.class);
//            }
//        } catch (IOException e) {
//            throw new IllegalArgumentException(e);
//        } catch (IllegalArgumentException e) {
//            throw new IllegalArgumentException("cannot connect AI server");
//        } finally {
//            if (con != null) {
//                con.disconnect();
//            }
//        }
//    }
//
//    private SurveyResponseDTO createDataForReport(String name, List<Response> responses) {
//        double totalScore = 1.0;
//        Map<String, QuestionAndAnswerDTO> surveyResponse = new LinkedHashMap<>();
//
//        for (Response response : responses) {
//            totalScore *= ResponseScore.getScore(response.getQuestionNumber(), response.getResponse());
//            createSurveyResponseDTO(response, surveyResponse);
//        }
//        totalScore = Math.round(totalScore * 1000.0) / 1000.0;
//        return SurveyResponseDTO.builder()
//                .name(name)
//                .rank(ResponseScore.calculateRank(totalScore))
//                .totalScore(totalScore)
//                .data(surveyResponse)
//                .build();
//    }
//
//    private void createSurveyResponseDTO(Response response, Map<String, QuestionAndAnswerDTO> surveyResponse) {
//        surveyResponse.put(response.getQuestionNumber(), createQuestionAndAnswerDTO(response));
//    }
//
//    private QuestionAndAnswerDTO createQuestionAndAnswerDTO(Response response) {
//        return QuestionAndAnswerDTO.builder()
//                .question(questionService.findByNumber(response.getQuestionNumber()))
//                .answer(response.getResponse())
//                .build();
//    }
//
//    private ResultResponseDTO createResultResponseDTO(Result result, Survey survey) {
//        return ResultResponseDTO.builder()
//                .resultId(result.getId())
//                .date(survey.getDate())
//                .rank(result.getRank())
//                .summary(result.getSummary())
//                .build();
//    }
//
//    private void updateSeniorInfo(Senior senior, SurveyResponseDTO surveyResponseDTO) {
//        String sex = surveyResponseDTO.getData().get("1").getAnswer();
//        String birth = surveyResponseDTO.getData().get("2").getAnswer().substring(0, 4);
//        String residence = surveyResponseDTO.getData().get("4").getAnswer();
//        senior.updateSeniorInfo(sex, residence, birth);
//    }
}
