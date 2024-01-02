package happyaging.server.utils;

import happyaging.server.domain.User;
import happyaging.server.dto.user.LoginResponseToken;
import happyaging.server.exception.AppException;
import happyaging.server.exception.ErrorCode;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import java.util.Date;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Slf4j
@Component
public class JwtUtil {

    private static final Long EXPIRE_TIME_MS = 1000 * 60 * 60L;
    private static String secretKey;

    @Value("${jwt.secret}")
    public void setSecretKey(String secret) {
        JwtUtil.secretKey = secret;
    }

    public static String getUserEmail(String token) {
        try {
            return Jwts.parser().setSigningKey(secretKey).parseClaimsJws(token)
                    .getBody().get("userEmail", String.class);
        } catch (ExpiredJwtException e) {
            throw new AppException(ErrorCode.TOKEN_EXPIRED);
        }

    }

    public static String getTokenType(String token) {
        return Jwts.parser().setSigningKey(secretKey).parseClaimsJws(token)
                .getBody().get("type", String.class);
    }

    public static boolean isExpired(String token) {
        try {
            Date expirationDate = Jwts.parser().setSigningKey(secretKey).parseClaimsJws(token)
                    .getBody().getExpiration();
            log.info("Token Expiration Time: {}", expirationDate);
            log.info("Current Time: {}", new Date());

            return Jwts.parser().setSigningKey(secretKey).parseClaimsJws(token)
                    .getBody().getExpiration().before(new Date());
        } catch (ExpiredJwtException e) {
            return true;
        } catch (Exception e) {
            log.error("Error while checking token expiration: {}", e.getMessage());
            return false;
        }

    }

    public static LoginResponseToken createJwt(User user) {
        Claims accesClaims = Jwts.claims();
        accesClaims.put("userId", user.getId());
        accesClaims.put("type", "ACCESS");
        accesClaims.put("role", user.getUserType());

        String accessToken = Jwts.builder()
                .setClaims(accesClaims)
                .setIssuedAt(new Date(System.currentTimeMillis()))
                .setExpiration(new Date(System.currentTimeMillis() + EXPIRE_TIME_MS))
                .signWith(SignatureAlgorithm.HS256, secretKey)
                .compact();

        Claims refreshClaims = Jwts.claims();
        refreshClaims.put("userId", user.getId());
        refreshClaims.put("type", "REFRESH");
        accesClaims.put("role", user.getUserType());
        String refreshToken = Jwts.builder()
                .setClaims(refreshClaims)
                .setIssuedAt(new Date(System.currentTimeMillis()))
                .setExpiration(new Date(System.currentTimeMillis() + 30 * 24 * EXPIRE_TIME_MS))
                .signWith(SignatureAlgorithm.HS256, secretKey)
                .compact();

        return LoginResponseToken.builder()
                .grantType("Bearer")
                .accessToken(accessToken)
                .refreshToken(refreshToken)
                .build();
    }
}
