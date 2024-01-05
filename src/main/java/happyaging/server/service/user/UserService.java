package happyaging.server.service.user;

import happyaging.server.domain.user.User;
import happyaging.server.repository.user.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class UserService {
    private final UserRepository userRepository;

    @Transactional(readOnly = true)
    public User getUserByEmail(String email) {
        return userRepository.findByEmail(email)
                .orElseThrow(() -> new IllegalArgumentException("cannot find user"));
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
