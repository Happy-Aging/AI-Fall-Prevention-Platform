package happyaging.server.service.user;

import happyaging.server.domain.user.User;
import happyaging.server.dto.user.UserInfoDTO;
import happyaging.server.dto.user.UserInfoUpdateDTO;
import happyaging.server.exception.AppException;
import happyaging.server.exception.errorcode.AppErrorCode;
import happyaging.server.repository.user.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class UserService {

    private final UserRepository userRepository;
    private final BCryptPasswordEncoder encoder;

    @Transactional(readOnly = true)
    public UserInfoDTO findUserInfo(Long userId) {
        User user = findUserById(userId);
        return UserInfoDTO.create(user);
    }

    @Transactional
    public void updateUserInfo(Long userId, UserInfoUpdateDTO userInfoUpdateDTO) {
        User user = findUserById(userId);
        user.update(userInfoUpdateDTO, encoder);
    }

    @Transactional
    public void deleteUser(Long userId) {
        User user = findUserById(userId);
        userRepository.delete(user);
    }

    public Long readCurrentUserId() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (authentication != null && authentication.isAuthenticated()) {
            return (Long) authentication.getPrincipal();
        }
        return null;
    }

    public User findUserById(Long userId) {
        return userRepository.findById(userId)
                .orElseThrow(() -> new AppException(AppErrorCode.INVALID_USER));
    }
}
