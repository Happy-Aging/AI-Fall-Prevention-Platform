package happyaging.server.exception.errorcode;

import org.springframework.http.HttpStatus;

public interface ErrorCode {
    String getMessage();

    HttpStatus getHttpStatus();
}
