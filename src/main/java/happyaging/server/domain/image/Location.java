package happyaging.server.domain.image;

import com.fasterxml.jackson.annotation.JsonCreator;
import happyaging.server.exception.AppException;
import happyaging.server.exception.errorcode.AppErrorCode;
import java.util.stream.Stream;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.extern.slf4j.Slf4j;

@AllArgsConstructor
@Getter
@Slf4j
public enum Location {
    BATHROOM("화장실/욕실"),
    KITCHEN("주방"),
    ROOM("침실/거실"),
    ENTRANCE("현관/출입구");

    private final String name;

    public static Location toLocation(String location) {
        return Stream.of(Location.values())
                .filter(value -> value.name.equals(location))
                .findFirst()
                .orElseThrow(() -> new AppException(AppErrorCode.INVALID_LOCATION));
    }

    @JsonCreator
    public static Location convertLocation(String location) {
        return Stream.of(Location.values())
                .filter(value -> value.name().equals(location))
                .findFirst()
                .orElse(null);
    }
}
