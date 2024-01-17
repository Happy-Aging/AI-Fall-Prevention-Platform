package happyaging.server.controller.senior;

import happyaging.server.dto.senior.ImageResponseDTO;
import happyaging.server.dto.senior.SeniorRequestDTO;
import happyaging.server.dto.senior.SeniorResponseDTO;
import happyaging.server.service.senior.SeniorService;
import happyaging.server.service.user.UserService;
import jakarta.validation.Valid;
import java.util.List;
import lombok.RequiredArgsConstructor;
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
public class SeniorController {
    private final UserService userService;
    private final SeniorService seniorService;

    @PostMapping
    public ResponseEntity<Long> createSenior(@RequestBody @Valid SeniorRequestDTO seniorRequestDTO) {
        Long userId = userService.readCurrentUserId();
        Long seniorId = seniorService.createSenior(userId, seniorRequestDTO);
        return ResponseEntity.ok().body(seniorId);
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

}
