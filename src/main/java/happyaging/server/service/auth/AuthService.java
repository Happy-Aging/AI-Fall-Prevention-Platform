package happyaging.server.service.auth;

import happyaging.server.domain.user.User;
import happyaging.server.domain.user.Vendor;
import happyaging.server.dto.auth.LoginFailureDTO;
import happyaging.server.dto.auth.LoginSuccessDTO;
import happyaging.server.dto.auth.SocialLoginRequestDTO;
import happyaging.server.exception.AppException;
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
    public String requestEmail(SocialLoginRequestDTO socialLoginRequestDTO) {
        String accessToken = socialLoginRequestDTO.getAccessToken();

        if (accessToken != null && accessToken.startsWith("Bearer ")) {
            String url = socialLoginRequestDTO.getVendor().getUrl();
            HttpEntity<String> header = createHeader(accessToken);
            return requestEmail(url, header);
        }
        throw new AppException(AuthErrorCode.INVALID_ACCESS_TOKEN);
    }

    @Transactional(readOnly = true)
    public ResponseEntity<?> socialLogin(String email, Vendor vendor) {
        User user = userRepository.findByEmail(email).orElse(null);
        if (user != null) {
            checkLoginMethod(user, vendor);
            return ResponseEntity.ok(JwtUtil.createJwt(user));
        }
        return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(new LoginFailureDTO(email, vendor));
    }

    @Transactional(readOnly = true)
    public LoginSuccessDTO login(String email, String password) {
        User findUser = findUserOrException(email);
        comparePassword(password, findUser);
        return JwtUtil.createJwt(findUser);
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

    private void comparePassword(String password, User findUser) {
        if (!encoder.matches(password, findUser.getPassword())) {
            throw new AppException(AuthErrorCode.INVALID_USER);
        }
    }

    private User findUserOrException(String email) {
        return userRepository.findByEmail(email)
                .orElseThrow(() -> new AppException(AuthErrorCode.INVALID_USER));
    }
}
