package happyaging.server.controller.senior;

import happyaging.server.domain.image.Location;
import happyaging.server.domain.product.InstalledImage;
import happyaging.server.domain.product.Product;
import happyaging.server.domain.product.Recommend;
import happyaging.server.domain.senior.Senior;
import happyaging.server.dto.admin.senior.ReadSeniorImageDTO;
import happyaging.server.dto.product.ReadRecommendResponseDTO;
import happyaging.server.dto.senior.ImageResponseDTO;
import happyaging.server.dto.senior.SeniorRequestDTO;
import happyaging.server.dto.senior.SeniorResponseDTO;
import happyaging.server.repository.product.InstalledImageRepository;
import happyaging.server.service.product.ProductService;
import happyaging.server.service.product.RecommendService;
import happyaging.server.service.senior.SeniorService;
import happyaging.server.service.user.UserService;
import jakarta.validation.Valid;
import java.util.List;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
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
@RequestMapping("senior")
@Slf4j
public class SeniorController {
    private final UserService userService;
    private final SeniorService seniorService;
    private final ProductService productService;
    private final RecommendService recommendService;

    private final InstalledImageRepository installedImageRepository;

    @PostMapping
    public ResponseEntity<Long> createSenior(@RequestBody @Valid SeniorRequestDTO seniorRequestDTO) {
        Long userId = userService.readCurrentUserId();
        Senior senior = seniorService.createSenior(userId, seniorRequestDTO);
        List<Product> products = productService.findAllProduct();
        recommendService.recommendAllProduct(senior, products);
        return ResponseEntity.ok().body(senior.getId());
    }

    @PutMapping("/{seniorId}")
    public ResponseEntity<Object> updateSenior(@PathVariable Long seniorId,
                                               @RequestBody @Valid SeniorRequestDTO seniorRequestDTO) {
        seniorService.updateSenior(seniorId, seniorRequestDTO);
        return ResponseEntity.ok().build();
    }

    @DeleteMapping("/{seniorId}")
    public ResponseEntity<Object> deleteSenior(@PathVariable Long seniorId) {
        seniorService.deleteSenior(seniorId);
        return ResponseEntity.ok().build();
    }

    @GetMapping
    public List<SeniorResponseDTO> getSeniorList() {
        Long userId = userService.readCurrentUserId();
        return seniorService.readSeniors(userId);
    }

    @GetMapping("/image")
    public List<ImageResponseDTO> readImage() {
        return seniorService.readExampleImage();
    }

    @PutMapping("/image/{seniorId}")
    public ResponseEntity<Object> uploadImage(@RequestParam("location") String location,
                                              @RequestParam("imageFiles") MultipartFile[] imageFiles,
                                              @PathVariable Long seniorId) {
        seniorService.saveSeniorImages(seniorId, location, imageFiles);
        return ResponseEntity.ok().build();
    }

    @GetMapping("/image/{seniorId}")
    public List<ReadSeniorImageDTO> readSeniorImage(@PathVariable Long seniorId) {
        Senior senior = seniorService.findSeniorById(seniorId);
        return seniorService.readSeniorImages(senior);
    }

    private ReadRecommendResponseDTO createReadRecommendResponseDTO(Recommend recommend) {
        Product product = recommend.getProduct();
        List<InstalledImage> installedImages = installedImageRepository.findAllByProductId(product.getId());
        List<String> imagesPath = installedImages.stream()
                .map(InstalledImage::getImage)
                .toList();
        return ReadRecommendResponseDTO.create(product.getName(), product.getDescription(),
                product.getImage(),
                imagesPath);
    }

    @GetMapping("/{seniorId}/{location}")
    public List<ReadRecommendResponseDTO> readRecommendProduct(@PathVariable Long seniorId,
                                                               @PathVariable Location location) {
        Senior senior = seniorService.findSeniorById(seniorId);
        List<Recommend> recommends = recommendService.findALLBySeniorAndLocation(senior, location);
        return recommends.stream()
                .map(this::createReadRecommendResponseDTO)
                .toList();
    }

}
