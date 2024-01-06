package happyaging.server.service.auth;

import happyaging.server.domain.user.User;
import happyaging.server.domain.user.Vendor;
import happyaging.server.dto.auth.JoinRequestDTO;
import happyaging.server.dto.auth.LoginFailureDTO;
import happyaging.server.dto.auth.LoginSuccessDTO;
import happyaging.server.dto.auth.SocialJoinRequestDTO;
import happyaging.server.dto.auth.SocialLoginRequestDTO;
import happyaging.server.exception.AppException;
import happyaging.server.exception.errorcode.AppErrorCode;
import happyaging.server.exception.errorcode.AuthErrorCode;
import happyaging.server.repository.user.UserRepository;
import happyaging.server.security.JwtUtil;
import java.util.Map;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.RestTemplate;

@Service
@RequiredArgsConstructor
public class AuthService {

    private final UserRepository userRepository;
    private final BCryptPasswordEncoder encoder;

    @Transactional(readOnly = true)
    public LoginSuccessDTO login(String email, String password) {
        User user = findUserByEmail(email);
        checkLoginMethod(user, Vendor.HAPPY_AGING);
        comparePassword(password, user.getPassword());
        return JwtUtil.createTokens(user);
    }

    @Transactional(readOnly = true)
    public ResponseEntity<?> socialLogin(SocialLoginRequestDTO socialLoginRequestDTO) {
        String email = getEmailFromExternalServer(socialLoginRequestDTO);
        User user = userRepository.findByEmail(email).orElse(null);
        Vendor vendor = socialLoginRequestDTO.getVendor();
        if (user != null) {
            checkLoginMethod(user, vendor);
            return ResponseEntity.ok(JwtUtil.createTokens(user));
        }
        return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(new LoginFailureDTO(email, vendor));
    }

    @Transactional
    public LoginSuccessDTO join(JoinRequestDTO userJoinRequestDTO) {
        checkDuplicateEmail(userJoinRequestDTO.getEmail());
        User user = User.createFromJoin(userJoinRequestDTO, encoder);
        userRepository.save(user);
        return JwtUtil.createTokens(user);
    }

    @Transactional
    public LoginSuccessDTO socialJoin(SocialJoinRequestDTO socialJoinRequestDTO) {
        checkDuplicateEmail(socialJoinRequestDTO.getEmail());
        User user = User.createFromSocialJoin(socialJoinRequestDTO);
        userRepository.save(user);
        return JwtUtil.createTokens(user);
    }

    public LoginSuccessDTO checkRefreshToken(String refreshToken) {
        Long userId = JwtUtil.getUserIdFromToken(refreshToken);
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new AppException(AuthErrorCode.INVALID_TOKEN));

        if (JwtUtil.isExpired(refreshToken)) {
            throw new AppException(AuthErrorCode.INVALID_TOKEN);
        }
        return JwtUtil.createTokens(user);
    }

    private String getEmailFromExternalServer(SocialLoginRequestDTO socialLoginRequestDTO) {
        String accessToken = socialLoginRequestDTO.getAccessToken();
        if (accessToken != null) {
            String url = socialLoginRequestDTO.getVendor().getUrl();
            HttpEntity<String> header = createHeader("Bearer " + accessToken);
            return requestEmail(url, header);
        }
        throw new AppException(AuthErrorCode.INVALID_EXTERNAL_TOKEN);
    }

    private void checkDuplicateEmail(String email) {
        userRepository.findByEmail(email).ifPresent(user -> {
            throw new AppException(AuthErrorCode.EMAIL_DUPLICATED);
        });
    }

    private static String requestEmail(String url, HttpEntity<String> entity) {
        try {
            RestTemplate restTemplate = new RestTemplate();
            ResponseEntity<Map> vendorResponse = restTemplate.exchange(
                    url, HttpMethod.GET, entity, Map.class);

            Map<String, Object> kakaoInformation = vendorResponse.getBody();
            Map<String, Object> kakaoAccount = (Map<String, Object>) kakaoInformation.get("kakao_account");

            return (String) kakaoAccount.get("email");
        } catch (NullPointerException | HttpClientErrorException e) {
            throw new AppException(AuthErrorCode.EXTERNAL_SERVER);
        }
    }

    private HttpEntity<String> createHeader(String accessToken) {
        HttpHeaders headers = new HttpHeaders();
        headers.set("Authorization", accessToken);
        return new HttpEntity<>(headers);
    }

    private static void checkLoginMethod(User user, Vendor vendor) {
        if (user.getVendor() != vendor) {
            throw new AppException(AuthErrorCode.INVALID_LOGIN_METHOD);
        }
    }

    private void comparePassword(String password, String encodedPassword) {
        if (!encoder.matches(password, encodedPassword)) {
            throw new AppException(AppErrorCode.INVALID_USER);
        }
    }

    private User findUserByEmail(String email) {
        return userRepository.findByEmail(email)
                .orElseThrow(() -> new AppException(AppErrorCode.INVALID_USER));
    }

}
