package happyaging.server.service;

import happyaging.server.domain.User;
import happyaging.server.repository.UserRepository;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.oauth2.client.userinfo.DefaultOAuth2UserService;
import org.springframework.security.oauth2.client.userinfo.OAuth2UserRequest;
import org.springframework.security.oauth2.core.OAuth2AuthenticationException;
import org.springframework.security.oauth2.core.user.DefaultOAuth2User;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class OAuth2UserService extends DefaultOAuth2UserService {

    private final UserRepository userRepository;

    @Override
    public OAuth2User loadUser(OAuth2UserRequest userRequest) throws OAuth2AuthenticationException {
        OAuth2User oAuth2User = super.loadUser(userRequest);
        Map<String, Object> attributes = oAuth2User.getAttributes();

        Map<String, Object> kakaoAccount = (Map<String, Object>) attributes.get("kakao_account");
        String email = (String) kakaoAccount.get("email");

        User user = userRepository.findByEmail(email)
                .orElseGet(() -> {
                    // 이메일이 일치하는 기존 사용자가 없으면 새로운 사용자를 생성합니다.
                    User newUser = User.builder()
                            .email(email)
                            .build();
                    return userRepository.save(newUser);
                });

        Map<String, Object> userAttribute = new HashMap<>();
        userAttribute.put("user", user);

        // TODO: 사용자가 새로 생성되었다면 추가정보 입력 페이지로 리다이렉션
        // TODO: 사용자가 돌봄 관리자라면 MANAGER 권한 부여

        return new DefaultOAuth2User(
                Collections.singleton(new SimpleGrantedAuthority("ROLE_USER")),
                userAttribute, "user");
    }
}
