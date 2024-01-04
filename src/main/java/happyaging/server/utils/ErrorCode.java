package happyaging.server.utils;

import org.springframework.http.HttpStatus;

public interface ErrorCode {
    String getMessage();

    HttpStatus getHttpStatus();
}
