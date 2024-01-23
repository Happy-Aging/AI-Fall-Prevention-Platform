package happyaging.server.domain.question;

import com.fasterxml.jackson.annotation.JsonCreator;
import java.util.Arrays;

public enum ResponseType {
    SINGLE,
    MULTI,
    SUBJECTIVE_INT,
    SUBJECTIVE_DOUBLE,
    SUBJECTIVE_STRING;

    @JsonCreator
    public ResponseType toResponseType(String responseType) {
        return Arrays.stream(ResponseType.values())
                .filter(value -> value.name().equals(responseType))
                .findFirst()
                .orElse(null);
    }
}
