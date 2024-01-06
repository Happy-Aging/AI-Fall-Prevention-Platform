package happyaging.server.service.senior;

import happyaging.server.domain.senior.Senior;
import happyaging.server.domain.user.User;
import happyaging.server.dto.senior.SeniorRequestDTO;
import happyaging.server.exception.AppException;
import happyaging.server.exception.errorcode.AppErrorCode;
import happyaging.server.repository.senior.SeniorRepository;
import happyaging.server.service.user.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class SeniorService {
    private final UserService userService;

    private final SeniorRepository seniorRepository;

    @Transactional
    public Long createSenior(Long userId, SeniorRequestDTO seniorRequestDTO) {
        User user = userService.findUserById(userId);
        Senior senior = Senior.create(user, seniorRequestDTO);
        seniorRepository.save(senior);
        return senior.getId();
    }

    @Transactional
    public void updateSenior(Long seniorId, SeniorRequestDTO seniorRequestDTO) {
        Senior senior = findSeniorById(seniorId);
        senior.update(seniorRequestDTO);
    }

    private Senior findSeniorById(Long seniorId) {
        return seniorRepository.findById(seniorId)
                .orElseThrow(() -> new AppException(AppErrorCode.INVALID_SENIOR));
    }
//
//    @Transactional(readOnly = true)
//    public List<SeniorResponseDTO> getSeniorList(User user) {
//        List<Senior> seniorList = user.getSeniorList();
//
//        return seniorList.stream()
//                .map(senior -> {
//                    return SeniorResponseDTO.builder()
//                            .id(senior.getId())
//                            .name(senior.getName())
//                            .age(calculateAge(senior.getBirth()))
//                            .address((senior.getAddress()))
//                            .profile(senior.getProfile())
//                            .rank(getRank(senior))
//                            .build();
//                })
//                .toList();
//    }
//
//    @Transactional(readOnly = true)
//    public Senior findSenior(Long seniorId) {
//        return seniorRepository.findById(seniorId)
//                .orElseThrow(() -> new IllegalArgumentException("cannot find senior"));
//    }
//
//    private Integer calculateAge(LocalDate birth) {
//        LocalDate today = LocalDate.now();
//        Period period = Period.between(birth, today);
//        return period.getYears();
//    }
//
//    private Integer getRank(Senior senior) {
//        Survey mostRecentSurvey = getMostRecentSurvey(senior);
//        if (mostRecentSurvey == null) {
//            return null;
//        }
//        if (mostRecentSurvey.getResult() == null) {
//            return null;
//        }
//        return mostRecentSurvey.getResult().getRank();
//    }
//
//    private Survey getMostRecentSurvey(Senior senior) {
//        return senior.getSurveyList().stream()
//                .max(Comparator.comparing(Survey::getDate))
//                .orElse(null);

//    }
//
//    public void deleteSenior(Long seniorId) {
//        Senior existingSenior = seniorRepository.findById(seniorId)
//                .orElseThrow(() -> new IllegalArgumentException("cannot find senior"));
//        seniorRepository.delete(existingSenior);
//    }
}
