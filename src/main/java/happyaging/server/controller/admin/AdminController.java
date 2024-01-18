package happyaging.server.controller.admin;

import happyaging.server.domain.product.InstalledImage;
import happyaging.server.domain.product.Product;
import happyaging.server.domain.response.Response;
import happyaging.server.domain.result.Result;
import happyaging.server.domain.senior.Senior;
import happyaging.server.domain.survey.Survey;
import happyaging.server.domain.user.User;
import happyaging.server.domain.user.UserType;
import happyaging.server.dto.admin.product.ProductInfo;
import happyaging.server.dto.admin.senior.ReadSeniorDTO;
import happyaging.server.dto.admin.survey.ReadResponseDTO;
import happyaging.server.dto.admin.survey.ReadSurveyDTO;
import happyaging.server.dto.admin.user.CreateManagerDTO;
import happyaging.server.dto.admin.user.ReadManagerDTO;
import happyaging.server.dto.admin.user.ReadUserDTO;
import happyaging.server.dto.admin.util.PagingResponse;
import happyaging.server.dto.admin.util.StatisticDTO;
import happyaging.server.exception.AppException;
import happyaging.server.exception.errorcode.AppErrorCode;
import happyaging.server.repository.product.InstalledImageRepository;
import happyaging.server.repository.product.ProductRepository;
import happyaging.server.repository.senior.SeniorRepository;
import happyaging.server.repository.survey.SurveyRepository;
import happyaging.server.repository.user.UserRepository;
import happyaging.server.service.auth.AuthService;
import happyaging.server.service.response.ResponseService;
import happyaging.server.service.result.ResultService;
import happyaging.server.service.senior.SeniorService;
import happyaging.server.service.survey.SurveyService;
import happyaging.server.service.user.UserService;
import jakarta.validation.Valid;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

@RestController
@RequiredArgsConstructor
@Slf4j
@RequestMapping("admin")
public class AdminController {

    private final UserService userService;
    private final AuthService authService;
    private final ResultService resultService;
    private final SeniorService seniorService;
    private final SurveyService surveyService;
    private final ResponseService responseService;

    private final UserRepository userRepository;
    private final SeniorRepository seniorRepository;
    private final SurveyRepository surveyRepository;
    private final ProductRepository productRepository;
    private final InstalledImageRepository installedImageRepository;
    private final BCryptPasswordEncoder encoder;

    @Value("${file.static-image}")
    private String staticPath;

    @Transactional(readOnly = true)
    @GetMapping("/statistics")
    public StatisticDTO readStatistics() {
        return StatisticDTO.create(userRepository.count(), seniorRepository.count(), surveyRepository.count());
    }

    @Transactional
    @PostMapping("/user/manager")
    public Long createManager(@RequestBody @Valid CreateManagerDTO request) {
        authService.checkDuplicateEmail(request.getEmail());
        User user = User.createManager(request.getEmail(), request.getPassword(), request.getName(),
                request.getPhoneNumber(), encoder);
        userRepository.save(user);
        return user.getId();
    }

    @Transactional
    @DeleteMapping("/user/manager/{managerId}")
    public void deleteManager(@PathVariable Long managerId) {
        userService.deleteUser(managerId);
    }

    @Transactional
    @PutMapping("/user/manager/{managerId}")
    public void updateManager(@RequestBody @Valid CreateManagerDTO request, @PathVariable Long managerId) {
        User user = userRepository.findById(managerId)
                .orElseThrow(() -> new AppException(AppErrorCode.INVALID_USER));
        user.updateManager(request, encoder);
    }

    @Transactional(readOnly = true)
    @GetMapping("/user/manager")
    public PagingResponse<ReadManagerDTO> readManager(@RequestParam Integer page,
                                                      @RequestParam(required = false) String name) {
        Page<User> pageNumber = userRepository.findAllByNameContainingAndUserTypeOrderByCreatedAtDesc(name,
                UserType.MANAGER, PageRequest.of(page, 20));
        List<ReadManagerDTO> readManagerDTOS = pageNumber.getContent().stream()
                .map(ReadManagerDTO::create)
                .toList();
        return new PagingResponse<>(pageNumber.hasNext(), readManagerDTOS);
    }

    @Transactional(readOnly = true)
    @GetMapping("/user")
    public PagingResponse<ReadUserDTO> readUser(@RequestParam Integer page,
                                                @RequestParam(required = false) String name) {
        Page<User> pageNumber = userRepository.findAllByNameContainingOrderByCreatedAtDesc(name,
                PageRequest.of(page, 20));
        List<ReadUserDTO> readUserDTOS = pageNumber.getContent().stream()
                .map(ReadUserDTO::create)
                .toList();
        return new PagingResponse<>(pageNumber.hasNext(), readUserDTOS);
    }

    @Transactional(readOnly = true)
    @GetMapping("/senior")
    public PagingResponse<ReadSeniorDTO> readSenior(@RequestParam Integer page,
                                                    @RequestParam(required = false) String name) {
        Page<Senior> pageNumber = seniorRepository.findAllByUser_NameContainingAndUser_UserType(name,
                UserType.MANAGER, PageRequest.of(page, 20));
        List<ReadSeniorDTO> readSeniorDTOS = pageNumber.getContent().stream()
                .map(ReadSeniorDTO::create)
                .toList();
        return new PagingResponse<>(pageNumber.hasNext(), readSeniorDTOS);
    }

    @Transactional(readOnly = true)
    @GetMapping("/survey")
    public PagingResponse<ReadSurveyDTO> readSurvey(@RequestParam Integer page,
                                                    @RequestParam(required = false) LocalDate startDate,
                                                    @RequestParam(required = false) LocalDate endDate,
                                                    @RequestParam(required = false) String seniorName,
                                                    @RequestParam(required = false) String seniorAddress,
                                                    @RequestParam(required = false) String userName) {
        Page<Survey> pageNumber;
        if (startDate == null) {
            startDate = LocalDate.of(2024, 1, 1);
        }
        if (endDate == null) {
            endDate = LocalDate.now();
        }
        if (seniorName.isEmpty()) {
            pageNumber = surveyRepository.findAllBySenior_AddressContainingAndSenior_User_NameContainingAndDateBetweenOrderByDateDesc(
                    seniorAddress, userName, startDate, endDate, PageRequest.of(page, 20));
        } else {
            pageNumber = surveyRepository.findAllBySenior_NameAndSenior_AddressContainingAndSenior_User_NameContainingAndDateBetweenOrderByDateDesc(
                    seniorName, seniorAddress, userName, startDate, endDate, PageRequest.of(page, 20));
        }
        List<ReadSurveyDTO> readSurveyDTOS = pageNumber.getContent().stream()
                .map(survey -> {
                    Result result = resultService.findBySurvey(survey);
                    Senior senior = seniorService.findSeniorById(survey.getSenior().getId());
                    return ReadSurveyDTO.create(survey, result, senior);
                })
                .toList();
        return new PagingResponse<>(pageNumber.hasNext(), readSurveyDTOS);
    }

    @Transactional(readOnly = true)
    @GetMapping("/survey/{surveyId}")
    public List<ReadResponseDTO> readResponse(@PathVariable Long surveyId) {
        Survey survey = surveyService.findSurveyById(surveyId);
        List<Response> responses = responseService.findResponsesBySurvey(survey);
        return responses.stream().map(ReadResponseDTO::create)
                .toList();
    }

    @Transactional(readOnly = true)
    @GetMapping("/product")
    public List<ProductInfo> readProducts() {
        List<ProductInfo> productInfos = new ArrayList<>();

        List<Product> products = productRepository.findAll();
        for (Product product : products) {
            List<InstalledImage> installImages = installedImageRepository.findAllByProductId(product.getId());
            productInfos.add(ProductInfo.create(product, installImages));
        }
        return productInfos;
    }

    @Transactional
    @PostMapping("/install/{productId}")
    public ResponseEntity<Object> addInstallImage(@RequestParam("imageFiles") MultipartFile[] images,
                                                  @PathVariable Long productId) {
        Product product = productRepository.findById(productId)
                .orElseThrow(() -> new AppException(AppErrorCode.INVALID_PRODUCT));
        List<String> filePaths = uploadFiles(images);
        List<InstalledImage> installedImages = filePaths.stream()
                .map(image -> InstalledImage.create(image, product))
                .toList();
        installedImageRepository.saveAll(installedImages);
        return ResponseEntity.ok().build();
    }

    private List<String> uploadFiles(MultipartFile[] images) {
        List<String> filePaths = new ArrayList<>();
        String path = staticPath + "/install";
        try {
            for (MultipartFile image : images) {
                String fileName = image.getOriginalFilename();
                Path filePath = Paths.get(path, fileName);
                Files.copy(image.getInputStream(), filePath, StandardCopyOption.REPLACE_EXISTING);
                filePaths.add("http://3.37.58.59:8080/image/install/" + fileName);
            }
            return filePaths;
        } catch (IOException exception) {
            throw new AppException(AppErrorCode.CANNOT_SAVE_IMAGES);
        }
    }
}
