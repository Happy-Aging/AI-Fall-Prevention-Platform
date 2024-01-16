package happyaging.server.controller.admin;

import happyaging.server.domain.admin.PagingResponse;
import happyaging.server.domain.user.User;
import happyaging.server.dto.admin.ManagerCreateRequestDTO;
import happyaging.server.dto.admin.ManagerReadResponseDTO;
import happyaging.server.dto.admin.StatisticDTO;
import happyaging.server.exception.AppException;
import happyaging.server.exception.errorcode.AppErrorCode;
import happyaging.server.repository.senior.SeniorRepository;
import happyaging.server.repository.survey.SurveyRepository;
import happyaging.server.repository.user.UserRepository;
import happyaging.server.service.user.UserService;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
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

@RestController
@RequiredArgsConstructor
@RequestMapping("admin")
public class AdminController {

    private final UserService userService;

    private final UserRepository userRepository;
    private final SeniorRepository seniorRepository;
    private final SurveyRepository surveyRepository;
    private final BCryptPasswordEncoder encoder;

    @Transactional(readOnly = true)
    @GetMapping("/statistics")
    public StatisticDTO readStatistics() {
        return StatisticDTO.create(userRepository.count(), seniorRepository.count(), surveyRepository.count());
    }

    @Transactional
    @PostMapping("/user/manager")
    public Long createManager(@RequestBody ManagerCreateRequestDTO request) {
        User user = User.createManager(request.getEmail(), request.getPassword(), request.getName(),
                request.getPhoneNumber());
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
    public void updateManager(@RequestBody ManagerCreateRequestDTO request, @PathVariable Long managerId) {
        User user = userRepository.findById(managerId)
                .orElseThrow(() -> new AppException(AppErrorCode.INVALID_USER));
        user.updateManager(request, encoder);
    }

    @Transactional(readOnly = true)
    @GetMapping("/user/manager")
    public PagingResponse<ManagerReadResponseDTO> readManager(@RequestParam Integer page,
                                                              @RequestParam(required = false) String name) {
        Page<User> pageNumber = userRepository.findAllByNameContaining(name, PageRequest.of(page, 20));
        List<ManagerReadResponseDTO> managerReadResponseDTOS = pageNumber.getContent().stream()
                .map(ManagerReadResponseDTO::create)
                .toList();
        return new PagingResponse<>(pageNumber.hasNext(), managerReadResponseDTOS);
    }
}
