package happyaging.server.domain;

import com.fasterxml.jackson.annotation.JsonCreator;
import java.util.stream.Stream;

public enum UserType {
    USER, MANAGER, ADMIN;

    @JsonCreator
    public static UserType toUserType(String userType) {
        return Stream.of(UserType.values())
                .filter(value -> value.toString().equals(userType))
                .findFirst()
                .orElse(null);
    }
}