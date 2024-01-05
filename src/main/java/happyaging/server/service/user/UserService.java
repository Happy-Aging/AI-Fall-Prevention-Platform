package happyaging.server.service.user;

import happyaging.server.domain.user.User;
import happyaging.server.dto.user.UserJoinRequestDTO;
import happyaging.server.exception.AppException;
import happyaging.server.exception.errorcode.AuthErrorCode;
import happyaging.server.repository.user.UserRepository;
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

    // user가 없음 -> 404
    // 있는데 만료 -> 404
    // 있는데 만료 x -> create
//    public LoginSuccessDTO refresh(String refreshToken) {
//        String email = JwtUtil.getUserIdFromToken(refreshToken);
//        User findUser = userRepository.findByEmail(email)
//                .orElseThrow(() -> new AppException(AuthErrorCode.INVALID_USER));
//
//        // user 있는지 찾고
//        // 만료여부 체크
//        return JwtUtil.createJwt(findUser);
//    }
}
