package happyaging.server.utils;

import happyaging.server.dto.user.LoginResponseToken;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import java.util.Date;
import lombok.extern.slf4j.Slf4j;

@Slf4j
public class JwtUtil {

    public static String getUserEmail(String token, String secretKey) {
        return Jwts.parser().setSigningKey(secretKey).parseClaimsJws(token)
                .getBody().get("userEmail", String.class);
    }

    public static String getTokenType(String token, String secretKey) {
        return Jwts.parser().setSigningKey(secretKey).parseClaimsJws(token)
                .getBody().get("type", String.class);
    }

    public static boolean isExpired(String token, String secretKey) {
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

    public static LoginResponseToken createJwt(String email, String key, long expiredTimeMs) {
        Claims accesClaims = Jwts.claims();
        accesClaims.put("userEmail", email);
        accesClaims.put("type", "ACCESS_TOKEN");

        String accessToken = Jwts.builder()
                .setClaims(accesClaims)
                .setIssuedAt(new Date(System.currentTimeMillis()))
                .setExpiration(new Date(System.currentTimeMillis() + expiredTimeMs))
                .signWith(SignatureAlgorithm.HS256, key)
                .compact();

        Claims refreshClaims = Jwts.claims();
        refreshClaims.put("userEmail", email);
        refreshClaims.put("type", "REFRESH_TOKEN");
        String refreshToken = Jwts.builder()
                .setClaims(refreshClaims)
                .setIssuedAt(new Date(System.currentTimeMillis()))
                .setExpiration(new Date(System.currentTimeMillis() + 30 * 24 * expiredTimeMs))
                .signWith(SignatureAlgorithm.HS256, key)
                .compact();

        return LoginResponseToken.builder()
                .grantType("Bearer")
                .accessToken(accessToken)
                .refreshToken(refreshToken)
                .build();
    }
}
