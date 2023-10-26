package happyaging.server.service;

import happyaging.server.domain.User;
import happyaging.server.exception.AppException;
import happyaging.server.exception.ErrorCode;
import happyaging.server.repository.UserRepository;
import java.time.LocalDate;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class UserService {
    private final UserRepository userRepository;
    private final BCryptPasswordEncoder encoder;

    @Transactional(readOnly = true)
    public User getUserById(Long accountId) {
        return userRepository.findById(accountId)
                .orElseThrow(() -> new IllegalArgumentException("cannot find account"));
    }

    public String join(String email, String password, String nickname) {
        // email 중복 체크
        userRepository.findByEmail(email)
                .ifPresent(user -> {
                    throw new AppException(ErrorCode.EMAIl_DUPLICATED, email + "는 이미 있습니다.");
                });

        LocalDate today = LocalDate.now();
        // 저장
        User user = User.builder()
                .email(email)
                .password(encoder.encode(password))
                .email(email)
                .nickname(nickname)
                .createdAt(today)
                .isManager(false)
                .build();
        userRepository.save(user);
        return "SUCCESS";
    }
}
