package happyaging.server.service;

import happyaging.server.domain.User;
import happyaging.server.dto.user.LoginResponseToken;
import happyaging.server.dto.user.UserJoinRequestDTO;
import happyaging.server.repository.UserRepository;
import happyaging.server.security.JwtUtil;
import happyaging.server.utils.AppException;
import happyaging.server.utils.ErrorCode;
import java.time.LocalDate;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Slf4j
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
            throw new AppException(ErrorCode.EMAIL_DUPLICATED);
        }
    }

    public void checkDuplicateEmail(String email) {
        userRepository.findByEmail(email).ifPresent(user -> {
            throw new AppException(ErrorCode.EMAIL_DUPLICATED);
        });
    }

    public LoginResponseToken login(String email, String password) {
        User findUser = userRepository.findByEmail(email)
                .orElseThrow(() -> new AppException(ErrorCode.EMAIL_NOT_FOUND));

        if (!encoder.matches(password, findUser.getPassword())) {
            throw new AppException(ErrorCode.INVALID_PASSWORD);
        }

        return JwtUtil.createJwt(findUser);
    }

    public LoginResponseToken refresh(String refreshToken) {
        String email = JwtUtil.getUserEmail(refreshToken);
        User findUser = userRepository.findByEmail(email)
                .orElseThrow(() -> new AppException(ErrorCode.EMAIL_NOT_FOUND));

        return JwtUtil.createJwt(findUser);
    }
}
