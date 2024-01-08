package happyaging.server.domain.senior;

import com.fasterxml.jackson.annotation.JsonCreator;
import java.util.stream.Stream;

public enum Relation {
    SELF, FAMILY, CARE_SENIOR, NOTHING;

    @JsonCreator
    public static Relation toRelation(String relation) {
        return Stream.of(Relation.values())
                .filter(value -> value.toString().equals(relation))
                .findFirst()
                .orElse(null);
    }
}
