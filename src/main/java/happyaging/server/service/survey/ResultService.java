package happyaging.server.service.survey;

import com.google.gson.Gson;
import happyaging.server.domain.product.Product;
import happyaging.server.domain.product.Recommend;
import happyaging.server.domain.survey.Response;
import happyaging.server.domain.survey.Result;
import happyaging.server.domain.senior.Senior;
import happyaging.server.domain.survey.Survey;
import happyaging.server.dto.ai.AiServerRequestDTO;
import happyaging.server.dto.ai.AiServerResponseDTO;
import happyaging.server.dto.ai.ResponseInfoDTO;
import happyaging.server.dto.ai.SolutionDTO;
import happyaging.server.exception.AppException;
import happyaging.server.exception.errorcode.AppErrorCode;
import happyaging.server.repository.product.RecommendRepository;
import happyaging.server.repository.survey.ResultRepository;
import happyaging.server.service.product.ProductService;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.core.io.Resource;
import org.springframework.core.io.UrlResource;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Slf4j
@RequiredArgsConstructor
public class ResultService {
    private static final String DISPOSITION_PREFIX = "attachment; filename*=UTF-8''";
    private static final String AI_SERVER_ENDPOINT = "http://localhost:8000/makeReport";
    private static final String AI_SERVER_REQUEST_METHOD = "POST";
    private static final String AI_SERVER_CONTENT_TYPE_KEY = "Content-Type";
    private static final String AI_SERVER_CONTENT_TYPE_VALUE = "application/json; utf-8";
    private static final String AI_SERVER_ACCEPT_TYPE_KEY = "Accept";
    private static final String AI_SERVER_ACCEPT_TYPE_VALUE = "application/json";
    private static final Gson gson = new Gson();

    private final ResultRepository resultRepository;
    private final RecommendRepository recommendRepository;
    private final ProductService productService;

    @Transactional
    public Result create(Senior senior, Survey survey, List<Response> responses) {
        List<ResponseInfoDTO> responseInfoDTOS = createResponseDTOS(responses);
        AiServerRequestDTO aiServerRequestDTO = AiServerRequestDTO.create(senior, responseInfoDTOS);
        AiServerResponseDTO aiServerResponseDTO = sendToAiServer(aiServerRequestDTO);
        updateSeniorSolution(senior, aiServerResponseDTO.getProduct());
        Result result = Result.create(survey, aiServerResponseDTO);
        return resultRepository.save(result);
    }

    private void updateSeniorSolution(Senior senior, List<SolutionDTO> products) {
        recommendRepository.deleteAllBySeniorId(senior.getId());

        List<Recommend> recommendProduct = new ArrayList<>();
        for (SolutionDTO solutionDTO : products) {
            Product product = productService.findProductById(solutionDTO.getId());
            recommendProduct.add(Recommend.create(senior, product, solutionDTO.getLocation()));
        }
        recommendRepository.saveAll(recommendProduct);
    }

    private List<ResponseInfoDTO> createResponseDTOS(List<Response> responses) {
        return responses.stream()
                .map(ResponseInfoDTO::create)
                .toList();
    }

    private AiServerResponseDTO sendToAiServer(AiServerRequestDTO aiServerRequestDTO) {
        HttpURLConnection con = null;
        try {
            con = setupHttpConnection();
            sendData(con, gson.toJson(aiServerRequestDTO));
            return receiveResponseData(con, gson);
        } catch (RuntimeException exception) {
            throw new AppException(AppErrorCode.DISCONNECT_AI_SERVER);
        } finally {
            if (con != null) {
                con.disconnect();
            }
        }
    }

    private AiServerResponseDTO receiveResponseData(HttpURLConnection con, Gson gson) {
        try (BufferedReader br = new BufferedReader(
                new InputStreamReader(con.getInputStream(), StandardCharsets.UTF_8))) {
            StringBuilder response = new StringBuilder();
            String responseLine;
            while ((responseLine = br.readLine()) != null) {
                response.append(responseLine.trim());
            }
            return gson.fromJson(response.toString(), AiServerResponseDTO.class);
        } catch (IOException e) {
            throw new AppException(AppErrorCode.DISCONNECT_AI_SERVER);
        }
    }

    private void sendData(HttpURLConnection con, String jsonInputString) {
        try (OutputStream os = con.getOutputStream()) {
            byte[] input = jsonInputString.getBytes(StandardCharsets.UTF_8);
            os.write(input, 0, input.length);
        } catch (IOException e) {
            throw new AppException(AppErrorCode.DISCONNECT_AI_SERVER);
        }
    }

    private HttpURLConnection setupHttpConnection() {
        try {
            URL url = new URL(AI_SERVER_ENDPOINT);
            HttpURLConnection con = (HttpURLConnection) url.openConnection();
            con.setRequestMethod(AI_SERVER_REQUEST_METHOD);
            con.setRequestProperty(AI_SERVER_CONTENT_TYPE_KEY, AI_SERVER_CONTENT_TYPE_VALUE);
            con.setRequestProperty(AI_SERVER_ACCEPT_TYPE_KEY, AI_SERVER_ACCEPT_TYPE_VALUE);
            con.setDoOutput(true);
            return con;
        } catch (IOException exception) {
            throw new AppException(AppErrorCode.DISCONNECT_AI_SERVER);
        }
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
//    @Transactional
//    public ResultResponseDTO createResult(Survey survey, List<Response> responses) {
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
//
//    private ResultResponseDTO createResultResponseDTO(Result result, Survey survey) {
//        return ResultResponseDTO.builder()
//                .resultId(result.getId())
//                .date(survey.getDate())
//                .rank(result.getRank())
//                .summary(result.getSummary())
//                .build();
//    }
}
