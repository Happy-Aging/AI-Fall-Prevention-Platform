package happyaging.server.exception;

import lombok.AllArgsConstructor;
import lombok.Getter;
import org.springframework.http.HttpStatus;

@AllArgsConstructor
@Getter
public enum ErrorCode {
    EMAIl_DUPLICATED(HttpStatus.CONFLICT, "해당하는 아이디는 이미 가입되어 있습니다."),
    EMAIL_NOT_FOUND(HttpStatus.NOT_FOUND, "해당하는 아이디를 찾을 수 없습니다."),
    INVALID_PASSWORD(HttpStatus.NOT_FOUND, "비밀번호가 틀렸습니다."),
    TOKEN_EXPIRED(HttpStatus.UNAUTHORIZED, "refresh Token이 만료되었습니다.");

    private HttpStatus httpStatus;
    private String message;
}
