package happyaging.server.domain.senior;

import com.fasterxml.jackson.annotation.JsonCreator;
import java.util.stream.Stream;

public enum Sex {
    MALE, FEMALE;

    @JsonCreator
    public static Sex toSex(String sex) {
        return Stream.of(Sex.values())
                .filter(value -> value.toString().equals(sex))
                .findFirst()
                .orElse(null);
    }
}
