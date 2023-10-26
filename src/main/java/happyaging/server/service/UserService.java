package happyaging.server.service;

import happyaging.server.domain.User;
import happyaging.server.exception.AppException;
import happyaging.server.exception.ErrorCode;
import happyaging.server.repository.UserRepository;
import happyaging.server.utils.JwtUtil;
import java.time.LocalDate;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class UserService {
    private final UserRepository userRepository;
    private final BCryptPasswordEncoder encoder;

    @Value("${jwt.secret}")
    private String key;
    private Long expireTimeMs = 1000 * 60 * 60L;

    @Transactional(readOnly = true)
    public User getUserByEmail(String email) {
        return userRepository.findByEmail(email)
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

    public String login(String email, String password) {
        // useremail 없음
        User selectedUser = userRepository.findByEmail(email)
                .orElseThrow(() -> new AppException(ErrorCode.EMAIL_NOT_FOUND, email + "이 없습니다."));

        // password 틀림
        if (!encoder.matches(password, selectedUser.getPassword())) {
            throw new AppException(ErrorCode.INVALID_PASSWORD, "패스워드를 잘못 입력 했습니다");
        }

        return JwtUtil.createJwt(selectedUser.getEmail(), key, expireTimeMs);
    }
}
