package happyaging.server.service;

import happyaging.server.domain.Senior;
import happyaging.server.domain.Survey;
import happyaging.server.domain.User;
import happyaging.server.dto.senior.SeniorRequestDTO;
import happyaging.server.dto.senior.SeniorResponseDTO;
import happyaging.server.repository.SeniorRepository;
import java.time.LocalDate;
import java.time.Period;
import java.util.Comparator;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class SeniorService {
    private final SeniorRepository seniorRepository;

//    @Transactional
//    public void createSenior(User user, SeniorRequestDTO seniorRequestDTO) {
//        Senior senior = Senior.builder()
//                .name(seniorRequestDTO.getName())
//                .sex(seniorRequestDTO.getSex())
//                .birth(seniorRequestDTO.getBirth())
//                .residence(seniorRequestDTO.getResidence())
//                .address(seniorRequestDTO.getAddress())
//                .profile(seniorRequestDTO.getProfile())
//                .user(user)
//                .build();
//        seniorRepository.save(senior);
//    }

    @Transactional
    public SeniorResponseDTO createSenior(SeniorRequestDTO seniorRequestDTO) {
        Senior senior = Senior.builder()
                .name(seniorRequestDTO.getName())
                .sex(seniorRequestDTO.getSex())
                .birth(seniorRequestDTO.getBirth())
                .residence(seniorRequestDTO.getResidence())
                .address(seniorRequestDTO.getAddress())
                .profile(seniorRequestDTO.getProfile())
                .user(null)
                .build();
        seniorRepository.save(senior);
        return SeniorResponseDTO.builder()
                .id(senior.getId())
                .name(senior.getName())
                .build();
    }

    @Transactional(readOnly = true)
    public List<SeniorResponseDTO> getSeniorList(User user) {
        List<Senior> seniorList = user.getSeniorList();

        return seniorList.stream()
                .map(senior -> {
                    return SeniorResponseDTO.builder()
                            .id(senior.getId())
                            .name(senior.getName())
                            .age(calculateAge(senior.getBirth()))
                            .address((senior.getAddress()))
                            .profile(senior.getProfile())
                            .rank(getRank(senior))
                            .build();
                })
                .toList();
    }

    @Transactional(readOnly = true)
    public Senior findSenior(Long seniorId) {
        return seniorRepository.findById(seniorId)
                .orElseThrow(() -> new IllegalArgumentException("cannot find senior"));
    }

    private Integer calculateAge(LocalDate birth) {
        LocalDate today = LocalDate.now();
        Period period = Period.between(birth, today);
        return period.getYears();
    }

    private Integer getRank(Senior senior) {
        Survey mostRecentSurvey = getMostRecentSurvey(senior);
        if (mostRecentSurvey == null) {
            return null;
        }
        if (mostRecentSurvey.getResult() == null) {
            return null;
        }
        return mostRecentSurvey.getResult().getRank();
    }

    private Survey getMostRecentSurvey(Senior senior) {
        return senior.getSurveyList().stream()
                .max(Comparator.comparing(Survey::getDate))
                .orElse(null);
    }

    public void updateSenior(Long seniorId, SeniorRequestDTO seniorRequestDTO) {
        Senior existingSenior = seniorRepository.findById(seniorId)
                .orElseThrow(() -> new IllegalArgumentException("cannot find senior"));

        existingSenior.update(seniorRequestDTO);
        seniorRepository.save(existingSenior);
    }

    public void deleteSenior(Long seniorId) {
        Senior existingSenior = seniorRepository.findById(seniorId)
                .orElseThrow(() -> new IllegalArgumentException("cannot find senior"));
        seniorRepository.delete(existingSenior);
    }
}
