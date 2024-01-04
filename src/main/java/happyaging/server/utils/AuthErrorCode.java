package happyaging.server.utils;

import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;

@AllArgsConstructor
public enum AuthErrorCode implements ErrorCode {
    EXTERNAL_SERVER(HttpStatus.INTERNAL_SERVER_ERROR, "email을 받아올 수 없습니다."),
    INVALID_ACCESS_TOKEN(HttpStatus.BAD_REQUEST, "잘못된 accessToken 입니다."),
    INVALID_USER(HttpStatus.UNAUTHORIZED, "가입되지 않은 회원입니다."),
    INVALID_LOGIN_METHOD(HttpStatus.CONFLICT, "올바르지 않은 로그인 방식입니다."),
    EMAIL_DUPLICATED(HttpStatus.CONFLICT, "중복된 ID 입니다."),
    TOKEN_EXPIRED(HttpStatus.UNAUTHORIZED, "refresh Token이 만료되었습니다.");

    private final HttpStatus httpStatus;
    private final String message;

    @Override
    public String getMessage() {
        return message;
    }

    @Override
    public HttpStatus getHttpStatus() {
        return httpStatus;
    }
}
