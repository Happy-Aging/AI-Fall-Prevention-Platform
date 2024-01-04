package happyaging.server.service.user;

import happyaging.server.domain.user.User;
import happyaging.server.dto.auth.LoginSuccessDTO;
import happyaging.server.dto.user.UserJoinRequestDTO;
import happyaging.server.repository.user.UserRepository;
import happyaging.server.security.JwtUtil;
import happyaging.server.exception.AppException;
import happyaging.server.exception.errorcode.AuthErrorCode;
import java.time.LocalDate;
import lombok.RequiredArgsConstructor;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class UserService {
    private final UserRepository userRepository;
    private final BCryptPasswordEncoder encoder;

    @Transactional(readOnly = true)
    public User getUserByEmail(String email) {
        return userRepository.findByEmail(email)
                .orElseThrow(() -> new IllegalArgumentException("cannot find user"));
    }

    @Transactional
    public void join(UserJoinRequestDTO userJoinRequestDTO) {
        try {
            User user = User.builder()
                    .name(userJoinRequestDTO.getName())
                    .email(userJoinRequestDTO.getEmail())
                    .password(encoder.encode(userJoinRequestDTO.getPassword()))
                    .phoneNumber(userJoinRequestDTO.getPhoneNumber())
                    .userType(userJoinRequestDTO.getUserType())
                    .createdAt(LocalDate.now())
                    .build();
            userRepository.save(user);
        } catch (DataIntegrityViolationException exception) {
            throw new AppException(AuthErrorCode.EMAIL_DUPLICATED);
        }
    }

    public void checkDuplicateEmail(String email) {
        userRepository.findByEmail(email).ifPresent(user -> {
            throw new AppException(AuthErrorCode.EMAIL_DUPLICATED);
        });
    }

    public LoginSuccessDTO login(String email, String password) {
        User findUser = userRepository.findByEmail(email)
                .orElseThrow(() -> new AppException(AuthErrorCode.INVALID_USER));

        if (!encoder.matches(password, findUser.getPassword())) {
            throw new AppException(AuthErrorCode.INVALID_USER);
        }

        return JwtUtil.createJwt(findUser);
    }

    public LoginSuccessDTO refresh(String refreshToken) {
        String email = JwtUtil.getUserEmail(refreshToken);
        User findUser = userRepository.findByEmail(email)
                .orElseThrow(() -> new AppException(AuthErrorCode.INVALID_USER));

        return JwtUtil.createJwt(findUser);
    }
}
