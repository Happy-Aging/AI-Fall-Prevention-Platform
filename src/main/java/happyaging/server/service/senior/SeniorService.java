package happyaging.server.service.senior;

import happyaging.server.domain.senior.Senior;
import happyaging.server.domain.survey.Survey;
import happyaging.server.domain.user.User;
import happyaging.server.dto.senior.SeniorRequestDTO;
import happyaging.server.dto.senior.SeniorResponseDTO;
import happyaging.server.exception.AppException;
import happyaging.server.exception.errorcode.AppErrorCode;
import happyaging.server.repository.senior.SeniorRepository;
import happyaging.server.repository.survey.SurveyRepository;
import happyaging.server.repository.user.UserRepository;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import java.util.Collections;
import java.util.List;
import java.util.Optional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class SeniorService {

    private final UserRepository userRepository;
    private final SeniorRepository seniorRepository;
    private final SurveyRepository surveyRepository;

    @PersistenceContext
    private EntityManager entityManager;

    @Transactional
    public Long createSenior(Long userId, SeniorRequestDTO seniorRequestDTO) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new AppException(AppErrorCode.INVALID_USER));
        Senior senior = Senior.create(user, seniorRequestDTO);
        seniorRepository.save(senior);
        return senior.getId();
    }

    @Transactional
    public void updateSenior(Long seniorId, SeniorRequestDTO seniorRequestDTO) {
        Senior senior = findSeniorById(seniorId);
        senior.update(seniorRequestDTO);
    }

    @Transactional
    public void deleteSenior(Long seniorId) {
        Senior senior = findSeniorById(seniorId);
        surveyRepository.findBySeniorId(seniorId)
                .forEach(Survey::deleteSenior);
        entityManager.flush();

        seniorRepository.delete(senior);
    }

    @Transactional(readOnly = true)
    public List<SeniorResponseDTO> readSeniors(Long userId) {
        List<Senior> seniors = findSeniorByUserId(userId);
        return seniors.stream()
                .map(SeniorResponseDTO::create)
                .toList();
    }

    public List<Senior> findSeniorByUserId(Long userId) {
        List<Senior> seniors = seniorRepository.findByUserId(userId);
        return Optional.ofNullable(seniors)
                .orElseGet(Collections::emptyList);
    }

    private Senior findSeniorById(Long seniorId) {
        return seniorRepository.findById(seniorId)
                .orElseThrow(() -> new AppException(AppErrorCode.INVALID_SENIOR));
    }
}
