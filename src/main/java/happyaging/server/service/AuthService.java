package happyaging.server.service;

import happyaging.server.domain.User;
import happyaging.server.domain.Vendor;
import happyaging.server.dto.LoginFailureDTO;
import happyaging.server.dto.auth.SocialLoginRequestDTO;
import happyaging.server.repository.UserRepository;
import happyaging.server.security.JwtUtil;
import happyaging.server.utils.AppException;
import happyaging.server.utils.AuthErrorCode;
import java.util.Map;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.RestTemplate;

@Service
@RequiredArgsConstructor
public class AuthService {

    private final UserRepository userRepository;

    @Transactional(readOnly = true)
    public String requestEmail(SocialLoginRequestDTO socialLoginRequestDTO) {

        String accessToken = socialLoginRequestDTO.getAccessToken();

        if (accessToken != null && accessToken.startsWith("Bearer ")) {
            RestTemplate restTemplate = new RestTemplate();
            HttpHeaders headers = new HttpHeaders();
            headers.set("Authorization", accessToken);
            HttpEntity<String> entity = new HttpEntity<>(headers);

            String url = socialLoginRequestDTO.getVendor().getUrl();
            try {
                ResponseEntity<Map> vendorResponse = restTemplate.exchange(
                        url, HttpMethod.GET, entity, Map.class);

                Map<String, Object> kakaoInformation = vendorResponse.getBody();
                Map<String, Object> kakaoAccount = (Map<String, Object>) kakaoInformation.get("kakao_account");

                return (String) kakaoAccount.get("email");
            } catch (NullPointerException | HttpClientErrorException e) {
                throw new AppException(AuthErrorCode.EXTERNAL_SERVER);
            }
        }
        throw new AppException(AuthErrorCode.INVALID_ACCESS_TOKEN);
    }

    @Transactional(readOnly = true)
    public ResponseEntity<?> login(String email, Vendor vendor) {
        User user = userRepository.findByEmail(email)
                .orElse(null);

        if (user != null) {
            if (user.getVendor() != vendor) {
                throw new AppException(AuthErrorCode.INVALID_LOGIN_METHOD);
            }
            return ResponseEntity.ok(JwtUtil.createJwt(user));
        }
        return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(new LoginFailureDTO(email, vendor));
    }
}
