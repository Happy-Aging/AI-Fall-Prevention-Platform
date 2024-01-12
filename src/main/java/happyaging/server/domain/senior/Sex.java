package happyaging.server.domain.senior;

import com.fasterxml.jackson.annotation.JsonCreator;
import java.util.stream.Stream;
import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum Sex {
    MALE("남자", 1.0171),
    FEMALE("여자", 1.0);

    private final String sex;
    private final Double weight;

    @JsonCreator
    public static Sex toSex(String sex) {
        return Stream.of(Sex.values())
                .filter(value -> value.toString().equals(sex))
                .findFirst()
                .orElse(null);
    }
}
