package happyaging.server.exception;

import lombok.AllArgsConstructor;
import lombok.Getter;
import org.springframework.http.HttpStatus;

@Getter
@AllArgsConstructor
public enum SuccessCode {
    JOIN(HttpStatus.CONFLICT, "회원가입에 성공했습니다.");

    private final HttpStatus httpStatus;
    private final String message;
}
