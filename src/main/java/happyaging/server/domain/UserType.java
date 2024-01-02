package happyaging.server.domain;

import com.fasterxml.jackson.annotation.JsonCreator;
import java.util.stream.Stream;

public enum UserType {
    USER, MANAGER;

    @JsonCreator
    public static UserType toUserType(String userType) {
        return Stream.of(UserType.values())
                .filter(type -> type.toString().equals(userType))
                .findFirst()
                .orElse(null);
    }
}