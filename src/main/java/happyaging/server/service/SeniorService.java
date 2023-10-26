package happyaging.server.service;

import happyaging.server.domain.Senior;
import happyaging.server.domain.User;
import happyaging.server.dto.SeniorRequestDTO;
import happyaging.server.repository.SeniorRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class SeniorService {
    private final SeniorRepository seniorRepository;

    @Transactional
    public void createSenior(User user, SeniorRequestDTO seniorRequestDTO) {
        Senior senior = Senior.builder()
                .name(seniorRequestDTO.getName())
                .sex(seniorRequestDTO.getSex())
                .birth(seniorRequestDTO.getBirth())
                .residence(seniorRequestDTO.getResidence())
                .address(seniorRequestDTO.getAddress())
                .profile(seniorRequestDTO.getProfile())
                .user(user)
                .build();
        seniorRepository.save(senior);
    }
}
