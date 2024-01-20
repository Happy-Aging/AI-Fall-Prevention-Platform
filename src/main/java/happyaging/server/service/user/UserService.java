package happyaging.server.service.user;

import happyaging.server.domain.senior.Senior;
import happyaging.server.domain.user.User;
import happyaging.server.dto.user.UserInfoDTO;
import happyaging.server.dto.user.UserInfoUpdateDTO;
import happyaging.server.exception.AppException;
import happyaging.server.exception.errorcode.AppErrorCode;
import happyaging.server.repository.senior.SeniorRepository;
import happyaging.server.repository.user.UserRepository;
import happyaging.server.service.senior.SeniorService;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import java.security.SecureRandom;
import lombok.RequiredArgsConstructor;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.scheduling.annotation.Async;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class UserService {

    private static final int TEMP_PASSWORD_LENGTH = 8;
    private final JavaMailSender emailSender;
    private final SeniorService seniorService;
    private final UserRepository userRepository;
    private final SeniorRepository seniorRepository;
    private final BCryptPasswordEncoder encoder;

    @PersistenceContext
    private EntityManager entityManager;

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
        seniorRepository.findByUserId(userId).stream()
                .map(Senior::getId)
                .forEach(seniorService::deleteSenior);
        entityManager.flush();

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

    @Transactional(readOnly = true)
    public User findUserById(Long userId) {
        return userRepository.findById(userId)
                .orElseThrow(() -> new AppException(AppErrorCode.INVALID_USER));
    }

    @Transactional(readOnly = true)
    public String findEmail(String name, String phoneNumber) {
        User user = userRepository.findByNameAndPhoneNumber(name, phoneNumber);
        if (user == null) {
            throw new AppException(AppErrorCode.INVALID_ACCOUNT);
        }
        return user.getEmail();
    }

    @Transactional(readOnly = true)
    public User findUserByEmail(String email) {
        return userRepository.findByEmail(email)
                .orElseThrow(() -> new AppException(AppErrorCode.INVALID_ACCOUNT));
    }

    @Transactional
    public String createNewPassword(User user) {
        String temporaryPassword = generateRandomPassword();
        String encodedPassword = encoder.encode(temporaryPassword);
        user.updatePassword(encodedPassword);
        return temporaryPassword;
    }

    private String generateRandomPassword() {
        SecureRandom random = new SecureRandom();
        String characters = "ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz0123456789";
        StringBuilder password = new StringBuilder(TEMP_PASSWORD_LENGTH);
        for (int i = 0; i < TEMP_PASSWORD_LENGTH; i++) {
            password.append(characters.charAt(random.nextInt(characters.length())));
        }
        return password.toString();
    }

    @Async
    public void sendEmail(String to, String temporaryPassword) {
        SimpleMailMessage message = new SimpleMailMessage();
        message.setTo(to);
        message.setSubject("[해피에이징] 비밀번호 재발급 안내");
        message.setText(
                "새로 생성된 비밀번호 입니다: " + temporaryPassword + "\n\n해당 비밀번호로 로그인 후 반드시 비밀번호를 변경해 주시기 바랍니다.");
        emailSender.send(message);
    }
}
